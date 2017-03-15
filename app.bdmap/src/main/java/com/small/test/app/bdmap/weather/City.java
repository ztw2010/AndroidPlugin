package com.small.test.app.bdmap.weather;

public class City
{
    
    private String id, name, weatherCode, mCity;
    
    public City()
    {
    }
    
    public City(String id, String name, String weatherCode, String mCity)
    {
        super();
        this.id = id;
        this.name = name;
        this.weatherCode = weatherCode;
        this.mCity = mCity;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getWeatherCode()
    {
        return weatherCode;
    }
    
    public void setWeatherCode(String weatherCode)
    {
        this.weatherCode = weatherCode;
    }
    
    public String getmCity()
    {
        return mCity;
    }
    
    public void setmCity(String mCity)
    {
        this.mCity = mCity;
    }
}
