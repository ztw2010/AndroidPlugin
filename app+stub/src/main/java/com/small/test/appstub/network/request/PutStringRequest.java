package com.small.test.appstub.network.request;


import com.small.test.appstub.network.utils.Exceptions;

import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PutStringRequest extends OkHttpRequest
{
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    
    private String content;
    
    private MediaType mediaType;
    
    public PutStringRequest(String url, Object tag, Map<String, Object> params, Map<String, String> headers,
        String content, MediaType mediaType)
    {
        super(url, tag, params, headers);
        this.content = content;
        this.mediaType = mediaType;
        
        if (this.content == null)
        {
            Exceptions.illegalArgument("the content can not be null !");
        }
        if (this.mediaType == null)
        {
            this.mediaType = MEDIA_TYPE_PLAIN;
        }
    }
    
    @Override
    protected RequestBody buildRequestBody()
    {
        return RequestBody.create(mediaType, content);
    }
    
    @Override
    protected Request buildRequest(Request.Builder builder, RequestBody requestBody)
    {
        return builder.put(requestBody).build();
    }
    
    @Override
    public String toString()
    {
        return super.toString() + ", put requestBody{content=" + content + "} ";
    }
}
