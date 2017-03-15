package com.small.test.app.historydata.treatmentmeasures;


import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;
import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;

public interface TreatmentMeasuresContract
{
    interface View extends IBaseView
    {
        public void onGetTreatmentMeasures(MeaDetailVO meaDetailVO);
        
        public void onGetTreatmentMeasuresNoDatas();
    }
    
    interface Presenter extends IBasePresenter
    {
        public void getTreatmentMeasures(final String manualId, String faltReason);
    }
}
