package com.example.expo;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expo.util.FriendManager;
import com.google.android.material.button.MaterialButton;

public class friends extends AppCompatActivity {

    private EditText enterNumberText;
    private FriendManager friendManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friends);

        enterNumberText = findViewById(R.id.enter_number_text);
        RelativeLayout addFriendsButton = findViewById(R.id.add_friends_button);


        friendManager = new FriendManager(this);

        addFriendsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFriend();
            }
        });


    }

    private void addFriend() {
        String phoneNumber = enterNumberText.getText().toString().trim();

        if (TextUtils.isEmpty(phoneNumber)) {
            showError("Phone number cannot be empty.");
            return;
        }

        if (phoneNumber.length() != 10) {
            showError("Phone number must be 10 digits.");
            return;
        }

        try {
            Long.parseLong(phoneNumber);
        } catch (NumberFormatException e) {
            showError("Invalid phone number format.");
            return;
        }


        friendManager.saveFriend(phoneNumber);
        Toast.makeText(this, "Friend added successfully!", Toast.LENGTH_SHORT).show();
        enterNumberText.setText("");

        Intent myIntent = new Intent(this, friendslide.class);
        this.startActivity(myIntent);

    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}