package com.small.test.app.main.login.data.source;

import java.io.Serializable;

public class LoginVO implements Serializable
{
    private String name, phoneNumber, areaId, areaName, jobId;
    
    public LoginVO()
    {
    }
    
    public LoginVO(String name, String phoneNumber, String areaId, String areaName, String jobId)
    {
        super();
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.areaId = areaId;
        this.areaName = areaName;
        this.jobId = jobId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    public String getAreaId()
    {
        return areaId;
    }
    
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }
    
    public String getAreaName()
    {
        return areaName;
    }
    
    public void setAreaName(String areaName)
    {
        this.areaName = areaName;
    }
    
    public String getJobId()
    {
        return jobId;
    }
    
    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }
}
