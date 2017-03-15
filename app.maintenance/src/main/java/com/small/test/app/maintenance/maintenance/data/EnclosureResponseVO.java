package com.small.test.app.maintenance.maintenance.data;

/**
 * 
 * 申请表接口返回值VO
 *
 * <p>detailed comment
 * @author ztw 2016年9月6日
 * @see
 * @since 1.0
 */
public class EnclosureResponseVO
{
    private String faultId;
    
    public String getFaultId()
    {
        return faultId;
    }
    
    public void setFaultId(String faultId)
    {
        this.faultId = faultId;
    }
    
    public EnclosureResponseVO(String faultId)
    {
        super();
        this.faultId = faultId;
    }
    
    public EnclosureResponseVO()
    {
    }
    
    @Override
    public String toString()
    {
        return "EnclosureResponseVO [faultId=" + faultId + "]";
    }
}
