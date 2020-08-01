package com.ration4l.nl.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

import com.ration4l.nl.weather.R;
import com.ration4l.nl.weather.model.Latlng;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by ThanhLongNL on 10-Jul-20.
 */

public class Utils {
    private static final String TAG = "Utils";

    public static String milisToDateTimeString(long milis) {
        Date date = new Date(milis * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String milisToDateString(long milis) {
        Date date = new Date(milis * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM");
        return simpleDateFormat.format(date);
    }

    public static String milisToHourString(long milis) {
        Date date = new Date(milis * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        return simpleDateFormat.format(date);
    }

    public static int getCurrentHour() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static void hideKeyboard(Activity activity, View view) {
        view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    @Nullable
    public static Latlng getLocationLatLngFromAddress(Context context, String addressStr) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocationName(addressStr, 5);
            if (addressList.get(0) != null) {
                Latlng latlng = new Latlng();
                latlng.setLat(addressList.get(0).getLatitude());
                latlng.setLng(addressList.get(0).getLongitude());
                return latlng;
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static String getAddressFromLatLng(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 5);
            if (addressList.get(0) != null) {
                String[] address = addressList.get(0).getAddressLine(0).split(",");
                String result = address[address.length - 2] + ", " + address[address.length - 1];
                return result;
            } else {
                return null;
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            Log.e(TAG, "getAddressFromLatLng: "+a.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static int matchIcon(String oldIcon){

        if (oldIcon.equals("01d")) {
            return R.drawable.icon_sun_clear_sky;
        } else if (oldIcon.equals("01n")) {
            return R.drawable.icons_full_moon;
        } else if (oldIcon.equals("02d")) {
            return R.drawable.icon_partly_cloudy_day;
        } else if (oldIcon.equals("03d") || oldIcon.equals("03n")) {
            return R.drawable.icon_clouds;
        } else if (oldIcon.equals("04d") || oldIcon.equals("04n")) {
            return R.drawable.icons_broken_cloud;
        } else if (oldIcon.equals("09d") || oldIcon.equals("09n")) {
            return R.drawable.icons_moderate_rain;
        } else if (oldIcon.equals("10d") || oldIcon.equals("10n")) {
            return R.drawable.icons_rainfall;
        } else if (oldIcon.equals("11d") || oldIcon.equals("11n")) {
            return R.drawable.icons_cloud_lightning;
        } else if (oldIcon.equals("13d") || oldIcon.equals("13n")) {
            return R.drawable.icons_snow_storm;
        } else if (oldIcon.equals("50d") || oldIcon.equals("50n")) {
            return R.drawable.icons_wind;
        }

        return 0;

    }
}
