package com.small.test.app.historydata.historydata;

import android.view.View;

import com.small.test.app.historydata.R;
import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.app.historydata.historydata.data.source.HistoryDataRepository;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.ErrorMessageFactory;
import com.small.test.appstub.mvp.MvpPresenter;
import com.small.test.appstub.mvp.UseCase;

import java.util.ArrayList;
import java.util.List;

public class HistoryDataPresenter extends MvpPresenter<HistoryDataRepository, HistoryContract.View>
    implements HistoryContract.Presenter
{
    private GetSysLocationDataUseCase getSystemDataUseCase;
    
    private GetSysOneAndSysTwoAndZongChengDataUsecase getSysOneAndSysTwoAndZongChengDataUsecase;
    
    private int btnIndex = 0;
    
    private TwoColumnItem sysInstallTwoColumnItem, sysFirstTwoColumnItem, sysSecondTwoColumnItem;
    
    @Override
    protected void initUsecase()
    {
        getSystemDataUseCase = new GetSysLocationDataUseCase(mModel);
        getSysOneAndSysTwoAndZongChengDataUsecase = new GetSysOneAndSysTwoAndZongChengDataUsecase(mModel);
    }
    
    @Override
    public void getSystemLocationDatas(final int btnIndex, int classCode, int pageNo, int recordNum,
        final boolean isRefresh)
    {
        iView.showLoading();
        mUseCaseHandler.execute(getSystemDataUseCase,
            new GetSysLocationDataUseCase.RequestValues(classCode, pageNo, recordNum, isRefresh),
            new UseCase.UseCaseCallback<GetSysLocationDataUseCase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(GetSysLocationDataUseCase.ResponseValue response)
                {
                    iView.hideLoading();
                    List<CategoryVO> categoryDOs = response.getCategoryDOs();
                    if (categoryDOs == null || categoryDOs.isEmpty())
                    {
                        iView.onCategoryNoData();
                    }
                    else
                    {
                        iView.onAddSystemDataView(getSystemDataView(categoryDOs));
                    }
                    iView.onTabSelected(btnIndex);
                }
                
                @Override
                public void onError(Exception exception)
                {
                    iView.hideLoading();
                    String errorMsg = ErrorMessageFactory.create(mContext, exception);
                    iView.showTip(errorMsg);
                }
            });
        this.btnIndex = btnIndex;
    }
    
    @Override
    public void getSysOneAndSysTwoAndZongChengDatas(final int btnIndex, int classCode, int pageNo, int recordNum,
        final boolean isRefresh)
    {
        String parentCode = "";
        switch (btnIndex)
        {
            case C.HistoryData.C_INDEX_SYSFIRST:
                if (sysInstallTwoColumnItem == null)
                {
                    iView.showTip(mContext.getString(R.string.please_chose_loaction_info));
                    return;
                }
                else
                {
                    parentCode = sysInstallTwoColumnItem.getSelectedId();
                }
                break;
            case C.HistoryData.C_INDEX_SYSSECOND:
                if ((sysInstallTwoColumnItem == null && sysFirstTwoColumnItem == null)
                    || (sysInstallTwoColumnItem == null && sysFirstTwoColumnItem != null))
                {
                    iView.showTip(mContext.getString(R.string.please_chose_loaction_info));
                    return;
                }
                else if (sysInstallTwoColumnItem != null && sysFirstTwoColumnItem == null)
                {
                    iView.showTip(mContext.getString(R.string.please_chose_sys_one_info));
                    return;
                }
                else
                {
                    parentCode = sysFirstTwoColumnItem.getSelectedId();
                }
                break;
            case C.HistoryData.C_INDEX_ZONGCHENG_REFRESH:
            case C.HistoryData.C_INDEX_ZONGCHENG:
                parentCode = sysSecondTwoColumnItem.getSelectedId();
                break;
            default:
                break;
        }
        this.btnIndex = btnIndex;
        iView.showLoading();
        mUseCaseHandler.execute(getSysOneAndSysTwoAndZongChengDataUsecase,
            new GetSysOneAndSysTwoAndZongChengDataUsecase.RequestValues(parentCode, classCode, pageNo, recordNum,
                isRefresh),
            new UseCase.UseCaseCallback<GetSysOneAndSysTwoAndZongChengDataUsecase.ResponseValue>()
            {
                
                @Override
                public void onSuccess(GetSysOneAndSysTwoAndZongChengDataUsecase.ResponseValue response)
                {
                    iView.hideLoading();
                    List<CategoryVO> categoryDOs = response.getCategoryDOs();
                    if (categoryDOs == null || categoryDOs.isEmpty())
                    {
                        if (btnIndex == C.HistoryData.C_INDEX_ZONGCHENG
                            || btnIndex == C.HistoryData.C_INDEX_ZONGCHENG_REFRESH)
                        {
                            iView.onZongChengNoData();
                        }
                        else if (btnIndex == C.HistoryData.C_INDEX_SYSSECOND
                            || btnIndex == C.HistoryData.C_INDEX_SYSFIRST)
                        {
                            iView.onCategoryNoData();
                        }
                    }
                    else
                    {
                        if (btnIndex == C.HistoryData.C_INDEX_ZONGCHENG
                            || btnIndex == C.HistoryData.C_INDEX_ZONGCHENG_REFRESH)
                        {
                            iView.onZongChengDataLoaded(categoryDOs);
                        }
                        else if (btnIndex == C.HistoryData.C_INDEX_SYSSECOND
                            || btnIndex == C.HistoryData.C_INDEX_SYSFIRST)
                        {
                            iView.onAddSystemDataView(getSystemDataView(categoryDOs));
                        }
                    }
                    iView.onTabSelected(btnIndex);
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
    
    private List<View> getSystemDataView(List<CategoryVO> categoryVOs)
    {
        List<View> views = new ArrayList<View>();
        int totalSize = categoryVOs.size();
        int k = 0; //行数
        if (totalSize % 2 == 0)
        {
            k = totalSize / 2;
        }
        else
        {
            k = totalSize / 2 + 1;
        }
        for (int m = 0; m < k; m++)
        {
            TwoColumnItem twoColumnItem = new TwoColumnItem(mContext, this);
            CategoryVO categoryDOFirst = categoryVOs.get(m * 2);
            CategoryVO categoryDOSecond = null;
            if (m * 2 + 1 < totalSize)
            {
                categoryDOSecond = categoryVOs.get(m * 2 + 1);
            }
            twoColumnItem.initWithData(categoryDOFirst, categoryDOSecond);
            switch (btnIndex)
            {
                case C.HistoryData.C_INDEX_SYSTYPE:
                    if (sysInstallTwoColumnItem != null)
                    {
                        if (sysInstallTwoColumnItem.getSelectedId().equals(categoryDOFirst.getComponentCode()))
                        {
                            twoColumnItem.setFirstItemSelected();
                            sysInstallTwoColumnItem.resetSelected();
                            sysInstallTwoColumnItem = twoColumnItem;
                        }
                        else if (categoryDOSecond != null
                            && sysInstallTwoColumnItem.getSelectedId().equals(categoryDOSecond.getComponentCode()))
                        {
                            twoColumnItem.setSecondItemSelected();
                            sysInstallTwoColumnItem.resetSelected();
                            sysInstallTwoColumnItem = twoColumnItem;
                        }
                    }
                    break;
                case C.HistoryData.C_INDEX_SYSFIRST:
                    if (sysFirstTwoColumnItem != null)
                    {
                        if (sysFirstTwoColumnItem.getSelectedId().equals(categoryDOFirst.getComponentCode()))
                        {
                            twoColumnItem.setFirstItemSelected();
                            sysFirstTwoColumnItem.resetSelected();
                            sysFirstTwoColumnItem = twoColumnItem;
                        }
                        else if (categoryDOSecond != null
                            && sysFirstTwoColumnItem.getSelectedId().equals(categoryDOSecond.getComponentCode()))
                        {
                            twoColumnItem.setSecondItemSelected();
                            sysFirstTwoColumnItem.resetSelected();
                            sysFirstTwoColumnItem = twoColumnItem;
                        }
                    }
                    break;
                case C.HistoryData.C_INDEX_SYSSECOND:
                    if (sysSecondTwoColumnItem != null)
                    {
                        if (sysSecondTwoColumnItem.getSelectedId().equals(categoryDOFirst.getComponentCode()))
                        {
                            twoColumnItem.setFirstItemSelected();
                            sysSecondTwoColumnItem.resetSelected();
                            sysSecondTwoColumnItem = twoColumnItem;
                        }
                        else if (categoryDOSecond != null
                            && sysSecondTwoColumnItem.getSelectedId().equals(categoryDOSecond.getComponentCode()))
                        {
                            twoColumnItem.setSecondItemSelected();
                            sysSecondTwoColumnItem.resetSelected();
                            sysSecondTwoColumnItem = twoColumnItem;
                        }
                    }
                    break;
                default:
                    break;
            }
            views.add(twoColumnItem.getView());
        }
        return views;
    }
    
    @Override
    public void selectedItem(TwoColumnItem twoColumnItem)
    {
        String str = twoColumnItem.getSelectedStr();
        String sysLocationBtnText = mContext.getString(R.string.install_location);
        String sysFirstBtnText = mContext.getString(R.string.sys_first);
        String sysSecondBtnText = mContext.getString(R.string.sys_second);
        switch (btnIndex)
        {
            case C.HistoryData.C_INDEX_SYSTYPE:
                sysFirstTwoColumnItem = null;
                sysSecondTwoColumnItem = null;
                sysLocationBtnText = str;
                if (sysInstallTwoColumnItem != null)
                {
                    if (!sysInstallTwoColumnItem.getSelectedId().equals(twoColumnItem.getSelectedId()))
                    {
                        sysInstallTwoColumnItem.resetSelected();
                    }
                    else
                    {
                        sysFirstBtnText = (sysFirstTwoColumnItem != null ? sysFirstTwoColumnItem.getSelectedStr()
                            : mContext.getString(R.string.sys_first));
                        sysSecondBtnText = (sysSecondTwoColumnItem != null ? sysSecondTwoColumnItem.getSelectedStr()
                            : mContext.getString(R.string.sys_second));
                    }
                }
                iView.onSetTabBtnText(sysLocationBtnText, sysFirstBtnText, sysSecondBtnText);
                sysInstallTwoColumnItem = twoColumnItem;
                break;
            case C.HistoryData.C_INDEX_SYSFIRST:
                sysSecondTwoColumnItem = null;
                sysLocationBtnText = (sysInstallTwoColumnItem != null ? sysInstallTwoColumnItem.getSelectedStr()
                    : mContext.getString(R.string.install_location));
                sysFirstBtnText = str;
                if (sysFirstTwoColumnItem != null)
                {
                    if (!sysFirstTwoColumnItem.getSelectedId().equals(twoColumnItem.getSelectedId()))
                    {
                        sysFirstTwoColumnItem.resetSelected();
                    }
                    else
                    {
                        sysSecondBtnText = (sysSecondTwoColumnItem != null ? sysSecondTwoColumnItem.getSelectedStr()
                            : mContext.getString(R.string.sys_second));
                    }
                }
                iView.onSetTabBtnText(sysLocationBtnText, sysFirstBtnText, sysSecondBtnText);
                sysFirstTwoColumnItem = twoColumnItem;
                break;
            case C.HistoryData.C_INDEX_SYSSECOND:
                sysLocationBtnText = (sysInstallTwoColumnItem != null ? sysInstallTwoColumnItem.getSelectedStr()
                    : mContext.getString(R.string.install_location));
                sysFirstBtnText = (sysFirstTwoColumnItem != null ? sysFirstTwoColumnItem.getSelectedStr()
                    : mContext.getString(R.string.sys_first));
                if (sysSecondTwoColumnItem != null)
                {
                    if (!sysSecondTwoColumnItem.getSelectedId().equals(twoColumnItem.getSelectedId()))
                    {
                        sysSecondTwoColumnItem.resetSelected();
                    }
                    sysSecondBtnText = str;
                }
                iView.onSetTabBtnText(sysLocationBtnText, sysFirstBtnText, sysSecondBtnText);
                sysSecondTwoColumnItem = twoColumnItem;
                getSysOneAndSysTwoAndZongChengDatas(C.HistoryData.C_INDEX_ZONGCHENG,
                    C.HistoryData.VALUE_CLASSCODE_3,
                    1,
                    1000,
                    false);
                break;
            default:
                break;
        }
    }
    
    @Override
    public int getBtnIndex()
    {
        return btnIndex;
    }
    
    @Override
    public void destory()
    {
        super.destory();
        sysInstallTwoColumnItem = null;
        sysFirstTwoColumnItem = null;
        sysSecondTwoColumnItem = null;
        btnIndex = 0;
    }
}
