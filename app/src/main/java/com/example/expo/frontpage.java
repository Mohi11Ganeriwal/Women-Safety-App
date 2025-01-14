package com.example.expo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class frontpage extends AppCompatActivity {
    private ImageView helpline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frontpage);
        helpline = findViewById(R.id.helpline_icon);

        findViewById(R.id.helpline_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHelpline();
            }
        });
    }

    private void goToHelpline(){
        Intent myIntent = new Intent(this, helpline.class);
        this.startActivity(myIntent);
    }
}