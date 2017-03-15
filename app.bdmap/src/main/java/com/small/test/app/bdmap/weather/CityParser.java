package com.small.test.app.bdmap.weather;

import java.io.InputStream;
import java.util.List;

public interface CityParser
{
    public List<City> parse(InputStream is)
        throws Exception;
    
    public String serialize(List<City> citys)
        throws Exception;
}