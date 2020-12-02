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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageRecyclerAdapter extends RecyclerView.Adapter<MessageRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public MessageRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_screen_cardview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageRecyclerAdapter.MyViewHolder holder, final int position) {

        Glide.with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F71286574?alt=media&token=77eaed3a-316a-4d8f-96c4-ed09a0a2e8a5")                // retrieve sample url from test_message
                .centerCrop()
                .into(holder.userPic);

        String lastMessage = (list.get(position).getKey());     // retrieve sample fullname from test_message

        holder.userName.setText(lastMessage);

        // when a conversation is clicked, the position is taken to get that conversation
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ConversationScreen.class);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView userName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userPic = itemView.findViewById(R.id.user_pic);
            userName = itemView.findViewById(R.id.message_user_full_name);
        }
    }
}
