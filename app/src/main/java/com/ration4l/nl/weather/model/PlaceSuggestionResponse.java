package com.ration4l.nl.weather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlaceSuggestionResponse {

    @SerializedName("_embedded")
    @Expose
    public Embedded embedded;

    @SerializedName("count")
    @Expose
    public Integer count;

    public Embedded getEmbedded() {
        return embedded;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "POJOResponse{" +
                "embedded=" + embedded +
                ", count=" + count +
                '}';
    }

    public static class Embedded {

        @SerializedName("city:search-results")
        @Expose
        public List<CitySearchResult> citySearchResults = null;

        public List<CitySearchResult> getCitySearchResults() {
            return citySearchResults;
        }

        @Override
        public String toString() {
            return "Embedded{" +
                    "citySearchResults=" + citySearchResults +
                    '}';
        }
    }

    public static class Links {

        @SerializedName("city:item")
        @Expose
        public CityItem cityItem;

        public CityItem getCityItem() {
            return cityItem;
        }

        @Override
        public String toString() {
            return "Links{" +
                    "cityItem=" + cityItem +
                    '}';
        }
    }

    public static class CitySearchResult {

        @SerializedName("_links")
        @Expose
        public Links links;

        @SerializedName("matching_full_name")
        @Expose
        public String matchingFullName;

        public Links getLinks() {
            return links;
        }

        public String getMatchingFullName() {
            return matchingFullName;
        }

        @Override
        public String toString() {
            return matchingFullName;
        }
    }

    public static class CityItem {

        @SerializedName("href")
        @Expose
        public String href;

        public String getHref() {
            return href;
        }

        @Override
        public String toString() {
            return "CityItem{" +
                    "href='" + href + '\'' +
                    '}';
        }
    }
}