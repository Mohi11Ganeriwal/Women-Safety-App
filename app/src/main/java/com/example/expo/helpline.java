package com.example.expo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class helpline extends AppCompatActivity {

    ImageView policeDialer, nationalDialer, ambulanceDialer, pregnancyMedicDialer, fireServiceDialer,
            womensDialer, childrensDialer, roadDialer, railwayDialer, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);
        policeDialer = findViewById(R.id.policeDial);
        nationalDialer = findViewById(R.id.nationalDial);
        ambulanceDialer = findViewById(R.id.ambulanceDial);
        pregnancyMedicDialer = findViewById(R.id.pregnancyDial);
        fireServiceDialer = findViewById(R.id.fireDial);
        womensDialer = findViewById(R.id.womensDial);
        childrensDialer = findViewById(R.id.childDial);
        roadDialer = findViewById(R.id.roadDial);
        railwayDialer = findViewById(R.id.railwayDial);

        findViewById(R.id.policeDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "100");
            }
        });

        findViewById(R.id.nationalDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "112");
            }
        });

        findViewById(R.id.ambulanceDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "108");
            }
        });

        findViewById(R.id.pregnancyDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "102");
            }
        });

        findViewById(R.id.fireDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "101");
            }
        });

        findViewById(R.id.womensDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "1091");
            }
        });

        findViewById(R.id.childDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "1098");
            }
        });

        findViewById(R.id.roadDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "1073");
            }
        });

        findViewById(R.id.railwayDial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber( "182");
            }
        });

        findViewById(R.id.arrow_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void finish() {
        super.finish();
    }

    public void dialNumber(String phoneNumber) {
        Context context = helpline.this;
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            Toast.makeText(context, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }
        Uri phoneUri = Uri.parse("tel:" + phoneNumber);
        Intent surf = new Intent(Intent.ACTION_DIAL, phoneUri);
        startActivity(surf);
    }
}