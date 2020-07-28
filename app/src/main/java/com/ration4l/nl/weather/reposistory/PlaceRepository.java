package com.ration4l.nl.weather.reposistory;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.ration4l.nl.weather.model.Place;
import com.ration4l.nl.weather.persistence.PlaceDao;
import com.ration4l.nl.weather.persistence.PlaceRoomDatabase;

import java.util.List;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

public class PlaceRepository {
    private PlaceDao placeDao;
    private LiveData<List<Place>> allPlaces;

    public PlaceRepository(Application application) {
        PlaceRoomDatabase db = PlaceRoomDatabase.getDatabase(application);
        placeDao = db.placeDao();
        allPlaces = placeDao.getAllPlaces();
    }

    public LiveData<List<Place>> getAllPlaces() {
        return allPlaces;
    }

    public void insert(Place place){
        PlaceRoomDatabase.databaseWriteExecutor.execute(() -> placeDao.insert(place));
    }

    public void deletePlace(String placeAddress){
        PlaceRoomDatabase.databaseWriteExecutor.execute(() -> placeDao.deletePlace(placeAddress));
    }
}
