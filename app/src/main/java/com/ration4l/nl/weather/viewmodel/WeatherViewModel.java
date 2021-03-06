package com.ration4l.nl.weather.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ration4l.nl.weather.model.WeatherResponse;
import com.ration4l.nl.weather.reposistory.WeatherRepository;

import static com.ration4l.nl.weather.utils.SharedPreferencesManager.getUnit;

/**
 * Created by ThanhLongNL on 11-Jul-20.
 */

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private MutableLiveData<WeatherResponse> weatherLiveData;
    private MutableLiveData<String> unitObservable;
    private MutableLiveData<Boolean> progressBarObservable;


    public WeatherViewModel(Application application) {
        super(application);
        weatherRepository = WeatherRepository.getInstance();
        weatherLiveData = weatherRepository.getWeatherResponseLiveData();
        progressBarObservable = weatherRepository.getProgressBarObservable();
        unitObservable = new MutableLiveData<>();
        unitObservable.setValue(getUnit(application));
    }

    public MutableLiveData<Boolean> getProgressBarObservable() {
        return progressBarObservable;
    }

    public MutableLiveData<String> getUnitObservable() {
        return unitObservable;
    }

    public void getWeatherData(double lat, double lng) {
        weatherRepository.getWeatherData(lat, lng, unitObservable.getValue());
    }

    public MutableLiveData<WeatherResponse> getWeatherLiveData() {
        return weatherLiveData;
    }

    /*
    public void getAPIData(double lat, double lng){
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Map<String, String> options = new HashMap<>();
        options.put("lat", String.valueOf(lat));
        options.put("lon", String.valueOf(lng));
        options.put("exclude", "minutely");
        options.put("appid", "7468892e223c208839e513cfd2d87544");
        options.put("units", "metric");
        Call<WeatherResponse> call = weatherAPI.getWeatherData(options);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    setWeatherLiveData(response.body());
                }
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

     */

}
