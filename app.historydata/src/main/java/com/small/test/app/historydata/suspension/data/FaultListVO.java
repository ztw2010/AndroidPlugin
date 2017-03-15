package com.small.test.app.historydata.suspension.data;

public class FaultListVO
{
    
    /**
     * 本条记录的ID
     */
    private String recordId;
    
    /**
     * 维修手册编号
     */
    private String manualId;
    
    /**
     * 现象
     */
    private String phenomenon;
    
    /**
     * 总成分类代码
     */
    private String assemblyId;
    
    public FaultListVO()
    {
    }
    
    public FaultListVO(String manualId, String phenomenon, String assemblyId, String recordId)
    {
        super();
        this.manualId = manualId;
        this.phenomenon = phenomenon;
        this.assemblyId = assemblyId;
        this.recordId = recordId;
    }
    
    public String getManualId()
    {
        return manualId;
    }
    
    public void setManualId(String manualId)
    {
        this.manualId = manualId;
    }
    
    public String getPhenomenon()
    {
        return phenomenon;
    }
    
    public void setPhenomenon(String phenomenon)
    {
        this.phenomenon = phenomenon;
    }
    
    public String getAssemblyId()
    {
        return assemblyId;
    }
    
    public void setAssemblyId(String assemblyId)
    {
        this.assemblyId = assemblyId;
    }
    
    public String getRecordId()
    {
        return recordId;
    }
    
    public void setRecordId(String recordId)
    {
        this.recordId = recordId;
    }
}
