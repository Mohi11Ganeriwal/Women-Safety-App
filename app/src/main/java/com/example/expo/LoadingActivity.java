package com.example.expo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expo.appwrite.Appwrite;
import com.example.expo.appwrite.AppwriteResponse;
import com.example.expo.util.SharedPrefsUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.appwrite.models.User;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        Appwrite appwrite = Appwrite.getInstance(this);
        if (SharedPrefsUtil.isLoggedIn(this)) {
            Runnable networkTask = () -> {
                AppwriteResponse<User<Map<String, Object>>> response = appwrite.auth.getUser();
                if (response instanceof AppwriteResponse.Success) {
                    Log.d("Expo_Logs", "Account get successful");
                    goToHomePage();
                } else if (response instanceof AppwriteResponse.Error) {
                    AppwriteResponse.Error<User<Map<String, Object>>> errorResponse = (AppwriteResponse.Error<User<Map<String, Object>>>) response;
                    Log.d("Expo_Logs", errorResponse.getCode() + " : " + errorResponse.getMessage());
                    Toast.makeText(this, errorResponse.getMessage(), Toast.LENGTH_LONG).show();
                    goToSignupPage();
                } else {
                    goToSignupPage();
                }
            };
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(networkTask);
            executorService.shutdown();
        }
    }

    private void goToHomePage(){
        Intent myIntent = new Intent(this, frontpage.class);
        this.startActivity(myIntent);
    }

    private void goToSignupPage(){
        Intent myIntent = new Intent(this, signup.class);
        this.startActivity(myIntent);
    }
}