package com.ration4l.nl.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ration4l.nl.weather.adapter.CurrentWeatherRVAdapter;
import com.ration4l.nl.weather.model.WeatherResponse;
import com.ration4l.nl.weather.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CurrentWeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentWeatherFragment extends Fragment {
    private static final String TAG = "DayWeatherFragment";
    private RecyclerView recyclerView;
    private List<Object> listContent;

    private CurrentWeatherRVAdapter adapter;
    private WeatherViewModel weatherViewModel;
    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance() {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);

        weatherViewModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        weatherViewModel.getWeatherLiveData().observe(getActivity(), new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                listContent.clear();
                listContent.add(weatherResponse);
                listContent.add(weatherResponse.getCurrent());
                adapter.notifyDataSetChanged();
                Log.e(TAG, "onChanged: "+listContent );
            }
        });

    }

    private void initComponents(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        listContent = new ArrayList<>();
        adapter = new CurrentWeatherRVAdapter(getContext(), listContent);
        recyclerView.setAdapter(adapter);
    }


}