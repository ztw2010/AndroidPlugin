package com.small.test.appstub.objectmapper;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.ResolvedType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class MObjectMapper extends ObjectMapper
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -2360037206568923861L;
    
    private static final MObjectMapper objectMapper;
    
    static
    {
        objectMapper = new MObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setDateFormat(new SimpleDateFormat("MMM d, yyyy HH:mm:ss aaa", Locale.ENGLISH));
    }
    
    private MObjectMapper()
    {
    }
    
    public static MObjectMapper getInstance()
    {
        return objectMapper;
    }
    
    public JavaType getJavaType(Class<?> collectionClass, Class<?>... elementClasses)
    {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
    
    @Override
    public <T> T readValue(String src, JavaType javaType)
    {
        try
        {
            return super.readValue(src, javaType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue String JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue String JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue String IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(byte[] src, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(byte[] src, int offset, int len, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, offset, len, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte offset JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte offset JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte offset IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(byte[] src, int offset, int len, JavaType valueType)
    {
        try
        {
            return super.readValue(src, offset, len, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte valueType JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte valueType JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte valueType IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(byte[] src, int offset, int len, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, offset, len, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte valueTypeRef JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte valueTypeRef JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte valueTypeRef IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(byte[] src, JavaType valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte valueType2 JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte valueType2 JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte valueType2 IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(byte[] src, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte TypeReference JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte TypeReference JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte TypeReference IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(File src, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte File JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte File JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte File IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(File src, JavaType valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte File valueType JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte File valueType JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte File valueType IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(File src, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte File valueTypeRef JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte File valueTypeRef JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte File valueTypeRef IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(InputStream src, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte InputStream JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte InputStream JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte InputStream IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(InputStream src, JavaType valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte InputStream valueType JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte InputStream valueType JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte InputStream valueType IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(InputStream src, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte InputStream valueTypeRef JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte InputStream valueTypeRef JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte InputStream valueTypeRef IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(JsonParser jp, Class<T> valueType)
    {
        try
        {
            return super.readValue(jp, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte JsonParser JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte JsonParser JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte JsonParser IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(JsonParser jp, JavaType valueType)
    {
        try
        {
            return super.readValue(jp, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte JsonParser valueType JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte JsonParser valueType JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte JsonParser valueType IOException", e);
        }
        return null;
    }
    
    @Override
    public <T> T readValue(JsonParser jp, TypeReference<?> valueTypeRef)
    {
        try
        {
            return super.readValue(jp, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue byte JsonParser valueTypeRef JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue byte JsonParser valueTypeRef JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue byte JsonParser valueTypeRef IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(Reader src, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue Reader JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue Reader JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue Reader IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(Reader src, JavaType valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            //LOGGER.error("readValue Reader valueType JsonParseException", e);
        }
        catch (JsonMappingException e)
        {
            //LOGGER.error("readValue Reader valueType JsonMappingException", e);
        }
        catch (IOException e)
        {
            //LOGGER.error("readValue Reader valueType IOException", e);
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(Reader src, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(String content, Class<T> valueType)
    {
        try
        {
            return super.readValue(content, valueType);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(String content, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(content, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
        
    }
    
    @Override
    public <T> T readValue(URL src, Class<T> valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public <T> T readValue(URL src, JavaType valueType)
    {
        try
        {
            return super.readValue(src, valueType);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public <T> T readValue(URL src, TypeReference valueTypeRef)
    {
        try
        {
            return super.readValue(src, valueTypeRef);
        }
        catch (JsonParseException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public <T> MappingIterator<T> readValues(JsonParser jp, Class<T> valueType)
    {
        try
        {
            return super.readValues(jp, valueType);
        }
        catch (JsonProcessingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public <T> MappingIterator<T> readValues(JsonParser jp, JavaType valueType)
    {
        try
        {
            return super.readValues(jp, valueType);
        }
        catch (JsonProcessingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
        
    }
    
    @Override
    public <T> MappingIterator<T> readValues(JsonParser jp, ResolvedType valueType)
    {
        try
        {
            return super.readValues(jp, valueType);
        }
        catch (JsonProcessingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
        
    }
    
    @Override
    public <T> MappingIterator<T> readValues(JsonParser jp, TypeReference<?> valueTypeRef)
    {
        try
        {
            return super.readValues(jp, valueTypeRef);
        }
        catch (JsonProcessingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public void writeValue(File resultFile, Object value)
    {
        try
        {
            super.writeValue(resultFile, value);
        }
        catch (JsonGenerationException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
    }
    
    @Override
    public void writeValue(JsonGenerator jgen, Object value)
    {
        try
        {
            super.writeValue(jgen, value);
        }
        catch (JsonGenerationException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
    }
    
    @Override
    public void writeValue(OutputStream out, Object value)
    {
        try
        {
            super.writeValue(out, value);
        }
        catch (JsonGenerationException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
    }
    
    @Override
    public void writeValue(Writer w, Object value)
    {
        try
        {
            super.writeValue(w, value);
        }
        catch (JsonGenerationException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
    }
    
    @Override
    public byte[] writeValueAsBytes(Object arg0)
    {
        try
        {
            return super.writeValueAsBytes(arg0);
        }
        catch (JsonGenerationException e)
        {
            
        }
        catch (JsonMappingException e)
        {
            
        }
        catch (IOException e)
        {
            
        }
        return null;
    }
    
    @Override
    public String writeValueAsString(Object arg0)
    {
        try
        {
            return super.writeValueAsString(arg0);
        }
        catch (JsonProcessingException e)
        {
            
        }
        return null;
    }
    
}
