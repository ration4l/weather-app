package com.ration4l.nl.weather.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ThanhLongNL on 19-Jul-20.
 */

public class PlacesAPIRetrofitService {
    private static Retrofit retrofit;
    public static final String TELEPORT_URL ="https://api.teleport.org";

    public static Retrofit getRetrofitInstance(){
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(TELEPORT_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
