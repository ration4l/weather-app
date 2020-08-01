package com.ration4l.nl.weather.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class WeatherRetrofitService {
    private static Retrofit INTANCE;
    public static final String OPEN_WEATHER_MAP_URL = "https://api.openweathermap.org";

    public static Retrofit getInstance() {
        if (INTANCE == null) {
            INTANCE = new Retrofit.Builder()
                    .baseUrl(OPEN_WEATHER_MAP_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return INTANCE;
    }


}
