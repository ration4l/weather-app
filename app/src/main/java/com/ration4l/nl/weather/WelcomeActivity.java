package com.ration4l.nl.weather;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ration4l.nl.weather.utils.SharedPreferencesManager;


public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";
    private EditText etUsername;
    private FloatingActionButton fabFinish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitivity_welcome);

        etUsername = findViewById(R.id.tvUsername);
        fabFinish = findViewById(R.id.btnFinish);

        fabFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = etUsername.getText().toString().trim();
                Log.e(TAG, "onClick: username: " + username );
                if (!TextUtils.isEmpty(username)) {
                    SharedPreferencesManager
                            .getDefaultSharedPreferences(getApplicationContext()).edit()
                            .putString(SharedPreferencesManager.KEY_USERNAME, username)
                            .apply();
                    String result = SharedPreferencesManager.getDefaultSharedPreferences(getApplicationContext())
                            .getString(SharedPreferencesManager.KEY_USERNAME, "null");
                    Log.e(TAG, "onClick: result: " + result);
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your name",
                            10*1000)
                            .show();
                }

            }
        });


    }
}