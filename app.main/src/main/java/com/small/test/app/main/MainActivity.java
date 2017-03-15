package com.small.test.app.main;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.test.app.main.login.data.source.LoginVO;
import com.small.test.appstub.VO.WeatherVO;
import com.small.test.appstub.cache.ACache;
import com.small.test.appstub.log.L;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.DefaultMvpContract;
import com.small.test.appstub.mvp.DefaultMvpPresenter;
import com.small.test.appstub.mvp.IBaseView;
import com.small.test.appstub.mvp.IMessageKey;
import com.small.test.appstub.mvp.IMessageListener;
import com.small.test.appstub.mvp.ListenerRegister;
import com.small.test.appstub.mvp.ManagerActivity;
import com.small.test.appstub.mvp.MvpActivity;
import com.small.test.appstub.permission.PermissionFail;
import com.small.test.appstub.permission.PermissionGen;
import com.small.test.appstub.permission.PermissionSuccess;
import com.small.test.appstub.utils.DateUtils;
import com.small.test.appstub.widget.dialog.AlertDialog;

import net.wequick.small.Small;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends MvpActivity<DefaultMvpPresenter> implements DefaultMvpContract.View, IMessageListener {

    private TextView mHello;

    private TextView mDate;

    private TextView weather;

    private ImageView iv_weather;

    private TextView attendence;

    private TextView sign;

    private Button signed;

    private LinearLayout wxbd;

    private LinearLayout wxdj;

    private LinearLayout jszc;

    private LinearLayout grzx;

    private Map<String, Integer> weatherMap = new HashMap<String, Integer>();

    private Long lastExitTime = 0l;

    private static final int PHONE_PERMISSION_REQUEST_CODE = 0x01;

    @Override
    public IBaseView getBaseView() {
        return this;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.wxbd:
                Small.openUri("historydata", this);
                break;
            case R.id.wxdj:
                Small.openUri("maintenance", this);
                break;
            case R.id.jszc:
                PermissionGen.needPermission(this, PHONE_PERMISSION_REQUEST_CODE, Manifest.permission.CALL_PHONE);
                break;
            case R.id.grzx:
                break;
            default:
                break;
        }
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState){
        ListenerRegister.getInstance().registListener(this);
        mHello = (TextView) findViewById(R.id.hello);
        mDate = (TextView) findViewById(R.id.date);
        weather = (TextView) findViewById(R.id.weather);
        iv_weather = (ImageView) findViewById(R.id.iv_weather);
        attendence = (TextView) findViewById(R.id.attendence);
        sign = (TextView) findViewById(R.id.sign);
        signed = (Button) findViewById(R.id.bt_sign);
        wxbd = (LinearLayout) findViewById(R.id.wxbd);
        wxbd.setOnClickListener(this);
        wxdj = (LinearLayout) findViewById(R.id.wxdj);
        wxdj.setOnClickListener(this);
        jszc = (LinearLayout) findViewById(R.id.jszc);
        jszc.setOnClickListener(this);
        grzx = (LinearLayout) findViewById(R.id.grzx);
        grzx.setOnClickListener(this);
        Object object = ACache.get(getApplicationContext()).getAsObject(C.API.KEY_CACHE_LOGIN_VO);
        if(object != null && object instanceof LoginVO){
            LoginVO loginVO = (LoginVO) object;
            mHello.setText(
                    String.format("%s%s%s", loginVO.getName(), ",", getString(R.string.hello)));
        }
        mDate.setText(DateUtils.getDt());
        initWeatherMap();
        Message message = new Message();
        message.what = IMessageKey.KEY_GETWEATHER;
        ListenerRegister.getInstance().dispatchMessage(message);
    }

    @Override
    protected void initTitle() {

    }

    private void initWeatherMap(){
        weatherMap.put(getString(R.string.sunny), R.mipmap.sunny);
        weatherMap.put(getString(R.string.partly_cloudy1), R.mipmap.partly_cloudy);
        weatherMap.put(getString(R.string.partly_cloudy2), R.mipmap.partly_cloudy);
        weatherMap.put(getString(R.string.partly_cloudy3), R.mipmap.partly_cloudy);
        weatherMap.put(getString(R.string.cloudy_day), R.mipmap.cloudy_day);
        weatherMap.put(getString(R.string.shower1), R.mipmap.shower);
        weatherMap.put(getString(R.string.shower2), R.mipmap.shower);
        weatherMap.put(getString(R.string.thunderstorms1), R.mipmap.thunderstorms);
        weatherMap.put(getString(R.string.thunderstorms2), R.mipmap.thunderstorms);
        weatherMap.put(getString(R.string.light_rain1), R.mipmap.light_rain);
        weatherMap.put(getString(R.string.light_rain2), R.mipmap.light_rain);
        weatherMap.put(getString(R.string.medium_rain), R.mipmap.medium_rain);
        weatherMap.put(getString(R.string.heavy_rain1), R.mipmap.heavy_rain);
        weatherMap.put(getString(R.string.heavy_rain2), R.mipmap.heavy_rain);
        weatherMap.put(getString(R.string.thunderstorms_hail), R.mipmap.thunderstorms_hail);
        weatherMap.put(getString(R.string.rainstorm), R.mipmap.rainstorm);
        weatherMap.put(getString(R.string.sleet), R.mipmap.sleet);
        weatherMap.put(getString(R.string.light_snow), R.mipmap.light_snow);
        weatherMap.put(getString(R.string.medium_snow), R.mipmap.medium_snow);
        weatherMap.put(getString(R.string.heavy_snow), R.mipmap.heavy_snow);
        weatherMap.put(getString(R.string.blizzard), R.mipmap.blizzard);
        weatherMap.put(getString(R.string.fog1), R.mipmap.fog);
        weatherMap.put(getString(R.string.fog2), R.mipmap.fog);
        weatherMap.put(getString(R.string.dust), R.mipmap.dust);
        weatherMap.put(getString(R.string.heavy_sandstorm), R.mipmap.heavy_sandstorm);
        weatherMap.put(getString(R.string.snow_shower), R.mipmap.snow_shower);
        weatherMap.put(getString(R.string.dust_fly), R.mipmap.dust_fly);
        weatherMap.put(getString(R.string.haze), R.mipmap.haze);
        weatherMap.put(getString(R.string.heavy_rainstorm), R.mipmap.heavy_rainstorm);
        weatherMap.put(getString(R.string.rain_snow1), R.mipmap.rain_snow);
        weatherMap.put(getString(R.string.rain_snow2), R.mipmap.rain_snow);
        weatherMap.put(getString(R.string.rain_snow3), R.mipmap.rain_snow);
        weatherMap.put(getString(R.string.sandstorm), R.mipmap.sandstorm);
    }

    @Override
    public void showTip(String content) {

    }

    @Override
    public void onMessageReceive(Message message) {
        switch (message.what)
        {
            case IMessageKey.KEY_GETWEATHER_ED:
                L.d("onMessageReceive weather get");
                WeatherVO weatherVO = (WeatherVO)message.obj;
                setWeather(weatherVO);
                break;
            default:
                break;
        }
    }

    private void setWeather(final WeatherVO weatherVO)
    {
        runOnUiThread(new Runnable()
        {

            @Override
            public void run()
            {
                if (weatherVO != null)
                {
                    String weatherStr = weatherVO.getWeather();
                    StringBuffer sBuffer = new StringBuffer();
                    sBuffer.append(weatherStr)
                            .append(" ")
                            .append(weatherVO.getMinTemperature())
                            .append("℃")
                            .append("-")
                            .append(weatherVO.getMaxTemperature())
                            .append("℃")
                            .append(" ");
                    weather.setText(sBuffer.toString());
                    if (weatherMap.containsKey(weatherStr))
                    {
                        iv_weather.setImageResource(weatherMap.get(weatherStr));
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ListenerRegister.getInstance().unregistListener(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((System.currentTimeMillis() - lastExitTime) < 2000){
            ManagerActivity.getInstance().exit();
            Intent mIntent = new Intent(Intent.ACTION_MAIN);
            mIntent.addCategory(Intent.CATEGORY_HOME);
            startActivity(mIntent);
        }else{
            Toast.makeText(this, getString(R.string.double_click_exit), Toast.LENGTH_SHORT).show();
            lastExitTime = System.currentTimeMillis();
        }
        return false;
    }

    @PermissionSuccess(requestCode = PHONE_PERMISSION_REQUEST_CODE)
    public void onGrantedCallPhonePermission(){
        new AlertDialog(this).builder()
                .setCanceledOnTouchOutside(false)
                .setTitle(getString(R.string.technology_center))
                .setMsg(getString(R.string.phone))
                .setPositiveButton(getString(R.string.dial), new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        String phoneNum = getString(R.string.phone);
                        StringBuffer sb = new StringBuffer();
                        sb.append(phoneNum.substring(0, 3)).append(phoneNum.substring(4, 12));
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.CALL");
                        intent.setData(Uri.parse("tel:" + sb));
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .show();
    }

    @PermissionFail(requestCode = PHONE_PERMISSION_REQUEST_CODE)
    public void onNotGrantedCallPhonePermission(){
        showTip(getString(R.string.phone_permission_denied));
    }

    @Override public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
