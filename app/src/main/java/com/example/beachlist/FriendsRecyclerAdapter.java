package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FriendsRecyclerAdapter extends RecyclerView.Adapter<FriendsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<FriendsData> list;

    public FriendsRecyclerAdapter(Context context, List<FriendsData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FriendsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_friend_list_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsRecyclerAdapter.MyViewHolder holder, final int position) {
        holder.profilePic.setImageResource(list.get(position).getImageProfile());
        holder.firstName.setText(list.get(position).getFirstName());
        holder.lastName.setText(list.get(position).getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedFriend.class);
                intent.putExtra("Profile Picture",list.get(position).getImageProfile());
                intent.putExtra("First Name",list.get(position).getFirstName());
                intent.putExtra("Last Name",list.get(position).getLastName());
                context.startActivity(intent);
            }
        });
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