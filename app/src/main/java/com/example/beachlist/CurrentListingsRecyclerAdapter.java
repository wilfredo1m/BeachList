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

public class CurrentListingsRecyclerAdapter extends RecyclerView.Adapter<CurrentListingsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public CurrentListingsRecyclerAdapter(Context context, List<DataSnapshot> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CurrentListingsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.current_listing_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrentListingsRecyclerAdapter.MyViewHolder holder, final int position) {
        Glide.with(context)
                .load(list.get(position).child("imageUrl").getValue(String.class))
                .centerCrop()
                .into(holder.listingPic);
        holder.listingTitle.setText(list.get(position).child("title").getValue(String.class));
        holder.listingPrice.setText(String.format("$%s", list.get(position).child("price").getValue(String.class)));

        // when a listing is clicked, the position is taken to get that person info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedOwnListing.class);
                intent.putExtra("type", list.get(position).child("type").getValue(String.class));
                intent.putExtra("listingID", list.get(position).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView listingPic;
        TextView listingTitle, listingPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listingPic = itemView.findViewById(R.id.current_listing_pic);
            listingTitle = itemView.findViewById(R.id.current_listing_title);
            listingPrice = itemView.findViewById(R.id.current_listing_price);
        }
    }
}