package com.small.test.app.historydata.suspension.data.source;

import com.small.test.app.historydata.suspension.data.FaultListVO;

import java.util.List;


public interface SuspensionDataSource
{
    interface GetSuspensionListCallBack
    {
        void onSuspensionLoaded(List<FaultListVO> faultListVOs);
        
        void onError(Exception exception);
    }
    
    /**
     * 获取故障现象列表
     * @param zongChengCode
     * @param className
     * @param pageNum
     * @param recordNum
     * @param isRefresh
     * @param getSuspensionListCallBack
     */
    public void getSuspensionList(String zongChengCode, String className, int pageNum, int recordNum, boolean isRefresh,
                                  GetSuspensionListCallBack getSuspensionListCallBack);
    
}
