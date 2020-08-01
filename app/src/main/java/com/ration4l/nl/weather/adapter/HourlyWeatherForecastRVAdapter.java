package com.ration4l.nl.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.ration4l.nl.weather.utils.Utils.milisToHourString;

/**
 * Created by ThanhLongNL on 11-Jul-20.
 */

public class HourlyWeatherForecastRVAdapter extends RecyclerView.Adapter<HourlyWeatherForecastRVAdapter.HourlyWeatherViewHolder>{
    private Context context;
    private List<WeatherResponse.Hourly> list;

    public HourlyWeatherForecastRVAdapter(Context context, List<WeatherResponse.Hourly> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HourlyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly_weather_forecast, parent, false);
        return new HourlyWeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherViewHolder holder, int position) {
        holder.setContents(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HourlyWeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView tvHourlyTime, tvHourlyTemp;
        private ImageView imgHourlyIcon;
        public HourlyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);
            tvHourlyTemp = itemView.findViewById(R.id.tvHourlyTemp);
            tvHourlyTime = itemView.findViewById(R.id.tvHourlyTime);
            imgHourlyIcon = itemView.findViewById(R.id.imgHourlyIcon);
        }

        public void setContents(WeatherResponse.Hourly hourly) {
            tvHourlyTemp.setText(Math.round(hourly.getTemp()) +"°");
            tvHourlyTime.setText(milisToHourString(hourly.getDt()));
            String icon = hourly.getWeather().get(0).getIcon();
            Picasso.with(context)
                    .load("https://openweathermap.org/img/wn/"+icon+"@2x.png")
                    .into(imgHourlyIcon);
        }
    }
}
