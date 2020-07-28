package com.ration4l.nl.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.PlaceSuggestionResponse;
import com.ration4l.nl.weather.network.PlacesAPI;
import com.ration4l.nl.weather.network.PlacesAPIRetrofitService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ThanhLongNL on 19-Jul-20.
 */

public class PlaceAutocompleteAdapter extends ArrayAdapter<PlaceSuggestionResponse.CitySearchResult> {
    private static final String TAG = "PlaceAutocompleteAdapte";

    private Context context;
    private int resId;
    private List<PlaceSuggestionResponse.CitySearchResult> suggestionList = new ArrayList<>();

    public PlaceAutocompleteAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
        this.resId = resource;
    }

    @Override
    public int getCount() {
        return suggestionList.size();
    }

    @Nullable
    @Override
    public PlaceSuggestionResponse.CitySearchResult getItem(int position) {
        return suggestionList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_place_matching_name, parent, false);
        TextView tvPlace = view.findViewById(R.id.tvMatchingName);
        tvPlace.setText(suggestionList.get(position).getMatchingFullName());
        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            private final Object lock = new Object();
            private final Object lock2 = new Object();
            boolean finished = false;

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                finished = false;
                if (constraint == null || constraint.length() == 0) {
                    synchronized (lock) {
                        filterResults.values = new ArrayList<PlaceSuggestionResponse.CitySearchResult>();
                        filterResults.count = 0;
                    }
                } else {
                    PlacesAPI placesAPI = PlacesAPIRetrofitService.getRetrofitInstance().create(PlacesAPI.class);
                    Map<String, String> options = new HashMap<>();
                    options.put("search", constraint.toString());
                    Call<PlaceSuggestionResponse> call = placesAPI.getPlacesData(options);
                    call.enqueue(new Callback<PlaceSuggestionResponse>() {
                        @Override
                        public void onResponse(Call<PlaceSuggestionResponse> call, Response<PlaceSuggestionResponse> response) {
                            if (response.isSuccessful()) {
                                suggestionList = response.body().getEmbedded().citySearchResults;
                                finished = true;
                                synchronized (lock2) {
                                    lock2.notifyAll();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PlaceSuggestionResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }

                    });
                    while (!finished) {
                        synchronized (lock2) {
                            try {
                                lock2.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                filterResults.values = suggestionList;
                filterResults.count = suggestionList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results.values != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
        return filter;
    }

}
