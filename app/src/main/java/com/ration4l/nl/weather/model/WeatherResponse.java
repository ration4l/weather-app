package com.ration4l.nl.weather.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class WeatherResponse {
    @SerializedName("lat")
    private float lat;

    @ColumnInfo(name = "lon")
    private float lon;

    @SerializedName("current")
    private Current current;

    @SerializedName("hourly")
    private List<Hourly> hourly;

    @SerializedName("daily")
    private List<Daily> daily;

    public List<Daily> getDaily() {
        return daily;
    }

    public float getLat() {
        return lat;
    }

    public float getLon() {
        return lon;
    }

    public Current getCurrent() {
        return current;
    }

    public List<Hourly> getHourly() {
        return hourly;
    }

    @Override
    public String toString() {
        return "POJOWeatherResponse{" +
                "lat=" + lat +
                ", lon=" + lon +
                ", current=" + current +
                ", hourly=" + hourly +
                ", daily=" + daily +
                '}';
    }

    /**
     * Created by ThanhLongNL on 10-Jul-20.
     */

    public class Current {
        @SerializedName("dt")
        private long dt;

        @SerializedName("sunrise")
        private long sunRise;

        @SerializedName("sunset")
        private long sunSet;

        @SerializedName("temp")
        private float temp;

        @SerializedName("feels_like")
        private float feelLike;

        @SerializedName("humdity")
        private int humidity;

        @SerializedName("uvi")
        private float uvi;

        @SerializedName("clouds")
        private int clouds;

        @SerializedName("visibility")
        private float visibility;

        @SerializedName("wind_speed")
        private float windSpeed;

        @SerializedName("weather")
        private List<Weather> weather = null;

        public long getDt() {
            return dt;
        }

        public long getSunRise() {
            return sunRise;
        }

        public long getSunSet() {
            return sunSet;
        }

        public float getTemp() {
            return temp;
        }

        public float getFeelLike() {
            return feelLike;
        }

        public int getHumidity() {
            return humidity;
        }

        public float getUvi() {
            return uvi;
        }

        public int getClouds() {
            return clouds;
        }

        public float getVisibility() {
            return visibility;
        }

        public float getWindSpeed() {
            return windSpeed;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        @Override
        public String toString() {
            return "Current{" +
                    "dt=" + dt +
                    ", sunRise=" + sunRise +
                    ", sunSet=" + sunSet +
                    ", temp=" + temp +
                    ", feelLike=" + feelLike +
                    ", humidity=" + humidity +
                    ", uvi=" + uvi +
                    ", clouds=" + clouds +
                    ", visibility=" + visibility +
                    ", windSpeed=" + windSpeed +
                    ", weather=" + weather +
                    '}';
        }
    }

    /**
     * Created by ThanhLongNL on 10-Jul-20.
     */

    public class Hourly {
        @SerializedName("dt")
        private long dt;

        @SerializedName("temp")
        private float temp;

        @SerializedName("weather")
        private List<Weather> weather = null;

        public List<Weather> getWeather() {
            return weather;
        }

        public long getDt() {
            return dt;
        }

        public float getTemp() {
            return temp;
        }


        @Override
        public String toString() {
            return "Hourly{" +
                    "dt=" + dt +
                    ", temp=" + temp +
                    ", weather=" + weather +
                    '}';
        }
    }

    /**
     * Created by ThanhLongNL on 11-Jul-20.
     */

    public class Daily {
        @SerializedName("dt")
        private long dt;

        @SerializedName("sunrise")
        private long sunrise;

        @SerializedName("sunset")
        private long sunset;

        @SerializedName("temp")
        private Temp temp;

        @SerializedName("humidity")
        private int humidity;

        @SerializedName("wind_speed")
        private float windSpeed;

        @SerializedName("weather")
        private List<Weather> weather;

        @SerializedName("uvi")
        private float uvi;

        private boolean expanded = false;

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        public boolean isExpanded() {
            return expanded;
        }

        public long getDt() {
            return dt;
        }

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public Temp getTemp() {
            return temp;
        }

        public int getHumidity() {
            return humidity;
        }

        public float getWindSpeed() {
            return windSpeed;
        }

        public List<Weather> getWeather() {
            return weather;
        }

        public float getUvi() {
            return uvi;
        }

        @Override
        public String toString() {
            return "Daily{" +
                    "dt=" + dt +
                    ", sunrise=" + sunrise +
                    ", sunset=" + sunset +
                    ", temp=" + temp +
                    ", humidity=" + humidity +
                    ", windSpeed=" + windSpeed +
                    ", weather=" + weather +
                    ", uvi=" + uvi +
                    '}';
        }
    }

    /**
     * Created by ThanhLongNL on 10-Jul-20.
     */

    public class Weather {
        @SerializedName("description")
        private String description;

        @SerializedName("icon")
        private String icon;

        public String getDescription() {
            return description;
        }

        public String getIcon() {
            return icon;
        }

        @Override
        public String toString() {
            return "Weather{" +
                    "description='" + description + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }
    }

    /**
     * Created by ThanhLongNL on 11-Jul-20.
     */

    public class Temp {
        @SerializedName("max")
        private float max;

        @SerializedName("min")
        private float min;

        public float getMax() {
            return max;
        }

        public float getMin() {
            return min;
        }

        @Override
        public String toString() {
            return "Temp{" +
                    "max=" + max +
                    ", min=" + min +
                    '}';
        }
    }
}
