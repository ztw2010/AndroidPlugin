package com.small.test.lib.video.record;

public interface RecordingButtonInterface
{
    
    public abstract void onRecordButtonClicked();
    
    /**
     * 点击√号
     */
    public abstract void onAcceptButtonClicked();
    
    /**
     * 点击X号
     */
    public abstract void onDeclineButtonClicked();
    
}
