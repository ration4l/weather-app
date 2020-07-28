package com.ration4l.nl.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;

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
                return addressList.get(0).getAddressLine(0);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
