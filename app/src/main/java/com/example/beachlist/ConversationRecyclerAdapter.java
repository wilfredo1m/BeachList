package com.example.beachlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ConversationRecyclerAdapter extends RecyclerView.Adapter<ConversationRecyclerAdapter.MyViewHolder> {
    private Context context;
    private List<Message> list;
    //private String imageUrl;
    public static final int MSG_LEFT = 0;
    public static final int MSG_RIGHT = 1;
    FirebaseUser fUser;

    public ConversationRecyclerAdapter(Context context, List<Message> list) {
        this.context = context;
        this.list = list;
        //this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public ConversationRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == MSG_RIGHT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_right, parent, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item_left, parent, false);
        }
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationRecyclerAdapter.MyViewHolder holder, final int position) {
        //TODO access messages
//        Glide.with(context)
//                .load(list.get(position).child("data").getValue(UserData.class).getImageUrl())
//                .centerCrop()
//                .into(holder.userPic);
//
//        //holder.userMessage.setText();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userPic;
        TextView userMessage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userPic = itemView.findViewById(R.id.profile_image);
            userMessage = itemView.findViewById(R.id.show_message);
        }
    }

    public int getItemViewType(int position){
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (list.get(position).getSender().equals(fUser.getUid())){
            return MSG_RIGHT;
        }
        else{
            return MSG_LEFT;
        }
    }
}

