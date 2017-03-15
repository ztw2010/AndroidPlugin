package com.small.test.lib.video.record;


import android.media.MediaRecorder;
import android.os.Parcel;
import android.os.Parcelable;

import com.small.test.lib.video.record.PredefinedCaptureConfigurations.CaptureQuality;
import com.small.test.lib.video.record.PredefinedCaptureConfigurations.CaptureResolution;

public class CaptureConfiguration implements Parcelable
{
    private static final int MBYTE_TO_BYTE = 1024 * 1024;
    
    private static final int MSEC_TO_SEC = 1000;
    
    public static final int NO_DURATION_LIMIT = -1;
    
    public static final int NO_FILESIZE_LIMIT = -1;
    
    private int mVideoWidth = PredefinedCaptureConfigurations.WIDTH_720P;
    
    private int mVideoHeight = PredefinedCaptureConfigurations.HEIGHT_720P;
    
    private int mBitrate = PredefinedCaptureConfigurations.BITRATE_HQ_720P;
    
    private int mMaxDurationMs = NO_DURATION_LIMIT;
    
    private int mMaxFilesizeBytes = NO_FILESIZE_LIMIT;
    
    private boolean mShowTimer = false;
    
    private int OUTPUT_FORMAT = MediaRecorder.OutputFormat.MPEG_4;
    
    private int AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT;
    
    private int AUDIO_ENCODER = MediaRecorder.AudioEncoder.AAC;
    
    private int VIDEO_SOURCE = MediaRecorder.VideoSource.CAMERA;
    
    private int VIDEO_ENCODER = MediaRecorder.VideoEncoder.H264;

    public CaptureConfiguration()
    {
    }
    
    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality)
    {
        mVideoWidth = resolution.width;
        mVideoHeight = resolution.height;
        mBitrate = resolution.getBitrate(quality);
    }
    
    /**
     * 
     * @param resolution 品质(1080p,720p,480p)
     * @param quality 质量(high,medium,low)
     * @param maxDurationSecs 录制视频的最大时长,单位s
     * @param maxFilesizeMb 视频的最大大小,单位MB
     * @param showTimer 是否显示录制的时间
     */
    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
        int maxFilesizeMb, boolean showTimer)
    {
        this(resolution, quality, maxDurationSecs, maxFilesizeMb);
        mShowTimer = showTimer;
    }
    
    public CaptureConfiguration(CaptureResolution resolution, CaptureQuality quality, int maxDurationSecs,
        int maxFilesizeMb)
    {
        this(resolution, quality);
        mMaxDurationMs = maxDurationSecs * MSEC_TO_SEC;
        mMaxFilesizeBytes = maxFilesizeMb * MBYTE_TO_BYTE;
    }
    
    public CaptureConfiguration(int videoWidth, int videoHeight, int bitrate)
    {
        mVideoWidth = videoWidth;
        mVideoHeight = videoHeight;
        mBitrate = bitrate;
    }
    
    public CaptureConfiguration(int videoWidth, int videoHeight, int bitrate, int maxDurationSecs, int maxFilesizeMb)
    {
        this(videoWidth, videoHeight, bitrate);
        mMaxDurationMs = maxDurationSecs * MSEC_TO_SEC;
        mMaxFilesizeBytes = maxFilesizeMb * MBYTE_TO_BYTE;
    }
    
    /**
     * @return Width of the captured video in pixels
     */
    public int getVideoWidth()
    {
        return mVideoWidth;
    }
    
    /**
     * @return Height of the captured video in pixels
     */
    public int getVideoHeight()
    {
        return mVideoHeight;
    }
    
    /**
     * @return Bitrate of the captured video in bits per second
     */
    public int getVideoBitrate()
    {
        return mBitrate;
    }
    
    /**
     * @return Maximum duration of the captured video in milliseconds
     */
    public int getMaxCaptureDuration()
    {
        return mMaxDurationMs;
    }
    
    /**
     * @return Maximum filesize of the captured video in bytes
     */
    public int getMaxCaptureFileSize()
    {
        return mMaxFilesizeBytes;
    }
    
    /**
     * @return If timer must be displayed during video capture
     */
    public boolean getShowTimer()
    {
        return mShowTimer;
    }
    
    public int getOutputFormat()
    {
        return OUTPUT_FORMAT;
    }
    
    public int getAudioSource()
    {
        return AUDIO_SOURCE;
    }
    
    public int getAudioEncoder()
    {
        return AUDIO_ENCODER;
    }
    
    public int getVideoSource()
    {
        return VIDEO_SOURCE;
    }
    
    public int getVideoEncoder()
    {
        return VIDEO_ENCODER;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }
    
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeInt(mVideoWidth);
        dest.writeInt(mVideoHeight);
        dest.writeInt(mBitrate);
        dest.writeInt(mMaxDurationMs);
        dest.writeInt(mMaxFilesizeBytes);
        dest.writeByte((byte)(mShowTimer ? 1 : 0));
        
        dest.writeInt(OUTPUT_FORMAT);
        dest.writeInt(AUDIO_SOURCE);
        dest.writeInt(AUDIO_ENCODER);
        dest.writeInt(VIDEO_SOURCE);
        dest.writeInt(VIDEO_ENCODER);
    }
    
    public static final Creator<CaptureConfiguration> CREATOR =
        new Creator<CaptureConfiguration>()
        {
            @Override
            public CaptureConfiguration createFromParcel(Parcel in)
            {
                return new CaptureConfiguration(in);
            }
            
            @Override
            public CaptureConfiguration[] newArray(int size)
            {
                return new CaptureConfiguration[size];
            }
        };
    
    private CaptureConfiguration(Parcel in)
    {
        mVideoWidth = in.readInt();
        mVideoHeight = in.readInt();
        mBitrate = in.readInt();
        mMaxDurationMs = in.readInt();
        mMaxFilesizeBytes = in.readInt();
        mShowTimer = in.readByte() != 0;
        
        OUTPUT_FORMAT = in.readInt();
        AUDIO_SOURCE = in.readInt();
        AUDIO_ENCODER = in.readInt();
        VIDEO_SOURCE = in.readInt();
        VIDEO_ENCODER = in.readInt();
    }
}
