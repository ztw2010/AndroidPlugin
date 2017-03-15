package com.small.test.appstub.VO;

import java.io.Serializable;
import java.util.List;

public class ResultModel<T> implements Serializable
{
    
    public final static int RESULTSUCCESS = 1, RESULTFAILED = 2;
    
    /**
     * <code>序列ID</code>.
    */
    private static final long serialVersionUID = 1L;
    
    /**
     * 总的记录数
     */
    private int size;
    
    /**
     * 服务标识 1:成功  2:失败
     */
    private int servFlag;
    
    /**
     * 错误信息
     */
    private String errorMsg;
    
    /**
     * 查询成功时返回的数据集合
     */
    private List<T> data;
    
    public int getSize()
    {
        return size;
    }
    
    public void setSize(int size)
    {
        this.size = size;
    }
    
    public int getServFlag()
    {
        return servFlag;
    }
    
    public void setServFlag(int servFlag)
    {
        this.servFlag = servFlag;
    }
    
    public String getErrorMsg()
    {
        return errorMsg;
    }
    
    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    public List<T> getData()
    {
        return data;
    }
    
    public void setData(List<T> datas)
    {
        this.data = datas;
    }
}