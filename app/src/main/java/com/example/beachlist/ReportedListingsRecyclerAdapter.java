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

public class ReportedListingsRecyclerAdapter extends RecyclerView.Adapter<ReportedListingsRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public ReportedListingsRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReportedListingsRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reported_listing_card,parent,false);
        return new MyViewHolder(view);
    }

    //TODO Set up where reported listing is stored so we can access it here
    @Override
    public void onBindViewHolder(@NonNull ReportedListingsRecyclerAdapter.MyViewHolder holder, final int position) {
//        if (list.get(position).child("listingImages").exists()) {
//            Glide.with(context)
//                    .load(list.get(position).child("listingImages").child("1").getValue(String.class))
//                    .centerCrop()
//                    .into(holder.reportedListingImage);
//        } else {
//            Glide.with(context)
//                    .load(list.get(position).child("imageUrl").getValue(String.class))
//                    .centerCrop()
//                    .into(holder.reportedListingImage);
//        }
//        holder.reportedListingTitle.setText(list.get(position).getValue(ListingData.class).getTitle());
//        holder.reportedReason.setText(String.format("$%s", list.get(position).getValue(ListingData.class).getPrice()));
//        holder.timesReported.setText(String.format("$%s", list.get(position).getValue(ListingData.class).getPrice()));
//
//        // when a reported listing is clicked, the position is taken to get that listings info
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SelectedItem.class);
//                intent.putExtra("ListingID", list.get(position).getKey());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView reportedListingImage;
        TextView reportedListingTitle, reportedReason, timesReported;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reportedListingImage = itemView.findViewById(R.id.reported_user_image);
            reportedListingTitle = itemView.findViewById(R.id.reported_listing_name_tv);
            reportedReason = itemView.findViewById(R.id.reason_for_report_listing_tv);
            timesReported = itemView.findViewById(R.id.num_times_list_reported_tv);
        }
    }
}
