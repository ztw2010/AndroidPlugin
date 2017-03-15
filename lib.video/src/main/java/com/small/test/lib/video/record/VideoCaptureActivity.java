package com.small.test.lib.video.record;


import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore.Video.Thumbnails;
import android.view.Display;
import android.view.WindowManager;

import com.small.test.appstub.log.L;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.DefaultMvpContract;
import com.small.test.appstub.mvp.DefaultMvpPresenter;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.lib.video.R;

public class VideoCaptureActivity extends MvpActivity<DefaultMvpPresenter>
    implements DefaultMvpContract.View, RecordingButtonInterface, VideoRecorderInterface
{
    public static final String EXTRA_OUTPUT_FILENAME = "extra_output_filename";
    
    public static final String EXTRA_FILE_PATH = "file_path";
    
    public static final String EXTRA_CAPTURE_CONFIGURATION = "extra_capture_configuration";
    
    public static final String EXTRA_ERROR_MESSAGE = "extra_error_message";
    
    public static final String EXTRA_SHOW_TIMER = "extra_show_timer";
    
    private static final String SAVED_RECORDED_BOOLEAN = "saved_recorded_boolean";
    
    protected static final String SAVED_OUTPUT_FILENAME = "saved_output_filename";
    
    private boolean mVideoRecorded = false;
    
    private VideoFile mVideoFile = null;
    
    private CaptureConfiguration mCaptureConfiguration;
    
    private VideoCaptureView mVideoCaptureView;
    
    private VideoRecorder mVideoRecorder;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_videocapture;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initializeCaptureConfiguration(savedInstanceState);
        mVideoCaptureView = (VideoCaptureView)findViewById(R.id.videocapture_videocaptureview_vcv);
        if (mVideoCaptureView == null)
            return;
        initializeRecordingUI();
    }

    @Override
    protected void initTitle() {

    }

    private void initializeCaptureConfiguration(final Bundle savedInstanceState)
    {
        mCaptureConfiguration = generateCaptureConfiguration();
        mVideoRecorded = generateVideoRecorded(savedInstanceState);
        mVideoFile = generateOutputFile(savedInstanceState);
    }
    
    private void initializeRecordingUI()
    {
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        mVideoRecorder = new VideoRecorder(this, mCaptureConfiguration, mVideoFile,
            new CameraWrapper(new NativeCamera(), display.getRotation()), mVideoCaptureView.getPreviewSurfaceHolder());
        mVideoCaptureView.setRecordingButtonInterface(this);
        boolean showTimer = this.getIntent().getBooleanExtra(EXTRA_SHOW_TIMER, false);
        mVideoCaptureView.showTimer(showTimer);
        if (mVideoRecorded)
        {
            mVideoCaptureView.updateUIRecordingFinished(getVideoThumbnail());
        }
        else
        {
            mVideoCaptureView.updateUINotRecording();
        }
        mVideoCaptureView.showTimer(mCaptureConfiguration.getShowTimer());
    }
    
    @Override
    protected void onPause()
    {
        if (mVideoRecorder != null)
        {
            mVideoRecorder.stopRecording(null);
        }
        releaseAllResources();
        super.onPause();
    }
    
    @Override
    public void onBackPressed()
    {
        finishCancelled();
    }
    
    @Override
    public void onRecordButtonClicked()
    {
        try
        {
            mVideoRecorder.toggleRecording();
        }
        catch (AlreadyUsedException e)
        {
            L.e("Cannot toggle recording after cleaning up all resources");
        }
    }
    
    @Override
    public void onAcceptButtonClicked()
    {
        finishCompleted();
    }
    
    @Override
    public void onDeclineButtonClicked()
    {
        finishCancelled();
    }
    
    @Override
    public void onRecordingStarted()
    {
        mVideoCaptureView.updateUIRecordingOngoing();
    }
    
    @Override
    public void onRecordingStopped(String message)
    {
        L.e(" onRecordingStopped msg=" + message);
        if (message != null)
        {
            showTip(message);
        }
        mVideoCaptureView.updateUIRecordingFinished(getVideoThumbnail());
        releaseAllResources();
    }
    
    @Override
    public void onRecordingSuccess()
    {
        mVideoRecorded = true;
    }
    
    @Override
    public void onRecordingFailed(String message)
    {
        finishError(message);
    }
    
    private void finishCompleted()
    {
        L.i(" finishCompleted");
        final Intent result = new Intent();
        result.putExtra(EXTRA_OUTPUT_FILENAME, mVideoFile.getFullPath());
        this.setResult(C.MaintenanceRegistration.C_RESULTCODE_OK, result);
        finish();
    }
    
    private void finishCancelled()
    {
        Intent result = new Intent();
        result.putExtra(EXTRA_OUTPUT_FILENAME, mVideoFile.getFullPath());
        setResult(C.MaintenanceRegistration.C_RESULTCODE_CANCLE);
        finish();
    }
    
    private void finishError(final String message)
    {
        L.e(" finishError msg=" + message);
        showTip("Can't capture video: " + message);
        final Intent result = new Intent();
        result.putExtra(EXTRA_ERROR_MESSAGE, message);
        this.setResult(C.MaintenanceRegistration.C_RESULTCODE_CANCLE, result);
        finish();
    }
    
    private void releaseAllResources()
    {
        if (mVideoRecorder != null)
        {
            mVideoRecorder.releaseAllResources();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        savedInstanceState.putBoolean(SAVED_RECORDED_BOOLEAN, mVideoRecorded);
        savedInstanceState.putString(SAVED_OUTPUT_FILENAME, mVideoFile.getFullPath());
        savedInstanceState.putString(EXTRA_FILE_PATH, mVideoFile.getFullPath());
        super.onSaveInstanceState(savedInstanceState);
    }
    
    protected CaptureConfiguration generateCaptureConfiguration()
    {
        CaptureConfiguration returnConfiguration = this.getIntent().getParcelableExtra(EXTRA_CAPTURE_CONFIGURATION);
        if (returnConfiguration == null)
        {
            returnConfiguration = new CaptureConfiguration();
            L.d("No captureconfiguration passed - using default configuration");
        }
        return returnConfiguration;
    }
    
    private boolean generateVideoRecorded(final Bundle savedInstanceState)
    {
        if (savedInstanceState == null)
            return false;
        return savedInstanceState.getBoolean(SAVED_RECORDED_BOOLEAN, false);
    }
    
    protected VideoFile generateOutputFile(Bundle savedInstanceState)
    {
        VideoFile returnFile;
        if (savedInstanceState != null)
        {
            returnFile = new VideoFile(savedInstanceState.getString(EXTRA_FILE_PATH));
        }
        else
        {
            returnFile = new VideoFile(this.getIntent().getStringExtra(EXTRA_FILE_PATH));
        }
        return returnFile;
    }
    
    public Bitmap getVideoThumbnail()
    {
        final Bitmap thumbnail =
            ThumbnailUtils.createVideoThumbnail(mVideoFile.getFullPath(), Thumbnails.FULL_SCREEN_KIND);
        if (thumbnail == null)
        {
            L.e("Failed to generate video preview");
        }
        return thumbnail;
    }
    
    @Override
    public IBaseView getBaseView()
    {
        return this;
    }
    
    @Override
    public void goBack()
    {
        super.goBack();
        finishCancelled();
    }

    @Override
    public void showLoading() {
        svProgressHUD.showWithStatus(getString(R.string.loading_data));
    }

    @Override
    public void hideLoading() {
        svProgressHUD.dismissImmediately();
    }

    @Override
    public void showTip(String content) {
        svProgressHUD.showInfoWithStatus(content);
    }
}
