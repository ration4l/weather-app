package com.ration4l.nl.weather;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ration4l.nl.weather.adapter.DailyWeatherForecastRVAdapter;
import com.ration4l.nl.weather.model.WeatherResponse;
import com.ration4l.nl.weather.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DailyWeatherForecastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyWeatherForecastFragment extends Fragment {
    private RecyclerView recyclerView;
    private List<WeatherResponse.Daily> list;
    private DailyWeatherForecastRVAdapter adapter;
    private WeatherViewModel weatherViewModel;
    public DailyWeatherForecastFragment() {
        // Required empty public constructor
    }

    public static DailyWeatherForecastFragment newInstance() {
        DailyWeatherForecastFragment fragment = new DailyWeatherForecastFragment();
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
        return inflater.inflate(R.layout.fragment_daily_weather_forecast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initComponents(view);

        weatherViewModel = new ViewModelProvider(getActivity()).get(WeatherViewModel.class);
        weatherViewModel.getWeatherLiveData().observe(getActivity(), new Observer<WeatherResponse>() {
            @Override
            public void onChanged(WeatherResponse weatherResponse) {
                list.clear();
                list.addAll(weatherResponse.getDaily().subList(1,8));
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initComponents(View view) {
        recyclerView = view.findViewById(R.id.recyclerView1);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        list = new ArrayList<>();
        adapter = new DailyWeatherForecastRVAdapter(list, getContext());
        recyclerView.setAdapter(adapter);


    }
}