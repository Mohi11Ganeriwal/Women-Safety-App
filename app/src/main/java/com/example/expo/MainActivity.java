package com.example.expo;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find your view
        Button button = findViewById(R.id.loginButton);

        // Create a GradientDrawable programmatically
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);  // Set shape to rectangle
        drawable.setColor(0xFFFFD230);                 // Set solid color
        drawable.setCornerRadius(16 * getResources().getDisplayMetrics().density); // Set corner radius (16dp)

        // Apply the drawable as the background of the button
        button.setBackground(drawable);
    }
}