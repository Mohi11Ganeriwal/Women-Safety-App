package com.example.expo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expo.appwrite.Appwrite;
import com.example.expo.appwrite.AppwriteResponse;
import com.example.expo.util.SharedPrefsUtil;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.appwrite.models.User;

public class signup extends AppCompatActivity {

    private EditText usernameBox, emailBox, passwordBox, confirmPasswordBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Appwrite appwrite = Appwrite.getInstance(this);

        // Initialize UI components
        usernameBox = findViewById(R.id.usernameBox);
        emailBox = findViewById(R.id.EmailBox);
        passwordBox = findViewById(R.id.passwordBox);
        confirmPasswordBox = findViewById(R.id.ConfirmpasswordBox);

        if (SharedPrefsUtil.isLoggedIn(this)) {
            startLoadingScreen();
        }

        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLoginPage();
            }
        });

        findViewById(R.id.signupButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndSignUp(appwrite);
            }
        });
    }

    private void validateAndSignUp(Appwrite appwrite) {
        String username = usernameBox.getText().toString().trim();
        String email = emailBox.getText().toString().trim();
        String password = passwordBox.getText().toString();
        String confirmPassword = confirmPasswordBox.getText().toString();

        // Validate fields
        if (TextUtils.isEmpty(username)) {
            showToast("Username cannot be empty");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            showToast("Email cannot be empty");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Invalid email format");
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

        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return;
        }
        Signup(appwrite, username, email,password);
    }

    private void Signup(Appwrite appwrite, String username, String email, String password){
        Runnable networkTask = () -> {
            AppwriteResponse<Boolean> response = appwrite.auth.createAccount(email, password, username);

            if (response instanceof AppwriteResponse.Success) {
                Log.d("Expo_Logs", "Account created successfully!");
                showToast("Account created successfully!");
                clearFields();
            } else if (response instanceof AppwriteResponse.Error) {
                AppwriteResponse.Error<Boolean> errorResponse = (AppwriteResponse.Error<Boolean>) response;
                Log.d("Expo_Logs", errorResponse.getCode() + " : " + errorResponse.getMessage());
            }
        };

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(networkTask);
        executorService.shutdown();

    }

    private void goToLoginPage(){
        Intent myIntent = new Intent(this, MainActivity.class);
        this.startActivity(myIntent);
    }

    private void goToHomePage(){
        Intent myIntent = new Intent(this, frontpage.class);
        this.startActivity(myIntent);
    }

    private void startLoadingScreen(){
        Intent myIntent = new Intent(this, LoadingActivity.class);
        this.startActivity(myIntent);
    }

    private void showToast(String message) {
        Toast.makeText(signup.this, message, Toast.LENGTH_SHORT).show();
    }

    private void clearFields(){
        usernameBox.setText("");
        emailBox.setText("");
        passwordBox.setText("");
        confirmPasswordBox.setText("");
    }
}