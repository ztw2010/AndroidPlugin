package com.small.test.app.maintenance.maintenance;

import android.content.Intent;

import com.small.test.app.maintenance.maintenance.data.ProvinceVO;
import com.small.test.appstub.mvp.IBasePresenter;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.lib.selectimg.bean.ImageItem;

import java.util.List;

public interface MaintenanceRegistrationContract
{
    interface View extends IBaseView
    {
        public boolean isFaultPage();

        public void refreshFaultPageAdapter(List<ImageItem> imageItems);

        public List<ImageItem> getFaultPageAdapterList();

        public boolean isRepairePage();

        public void refreshRepairePageAdapter(List<ImageItem> imageItems);

        public List<ImageItem> getRepairePageAdapterList();

        public void onProvinceSelected(ProvinceVO provinceVO);

        public void onProvincePull2Refresh();
    }
    
    interface Presenter extends IBasePresenter
    {
        public void onActivityResult(int requestCode, int resultCode, Intent data);

        public void initializaVitamino(OnInitListener onInitListener);

        /**
         * 得到省份简称
         * @param jobId
         * @param isRefresh
         */
        public void getProvinces(String jobId, boolean isRefresh);

        public interface OnInitListener{
            public void onStart();
            public void onError();
            public void onFinish();
        }
    }
}
