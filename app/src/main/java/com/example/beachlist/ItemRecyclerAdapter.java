package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;

import java.util.List;

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.MyViewHolder> {
    Context context;
    List<DataSnapshot> list;

    public ItemRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ItemRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRecyclerAdapter.MyViewHolder holder, final int position) {
        if (list.get(position).child("listingImages").exists()) {
            Glide.with(context)
                    .load(list.get(position).child("listingImages").child("1").getValue(String.class))
                    .centerCrop()
                    .into(holder.listingPic);
        } else {
            Glide.with(context)
                    .load(list.get(position).child("imageUrl").getValue(String.class))
                    .centerCrop()
                    .into(holder.listingPic);
        }
        ListingData test = list.get(position).getValue(ListingData.class);
        holder.listingTitle.setText(list.get(position).getValue(ListingData.class).getTitle());
        holder.listingPrice.setText(String.format("$%s", list.get(position).getValue(ListingData.class).getPrice()));

        // when a listing is clicked, the position is taken to get that listings info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String className = view.getContext().getClass().getName();
                int pos = className.lastIndexOf ('.') + 1;
                String onlyClass = className.substring(pos);
                Toast.makeText(view.getContext(), onlyClass, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SelectedItem.class);
                intent.putExtra("ListingID", list.get(position).getKey());
                intent.putExtra("callingPage", onlyClass);
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
            listingPic = itemView.findViewById(R.id.listing_image);
            listingTitle = itemView.findViewById(R.id.title_service_row_field);
            listingPrice = itemView.findViewById(R.id.listing_price);
        }
    }

    public void setFilter(List<DataSnapshot> filter){
        list = filter;
        notifyDataSetChanged();
    }

}
