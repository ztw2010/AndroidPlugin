package com.small.test.app.maintenance.maintenance.data;

import android.text.TextUtils;

import java.io.Serializable;

public class CategoryVO implements Serializable
{
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 部件代码
     */
    private String componentCode;
    
    /**
     * 部件名称
     */
    private String componentName;
    
    /**
     * 总成code
     */
    private String assemblyCode;
    
    /**
     * 总成供应商
     */
    private String suppiler;
    
    public CategoryVO()
    {
    }
    
    public String getComponentCode()
    {
        return componentCode;
    }
    
    public void setComponentCode(String componentCode)
    {
        this.componentCode = componentCode;
    }
    
    public String getComponentName()
    {
        return componentName;
    }
    
    public void setComponentName(String componentName)
    {
        this.componentName = componentName;
    }
    
    public void setAssemblyCode(String assemblyCode)
    {
        this.assemblyCode = assemblyCode;
    }
    
    public String getAssemblyCode()
    {
        return assemblyCode;
    }
    
    public void setSuppiler(String suppiler)
    {
        this.suppiler = suppiler;
    }
    
    public String getSuppiler()
    {
        return suppiler;
    }
    
    public CategoryVO(String componentCode, String componentName, String assemblyCode, String suppiler)
    {
        super();
        this.componentCode = componentCode;
        this.componentName = componentName;
        this.assemblyCode = assemblyCode;
        this.suppiler = suppiler;
    }
    
    @Override
    public boolean equals(Object o)
    {
        try
        {
            CategoryVO other = (CategoryVO)o;
            if (!TextUtils.isEmpty(this.componentCode) && !TextUtils.isEmpty(other.componentCode))
            {
                return this.componentCode.equalsIgnoreCase(other.componentCode);
            }
            else
            {
                return false;
            }
        }
        catch (ClassCastException e)
        {
        }
        return super.equals(o);
    }
    
}
