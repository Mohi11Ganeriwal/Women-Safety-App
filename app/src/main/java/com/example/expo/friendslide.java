package com.example.expo;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import com.example.expo.util.FriendManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class friendslide extends AppCompatActivity {

    private RecyclerView friendsRecyclerView;
    private TextView noFriendsMessage;
    private FriendAdapter friendAdapter;
    private FriendManager friendManager;
    private double latitude = 0.0;
    private double longitude = 0.0;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friendslide);

        // Get data from Intent
        latitude = getIntent().getDoubleExtra("latitude", 0.0);
        longitude = getIntent().getDoubleExtra("longitude", 0.0);

        // Initialize UI components
        friendsRecyclerView = findViewById(R.id.friends_recycler_view);
        noFriendsMessage = findViewById(R.id.no_friends_message);

        if (friendsRecyclerView == null || noFriendsMessage == null) {
            throw new NullPointerException("Check XML IDs: RecyclerView or TextView is null.");
        }

        // Initialize FriendManager
        friendManager = new FriendManager(this);

        // Get friends list
        Set<String> friends = friendManager.getFriends();
        if (friends == null) {
            friends = new HashSet<>();
        }

        // Set up RecyclerView
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendAdapter = new FriendAdapter((Set<String>) new ArrayList<>(friends), this, latitude, longitude);
        friendsRecyclerView.setAdapter(friendAdapter);

        // Update UI
        updateFriendListVisibility();
    }

    private void updateFriendListVisibility() {
        Set<String> friends = friendManager.getFriends();
        if (friends == null || friends.isEmpty()) {
            friendsRecyclerView.setVisibility(View.GONE);
            noFriendsMessage.setVisibility(View.VISIBLE);
        } else {
            friendsRecyclerView.setVisibility(View.VISIBLE);
            noFriendsMessage.setVisibility(View.GONE);
        }
    }
}
