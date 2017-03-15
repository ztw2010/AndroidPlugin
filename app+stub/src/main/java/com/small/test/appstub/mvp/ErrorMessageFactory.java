package com.small.test.appstub.mvp;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.text.TextUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

/**
 * Created by zhongruan on 2016/12/8.
 */

public class ErrorMessageFactory {

    private ErrorMessageFactory()
    {
    }

    public static String create(Context context, Exception exception)
    {
        String message = "";

        if (exception instanceof ConnectException)
        {
            message = "网络不通";
        }
        else if (exception instanceof SocketTimeoutException)
        {
            message = "连接超时！";
        }
        else if (exception instanceof NullPointerException)
        {
            message = "程序出错，空指针错误！";
        }
        else if (exception instanceof JsonParseException)
        {
            message = "程序出错，Json字符串解析出错！";
        }
        else if (exception instanceof IllegalArgumentException)
        {
            message = "程序出错，错误的请求参数！";
        }
        else if (exception instanceof ArithmeticException)
        {
            message = "程序出错，数值计算出错！";
        }
        else if (exception instanceof JsonMappingException)
        {
            message = "程序出错，Json语法错误！";
        }
        else if (exception instanceof NetworkOnMainThreadException)
        {
            message = "程序出错，网络请求放在主线程中运行！";
        }
        else if (exception instanceof IllegalStateException)
        {
            message = "程序出错，网络请求要放在主线程中运行！";
        }
        else if (exception instanceof IndexOutOfBoundsException)
        {
            message = "数组越界异常!";
        }
        else if (exception instanceof IOException)
        {
            message = "程序出错，IO错误！";
        }
        else
        {
            message = exception.getMessage();
        }
        if(TextUtils.isEmpty(message))
        {
            message = "未知错误";
        }
        return message;
    }
}
