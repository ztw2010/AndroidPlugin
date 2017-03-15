package com.small.test.app.historydata.treatmentmeasures.data;

public class MeaDetailVO
{
    
    /**
     * 处理措施
     */
    private String measure;
    
    /**
     * 图片
     */
    private String meaPhoto;
    
    /**
     * 视频
     */
    private String video;
    
    public MeaDetailVO()
    {
    }
    
    public String getMeasure()
    {
        return measure;
    }
    
    public void setMeasure(String measure)
    {
        this.measure = measure;
    }
    
    public String getMeaPhoto()
    {
        return meaPhoto;
    }
    
    public void setMeaPhoto(String meaPhoto)
    {
        this.meaPhoto = meaPhoto;
    }
    
    public String getVideo()
    {
        return video;
    }
    
    public void setVideo(String video)
    {
        this.video = video;
    }
    
    public MeaDetailVO(String measure, String meaPhoto, String video)
    {
        super();
        this.measure = measure;
        this.meaPhoto = meaPhoto;
        this.video = video;
    }
    
}
