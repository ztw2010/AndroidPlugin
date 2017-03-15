package com.small.test.app.bdmap.weather;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.small.test.app.bdmap.R;
import com.small.test.appstub.VO.WeatherVO;
import com.small.test.appstub.log.L;
import com.small.test.appstub.mvp.C;
import com.small.test.appstub.mvp.IMessageKey;
import com.small.test.appstub.mvp.IMessageListener;
import com.small.test.appstub.mvp.ListenerRegister;
import com.small.test.appstub.network.OkHttpUtils;
import com.small.test.appstub.network.callback.StringCallback;
import com.small.test.appstub.objectmapper.MObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by ztw on 2016/12/8.
 */

public class WeatherService extends Service implements IMessageListener {

    private LocationService locationService;

    private BDLocationListener mListener = new BDLocationListener()
    {
        @Override
        public void onReceiveLocation(BDLocation location)
        {
            if (null != location && location.getLocType() != BDLocation.TypeServerError)
            {
                String cityName = location.getCity();
                L.d("WeatherService cityName="+cityName);
                String cityWeatherId = getCityWeatherIdByCityName(cityName);
                getWeather(cityWeatherId);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        ListenerRegister.getInstance().registListener(this);
        locationService = new LocationService(this.getApplicationContext());
        L.d("WeatherService onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ListenerRegister.getInstance().unregistListener(this);
        L.e("WeatherService onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private String getCityWeatherIdByCityName(String cityName)
    {
        String cityWeatherId = "";
        if (!TextUtils.isEmpty(cityName))
        {
            try
            {
                InputStream is = getAssets().open("city.xml");
                PullCityParser parser = new PullCityParser();
                List<City> citys = parser.parse(is);
                int size = citys.size();
                for (int i = 0; i < size; i++)
                {
                    if (citys.get(i).getmCity().equals(citys.get(i).getName()))
                    {
                        if (citys.get(i).getmCity().startsWith(getString(R.string.zhjj))
                                && cityName.startsWith(getString(R.string.zhjj)))
                        {
                            cityWeatherId = citys.get(i).getWeatherCode();
                        }
                        else if (citys.get(i).getmCity().startsWith(getString(R.string.zhjk))
                                && cityName.startsWith(getString(R.string.zhjk)))
                        {
                            cityWeatherId = citys.get(i).getWeatherCode();
                        }
                        else if (citys.get(i).getmCity().startsWith(getString(R.string.aler))
                                && cityName.startsWith(getString(R.string.aler)))
                        {
                            cityWeatherId = citys.get(i).getWeatherCode();
                        }
                        else if (citys.get(i).getmCity().startsWith(getString(R.string.alshm))
                                && cityName.startsWith(getString(R.string.alshm)))
                        {
                            cityWeatherId = citys.get(i).getWeatherCode();
                        }
                        else if (citys.get(i).getmCity().substring(0, 2).equals(cityName.substring(0, 2)))
                        {
                            cityWeatherId = citys.get(i).getWeatherCode();
                        }
                    }
                }
            }
            catch (IOException e)
            {
               L.e("getCityWeatherIdByCityName IOException", e);
            }
            catch (Exception e)
            {
                L.e("getCityWeatherIdByCityName Exception", e);
            }
        }
        return cityWeatherId;
    }

    private void getWeather(String cityWeatherId){
        if (!TextUtils.isEmpty(cityWeatherId))
        {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(C.Login.PARAMS_CITYID, cityWeatherId);
            map.put(C.Login.PARAMS_KEY, C.Login.VALUE_KEYVALUE);
            try
            {
                OkHttpUtils.get(this).url(C.API.API_ROOT_WEATHER).params(map).build().execute(new StringCallback()
                {

                    @Override
                    public void onResponse(String response)
                    {
                        MObjectMapper objectMapper = MObjectMapper.getInstance();
                        if (!TextUtils.isEmpty(response))
                        {
                            try
                            {
                                Map<String, List<Object>> weatherMap = objectMapper.readValue(response, Map.class);
                                List<Object> objects = weatherMap.get("HeWeather data service 3.0");
                                if (objects != null && !objects.isEmpty())
                                {
                                    WeatherVO weatherVO = new WeatherVO();
                                    Object object = objects.iterator().next();
                                    Map<String, Object> forecastMap = (Map<String, Object>)object;
                                    Object object2 = forecastMap.get("daily_forecast");
                                    if (object2 != null)
                                    {
                                        List<Map<String, Object>> list = (List<Map<String, Object>>)object2;
                                        if (list != null && !list.isEmpty())
                                        {
                                            Map<String, Object> dailyForecastMap = list.iterator().next();
                                            Object object3 = dailyForecastMap.get("cond");
                                            if (object3 != null)
                                            {
                                                Map<String, String> map = (Map<String, String>)object3;
                                                String weather = map.get("txt_d");
                                                weatherVO.setWeather(weather);
                                            }
                                            Object object4 = dailyForecastMap.get("tmp");
                                            if (object4 != null)
                                            {
                                                Map<String, String> map = (Map<String, String>)object4;
                                                String minTemperature = map.get("min");
                                                weatherVO.setMinTemperature(minTemperature);
                                                String maxTemperature = map.get("max");
                                                weatherVO.setMaxTemperature(maxTemperature);
                                            }
                                            L.d("getWeather WeatherVO=" + weatherVO);
                                            Message message = new Message();
                                            message.what = IMessageKey.KEY_GETWEATHER_ED;
                                            message.obj = weatherVO;
                                            ListenerRegister.getInstance().dispatchMessage(message);
                                            stopSelf();
                                        }
                                    }
                                }
                            }
                            catch (Exception e)
                            {
                                L.e("getWeather Exception", e);
                            }
                        }
                    }

                    @Override
                    public void onError(Request request, Exception e)
                    {
                        L.e("getWeather onError", e);
                    }
                });
            }
            catch (ConnectException e)
            {
                L.e("getWeather ConnectException");
            }
        }
    }

    @Override
    public void onMessageReceive(Message message) {
        L.d("WeatherService receive get weather request");
        locationService.registerListener(mListener);
        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        locationService.start();
    }
}
