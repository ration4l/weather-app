package com.ration4l.nl.weather.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ration4l.nl.weather.model.Place;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ThanhLongNL on 22-Jul-20.
 */

@Database(entities = {Place.class}, version = 1, exportSchema = false)
public abstract class PlaceRoomDatabase extends RoomDatabase {

    public abstract PlaceDao placeDao();

    private static volatile PlaceRoomDatabase INSTANCE;
    public static final int NUMBER_OF_THREAD = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREAD);

    public static PlaceRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PlaceRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                            PlaceRoomDatabase.class, "place.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
