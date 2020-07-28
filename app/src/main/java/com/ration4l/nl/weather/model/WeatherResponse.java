package com.ration4l.nl.weather.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class WeatherResponse {
    private float lat;
    private float lon;

    private Current current;

    private List<Hourly> hourly;

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

    public static class Current {
        private long dt;

        @SerializedName("sunrise")
        private long sunRise;

        @SerializedName("sunset")
        private long sunSet;

        private float temp;

        @SerializedName("feels_like")
        private float feelLike;

        private int humidity;
        private float uvi;
        private int clouds;
        private float visibility;

        @SerializedName("wind_speed")
        private float windSpeed;

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

    public static class Hourly {
        private long dt;
        private float temp;
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

    public static class Daily {
        private long dt;
        private long sunrise;
        private long sunset;
        private Temp temp;
        private int humidity;

        @SerializedName("wind_speed")
        private float windSpeed;
        private List<Weather> weather;
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

    public static class Weather {
        private String description;
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

    public static class Temp {
        private float max;
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
