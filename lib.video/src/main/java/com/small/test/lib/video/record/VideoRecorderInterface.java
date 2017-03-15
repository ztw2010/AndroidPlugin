package com.small.test.lib.video.record;

public interface VideoRecorderInterface
{
    public abstract void onRecordingStopped(String message);
    
    public abstract void onRecordingStarted();
    
    public abstract void onRecordingSuccess();
    
    public abstract void onRecordingFailed(String message);
}
