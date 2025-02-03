package com.example.expo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {

    private List<String> friendList;

    public FriendAdapter(Set<String> friends) {
        this.friendList = new ArrayList<>(friends);
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView contactIcon;
        TextView friendTextView;
        ImageView moreOptions;

        public FriendViewHolder(View itemView) {
            super(itemView);
            contactIcon = itemView.findViewById(R.id.contact_icon);
            friendTextView = itemView.findViewById(R.id.friend_name);
            moreOptions = itemView.findViewById(R.id.more_options);
        }
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        String friend = friendList.get(position);
        holder.friendTextView.setText(friend);
    }

    @Override
    public int getItemCount() {
        return friendList.size();
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friends_list_item, parent, false); // Use your custom layout
        return new FriendViewHolder(view);
    }


}
