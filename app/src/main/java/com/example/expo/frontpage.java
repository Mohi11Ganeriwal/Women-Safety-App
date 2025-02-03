package com.example.expo;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class frontpage extends AppCompatActivity {
    private ImageView helpline, trackme, record, messages;
    private Button friendsBtn;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
        helpline = findViewById(R.id.helpline_icon);

        requestLocationPermission();

        findViewById(R.id.helpline_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHelpline();
            }
        });

        findViewById(R.id.trackme_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTrackMe();
            }
        });

        findViewById(R.id.profile_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToProfile();
            }
        });

        findViewById(R.id.record_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRecord();
            }
        });

        findViewById(R.id.messages_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessages();
            }
        });

        findViewById(R.id.add_friend_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddFriends();
            }
        });

        WebView mapWebView = findViewById(R.id.mapWebView);

        WebSettings webSettings = mapWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Load the map URL (e.g., Google Maps or OpenStreetMap)
        String mapUrl = "https://www.google.com/maps";
        mapWebView.setWebViewClient(new WebViewClient());
        mapWebView.loadUrl(mapUrl);
    }

    private void goToHelpline(){
        Intent myIntent = new Intent(this, helpline.class);
        this.startActivity(myIntent);
    }

    private void goToTrackMe(){
        Intent myIntent = new Intent(this, Trackme.class);
        this.startActivity(myIntent);
    }

    private void goToRecord(){
        Intent myIntent = new Intent(this, record.class);
        this.startActivity(myIntent);
    }

    private void goToMessages(){
        Intent myIntent = new Intent(this, messages.class);
        this.startActivity(myIntent);
    }

    private void goToProfile(){
        Intent myIntent = new Intent(this, profile.class);
        this.startActivity(myIntent);
    }

    private void goToAddFriends(){
        Intent myIntent = new Intent(this, friends.class);
        this.startActivity(myIntent);
    }

    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Request the permission
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE
            );
        } else {
            // Permission already granted
            Toast.makeText(this, "Location permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Location permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}