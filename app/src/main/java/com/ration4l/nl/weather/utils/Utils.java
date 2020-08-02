package com.ration4l.nl.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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

    public static SpannableString underline(String text){
        SpannableString result = new SpannableString(text);
        result.setSpan(new UnderlineSpan(), 0, text.length(), 0);
        return result;
    }

    public static String standardizeString(String input) {
        String[] array = input.split(" ");
        array[0] = String.valueOf(array[0].charAt(0)).toUpperCase() + array[0].substring(1);
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i] + " ";
        }
        return result.trim();
    }

    public static String milisToDateTimeString(long milis) {
        Date date = new Date(milis * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM, HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String milisToDateString(long milis) {
        Date date = new Date(milis * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMMM");
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
            if (addressList != null) {
                if (addressList.get(0) != null) {
                    Latlng latlng = new Latlng();
                    latlng.setLat(addressList.get(0).getLatitude());
                    latlng.setLng(addressList.get(0).getLongitude());
                    return latlng;
                } else {
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Nullable
    public static String getAddressFromLatLng(Context context, double lat, double lng) {
        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> addressList = geocoder.getFromLocation(lat, lng, 5);
            if (addressList != null && addressList.size() > 0) {
                try {
                    if (addressList.get(0) != null) {
                        String[] address = addressList.get(0).getAddressLine(0).split(",");
                        return address[address.length - 2] + ", " + address[address.length - 1];
                    }
                } catch (ArrayIndexOutOfBoundsException a) {
                    Log.e(TAG, "getAddressFromLatLng: " + a.getMessage());
                }
            }
        } catch (ArrayIndexOutOfBoundsException a) {
            Log.e(TAG, "getAddressFromLatLng: " + a.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static int matchIcon(String oldIcon) {

        switch (oldIcon) {
            case "01d":
                return R.drawable.icon_sun_clear_sky;
            case "01n":
                return R.drawable.icons_full_moon;
            case "02d":
                return R.drawable.icon_partly_cloudy_day;
            case "03d":
            case "03n":
                return R.drawable.icon_clouds;
            case "04d":
            case "04n":
                return R.drawable.icons_broken_cloud;
            case "09d":
            case "09n":
                return R.drawable.icons_moderate_rain;
            case "10d":
            case "10n":
                return R.drawable.icons_rainfall;
            case "11d":
            case "11n":
                return R.drawable.icons_cloud_lightning;
            case "13d":
            case "13n":
                return R.drawable.icons_snow_storm;
            case "50d":
            case "50n":
                return R.drawable.icons_wind;
        }

        return 0;

    }
}
