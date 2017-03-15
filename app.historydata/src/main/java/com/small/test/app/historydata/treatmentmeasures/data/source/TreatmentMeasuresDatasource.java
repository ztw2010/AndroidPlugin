package com.small.test.app.historydata.treatmentmeasures.data.source;

import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;

import java.util.List;


public interface TreatmentMeasuresDatasource
{
    interface GetTreatmentMeasuresCallBack
    {
        void onSuspensionLoaded(List<MeaDetailVO> meaDetailVOs);
        
        void onError(Exception exception);
    }
    
    public void getTreatmentMeasures(String manualId, String faltReason,
                                     GetTreatmentMeasuresCallBack getTreatmentMeasuresCallBack);
}
