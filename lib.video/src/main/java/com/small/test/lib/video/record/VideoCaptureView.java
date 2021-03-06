package com.small.test.lib.video.record;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.small.test.lib.video.R;

/**
 * 
 * 录制视频界面
 *
 * <p>detailed comment
 * @author ztw 2016年8月19日
 * @see
 * @since 1.0
 */
public class VideoCaptureView extends FrameLayout implements OnClickListener
{
    
    private ImageView mDeclineBtnIv;
    
    private ImageView mAcceptBtnIv;
    
    private ImageView mRecordBtnIv;
    
    private SurfaceView mSurfaceView;
    
    private ImageView mThumbnailIv;
    
    private TextView mTimerTv;
    
    private Handler customHandler = new Handler();
    
    private long startTime = 0L;
    
    private RecordingButtonInterface mRecordingInterface;
    
    private boolean mShowTimer;
    
    public VideoCaptureView(Context context)
    {
        super(context);
        initialize(context);
    }
    
    public VideoCaptureView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }
    
    public VideoCaptureView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }
    
    private void initialize(Context context)
    {
        final View videoCapture = View.inflate(context, R.layout.view_videocapture, this);
        
        mRecordBtnIv = (ImageView)videoCapture.findViewById(R.id.videocapture_recordbtn_iv);
        mAcceptBtnIv = (ImageView)videoCapture.findViewById(R.id.videocapture_acceptbtn_iv);
        mDeclineBtnIv = (ImageView)videoCapture.findViewById(R.id.videocapture_declinebtn_iv);
        
        mRecordBtnIv.setOnClickListener(this);
        mAcceptBtnIv.setOnClickListener(this);
        mDeclineBtnIv.setOnClickListener(this);
        
        mThumbnailIv = (ImageView)videoCapture.findViewById(R.id.videocapture_preview_iv);
        mSurfaceView = (SurfaceView)videoCapture.findViewById(R.id.videocapture_preview_sv);
        
        mTimerTv = (TextView)videoCapture.findViewById(R.id.videocapture_timer_tv);
    }
    
    public void setRecordingButtonInterface(RecordingButtonInterface mBtnInterface)
    {
        this.mRecordingInterface = mBtnInterface;
    }
    
    public SurfaceHolder getPreviewSurfaceHolder()
    {
        return mSurfaceView.getHolder();
    }
    
    public void updateUINotRecording()
    {
        mRecordBtnIv.setSelected(false);
        mRecordBtnIv.setVisibility(View.VISIBLE);
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
    }
    
    private Runnable updateTimerThread = new Runnable()
    {
        public void run()
        {
            long timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            int seconds = (int)(timeInMilliseconds / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            updateRecordingTime(seconds, minutes);
            customHandler.postDelayed(this, 1000);
        }
    };
    
    public void updateUIRecordingOngoing()
    {
        mRecordBtnIv.setSelected(true);
        mRecordBtnIv.setVisibility(View.VISIBLE);
        mAcceptBtnIv.setVisibility(View.GONE);
        mDeclineBtnIv.setVisibility(View.GONE);
        mThumbnailIv.setVisibility(View.GONE);
        mSurfaceView.setVisibility(View.VISIBLE);
        if (mShowTimer)
        {
            mTimerTv.setVisibility(View.VISIBLE);
            startTime = SystemClock.uptimeMillis();
            updateRecordingTime(0, 0);
            customHandler.postDelayed(updateTimerThread, 1000);
        }
    }
    
    public void updateUIRecordingFinished(Bitmap videoThumbnail)
    {
        mRecordBtnIv.setVisibility(View.INVISIBLE);
        mAcceptBtnIv.setVisibility(View.VISIBLE);
        mDeclineBtnIv.setVisibility(View.VISIBLE);
        mThumbnailIv.setVisibility(View.VISIBLE);
        mSurfaceView.setVisibility(View.GONE);
        final Bitmap thumbnail = videoThumbnail;
        if (thumbnail != null)
        {
            mThumbnailIv.setScaleType(ScaleType.CENTER_CROP);
            mThumbnailIv.setImageBitmap(videoThumbnail);
        }
        customHandler.removeCallbacks(updateTimerThread);
        
    }
    
    @Override
    public void onClick(View v)
    {
        if (mRecordingInterface == null)
            return;
        
        if (v.getId() == mRecordBtnIv.getId())
        {
            mRecordingInterface.onRecordButtonClicked();
        }
        else if (v.getId() == mAcceptBtnIv.getId())
        {
            mRecordingInterface.onAcceptButtonClicked();
        }
        else if (v.getId() == mDeclineBtnIv.getId())
        {
            mRecordingInterface.onDeclineButtonClicked();
        }
        
    }
    
    public void showTimer(boolean showTimer)
    {
        this.mShowTimer = showTimer;
    }
    
    private void updateRecordingTime(int seconds, int minutes)
    {
        mTimerTv.setText(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
    }
}
