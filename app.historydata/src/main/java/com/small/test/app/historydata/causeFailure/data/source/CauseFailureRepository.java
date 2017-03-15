package com.small.test.app.historydata.causeFailure.data.source;

import android.text.TextUtils;

import com.small.test.app.historydata.causeFailure.data.CauseFailureVO;
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

public class CauseFailureRepository extends MvpModel implements CauseFailureDataSource
{
    @Override
    public void getCauseFailureData(final String manualId, final String assemblyId, final String phenomenon,
        final int pageNum, final int recordNum, final boolean isRefresh,
        final GetCauseFailureDataCallBack getCauseFailureDataCallBack)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(C.Suspension.PARAMS_ASSEMBLYID, assemblyId);
        params.put(C.Suspension.KEY_PHENOMENON, phenomenon);
        params.put(C.HistoryData.PARAMS_PAGENO, pageNum);
        params.put(C.HistoryData.PARAMS_RECORDNUM, recordNum);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.CauseFailure.API_METHOD_FAULTREASON))
                .params(params)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<CauseFailureVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, CauseFailureVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                List<CauseFailureVO> causeFailureVOs = resultModel.getData();
                                getCauseFailureDataCallBack.onCauseFailureDataLoaded(causeFailureVOs);
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getCauseFailureDataCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getCauseFailureDataCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getCauseFailureDataCallBack.onError(e);
        }
    }
}
