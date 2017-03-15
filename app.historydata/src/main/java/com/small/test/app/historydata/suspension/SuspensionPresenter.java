package com.small.test.app.historydata.suspension;

import com.small.test.app.historydata.suspension.data.FaultListVO;
import com.small.test.app.historydata.suspension.data.source.SuspensionRepository;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;

import java.util.List;

public class SuspensionPresenter extends MvpPresenter<SuspensionRepository, SuspensionContract.View>
    implements SuspensionContract.Presenter
{
    private SuspensionUseCase suspensionUseCase;
    
    @Override
    protected void initUsecase()
    {
        suspensionUseCase = new SuspensionUseCase(mModel);
    }
    
    @Override
    public void getSuspensionList(final String zongChengCode, final String className, final int pageNum,
        final int recordNum, final boolean isRefresh)
    {
        iView.showLoading();
        mUseCaseHandler.execute(suspensionUseCase,
            new SuspensionUseCase.RequestValues(zongChengCode, className, pageNum, recordNum, isRefresh),
            new UseCase.UseCaseCallback<SuspensionUseCase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(SuspensionUseCase.ResponseValue response)
                {
                    iView.hideLoading();
                    List<FaultListVO> faultListVOs = response.getFaultListVOs();
                    if (faultListVOs == null || faultListVOs.isEmpty())
                    {
                        iView.onSuspensionNoDatas();
                    }
                    else
                    {
                        iView.onGetSuspensionList(response.getFaultListVOs());
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
