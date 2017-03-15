package com.small.test.app.historydata.historydata.data.source;

import com.small.test.app.historydata.historydata.data.CategoryVO;

import java.util.List;


public interface HistoryDataSource
{
    interface GetHistoryDataCallBack
    {
        void onHistoryDataLoaded(List<CategoryVO> categoryDOs);
        
        void onError(Exception exception);
    }
    
    /**
     * 得到安装位置数据
     * @param classCode
     * @param pageNo
     * @param recordNum
     * @param isRefresh
     */
    public void getSystemLocationDatas(int classCode, int pageNo, int recordNum, boolean isRefresh,
                                       GetHistoryDataCallBack getHistoryDataCallBack);
    
    /**
     * 得到一级系统二级系统总成数据
     * @param parentCode
     * @param classCode
     * @param pageNo
     * @param recordNum
     */
    public void getSysOneAndSysTwoAndZongChengDatas(String parentCode, int classCode, int pageNo, int recordNum,
                                                    boolean isRefresh, GetHistoryDataCallBack getHistoryDataCallBack);
    
}
