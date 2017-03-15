package com.small.test.app.historydata.suspension;

import com.small.test.app.historydata.suspension.data.FaultListVO;
import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;

import java.util.List;


public interface SuspensionContract
{
    
    interface View extends IBaseView
    {
        public void onGetSuspensionList(List<FaultListVO> faultListVOs);
        
        public void onSuspensionNoDatas();
    }
    
    interface Presenter extends IBasePresenter
    {
        public void getSuspensionList(final String zongChengCode, final String className, final int pageNum,
                                      final int recordNum, final boolean isRefresh);
    }
}
