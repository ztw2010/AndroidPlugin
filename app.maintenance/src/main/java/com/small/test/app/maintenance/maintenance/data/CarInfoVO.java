package com.small.test.app.maintenance.maintenance.data;

import java.io.Serializable;

public class CarInfoVO implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    /**
     * 车型分类编号
     */
    private String modelId;
    
    /**
     * 车辆vin码
     */
    private String vinNumber;
    
    /**
     * 车辆型号
     */
    private String modelName;
    
    /**
     * 所属公司
     */
    private String company;
    
    /**
     * 保修到期
     */
    private Long qualityDate;
    
    public CarInfoVO()
    {
    }
    
    public String getVinNumber()
    {
        return vinNumber;
    }
    
    public void setVinNumber(String vinNumber)
    {
        this.vinNumber = vinNumber;
    }
    
    public String getModelName()
    {
        return modelName;
    }
    
    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }
    
    public String getCompany()
    {
        return company;
    }
    
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    public Long getQualityDate()
    {
        return qualityDate;
    }
    
    public void setQualityDate(Long qualityDate)
    {
        this.qualityDate = qualityDate;
    }
    
    public String getModelId()
    {
        return modelId;
    }
    
    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }
    
    public CarInfoVO(String modelId, String vinNumber, String modelName, String company, Long qualityDate)
    {
        super();
        this.modelId = modelId;
        this.vinNumber = vinNumber;
        this.modelName = modelName;
        this.company = company;
        this.qualityDate = qualityDate;
    }
}
