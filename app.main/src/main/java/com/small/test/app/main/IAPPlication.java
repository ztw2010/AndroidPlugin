package com.small.test.app.main;

import android.app.Application;

import com.small.test.appstub.mvp.Mvp;


/**
 * Created by ztw on 2016/12/2.
 */

public class IAPPlication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Mvp.getInstance().init(this.getApplicationContext());
    }
}
