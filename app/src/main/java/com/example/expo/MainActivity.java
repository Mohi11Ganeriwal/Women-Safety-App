package com.example.expo;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expo.appwrite.Appwrite;
import com.example.expo.appwrite.AppwriteResponse;
import com.example.expo.util.SharedPrefsUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.appwrite.models.User;

public class MainActivity extends AppCompatActivity {

    private TextView signupBtn;
    private EditText usernameBox, passwordBox;

    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupBtn = findViewById(R.id.signup);
        usernameBox = findViewById(R.id.usernameBox);
        passwordBox = findViewById(R.id.passwordBox);
        loginBtn = findViewById(R.id.loginButton);
        Appwrite appwrite = Appwrite.getInstance(this);

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSignupPage();
            }
        });

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndLogin(appwrite);
            }
        });
    }

    private void goToSignupPage(){
        Intent myIntent = new Intent(this, signup.class);
        this.startActivity(myIntent);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void validateAndLogin(Appwrite appwrite) {
        String username = usernameBox.getText().toString().trim();
        String password = passwordBox.getText().toString();

        // Validate fields
        if (TextUtils.isEmpty(username)) {
            showToast("Username cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            showToast("Password cannot be empty");
            return;
        }

        if (password.length() < 8) {
            showToast("Password must be at least 8 characters");
            return;
        }

        Runnable networkTask = () -> {
            AppwriteResponse<Boolean> response = appwrite.auth.login(username, password);
            if (response instanceof AppwriteResponse.Success) {
                Log.d("Expo_Logs", "Account get successful");
                SharedPrefsUtil.setLoggedIn(MainActivity.this, true);
                goToHomePage();
            } else if (response instanceof AppwriteResponse.Error) {
                AppwriteResponse.Error<Boolean> errorResponse = (AppwriteResponse.Error<Boolean>) response;
                Log.d("Expo_Logs", errorResponse.getCode() + " : " + errorResponse.getMessage());
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(networkTask);
        executorService.shutdown();

    }

    private void goToHomePage(){
        Intent myIntent = new Intent(this, frontpage.class);
        this.startActivity(myIntent);
    }
}