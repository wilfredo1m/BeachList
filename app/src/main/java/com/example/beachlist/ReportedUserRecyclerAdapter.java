package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ReportedUserRecyclerAdapter extends RecyclerView.Adapter<ReportedUserRecyclerAdapter.MyViewHolder> {
    private static final String TAG = "FIREBASE DATABASE";
    Context context;
    List<DataSnapshot> list;
    FirebaseDatabase database;

    public ReportedUserRecyclerAdapter(Context context, List<DataSnapshot> list) {
        this.context = context;
        this.list = list;
        database = FirebaseDatabase.getInstance();
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
        Glide.with(context)
                .load(list.get(position).child("imageUrl").getValue(String.class))
                .centerCrop()
                .into(holder.reportedUserImage);
        holder.reportedUserName.setText(list.get(position).child("name").getValue(String.class));
        holder.reportedReason.setText(list.get(position).child("reason").getValue(String.class));
        //holder.timesReported

        final String userId = list.get(position).getKey();
        // when a listing is clicked, the position is taken to get that person info
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SelectedUser.class);
                intent.putExtra("selectedUserId", userId);
                context.startActivity(intent);
            }
        });

        holder.waiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DatabaseReference listingRef = database.getReference("users").child(userId).child("banned");
//                listingRef.setValue(false).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
                        DatabaseReference reportedRef = database.getReference("reported").child("users").child(userId);
                        reportedRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                System.out.println("Waived");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: " + e.getLocalizedMessage(), e.getCause());
                            }
                        });
                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: " + e.getLocalizedMessage(), e.getCause());
//                    }
//                });
//            }
        });

        holder.tempBanUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference listingRef = database.getReference("users").child(userId).child("isBanned");
                listingRef.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        userBanTimeLimit(userId);
                        DatabaseReference reportedRef = database.getReference("reported").child("users").child(userId);
                        reportedRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                list.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, list.size());
                                System.out.println("Waived");
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e(TAG, "onFailure: " + e.getLocalizedMessage(), e.getCause());
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });

        holder.permaBanUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference listingRef = database.getReference("users").child(userId).child("isBanned");
                listingRef.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Timestamp permanent = new Timestamp(0);
                        System.out.println(permanent.getTime());
                        DatabaseReference banTimeRef = database.getReference("users").child(userId).child("banClock");
                        banTimeRef.setValue(permanent.getTime()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference reportedRef = database.getReference("reported").child("users").child(userId);
                                reportedRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        list.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, list.size());
                                        System.out.println("Waived");
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e(TAG, "onFailure: " + e.getLocalizedMessage(), e.getCause());
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size() ;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView reportedUserImage;
        TextView reportedUserName, reportedReason, timesReported;
        Button waiveButton, tempBanUserButton, permaBanUserButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            reportedUserImage = itemView.findViewById(R.id.reported_user_image);
            reportedUserName = itemView.findViewById(R.id.reported_user_tv);
            reportedReason = itemView.findViewById(R.id.reason_for_report_user_tv);
            timesReported = itemView.findViewById(R.id.num_of_report_user_tv);
            waiveButton = itemView.findViewById(R.id.waive_ban_btn);
            tempBanUserButton = itemView.findViewById(R.id.temp_ban_user_btn);
            permaBanUserButton = itemView.findViewById(R.id.perma_ban_user_btn);
        }
    }

    public void userBanTimeLimit(String userId) {
        Time currentTime = new Time();
        currentTime.setToNow();
        Timestamp currentDate = new Timestamp(currentTime.toMillis(true));
        System.out.println(currentDate.getTime());
        currentTime.monthDay += 1;
        Timestamp endDate = new Timestamp(currentTime.toMillis(true));
        System.out.println(endDate.getTime());
        DatabaseReference banTimeRef = database.getReference("users").child(userId).child("banClock");
        banTimeRef.setValue(endDate.getTime()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
