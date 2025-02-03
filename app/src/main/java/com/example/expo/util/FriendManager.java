package com.example.expo.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class FriendManager {

    private static final String PREF_NAME = "friend_prefs";
    private static final String KEY_FRIENDS = "friends";

    private SharedPreferences sharedPreferences;

    public FriendManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveFriend(String phoneNumber) {
        Set<String> friends = getFriends();
        friends.add(phoneNumber); // Add the new number
        saveFriends(friends);
    }

    public Set<String> getFriends() {
        Set<String> friends = sharedPreferences.getStringSet(KEY_FRIENDS, new HashSet<>());
        return new HashSet<>(friends); // Return a new HashSet to prevent modification
    }


    private void saveFriends(Set<String> friends) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(KEY_FRIENDS, friends);
        editor.apply();
    }
}