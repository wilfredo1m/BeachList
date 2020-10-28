package com.example.beachlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ImageAdapter2 extends RecyclerView.Adapter<ImageAdapter2.ViewHolder> {
    Context context;
    ArrayList<Bitmap> images;

    public ImageAdapter2(Context context, ArrayList<Bitmap> images){
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ImageAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_image_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter2.ViewHolder holder, int position) {
        //holder.imageView.setBackgroundResource(images[position]);
        Glide.with(context)
                .load(images.get(position))
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.listing_image_view);
        }
    }
}
