package com.small.test.app.bdmap.baidumapsdk;

import android.app.Application;
import android.content.Intent;

import com.baidu.mapapi.SDKInitializer;
import com.small.test.app.bdmap.weather.WeatherService;

public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext(此处要将this改为this.getApplicationContext()才能显示mapview)
        SDKInitializer.initialize(this.getApplicationContext());

        startService(new Intent(this.getApplicationContext(), WeatherService.class));
    }

}