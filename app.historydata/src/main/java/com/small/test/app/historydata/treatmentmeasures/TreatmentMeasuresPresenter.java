package com.small.test.app.historydata.treatmentmeasures;

import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;
import com.small.test.app.historydata.treatmentmeasures.data.source.TreatmentMeasuresRepository;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;

public class TreatmentMeasuresPresenter
    extends MvpPresenter<TreatmentMeasuresRepository, TreatmentMeasuresContract.View>
    implements TreatmentMeasuresContract.Presenter
{
    
    private TreatmentMeasuresUseCase treatmentMeasuresUseCase;
    
    @Override
    protected void initUsecase()
    {
        treatmentMeasuresUseCase = new TreatmentMeasuresUseCase(mModel);
    }
    
    @Override
    public void getTreatmentMeasures(String manualId, String faltReason)
    {
        iView.showLoading();
        mUseCaseHandler.execute(treatmentMeasuresUseCase,
            new TreatmentMeasuresUseCase.RequestValues(manualId, faltReason),
            new UseCase.UseCaseCallback<TreatmentMeasuresUseCase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(TreatmentMeasuresUseCase.ResponseValue response)
                {
                    iView.hideLoading();
                    List<MeaDetailVO> meaDetailVO = response.getMeaDetailVOs();
                    if (meaDetailVO == null || meaDetailVO.isEmpty())
                    {
                        
                    }
                    else
                    {
                        iView.onGetTreatmentMeasures(meaDetailVO.iterator().next());
                    }
                }
                
                @Override
                public void onError(Exception exception)
                {
                    iView.hideLoading();
                    String errorMsg = ErrorMessageFactory.create(mContext, exception);
                    iView.showTip(errorMsg);
                }
            });
    }
    
}
