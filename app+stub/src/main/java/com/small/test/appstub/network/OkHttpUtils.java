package com.small.test.appstub.network;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.small.test.appstub.network.builder.GetBuilder;
import com.small.test.appstub.network.builder.PostFileBuilder;
import com.small.test.appstub.network.builder.PostFormBuilder;
import com.small.test.appstub.network.builder.PostStringBuilder;
import com.small.test.appstub.network.builder.PutBuilder;
import com.small.test.appstub.network.callback.Callback;
import com.small.test.appstub.network.cookie.SimpleCookieJar;
import com.small.test.appstub.network.https.HttpsUtils;
import com.small.test.appstub.network.request.RequestCall;
import com.small.test.appstub.network.utils.NetworkUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpUtils
{
    public static final String TAG = "OkHttpUtils";
    
    public static final long DEFAULT_MILLISECONDS = 10000;
    
    private static OkHttpUtils mInstance;
    
    private OkHttpClient mOkHttpClient;
    
    private Handler mDelivery;
    
    private OkHttpUtils()
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //cookie enabled
        okHttpClientBuilder.addInterceptor(new Interceptor()
        {
            
            @Override
            public Response intercept(Chain chain)
                throws IOException
            {
                Request request = chain.request().newBuilder().addHeader("Connection", "close").build();//在头部添加该字段，可防止经常性的出现java.io.IOException: unexpected end of stream on okhttp3.Address@b01d4a1c错误
                return chain.proceed(request);
            }
        });
        okHttpClientBuilder.retryOnConnectionFailure(false);//设置为连接超时后不进行重新连接(Okhttp内部有重连机制,第一次连接超时时不会调用public void onError(Request request, Exception e)方法,第二次超时时才会调用.设置该值之后Okhttp就不会进行第二次重连了)
        okHttpClientBuilder.cookieJar(new SimpleCookieJar());
        
        mDelivery = new Handler(Looper.getMainLooper());
        
        if (true)
        {
            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
            {
                @Override
                public boolean verify(String hostname, SSLSession session)
                {
                    return true;
                }
            });
        }
        
        mOkHttpClient = okHttpClientBuilder.build();
    }
    
    private boolean debug;
    
    private String tag;
    
    public OkHttpUtils debug(String tag)
    {
        debug = true;
        this.tag = tag;
        return this;
    }
    
    public static OkHttpUtils getInstance()
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils();
                }
            }
        }
        return mInstance;
    }
    
    public Handler getDelivery()
    {
        return mDelivery;
    }
    
    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }
    
    public static GetBuilder get(Context context)
        throws ConnectException
    {
        if (context != null)
        {
            if (!NetworkUtils.isNetworkActive(context))
            {
                throw new ConnectException();
            }
        }
        return new GetBuilder();
    }
    
    public static PostStringBuilder postString(Context context)
        throws ConnectException
    {
        if (context != null)
        {
            if (!NetworkUtils.isNetworkActive(context))
            {
                throw new ConnectException();
            }
        }
        return new PostStringBuilder();
    }
    
    public static PostFileBuilder postFile(Context context)
        throws ConnectException
    {
        if (context != null)
        {
            if (!NetworkUtils.isNetworkActive(context))
            {
                throw new ConnectException();
            }
        }
        return new PostFileBuilder();
    }
    
    public static PostFormBuilder post(Context context)
        throws ConnectException
    {
        if (context != null)
        {
            if (!NetworkUtils.isNetworkActive(context))
            {
                throw new ConnectException();
            }
        }
        return new PostFormBuilder();
    }
    
    public static PutBuilder put(Context context)
        throws ConnectException
    {
        if (context != null)
        {
            if (!NetworkUtils.isNetworkActive(context))
            {
                throw new ConnectException();
            }
        }
        return new PutBuilder();
    }
    
    public void execute(final RequestCall requestCall, Callback callback)
    {
        if (debug)
        {
            if (TextUtils.isEmpty(tag))
            {
                tag = TAG;
            }
            Log.d(tag,
                "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getOkHttpRequest().toString()
                    + "}");
        }
        
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;
        
        requestCall.getCall().enqueue(new okhttp3.Callback()
        {
            @Override
            public void onFailure(Call call, IOException e)
            {
                sendFailResultCallback(call.request(), e, finalCallback);
            }
            
            @Override
            public void onResponse(Call call, Response response)
                throws IOException
            {
                if (response.code() >= 400 && response.code() <= 599)
                {
                    try
                    {
                        sendFailResultCallback(requestCall.getRequest(),
                            new RuntimeException(response.body().string()),
                            finalCallback);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    return;
                }
                
                try
                {
                    Object o = finalCallback.parseNetworkResponse(response);
                    sendSuccessResultCallback(o, finalCallback);
                }
                catch (Exception e)
                {
                    sendFailResultCallback(response.request(), e, finalCallback);
                }
            }
        });
    }
    
    public void sendFailResultCallback(final Request request, final Exception e, final Callback callback)
    {
        if (callback == null)
            return;
        
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(request, e);
                callback.onAfter();
            }
        });
    }
    
    public void sendSuccessResultCallback(final Object object, final Callback callback)
    {
        if (callback == null)
            return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }
    
    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }
    
    public void setCertificates(InputStream... certificates)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
            .sslSocketFactory(HttpsUtils.getSslSocketFactory(certificates, null, null))
            .build();
    }
    
    public void setTimeout(int connTimeout, TimeUnit connTimeoutUnits, int readTimeout, TimeUnit readTimeoutUnits,
        int writeTimeout, TimeUnit writeTimeoutUnit)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
            .connectTimeout(connTimeout, connTimeoutUnits)
            .readTimeout(readTimeout, readTimeoutUnits)
            .writeTimeout(writeTimeout, writeTimeoutUnit)
            .build();
    }
}
