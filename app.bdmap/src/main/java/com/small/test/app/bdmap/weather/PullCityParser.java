package com.small.test.app.bdmap.weather;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PullCityParser implements CityParser
{
    @Override
    public List<City> parse(InputStream is)
        throws Exception
    {
        List<City> citys = null;
        City city = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(is, "UTF-8");
        int eventType = parser.getEventType();
        String mString = null;
        while (eventType != XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                    citys = new ArrayList<City>();
                    break;
                case XmlPullParser.START_TAG:
                    
                    if (parser.getName().equals("city"))
                    {
                        mString = parser.getAttributeValue(1);
                    }
                    else if (parser.getName().equals("county"))
                    {
                        city = new City();
                        city.setmCity(mString);
                        city.setId(parser.getAttributeValue(0));
                        city.setName(parser.getAttributeValue(1));
                        city.setWeatherCode(parser.getAttributeValue(2));
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("county") && city != null && citys != null)
                    {
                        citys.add(city);
                        city = null;
                        if (parser.getName().equals("city") && mString != null && mString != null)
                        {
                            mString = null;
                        }
                    }
                    
            }
            eventType = parser.next();
        }
        
        return citys;
    }
    
    @Override
    public String serialize(List<City> citys)
        throws Exception
    {
        return null;
    }
    
}
