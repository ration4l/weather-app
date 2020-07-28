package com.ration4l.nl.weather;

/**
 * Created by ThanhLongNL on 19-Jul-20.
 */

public class City {
    private String id;
    private String country;
    private double lat;
    private double lng;
    private String name;

    public City() {
    }

    public City(String id, String country, double lat, double lng, String name) {
        this.id = id;
        this.country = country;
        this.lat = lat;
        this.lng = lng;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", name='" + name + '\'' +
                '}';
    }
}
