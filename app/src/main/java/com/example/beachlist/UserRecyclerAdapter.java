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

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public UserRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public UserRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_user_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRecyclerAdapter.MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(list.get(position).child("data").getValue(UserData.class).getImageUrl())
                .centerCrop()
                .into(holder.profilePic);
        holder.firstName.setText(list.get(position).child("data").getValue(UserData.class).getFirstName());
        holder.lastName.setText(list.get(position).child("data").getValue(UserData.class).getLastName());

        // when a pending friend is clicked, the position is taken to get that person info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedUser.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePic;
        TextView firstName, lastName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic = itemView.findViewById(R.id.ivProfileImage);
            firstName = itemView.findViewById(R.id.friend_first_name);
            lastName = itemView.findViewById(R.id.friend_last_name);
        }
    }

    public void setFilter(List<DataSnapshot> filter){
        list = filter;
        notifyDataSetChanged();
    }
}
