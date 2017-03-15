package com.small.test.app.historydata.causeFailure.data;

public class CauseFailureVO
{
    
    /**
     * 维修手册编号
     */
    private String manualId;
    
    /**
     * 总成分类代码
     */
    private String assemblyId;
    
    /**
     * 现象
     */
    private String phenomenon;
    
    /**
     * 原因
     */
    private String reason;
    
    public CauseFailureVO()
    {
        
    }
    
    public CauseFailureVO(String manualId, String assemblyId, String phenomenon, String reason)
    {
        super();
        this.manualId = manualId;
        this.assemblyId = assemblyId;
        this.phenomenon = phenomenon;
        this.reason = reason;
    }
    
    public String getManualId()
    {
        return manualId;
    }
    
    public void setManualId(String manualId)
    {
        this.manualId = manualId;
    }
    
    public String getReason()
    {
        return reason;
    }
    
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    
    public String getAssemblyId()
    {
        return assemblyId;
    }
    
    public void setAssemblyId(String assemblyId)
    {
        this.assemblyId = assemblyId;
    }
    
    public String getPhenomenon()
    {
        return phenomenon;
    }
    
    public void setPhenomenon(String phenomenon)
    {
        this.phenomenon = phenomenon;
    }
    
}
