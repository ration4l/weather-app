package com.ration4l.nl.weather.reposistory;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.ration4l.nl.weather.model.WeatherResponse;
import com.ration4l.nl.weather.network.WeatherAPI;
import com.ration4l.nl.weather.network.WeatherRetrofitService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ThanhLongNL on 25-Jul-20.
 */

public class WeatherRepository {
    private static final String TAG = "WeatherRepository";
    private static WeatherRepository INSTANCE;
    private WeatherAPI weatherAPI;
    private MutableLiveData<WeatherResponse> weatherResponseLiveData;
    private MutableLiveData<Boolean> progressBarObservable;

    public static WeatherRepository getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new WeatherRepository();
        }
        return INSTANCE;
    }

    public WeatherRepository(){
        weatherResponseLiveData = new MutableLiveData<>();
        weatherAPI = WeatherRetrofitService.getInstance().create(WeatherAPI.class);
        progressBarObservable = new MutableLiveData<>();
    }

    public void getWeatherData(double lat, double lng, String unit){
        progressBarObservable.setValue(true);
        Map<String, String> options = new HashMap<>();
        options.put("lat", String.valueOf(lat));
        options.put("lon", String.valueOf(lng));
        options.put("exclude", "minutely");
        options.put("appid", "7468892e223c208839e513cfd2d87544");
        options.put("units", unit);
        Call<WeatherResponse> call = weatherAPI.getWeatherData(options);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                progressBarObservable.setValue(false);
                if (response.isSuccessful()) {
                    weatherResponseLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                progressBarObservable.setValue(false);
                Log.e(TAG, "onFailure: Error calling api" );
            }
        });
    }

    public MutableLiveData<WeatherResponse> getWeatherResponseLiveData() {
        return weatherResponseLiveData;
    }

    public MutableLiveData<Boolean> getProgressBarObservable() {
        return progressBarObservable;
    }
}
