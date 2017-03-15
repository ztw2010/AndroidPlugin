package com.small.test.appstub.mvp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ManagerActivity
{
    
    /**
     * activity列表
     */
    private List<Activity> activityList = new LinkedList<Activity>();
    
    private static ManagerActivity instance = null;
    
    private ManagerActivity()
    {
        
    }
    
    public static ManagerActivity getInstance()
    {
        if (instance == null)
        {
            instance = new ManagerActivity();
        }
        return instance;
    }
    
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    
    public void removeActivity(Activity activity)
    {
        Activity removeActivity = null;
        for (Activity currentActivity : activityList)
        {
            if (currentActivity.equals(activity))
            {
                removeActivity = currentActivity;
            }
        }
        if (removeActivity != null)
        {
            activityList.remove(removeActivity);
        }
    }
    
    public void exit()
    {
        for (Activity activity : activityList)
        {
            if (activity != null && !activity.isFinishing())
            {
                activity.finish();
            }
        }
        int id = android.os.Process.myPid();
        if (id != 0)
        {
            android.os.Process.killProcess(id);
        }
    }
    
    /**
     * 得到顶层的Activity
     * @return
     */
    public Activity getTopActivity()
    {
        android.app.ActivityManager manager = null;
        if (!activityList.isEmpty())
        {
            manager = (android.app.ActivityManager)activityList.get(0).getSystemService(Context.ACTIVITY_SERVICE);
        }
        if (manager != null)
        {
            ComponentName componentName = manager.getRunningTasks(Integer.MAX_VALUE).get(0).topActivity;
            for (Activity activity : activityList)
            {
                if (!activity.isFinishing()
                    && componentName.getClassName().equals(activity.getComponentName().getClassName()))
                {
                    return activity;
                }
            }
        }
        return null;
    }
    
    /**
     * 判断某一个Activity是否处于顶层
     * @param className
     * @return
     */
    public boolean isTopActivity(String className)
    {
        if (TextUtils.isEmpty(className))
        {
            return false;
        }
        if (className.equals(getTopActivity().getComponentName().getClassName()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void killActivity(Class<? extends  MvpActivity>... clazz){
        if(clazz != null){
            List<Activity> killActivities = new ArrayList<Activity>();
            for(Class c : clazz){
                for(Activity activity : activityList){
                    if(c.getName().equals(activity.getClass().getName())){
                        killActivities.add(activity);
                    }
                }
            }
            for(Activity activity : killActivities){
                activity.finish();
                activityList.remove(activity);
            }
        }
    }
    
}
