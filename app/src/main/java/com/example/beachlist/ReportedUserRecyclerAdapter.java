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

public class ReportedUserRecyclerAdapter extends RecyclerView.Adapter<ReportedUserRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public ReportedUserRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ReportedUserRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reported_user_card,parent,false);
        return new MyViewHolder(view);
    }

    //TODO Set up where reported user is stored so we can access it here
    @Override
    public void onBindViewHolder(@NonNull ReportedUserRecyclerAdapter.MyViewHolder holder, final int position) {
//        if (list.get(position).child("listingImages").exists()) {
//            Glide.with(context)
//                    .load(list.get(position).child("listingImages").child("1").getValue(String.class))
//                    .centerCrop()
//                    .into(holder.reportedUserImage);
//        } else {
//            Glide.with(context)
//                    .load(list.get(position).child("imageUrl").getValue(String.class))
//                    .centerCrop()
//                    .into(holder.reportedUserImage);
//        }
//        holder.reportedUserName.setText(list.get(position).getValue(ListingData.class).getTitle());
//        holder.reportedReason.setText(String.format("$%s", list.get(position).getValue(ListingData.class).getPrice()));
//        holder.timesReported.setText(String.format("$%s", list.get(position).getValue(ListingData.class).getPrice()));
//
//        // when a reported user is clicked, the position is taken to get that users info
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SelectedUser.class);
//                intent.putExtra("position", list.get(position).getKey());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView reportedUserImage;
        TextView reportedUserName, reportedReason, timesReported;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reportedUserImage = itemView.findViewById(R.id.reported_user_image);
            reportedUserName = itemView.findViewById(R.id.reported_user_tv);
            reportedReason = itemView.findViewById(R.id.reason_for_report_user_tv);
            timesReported = itemView.findViewById(R.id.num_of_report_user_tv);
        }
    }
}
