package com.ration4l.nl.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.WeatherResponse;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.ration4l.nl.weather.utils.Utils.milisToDateString;
import static com.ration4l.nl.weather.utils.Utils.milisToHourString;

/**
 * Created by ThanhLongNL on 11-Jul-20.
 */

public class DailyWeatherForecastRVAdapter extends RecyclerView.Adapter<DailyWeatherForecastRVAdapter.DailyWeatherViewHolder> {
    private static final String TAG = "MultiDayWeatherRVAdapte";
    private List<WeatherResponse.Daily> list;
    private Context context;

    public DailyWeatherForecastRVAdapter(List<WeatherResponse.Daily> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public DailyWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DailyWeatherViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_weather_forecast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DailyWeatherViewHolder holder, int position) {
        holder.setContents(list.get(position));

        boolean isExpanded = list.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class DailyWeatherViewHolder extends RecyclerView.ViewHolder{
        private TextView tvDateTime, tvDes, tvMax, tvMin, tvWindSpeed, tvHumidity, tvUVIndex, tvSunrise, tvSunset;
        private ImageView imgDailyIcon;
        private TableLayout expandableLayout;
        private LinearLayout linearLayout;
        public DailyWeatherViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDateTime = itemView.findViewById(R.id.tvDailyDateTime);
            tvDes = itemView.findViewById(R.id.tvDailyDes);
            tvMax = itemView.findViewById(R.id.tvDailyMax);
            tvMin = itemView.findViewById(R.id.tvDailyMin);
            tvWindSpeed = itemView.findViewById(R.id.tvDailyWindSpeed);
            tvHumidity = itemView.findViewById(R.id.tvDailyHumidity);
            tvUVIndex = itemView.findViewById(R.id.tvDailyUVI);
            tvSunrise = itemView.findViewById(R.id.tvDailySunraise);
            tvSunset = itemView.findViewById(R.id.tvDailySunset);
            imgDailyIcon = itemView.findViewById(R.id.imgDailyIcon);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            expandableLayout = itemView.findViewById(R.id.tableLayout);

            linearLayout.setOnClickListener(v -> {
                WeatherResponse.Daily daily = list.get(getAdapterPosition());
                daily.setExpanded(!daily.isExpanded());
                notifyItemChanged(getAdapterPosition());
            });

        }

        public void setContents(WeatherResponse.Daily daily) {
            tvDateTime.setText(milisToDateString(daily.getDt()));
            tvDes.setText(daily.getWeather().get(0).getDescription());
            tvMax.setText(Math.round(daily.getTemp().getMax())+"°");
            tvMin.setText(Math.round(daily.getTemp().getMin())+"°");
            tvWindSpeed.setText(daily.getWindSpeed()+" km/h");
            tvHumidity.setText(daily.getHumidity()+"%");
            tvUVIndex.setText(daily.getUvi()+"");
            tvSunrise.setText(milisToHourString(daily.getSunrise()));
            tvSunset.setText(milisToHourString(daily.getSunset()));

            String icon = daily.getWeather().get(0).getIcon();
            Picasso.with(context)
                    .load("https://openweathermap.org/img/wn/"+icon+"@2x.png")
                    .into(imgDailyIcon);
        }
    }
}
