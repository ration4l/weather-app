package com.ration4l.nl.weather.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ration4l.nl.weather.model.Place;

import java.util.List;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

@Dao
public interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Place place);

    @Query("DELETE FROM place_table")
    void deleteAll();

    @Query("DELETE FROM place_table WHERE address = :address")
    void deletePlace(String address);

    @Query("SELECT * FROM place_table ORDER BY address ASC")
    LiveData<List<Place>> getAllPlaces();
}
