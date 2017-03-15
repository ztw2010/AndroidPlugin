package com.small.test.app.historydata.treatmentmeasures;

import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;
import com.small.test.app.historydata.treatmentmeasures.data.source.TreatmentMeasuresDatasource;
import com.small.test.app.historydata.treatmentmeasures.data.source.TreatmentMeasuresRepository;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;


public class TreatmentMeasuresUseCase extends UseCase<TreatmentMeasuresUseCase.RequestValues, TreatmentMeasuresUseCase.ResponseValue>
{
    private final TreatmentMeasuresRepository treatmentMeasuresRepository;
    
    public TreatmentMeasuresUseCase(TreatmentMeasuresRepository treatmentMeasuresRepository)
    {
        this.treatmentMeasuresRepository = treatmentMeasuresRepository;
    }
    
    public static final class RequestValues implements UseCase.RequestValues
    {
        private final String manualId, faltReason;
        
        public RequestValues(String manualId, String faltReason)
        {
            this.manualId = manualId;
            this.faltReason = faltReason;
        }
        
        public String getManualId()
        {
            return manualId;
        }
        
        public String getFaltReason()
        {
            return faltReason;
        }
    }
    
    public static final class ResponseValue implements UseCase.ResponseValue
    {
        private final List<MeaDetailVO> meaDetailVOs;
        
        public ResponseValue(List<MeaDetailVO> meaDetailVOs)
        {
            this.meaDetailVOs = meaDetailVOs;
        }
        
        public List<MeaDetailVO> getMeaDetailVOs()
        {
            return meaDetailVOs;
        }
    }
    
    @Override
    protected void executeUseCase(RequestValues requestValues)
    {
        treatmentMeasuresRepository.getTreatmentMeasures(requestValues.getManualId(),
            requestValues.getFaltReason(),
            new TreatmentMeasuresDatasource.GetTreatmentMeasuresCallBack()
            {
                
                @Override
                public void onSuspensionLoaded(List<MeaDetailVO> meaDetailVOs)
                {
                    getUseCaseCallback().onSuccess(new ResponseValue(meaDetailVOs));
                }
                
                @Override
                public void onError(Exception exception)
                {
                    getUseCaseCallback().onError(exception);
                }
            });
    }
}
