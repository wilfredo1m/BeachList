package com.example.beachlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class SoldListingsRecyclerAdapter extends RecyclerView.Adapter<SoldListingsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<ListingData> list;

    public SoldListingsRecyclerAdapter(Context context, List<ListingData> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public SoldListingsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sold_listings_row,parent,false);
        return new SoldListingsRecyclerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SoldListingsRecyclerAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load(list.get(position).getListingImages().get(1))
                .centerCrop()
                .into(holder.listingPic);
        //holder.listingPic.setImageResource(list.get(position).getListingPhotos()[0]);
        holder.listingTitle.setText(list.get(position).getTitle());
        holder.listingDate.setText(list.get(position).getSellDate());
        holder.listingSoldPrice.setText("$"+list.get(position).getSellPrice());
        holder.listingSoldTo.setText(list.get(position).getBuyerId());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView listingPic;
        TextView listingTitle, listingDate, listingSoldPrice, listingSoldTo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            listingPic = itemView.findViewById(R.id.sold_listing_pic);
            listingTitle = itemView.findViewById(R.id.sold_listing_title);
            listingDate = itemView.findViewById(R.id.sold_listing_date);
            listingSoldPrice = itemView.findViewById(R.id.sold_listing_price);
            listingSoldTo = itemView.findViewById(R.id.sold_listing_soldTo);
        }
    }
}