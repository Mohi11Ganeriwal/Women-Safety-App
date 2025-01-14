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

    private TextView signupBtn, forgotPass;
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
        forgotPass = findViewById(R.id.forgotPassword);
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

        findViewById(R.id.forgotPassword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToForgotPassword();
            }
        });
    }

    private void goToSignupPage(){
        Intent myIntent = new Intent(this, signup.class);
        this.startActivity(myIntent);
        finish();
    }

    private void goToForgotPassword(){
        Intent myIntent = new Intent(this, forgetpassword.class);
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
                Log.d("Expo_Logs", "Account login successful");
                SharedPrefsUtil.setLoggedIn(MainActivity.this, true);
                tryGetUser(appwrite);
            } else if (response instanceof AppwriteResponse.Error) {
                AppwriteResponse.Error<Boolean> errorResponse = (AppwriteResponse.Error<Boolean>) response;
                Log.d("Expo_Logs", errorResponse.getCode() + " : " + errorResponse.getMessage());
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        };
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(networkTask);
        executorService.shutdown();

    }

    private void tryGetUser(Appwrite appwrite){
        Runnable networkTask = () -> {
            AppwriteResponse<User<Map<String, Object>>> response = appwrite.auth.getUser();
            if (response instanceof AppwriteResponse.Success) {
                Log.d("Expo_Logs_LoadingActivity", "Account get successful");
                goToHomePage();
            } else if (response instanceof AppwriteResponse.Error) {
                SharedPrefsUtil.setLoggedIn(MainActivity.this, false);
                AppwriteResponse.Error<User<Map<String, Object>>> errorResponse = (AppwriteResponse.Error<User<Map<String, Object>>>) response;
                Log.d("Expo_Logs", "Error" + errorResponse.getCode() + " : " + errorResponse.getMessage());
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