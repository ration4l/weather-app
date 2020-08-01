package com.ration4l.nl.weather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
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

import static com.ration4l.nl.weather.utils.Utils.hideKeyboard;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private EditText etEmail, etUsername, etPassword;
    private Button btnReg, btnBack;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnReg = findViewById(R.id.btnRegister);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.loading);

        progressBar.setVisibility(View.GONE);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("users");

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: RegisterActivity" );
                v.setBackgroundColor(getColor(R.color.colorPrimaryDark));
                progressBar.setVisibility(View.VISIBLE);
                hideKeyboard(RegisterActivity.this, v);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        v.setBackgroundColor(getColor(R.color.colorPrimary));
                        progressBar.setVisibility(View.GONE);
                        Log.e(TAG, "onDataChange: ");
                        String email = etEmail.getText().toString().trim();
                        String name = etUsername.getText().toString().trim();
                        String password = etPassword.getText().toString().trim();

                        if (!TextUtils.isEmpty(email)
                                && !TextUtils.isEmpty(name)
                                && !TextUtils.isEmpty(password)
                                && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {

                            if (snapshot != null && snapshot.getValue() != null && snapshot.getChildrenCount() > 0) {
                                Boolean isDuplicated = false;
                                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                                    User user = dataSnapshot.getValue(User.class);
                                    if (email.equals(user.getEmail())) {
                                        isDuplicated = true;
                                        break;
                                    }
                                }
                                Log.e(TAG, "onDataChange: "+isDuplicated );
                                if (isDuplicated) {
                                    Toast.makeText(getApplicationContext(),
                                            "This email's been used, plese choose another email",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    String id = databaseReference.push().getKey();
                                    User user = new User(email, password, name, id);
                                    databaseReference.child(id).setValue(user);

                                    Toast.makeText(getApplicationContext(),
                                            "Register successfully",
                                            Toast.LENGTH_LONG).show();
                                    Log.e(TAG, "onDataChange: Register successfully");
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    intent.putExtra("email", email);
                                    intent.putExtra("pass", password);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }
                            } else {
                                String id = databaseReference.push().getKey();
                                User user = new User(email, password, name, id);
                                databaseReference.child(id).setValue(user);
                                Toast.makeText(getApplicationContext(),
                                        "Register successfully",
                                        Toast.LENGTH_LONG).show();

                                Log.e(TAG, "onDataChange: Register successfully");
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                intent.putExtra("email", email);
                                intent.putExtra("pass", password);
                                setResult(RESULT_OK, intent);
                                finish();
                            }
                        } else {

                            Toast.makeText(getApplicationContext(),
                                    "Invalid inputs",
                                    Toast.LENGTH_LONG).show();
                            Log.e(TAG, "onDataChange: Invalid inputs" );
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled: "+error.getMessage() );
                        v.setBackgroundColor(getColor(R.color.colorPrimary));
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
}