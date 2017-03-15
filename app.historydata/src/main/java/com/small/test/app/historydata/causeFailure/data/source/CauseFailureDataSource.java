package com.small.test.app.historydata.causeFailure.data.source;

import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;

import java.util.List;


public interface CauseFailureDataSource
{
    interface GetCauseFailureDataCallBack
    {
        void onCauseFailureDataLoaded(List<CauseFailureVO> causeFailureVOs);
        
        void onError(Exception exception);
    }
    
    public void getCauseFailureData(String manualId, String assemblyId, String phenomenon, int pageNum, int recordNum,
                                    boolean isRefresh, GetCauseFailureDataCallBack getCauseFailureDataCallBack);
}
