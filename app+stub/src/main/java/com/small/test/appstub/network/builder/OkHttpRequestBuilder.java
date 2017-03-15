package com.small.test.appstub.network.builder;


import com.small.test.appstub.network.request.RequestCall;

import java.util.Map;


public abstract class OkHttpRequestBuilder
{
    protected String url;
    
    protected Object tag;
    
    protected Map<String, String> headers;
    
    protected Map<String, Object> params;
    
    public abstract OkHttpRequestBuilder url(String url);
    
    public abstract OkHttpRequestBuilder tag(Object tag);
    
    public abstract OkHttpRequestBuilder params(Map<String, Object> params);
    
    public abstract OkHttpRequestBuilder addParams(String key, Object val);
    
    public abstract OkHttpRequestBuilder headers(Map<String, String> headers);
    
    public abstract OkHttpRequestBuilder addHeader(String key, String val);
    
    public abstract RequestCall build();
    
}
