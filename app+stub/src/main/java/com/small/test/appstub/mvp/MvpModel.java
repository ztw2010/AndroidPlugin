package com.small.test.appstub.mvp;

import android.content.Context;

import com.small.test.appstub.objectmapper.MObjectMapper;
import com.small.test.appstub.utils.ShareUtils;


public abstract class MvpModel
{
    protected Context context;

    protected final MObjectMapper objectMapper = MObjectMapper.getInstance();

    protected final String HTTP_TASK_KEY = "HttpTaskKey_" + hashCode();

    public void initContext(Context context){
        this.context = context;
        ShareUtils.getInstance().init(context);
    }
}
