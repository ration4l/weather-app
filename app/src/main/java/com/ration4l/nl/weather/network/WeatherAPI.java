package com.ration4l.nl.weather.network;

import com.ration4l.nl.weather.model.WeatherResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public interface WeatherAPI {

    @GET("/data/2.5/onecall?")
    Call<WeatherResponse> getWeatherData(@QueryMap Map<String, String> options);

}
