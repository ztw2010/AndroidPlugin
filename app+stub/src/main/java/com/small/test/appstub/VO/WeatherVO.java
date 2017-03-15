package com.small.test.appstub.VO;

public class WeatherVO
{
    private String weather;
    
    private String minTemperature;
    
    private String maxTemperature;
    
    public WeatherVO()
    {
    }
    
    public WeatherVO(String weather, String minTemperature, String maxTemperature)
    {
        super();
        this.weather = weather;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
    }
    
    public String getWeather()
    {
        return weather;
    }
    
    public void setWeather(String weather)
    {
        this.weather = weather;
    }
    
    public String getMinTemperature()
    {
        return minTemperature;
    }
    
    public void setMinTemperature(String minTemperature)
    {
        this.minTemperature = minTemperature;
    }
    
    public String getMaxTemperature()
    {
        return maxTemperature;
    }
    
    public void setMaxTemperature(String maxTemperature)
    {
        this.maxTemperature = maxTemperature;
    }
    
    @Override
    public String toString()
    {
        return "WeatherVO [weather=" + weather + ", minTemperature=" + minTemperature + ", maxTemperature="
            + maxTemperature + "]";
    }
}
