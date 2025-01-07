package com.example.expo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class successful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful);

        // Handle Continue Button Click
        RelativeLayout continueButton = findViewById(R.id.continue_button);
        continueButton.setOnClickListener(v -> {
            // Navigate to Login Activity or Display a Toast
            Toast.makeText(successful.this, "Continue to login clicked!", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(SuccessActivity.this, LoginActivity.class);
            // startActivity(intent);
        });
    }
}