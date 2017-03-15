package com.small.test.app.maintenance.maintenance.data;

/**
 * 
 * 保存或提交附件至本地的VO
 *
 * <p>detailed comment
 * @author ztw 2016年9月6日
 * @see
 * @since 1.0
 */
public class EnclosureVO
{
    /**
     * 附件的编号
     */
    private String encloNum;
    
    /**
     * 附件的类型(jpg,txt,mp4)
     */
    private String encloType;
    
    /**
     * 附件的md5(附件为文字时:文字的value的md5,附件为图片或视频时:图片或视频的md5)
     */
    private String md5;
    
    /**
     * 附件内容(附件为文字:对应文字的内容,附件为图片和视频:图片和视频对应的路径)
     */
    private String encloContent;
    
    public String getEncloNum()
    {
        return encloNum;
    }
    
    public void setEncloNum(String encloNum)
    {
        this.encloNum = encloNum;
    }
    
    public String getEncloType()
    {
        return encloType;
    }
    
    public void setEncloType(String encloType)
    {
        this.encloType = encloType;
    }
    
    public String getMd5()
    {
        return md5;
    }
    
    public void setMd5(String md5)
    {
        this.md5 = md5;
    }
    
    public String getEncloContent()
    {
        return encloContent;
    }
    
    public void setEncloContent(String encloContent)
    {
        this.encloContent = encloContent;
    }
    
    public EnclosureVO(String encloNum, String encloType, String md5, String encloContent)
    {
        super();
        this.encloNum = encloNum;
        this.encloType = encloType;
        this.md5 = md5;
        this.encloContent = encloContent;
    }
    
    public EnclosureVO()
    {
        super();
    }
    
    @Override
    public String toString()
    {
        return "EnclosureVO [encloNum=" + encloNum + ", encloType=" + encloType + ", md5=" + md5 + ", encloContent="
            + encloContent + "]";
    }
}
