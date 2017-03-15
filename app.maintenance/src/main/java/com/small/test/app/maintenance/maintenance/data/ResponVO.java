package com.small.test.app.maintenance.maintenance.data;

import java.io.Serializable;

public class ResponVO implements Serializable
{
    private static final long serialVersionUID = 2732195866914184558L;
    
    /**
     * 责任编号
     */
    private String responTypeId;
    
    /**
     * 责任名称
     */
    private String responTypeName;
    
    public String getResponTypeId()
    {
        return responTypeId;
    }
    
    public void setResponTypeId(String responTypeId)
    {
        this.responTypeId = responTypeId;
    }
    
    public String getResponTypeName()
    {
        return responTypeName;
    }
    
    public void setResponTypeName(String responTypeName)
    {
        this.responTypeName = responTypeName;
    }
    
    public ResponVO(String responTypeId, String responTypeName)
    {
        super();
        this.responTypeId = responTypeId;
        this.responTypeName = responTypeName;
    }
    
    public ResponVO()
    {
    }
}
