package com.example.expo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.expo.util.ContactFetcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> implements ContactFetcher.FetchContactNameCallback {

    private List<String> friendList;
    private ContactFetcher contactFetcher;
    private Map<String, String> contactNames = new HashMap<>();
    private Context context;

    public FriendAdapter(Set<String> friends, Context context) {
        this.friendList = new ArrayList<>(friends);
        this.context = context;
        this.contactFetcher = new ContactFetcher(context);

        String[] numbers = friendList.toArray(new String[0]);

        contactFetcher.getContactNamesAsync(numbers, this);
    }

    @Override
    public void onContactNameFetched(Map<String, String> contactNames) {
        this.contactNames = contactNames;
        new Handler(Looper.getMainLooper()).post(() -> notifyDataSetChanged());
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView contactIcon;
        TextView friendTextView;
        TextView friendNumberTextView;
        ImageView moreOptions;

        public FriendViewHolder(View itemView) {
            super(itemView);
            contactIcon = itemView.findViewById(R.id.contact_icon);
            friendTextView = itemView.findViewById(R.id.friend_name);
            friendNumberTextView = itemView.findViewById(R.id.friend_number);
            moreOptions = itemView.findViewById(R.id.more_options);
        }
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        String friendNumber = friendList.get(position);
        String friendName = contactNames.getOrDefault(friendNumber, "Unknown");

        holder.friendTextView.setText(friendName);
        holder.friendNumberTextView.setText(friendNumber);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false);
        return new FriendViewHolder(view);
    }
}
