package com.small.test.appstub.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils
{
    public static final String APP_NAME = "Yajun";
    
    private static ShareUtils INSTANCE;

    private Context context;

    private ShareUtils(){

    }

    public static ShareUtils getInstance() {
        if (INSTANCE == null) {
            synchronized (ShareUtils.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ShareUtils();
                }
            }
        }
        return INSTANCE;
    }

    public void init(Context context){
        this.context = context;
    }

    public String getValue(String key)
    {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE).getString(key, null);
    }
    
    public String getValue(String key, String defValue)
    {
        return context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE).getString(key, defValue);
    }
    
    public void clearValue(String... key)
    {
        for (String string : key)
        {
            SharedPreferences.Editor editor = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE).edit();
            editor.remove(string);
            editor.commit();
        }
    }
    
    public void updateValue(String key, String value)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(APP_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.commit();
    }
}
