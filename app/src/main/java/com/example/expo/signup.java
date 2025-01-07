package com.example.expo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity {

    private EditText usernameBox, emailBox, passwordBox, confirmPasswordBox;
    private TextView loginTextView, signupTextView;
    private Button loginButton, signupTab, loginTab;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        usernameBox = findViewById(R.id.usernameBox);
        emailBox = findViewById(R.id.EmailBox);
        passwordBox = findViewById(R.id.passwordBox);
        confirmPasswordBox = findViewById(R.id.ConfirmpasswordBox);
        loginTextView = findViewById(R.id.login);
        signupTextView = findViewById(R.id.signup);
        loginButton = findViewById(R.id.loginButton);
        signupTab = findViewById(R.id.signupTab);
        loginTab = findViewById(R.id.loginTab);

        // Set up login button click listener
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameBox.getText().toString().trim();
                String email = emailBox.getText().toString().trim();
                String password = passwordBox.getText().toString().trim();
                String confirmPassword = confirmPasswordBox.getText().toString().trim();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(signup.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(signup.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(signup.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    // Add your logic here for sign up
                }
            }
        });

        // Set up login text click listener
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signup.this, "Login clicked", Toast.LENGTH_SHORT).show();
                // Add your login logic here
            }
        });

        // Set up signup text click listener
        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signup.this, "Sign Up clicked", Toast.LENGTH_SHORT).show();
                // Add your sign-up logic here
            }
        });

        // Set up tab click listeners
        signupTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signup.this, "Switched to Sign Up Tab", Toast.LENGTH_SHORT).show();
                // Add logic for switching to sign-up tab
            }
        });

        loginTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(signup.this, "Switched to Login Tab", Toast.LENGTH_SHORT).show();
                // Add logic for switching to login tab
            }
        });
    }
}