package com.small.test.app.historydata;

import android.app.Application;

import com.small.test.appstub.systeminit.SystemInit;

/**
 * Created by ztw on 2016/12/20.
 */

public class IApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        SystemInit.getInstance().init(this.getApplicationContext());
    }
}
