package com.small.test.app.maintenance.maintenance.data.source;

import android.text.TextUtils;

import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.appstub.VO.ResultModel;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.MvpModel;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.network.callback.StringCallback;

import java.net.ConnectException;

import okhttp3.Request;

public class MaintenanceregistrationRepository extends MvpModel implements MaintenanceregistrationDataSource {

    @Override
    public void getProvinces(final String jobId, final boolean isRefresh, final GetProvinceCallBack getProvinceCallBack) {
        try {
            OkHttpUtils.get(context)
                    .url(String.format(C.API.C_API_ROOT, C.MaintenanceRegistration.API_METHOD_PROVINCE))
                    .addParams(C.Login.PARAMS_JOBID, jobId)
                    .build()
                    .execute(new StringCallback() {

                        @Override
                        public void onResponse(String response) {
                            ResultModel<ProvinceVO> resultModel = objectMapper.readValue(response,
                                    objectMapper.getJavaType(ResultModel.class, ProvinceVO.class));
                            if (ResultModel.RESULTSUCCESS == resultModel.getServFlag()) {
                                getProvinceCallBack.onProvincesLoaded(resultModel.getData());
                            } else if (ResultModel.RESULTFAILED == resultModel.getServFlag()) {
                                String errorMsg = resultModel.getErrorMsg();
                                if (!TextUtils.isEmpty(errorMsg)) {
                                    getProvinceCallBack.onError(new Exception(errorMsg));
                                }
                            }
                        }

                        @Override
                        public void onError(Request request, Exception e) {
                            getProvinceCallBack.onError(e);
                        }
                    });
        } catch (ConnectException e) {
            getProvinceCallBack.onError(e);
        }
    }
}
