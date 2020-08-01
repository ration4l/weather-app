package com.ration4l.nl.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Space;

import com.ration4l.nl.weather.utils.SharedPreferencesManager;

import static com.ration4l.nl.weather.utils.SharedPreferencesManager.getFirstUseState;
import static com.ration4l.nl.weather.utils.SharedPreferencesManager.getLoginState;
import static com.ration4l.nl.weather.utils.SharedPreferencesManager.saveFirstUseState;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        if (getFirstUseState(getApplicationContext())) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            saveFirstUseState(getApplicationContext(), true);
        } else {
            if (getLoginState(getApplicationContext())){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }

        }
        finish();
    }

}