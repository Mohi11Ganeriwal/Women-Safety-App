package com.example.expo;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.expo.util.FriendManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class friendslide extends AppCompatActivity {

    private RecyclerView friendsRecyclerView;
    private TextView noFriendsMessage;
    private FriendAdapter friendAdapter;
    private FriendManager friendManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_friendslide);

        friendsRecyclerView = findViewById(R.id.friends_recycler_view);
        noFriendsMessage = findViewById(R.id.no_friends_message);

        friendManager = new FriendManager(this); // Initialize FriendManager

        // Set up RecyclerView
        friendsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        friendAdapter = new FriendAdapter(friendManager.getFriends(), friendslide.this); // Get friends from FriendManager
        friendsRecyclerView.setAdapter(friendAdapter);

        updateFriendListVisibility();
    }

    private void updateFriendListVisibility() {
        Set<String> friends = friendManager.getFriends();
        if (friends.isEmpty()) {
            friendsRecyclerView.setVisibility(View.GONE);
            noFriendsMessage.setVisibility(View.VISIBLE);
        } else {
            friendsRecyclerView.setVisibility(View.VISIBLE);
            noFriendsMessage.setVisibility(View.GONE);
        }
    }
}