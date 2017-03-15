package com.small.test.appstub.network.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 
 * 判断网络状态是否可用工具类
 *
 * <p>detailed comment
 * @author ztw 2016年5月18日
 * @see
 * @since 1.0
 */
public class NetworkUtils
{
    /**
     * 网络是否通畅.
     * 
     * @param context
     * @return
     */
    public static boolean isNetworkActive(Context context)
    {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        if (manager == null)
        {
            return false;
        }
        else
        {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i = 0; i < info.length; i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    /**
     * 是否是WiFi连接
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context)
    {
        if (context != null)
        {
            ConnectivityManager mConnectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null)
            {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
