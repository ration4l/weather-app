package com.ration4l.nl.weather.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.ration4l.nl.weather.utils.Utils.getCurrentHour;
import static com.ration4l.nl.weather.utils.Utils.milisToDateTimeString;
import static com.ration4l.nl.weather.utils.Utils.milisToHourString;
import static com.ration4l.nl.weather.utils.Utils.standardizeString;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class CurrentWeatherRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private static final String TAG = "DayWeatherRVAdapter";
    public static final int TYPE_DAY_WEATHER = 0;
    public static final int TYPE_DAY_WEATHER_DETAIL = 1;

    private Context context;
    private List<Object> list;

    public CurrentWeatherRVAdapter(Context context, List<Object> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TYPE_DAY_WEATHER:
                return new DayWeatherViewHolder(layoutInflater.inflate(R.layout.item_current_weather, parent, false));
            case TYPE_DAY_WEATHER_DETAIL:
                return new DayWeatherDetailViewHolder(layoutInflater.inflate(R.layout.item_current_weather_detail, parent, false));
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_DAY_WEATHER:
                DayWeatherViewHolder dayWeatherViewHolder = (DayWeatherViewHolder) holder;
                dayWeatherViewHolder.setContents((WeatherResponse) list.get(position));
                break;
            case TYPE_DAY_WEATHER_DETAIL:
                DayWeatherDetailViewHolder dayWeatherDetailViewHolder = (DayWeatherDetailViewHolder) holder;
                dayWeatherDetailViewHolder.setContents((WeatherResponse.Current) list.get(position));
        }
    }

    @Override
    public int getItemCount() {
        Log.e(TAG, "getItemCount: " + list.size() );
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof WeatherResponse){
            return TYPE_DAY_WEATHER;
        } else if (list.get(position) instanceof WeatherResponse.Current){
            return TYPE_DAY_WEATHER_DETAIL;
        }
        return -1;
    }

    class DayWeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDateTime, tvTemp, tvFeelLike, tvDes;
        private ImageView imgIcon;
        private RecyclerView recyclerView;
        public DayWeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvTemp = itemView.findViewById(R.id.tvTemp);
            tvFeelLike = itemView.findViewById(R.id.tvFeelLike);
            tvDes = itemView.findViewById(R.id.tvDes);
            imgIcon = itemView.findViewById(R.id.imgIcon);
            recyclerView = itemView.findViewById(R.id.rvHourly);

        }

        public void setContents(WeatherResponse weatherResponse) {
            tvDateTime.setText(milisToDateTimeString(System.currentTimeMillis()/1000));
            int temp = Math.round(weatherResponse.getCurrent().getTemp());
            tvTemp.setText(temp +"°");
            tvFeelLike.setText("Feels like "+ Math.round(weatherResponse.getCurrent().getFeelLike()) +"°");
            tvDes.setText(standardizeString(weatherResponse.getCurrent().getWeather().get(0).getDescription()));
            String icon = weatherResponse.getCurrent().getWeather().get(0).getIcon();
            Picasso.with(context)
                    .load("https://openweathermap.org/img/wn/"+icon+"@2x.png")
                    .into(imgIcon);
            List<WeatherResponse.Hourly> hourlyList = new ArrayList<>();
            hourlyList.addAll(weatherResponse.getHourly().subList(0,24 - getCurrentHour() + 6 + 1));
            HourlyWeatherForecastRVAdapter hourlyWeatherRVAdapter = new HourlyWeatherForecastRVAdapter(context, hourlyList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(hourlyWeatherRVAdapter);

        }
    }

    static class DayWeatherDetailViewHolder extends RecyclerView.ViewHolder{
        private TextView tvWindSpeed, tvHumidity, tvUVIndex, tvVisibility, tvSunrise, tvSunset;
        public DayWeatherDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            tvWindSpeed = itemView.findViewById(R.id.tvWindSpeed);
            tvHumidity = itemView.findViewById(R.id.tvHumidity);
            tvUVIndex = itemView.findViewById(R.id.tvUVIndex);
            tvVisibility = itemView.findViewById(R.id.tvVisibility);
            tvSunrise = itemView.findViewById(R.id.tvSunrise);
            tvSunset = itemView.findViewById(R.id.tvSunset);
        }

        public void setContents(WeatherResponse.Current current) {
            tvWindSpeed.setText(current.getWindSpeed()+" km/h");
            tvHumidity.setText(current.getHumidity()+"%");
            tvUVIndex.setText(String.valueOf(current.getUvi()));
            tvVisibility.setText((int)current.getVisibility()/1000+" km");
            tvSunrise.setText(milisToHourString(current.getSunRise()));
            tvSunset.setText(milisToHourString(current.getSunSet()));
        }
    }
}
