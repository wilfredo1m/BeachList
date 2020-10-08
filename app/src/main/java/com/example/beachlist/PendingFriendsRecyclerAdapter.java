package com.example.beachlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PendingFriendsRecyclerAdapter extends RecyclerView.Adapter<PendingFriendsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<FriendsData> list;

    public PendingFriendsRecyclerAdapter(Context context, List<FriendsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PendingFriendsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_pending_friend_row,parent,false);
        return new MyViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull PendingFriendsRecyclerAdapter.MyViewHolder holder, int position) {
        holder.profilePic.setImageResource(list.get(position).getImageProfile());
        holder.firstName.setText(list.get(position).getFirstName());
        holder.lastName.setText(list.get(position).getLastName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView firstName, lastName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            firstName = itemView.findViewById(R.id.friend_first_name);
            lastName = itemView.findViewById(R.id.friend_last_name);
        }
    }
}
