package com.ration4l.nl.weather.model;

/**
 * Created by ThanhLongNL on 19-Jul-20.
 */

public class Latlng {
    private double lat;
    private double lng;

    @Override
    public String toString() {
        return "Latlng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
