package com.example.expo;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class help extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // Setup toggle logic for each FAQ item
        setupToggle(findViewById(R.id.drag_icon), findViewById(R.id.answer2));
        setupToggle(findViewById(R.id.drag1), findViewById(R.id.answer3));
        setupToggle(findViewById(R.id.drag2), findViewById(R.id.answer4));
        setupToggle(findViewById(R.id.drag3), findViewById(R.id.answer5));
        setupToggle(findViewById(R.id.drag4), findViewById(R.id.answer6));
        setupToggle(findViewById(R.id.drag6), findViewById(R.id.answer8));
        setupToggle(findViewById(R.id.drag7), findViewById(R.id.answer7));
        setupToggle(findViewById(R.id.drag8), findViewById(R.id.answer1));
        setupToggle(findViewById(R.id.drag9), findViewById(R.id.answer9));
    }

    private void setupToggle(ImageView toggleButton, final TextView answerView) {
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerView.getVisibility() == View.GONE) {
                    answerView.setVisibility(View.VISIBLE);
                } else {
                    answerView.setVisibility(View.GONE);
                }
            }
        });
    }
}