package com.small.test.lib.video.record;

import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaRecorder.OnInfoListener;
import android.view.Surface;
import android.view.SurfaceHolder;

import com.small.test.appstub.log.L;

import java.io.IOException;

public class VideoRecorder implements OnInfoListener, CapturePreviewInterface
{
    private CameraWrapper mCameraWrapper;
    
    private final Surface mPreviewSurface;
    
    private CapturePreview mVideoCapturePreview;
    
    private final CaptureConfiguration mCaptureConfiguration;
    
    private final VideoFile mVideoFile;
    
    private MediaRecorder mRecorder;
    
    private boolean mRecording = false;
    
    private final VideoRecorderInterface mRecorderInterface;
    
    public VideoRecorder(VideoRecorderInterface recorderInterface, CaptureConfiguration captureConfiguration,
        VideoFile videoFile, CameraWrapper cameraWrapper, SurfaceHolder previewHolder)
    {
        mCaptureConfiguration = captureConfiguration;
        mRecorderInterface = recorderInterface;
        mVideoFile = videoFile;
        mCameraWrapper = cameraWrapper;
        mPreviewSurface = previewHolder.getSurface();
        initializeCameraAndPreview(previewHolder);
    }
    
    protected void initializeCameraAndPreview(SurfaceHolder previewHolder)
    {
        try
        {
            mCameraWrapper.openCamera();
        }
        catch (final OpenCameraException e)
        {
            e.printStackTrace();
            mRecorderInterface.onRecordingFailed(e.getMessage());
            return;
        }
        
        mVideoCapturePreview = new CapturePreview(this, mCameraWrapper, previewHolder);
    }
    
    public void toggleRecording()
        throws AlreadyUsedException
    {
        if (mCameraWrapper == null)
        {
            throw new AlreadyUsedException();
        }
        
        if (isRecording())
        {
            stopRecording(null);
        }
        else
        {
            startRecording();
        }
    }
    
    protected void startRecording()
    {
        mRecording = false;
        
        if (!initRecorder())
            return;
        if (!prepareRecorder())
            return;
        if (!startRecorder())
            return;
        
        mRecording = true;
        mRecorderInterface.onRecordingStarted();
        L.i("Successfully started recording - outputfile: " + mVideoFile.getFullPath());
    }
    
    public void stopRecording(String message)
    {
        if (!isRecording())
            return;
        
        try
        {
            getMediaRecorder().stop();
            mRecorderInterface.onRecordingSuccess();
            L.i("Successfully stopped recording - outputfile: " + mVideoFile.getFullPath());
        }
        catch (final RuntimeException e)
        {
            L.e("Failed to stop recording");
        }
        mRecording = false;
        mRecorderInterface.onRecordingStopped(message);
    }
    
    private boolean initRecorder()
    {
        try
        {
            mCameraWrapper.prepareCameraForRecording();
        }
        catch (final PrepareCameraException e)
        {
            mRecorderInterface.onRecordingFailed("Unable to record video");
            L.e("Failed to initialize recorder - ", e);
            return false;
        }
        
        setMediaRecorder(new MediaRecorder());
        configureMediaRecorder(getMediaRecorder(), mCameraWrapper.getCamera());

        L.i("MediaRecorder successfully initialized");
        return true;
    }
    
    @SuppressWarnings("deprecation")
    protected void configureMediaRecorder(final MediaRecorder recorder, android.hardware.Camera camera)
        throws IllegalStateException, IllegalArgumentException
    {
        recorder.setCamera(camera);
        recorder.setAudioSource(mCaptureConfiguration.getAudioSource());
        recorder.setVideoSource(mCaptureConfiguration.getVideoSource());
        
        CamcorderProfile baseProfile = mCameraWrapper.getBaseRecordingProfile();
        baseProfile.fileFormat = mCaptureConfiguration.getOutputFormat();
        
        RecordingSize size = mCameraWrapper.getSupportedRecordingSize(mCaptureConfiguration.getVideoWidth(),
            mCaptureConfiguration.getVideoHeight());
        baseProfile.videoFrameWidth = size.width;
        baseProfile.videoFrameHeight = size.height;
        baseProfile.videoBitRate = mCaptureConfiguration.getVideoBitrate();
        
        baseProfile.audioCodec = mCaptureConfiguration.getAudioEncoder();
        baseProfile.videoCodec = mCaptureConfiguration.getVideoEncoder();
        
        recorder.setProfile(baseProfile);
        recorder.setMaxDuration(mCaptureConfiguration.getMaxCaptureDuration());
        recorder.setOutputFile(mVideoFile.getFullPath());
        recorder.setOrientationHint(mCameraWrapper.getRotationCorrection());
        
        try
        {
            recorder.setMaxFileSize(mCaptureConfiguration.getMaxCaptureFileSize());
        }
        catch (IllegalArgumentException e)
        {
            L.e(
                "Failed to set max filesize - illegal argument: " + mCaptureConfiguration.getMaxCaptureFileSize(), e);
        }
        catch (RuntimeException e2)
        {
            L.e("Failed to set max filesize - runtime exception", e2);
        }
        recorder.setOnInfoListener(this);
    }
    
    private boolean prepareRecorder()
    {
        try
        {
            getMediaRecorder().prepare();
            L.i("MediaRecorder successfully prepared");
            return true;
        }
        catch (final IllegalStateException e)
        {
            L.e("MediaRecorder preparation failed - ", e);
            return false;
        }
        catch (final IOException e)
        {
            L.e("MediaRecorder preparation failed - ", e);
            return false;
        }
    }
    
    private boolean startRecorder()
    {
        try
        {
            getMediaRecorder().start();
            L.i("MediaRecorder successfully started");
            return true;
        }
        catch (final IllegalStateException e)
        {
            L.e("MediaRecorder start failed - ", e);
            return false;
        }
        catch (final RuntimeException e2)
        {
            L.e("MediaRecorder start failed - ", e2);
            mRecorderInterface.onRecordingFailed("Unable to record video with given settings");
            return false;
        }
    }
    
    protected boolean isRecording()
    {
        return mRecording;
    }
    
    protected void setMediaRecorder(MediaRecorder recorder)
    {
        mRecorder = recorder;
    }
    
    protected MediaRecorder getMediaRecorder()
    {
        return mRecorder;
    }
    
    private void releaseRecorderResources()
    {
        MediaRecorder recorder = getMediaRecorder();
        if (recorder != null)
        {
            recorder.release();
            setMediaRecorder(null);
        }
    }
    
    public void releaseAllResources()
    {
        if (mVideoCapturePreview != null)
        {
            mVideoCapturePreview.releasePreviewResources();
        }
        if (mCameraWrapper != null)
        {
            mCameraWrapper.releaseCamera();
            mCameraWrapper = null;
        }
        releaseRecorderResources();
        L.i("Released all resources");
    }
    
    @Override
    public void onCapturePreviewFailed()
    {
        mRecorderInterface.onRecordingFailed("不能预览");
    }
    
    @Override
    public void onInfo(MediaRecorder mr, int what, int extra)
    {
        switch (what)
        {
            case MediaRecorder.MEDIA_RECORDER_INFO_UNKNOWN:
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED://时间达到最大值
                L.i("MediaRecorder max duration reached");
                stopRecording("时长已达到最大");
                break;
            case MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED://视频大小达到最大
                L.i("MediaRecorder max filesize reached");
                stopRecording("容量已达到最大");
                break;
            default:
                break;
        }
    }
}
