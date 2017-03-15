package com.small.test.app.maintenance.maintenance.data;

public class ProvinceVO
{
    
    /**
     * 省份编号
     */
    private String provinceId;
    
    /**
     * 省份简称
     */
    private String provinceAbbr;
    
    public ProvinceVO()
    {
    }
    
    public String getProvinceId()
    {
        return provinceId;
    }
    
    public void setProvinceId(String provinceId)
    {
        this.provinceId = provinceId;
    }
    
    public String getProvinceAbbr()
    {
        return provinceAbbr;
    }
    
    public void setProvinceAbbr(String provinceAbbr)
    {
        this.provinceAbbr = provinceAbbr;
    }
    
    public ProvinceVO(String provinceId, String provinceAbbr)
    {
        super();
        this.provinceId = provinceId;
        this.provinceAbbr = provinceAbbr;
    }
    
}
