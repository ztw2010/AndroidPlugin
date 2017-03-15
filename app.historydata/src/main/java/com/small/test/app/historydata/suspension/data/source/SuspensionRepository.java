package com.small.test.app.historydata.suspension.data.source;

import android.text.TextUtils;

import com.small.test.app.historydata.suspension.data.FaultListVO;
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

public class SuspensionRepository extends MvpModel implements SuspensionDataSource
{
    @Override
    public void getSuspensionList(final String zongChengCode, final String className, final int pageNum,
        final int recordNum, final boolean isRefresh, final GetSuspensionListCallBack getSuspensionListCallBack)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(C.Suspension.PARAMS_ASSEMBLYID, zongChengCode);
        params.put(C.HistoryData.PARAMS_PAGENO, pageNum);
        params.put(C.HistoryData.PARAMS_RECORDNUM, recordNum);
        params.put(C.Suspension.PARAMS_CLASSNAME, className);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.Suspension.API_METHOD_PHENBYCATEGORY))
                .params(params)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<FaultListVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, FaultListVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                List<FaultListVO> faultListVOs = resultModel.getData();
                                getSuspensionListCallBack.onSuspensionLoaded(faultListVOs);
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getSuspensionListCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getSuspensionListCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getSuspensionListCallBack.onError(e);
        }
    }

}
