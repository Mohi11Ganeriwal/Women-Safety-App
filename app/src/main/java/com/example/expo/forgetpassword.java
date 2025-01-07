package com.example.expo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class forgetpassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText emailInput = findViewById(R.id.email_input);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button resetPasswordButton = findViewById(R.id.reset_password_button);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView arrow = findViewById(R.id.arrow);

        // Set onClick listener for Reset Password button
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString().trim();
                if (email.isEmpty()) {
                    Toast.makeText(forgetpassword.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else {
                    // Simulate password reset functionality
                    Toast.makeText(forgetpassword.this, "Password reset link sent to " + email, Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Set onClick listener for the arrow (back button)
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Navigate back to the previous screen
            }
        });
    }
}