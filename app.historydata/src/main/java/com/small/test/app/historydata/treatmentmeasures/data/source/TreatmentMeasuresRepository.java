package com.small.test.app.historydata.treatmentmeasures.data.source;

import android.text.TextUtils;

import com.small.test.app.historydata.treatmentmeasures.data.MeaDetailVO;
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

public class TreatmentMeasuresRepository extends MvpModel implements TreatmentMeasuresDatasource
{
    @Override
    public void getTreatmentMeasures(final String manualId, final String faltReason,
        final GetTreatmentMeasuresCallBack getTreatmentMeasuresCallBack)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(C.Suspension.KEY_MANUALID, manualId);
        try
        {
            OkHttpUtils.get(context)
                .url(String.format(C.API.C_API_ROOT, C.CauseFailure.API_METHOD_MEADETAIL))
                .params(params)
                .build()
                .execute(new StringCallback()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        ResultModel<MeaDetailVO> resultModel = objectMapper.readValue(response,
                            objectMapper.getJavaType(ResultModel.class, MeaDetailVO.class));
                        if (resultModel != null)
                        {
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag())
                            {
                                List<MeaDetailVO> meaDetailVOs = resultModel.getData();
                                getTreatmentMeasuresCallBack.onSuspensionLoaded(meaDetailVOs);
                            }
                            else if (ResultModel.RESULTFAILED == resultModel.getServFlag())
                            {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg))
                                {
                                    getTreatmentMeasuresCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        getTreatmentMeasuresCallBack.onError(e);
                    }
                });
        }
        catch (ConnectException e)
        {
            getTreatmentMeasuresCallBack.onError(e);
        }
    }
}
