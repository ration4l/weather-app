package com.ration4l.nl.weather.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ration4l.nl.weather.CurrentWeatherFragment;
import com.ration4l.nl.weather.DailyWeatherForecastFragment;

/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return CurrentWeatherFragment.newInstance();
            case 1: return DailyWeatherForecastFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0: return "TODAY";
            case 1: return "7 DAYS";
        }
        return null;
    }
}
