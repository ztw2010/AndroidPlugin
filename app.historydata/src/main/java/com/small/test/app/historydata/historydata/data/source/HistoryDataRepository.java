package com.small.test.app.historydata.historydata.data.source;

import android.text.TextUtils;

import com.small.test.app.historydata.historydata.data.CategoryVO;
import com.small.test.appstub.VO.ResultModel;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.MvpModel;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.network.callback.StringCallback;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class HistoryDataRepository extends MvpModel implements HistoryDataSource
{
    @Override
    public void getSystemLocationDatas(final int classCode, final int pageNo, final int recordNum,
        final boolean isRefresh, final GetHistoryDataCallBack getHistoryDataCallBack)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(C.HistoryData.PARAMS_PAGENO, pageNo);
        params.put(C.HistoryData.PARAMS_RECORDNUM, recordNum);
        params.put(C.HistoryData.PARAMS_CLASSCODE, classCode);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.HistoryData.API_METHOD_POSITIONLIST))
                .params(params)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onBefore(Request request)
                    {
                        super.onBefore(request);
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<CategoryVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, CategoryVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                getHistoryDataCallBack.onHistoryDataLoaded(resultModel.getData());
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getHistoryDataCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getHistoryDataCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getHistoryDataCallBack.onError(e);
        }
    }
    
    @Override
    public void getSysOneAndSysTwoAndZongChengDatas(final String parentCode, final int classCode, final int pageNo,
        final int recordNum, final boolean isRefresh, final GetHistoryDataCallBack getHistoryDataCallBack)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(C.HistoryData.PARAMS_PARENTCODE, parentCode);
        params.put(C.HistoryData.PARAMS_PAGENO, pageNo);
        params.put(C.HistoryData.PARAMS_RECORDNUM, recordNum);
        params.put(C.HistoryData.PARAMS_CLASSCODE, classCode);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.HistoryData.API_METHOD_CATEGORYLIST))
                .params(params)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onBefore(Request request)
                    {
                        super.onBefore(request);
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<CategoryVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, CategoryVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                List<CategoryVO> categoryVOs = resultModel.getData();
                                getHistoryDataCallBack.onHistoryDataLoaded(categoryVOs);
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getHistoryDataCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getHistoryDataCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getHistoryDataCallBack.onError(e);
        }
    }
    
}
