package com.small.test.lib.video.record;

public class VideoFile
{
    private String filePath;
    
    public VideoFile(String filePath)
    {
        this.filePath = filePath;
    }
    
    public String getFullPath()
    {
        return filePath;
    }
}
