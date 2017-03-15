package com.small.test.app.main.login.data;

public class UserVO
{
    private String isMemory;
    
    private String password;
    
    private String jobId;
    
    public String getIsMemory()
    {
        return isMemory;
    }
    
    public void setIsMemory(String isMemory)
    {
        this.isMemory = isMemory;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getJobId()
    {
        return jobId;
    }
    
    public void setJobId(String jobId)
    {
        this.jobId = jobId;
    }
    
    public UserVO(String isMemory, String password, String jobId)
    {
        super();
        this.isMemory = isMemory;
        this.password = password;
        this.jobId = jobId;
    }
    
    public UserVO()
    {
    }
}
