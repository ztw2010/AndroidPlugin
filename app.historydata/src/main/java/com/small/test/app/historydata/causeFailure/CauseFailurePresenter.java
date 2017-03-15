package com.small.test.app.historydata.causeFailure;

import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;
import com.small.test.app.historydata.causeFailure.data.source.CauseFailureRepository;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;

public class CauseFailurePresenter extends MvpPresenter<CauseFailureRepository, CauseFailureContract.View>
    implements CauseFailureContract.Presenter
{
    private CauseFailureUseCase causeFailureUseCase;
    
    @Override
    protected void initUsecase()
    {
        causeFailureUseCase = new CauseFailureUseCase(mModel);
    }
    
    @Override
    public void getCauseFailureData(final String manualId, final String assemblyId, final String phenomenon,
        final int pageNum, final int recordNum, final boolean isRefresh)
    {
        iView.showLoading();
        mUseCaseHandler.execute(causeFailureUseCase,
            new CauseFailureUseCase.RequestValues(manualId, assemblyId, phenomenon, pageNum, recordNum, isRefresh),
            new UseCase.UseCaseCallback<CauseFailureUseCase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(CauseFailureUseCase.ResponseValue response)
                {
                    iView.hideLoading();
                    List<CauseFailureVO> causeFailureVOs = response.getFaultListVOs();
                    if (causeFailureVOs == null || causeFailureVOs.isEmpty())
                    {
                        iView.onCauseFailureNoDatas();
                    }
                    else
                    {
                        iView.onGetCauseFailureList(causeFailureVOs);
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
