package com.small.test.app.maintenance.maintenance.data.source;

import com.small.test.app.maintenance.maintenance.data.ProvinceVO;

import java.util.List;

public interface MaintenanceregistrationDataSource
{
    interface GetProvinceCallBack
    {
        void onProvincesLoaded(List<ProvinceVO> provinceDOs);

        void onError(Exception exception);
    }

    /**
     * 获取省份简称
     * @param jobId
     * @param isRefresh
     * @param getProvinceCallBack
     */
    public void getProvinces(String jobId, boolean isRefresh,
                             MaintenanceregistrationDataSource.GetProvinceCallBack getProvinceCallBack);
}
