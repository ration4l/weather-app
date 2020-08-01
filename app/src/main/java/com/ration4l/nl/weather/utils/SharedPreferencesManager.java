package com.ration4l.nl.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ThanhLongNL on 31-Jul-20.
 */

public class SharedPreferencesManager {
    public static final String KEY_FIRST_USE = "first_use";
    public static final String KEY_LOGIN = "login";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_UNIT = "unit";
    private static SharedPreferences sharedPref;

    private SharedPreferencesManager(Context context) {
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        if (sharedPref == null) {
            sharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        return sharedPref;
    }

    public static void saveUsername(Context context, String value) {
        SharedPreferencesManager.getDefaultSharedPreferences(context)
                .edit().putString(SharedPreferencesManager.KEY_USERNAME, value)
                .apply();
    }

    public static void saveEmail(Context context, String value) {
        SharedPreferencesManager.getDefaultSharedPreferences(context)
                .edit().putString(SharedPreferencesManager.KEY_EMAIL, value)
                .apply();
    }

    public static void saveLoginState(Context context, boolean value) {
        SharedPreferencesManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(SharedPreferencesManager.KEY_LOGIN, value)
                .apply();
    }

    public static void saveFirstUseState(Context context, boolean value) {
        SharedPreferencesManager.getDefaultSharedPreferences(context)
                .edit().putBoolean(SharedPreferencesManager.KEY_FIRST_USE, value)
                .apply();
    }

    public static void saveUnit(Context context, String value) {
        SharedPreferencesManager.getDefaultSharedPreferences(context)
                .edit().putString(SharedPreferencesManager.KEY_UNIT, value)
                .apply();
    }

    public static String getUsername(Context context) {
        return SharedPreferencesManager.getDefaultSharedPreferences(context)
                .getString(SharedPreferencesManager.KEY_USERNAME, "");
    }

    public static String getEmail(Context context) {
        return SharedPreferencesManager.getDefaultSharedPreferences(context)
                .getString(SharedPreferencesManager.KEY_EMAIL, "");
    }

    public static boolean getLoginState(Context context) {
        return SharedPreferencesManager.getDefaultSharedPreferences(context)
                .getBoolean(SharedPreferencesManager.KEY_LOGIN, false);
    }

    public static boolean getFirstUseState(Context context) {
        return SharedPreferencesManager.getDefaultSharedPreferences(context)
                .getBoolean(SharedPreferencesManager.KEY_FIRST_USE, true);
    }

    public static String getUnit(Context context) {
        return SharedPreferencesManager.getDefaultSharedPreferences(context)
                .getString(SharedPreferencesManager.KEY_UNIT, "metric");
    }
}
