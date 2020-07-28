package com.ration4l.nl.weather.network;

import com.ration4l.nl.weather.model.PlaceSuggestionResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

public interface PlacesAPI {

    @GET("/api/cities/?")
    Call<PlaceSuggestionResponse> getPlacesData(@QueryMap Map<String, String> options);
}
