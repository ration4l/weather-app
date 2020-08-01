package com.ration4l.nl.weather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ration4l.nl.weather.model.User;
import com.ration4l.nl.weather.utils.SharedPreferencesManager;

import static com.ration4l.nl.weather.utils.SharedPreferencesManager.saveEmail;
import static com.ration4l.nl.weather.utils.SharedPreferencesManager.saveLoginState;
import static com.ration4l.nl.weather.utils.SharedPreferencesManager.saveUsername;
import static com.ration4l.nl.weather.utils.Utils.hideKeyboard;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    public static final int REGISTER_REQUEST_CODE = 999;

    private EditText etEmail, etPassword;
    private Button btnLogin, btnNavRegister;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnNavRegister = findViewById(R.id.btnNavRegister);

        progressBar = findViewById(R.id.loading);
        progressBar.setVisibility(View.GONE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: Login");
                v.setBackground(getDrawable(R.drawable.background_button_onpress));
                progressBar.setVisibility(View.VISIBLE);
                hideKeyboard(LoginActivity.this, v);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        v.setBackground(getDrawable(R.drawable.background_button));
                        progressBar.setVisibility(View.GONE);
                        String email = etEmail.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        if (snapshot != null && snapshot.getValue() != null && snapshot.getChildrenCount() > 0) {
                            boolean isSuccessful = false;
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                User user = dataSnapshot.getValue(User.class);
                                if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                                    isSuccessful = true;
                                    saveLoginState(getApplicationContext(), true);
                                    saveUsername(getApplicationContext(), user.getUserName());
                                    saveEmail(getApplicationContext(), user.getEmail());
                                    Toast.makeText(getApplicationContext(),
                                            "Login Successfully",
                                            Toast.LENGTH_LONG).show();

                                    Log.e(TAG, "onDataChange: Login Successfully");
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                            }
                            if (!isSuccessful) {
                                Toast.makeText(getApplicationContext(),
                                        "Login unsuccessfully. Invalid email or password.",
                                        Toast.LENGTH_LONG).show();
                                Log.e(TAG, "onDataChange: Invalid email or password.");

                            }
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Login unsuccessfully. Invalid email or password.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: " + error.getMessage());
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnNavRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                v.setBackground(getDrawable(R.drawable.background_sub_button_onpress));
                startActivityForResult(intent, REGISTER_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REGISTER_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String email = data.getStringExtra("email");
                String password = data.getStringExtra("pass");
                Log.e(TAG, "onActivityResult: email: " + email + ", pass: " + password);
                etEmail.setText(email);
                etPassword.setText(password);
            } else {
                Log.e(TAG, "onActivityResult: returned null");
            }
        } else {
            Log.e(TAG, "onActivityResult: failed");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
        btnNavRegister.setBackground(getDrawable(R.drawable.background_sub_button));
    }
}