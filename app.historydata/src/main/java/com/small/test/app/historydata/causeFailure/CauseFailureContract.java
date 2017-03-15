package com.small.test.app.historydata.causeFailure;

import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;
import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;

import java.util.List;


public interface CauseFailureContract
{
    interface View extends IBaseView
    {
        public void onGetCauseFailureList(List<CauseFailureVO> causeFailureVOs);
        
        public void onCauseFailureNoDatas();
    }
    
    interface Presenter extends IBasePresenter
    {
        public void getCauseFailureData(String manualId, String assemblyId, String phenomenon, int pageNum,
                                        int recordNum, boolean isRefresh);
    }
}
