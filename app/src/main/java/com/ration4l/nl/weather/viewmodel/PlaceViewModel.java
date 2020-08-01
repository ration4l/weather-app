package com.ration4l.nl.weather.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.ration4l.nl.weather.model.Place;
import com.ration4l.nl.weather.reposistory.PlaceRepository;

import java.util.List;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

public class PlaceViewModel extends AndroidViewModel {
    private PlaceRepository placeRepository;
    private LiveData<List<Place>> allPlaces;

    public PlaceViewModel(@NonNull Application application) {
        super(application);

        placeRepository = new PlaceRepository(application);
        allPlaces = placeRepository.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces() {
        return allPlaces;
    }

    public void insert(Place place) {
        placeRepository.insert(place);
    }

    public void deletePlace(String placeAddress) {
        placeRepository.deletePlace(placeAddress);
    }
}
