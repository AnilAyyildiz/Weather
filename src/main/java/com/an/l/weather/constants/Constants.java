package com.an.l.weather.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
    public static String API_URL;
    public static String ACCESS_KEY_PARAM = "?access_key=a36f86657a2da49a3d0075641e0d61c4";
    public static String QUERY_KEY_PARAM = "&query=";


    @Value("${weather-stack.api-url}")
    public void setApiUrl(String apiUrl){
        Constants.API_URL =apiUrl;
    }

}
