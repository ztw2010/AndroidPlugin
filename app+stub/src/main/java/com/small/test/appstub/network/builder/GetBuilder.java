package com.small.test.appstub.network.builder;


import com.small.test.appstub.network.request.GetRequest;
import com.small.test.appstub.network.request.RequestCall;

import java.util.LinkedHashMap;
import java.util.Map;

public class GetBuilder extends OkHttpRequestBuilder
{
    @Override
    public RequestCall build()
    {
        if (params != null)
        {
            url = appendParams(url, params);
        }
        
        return new GetRequest(url, tag, params, headers).build();
    }
    
    private String appendParams(String url, Map<String, Object> params)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(url + "?");
        if (params != null && !params.isEmpty())
        {
            for (String key : params.keySet())
            {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }
        }
        
        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }
    
    @Override
    public GetBuilder url(String url)
    {
        this.url = url;
        return this;
    }
    
    @Override
    public GetBuilder tag(Object tag)
    {
        this.tag = tag;
        return this;
    }
    
    @Override
    public GetBuilder params(Map<String, Object> params)
    {
        this.params = params;
        return this;
    }
    
    @Override
    public GetBuilder addParams(String key, Object val)
    {
        if (this.params == null)
        {
            params = new LinkedHashMap<>();
        }
        params.put(key, val);
        return this;
    }
    
    @Override
    public GetBuilder headers(Map<String, String> headers)
    {
        this.headers = headers;
        return this;
    }
    
    @Override
    public GetBuilder addHeader(String key, String val)
    {
        if (this.headers == null)
        {
            headers = new LinkedHashMap<>();
        }
        headers.put(key, val);
        return this;
    }
}
