package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedPendingFriend extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    public static List<DataSnapshot> itemList = new ArrayList<>();
    public static List<DataSnapshot> serviceList = new ArrayList<>();
    ItemRecyclerAdapter itemRecyclerAdapter;
    ServiceRecyclerAdapter serviceRecyclerAdapter;
    RecyclerView recyclerViewItem;
    RecyclerView recyclerViewService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pending_friend);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        profilePic = findViewById(R.id.iv_selected_pending_friend_image);
        firstName = findViewById(R.id.tv_pending_first_name);
        lastName = findViewById(R.id.tv_pending_last_name);

        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        Glide.with(this)
                .load(PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getImageUrl())
                .centerCrop()
                .into(profilePic);
        firstName.setText(PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getFirstName());
        lastName.setText(PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getLastName());

        // get selected friend's active listings
        Query itemsQuery = database.getReference().child("listings").child("item").orderByChild("ownerId").equalTo(PendingFriendsListTab.list.get(position).getKey());
        Query serviceQuery = database.getReference().child("listings").child("service").orderByChild("ownerId").equalTo(PendingFriendsListTab.list.get(position).getKey());

        recyclerViewItem = findViewById(R.id.pending_friend_item_recycler);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewService = findViewById(R.id.pending_friend_service_recycler);
        recyclerViewService.setLayoutManager(new LinearLayoutManager(this));

        itemsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    itemList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        itemList.add(child);
                    }

                    onItemListQuery();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        serviceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    serviceList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        serviceList.add(child);
                    }

                    onServiceListQuery();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//*****************************************************BUTTON GROUP******************************************************************************//
        // Go back to pending friends list
        Button backButton = findViewById(R.id.pending_friend_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingFriendListScreen();
            }
        });

        // Accept and Reject Request buttons
        final Button acceptRequest = findViewById(R.id.btn_accept_user);
        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptRequest(position);
            }
        });

        Button rejectRequest = findViewById(R.id.btn_reject_user);
        rejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(PendingFriendsListTab.list.get(position).getKey());
                deletePendingReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openFriendsListScreen();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "could not delete value");
                            }
                        });
            }
        });
//*****************************************************END BUTTON GROUP******************************************************************************//

    }//end onCreate()


    //pending friendlist
    public void openPendingFriendListScreen(){
        Intent openScreen = new Intent(this, PendingFriendsListTab.class);
        startActivity(openScreen);
    }
    //friendlist
    public void openFriendsListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    public void acceptRequest(final int position) {
        DatabaseReference newFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(PendingFriendsListTab.list.get(position).getKey());
        newFriendReference.setValue(new OtherUser(PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getFirstName(), PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getLastName(), PendingFriendsListTab.list.get(position).getValue(OtherUser.class).getImageUrl()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFromPending(position);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not add value");
                    }
                });
    }

    public void removeFromPending(final int position) {
        DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(PendingFriendsListTab.list.get(position).getKey());
        deletePendingReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        retrieveMyData(position);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not delete value");
                    }
                });
    }

    public void updateOtherUsersFriends(final int position, OtherUser myData) {
        DatabaseReference updateOtherUserReference = database.getReference().child("users").child(PendingFriendsListTab.list.get(position).getKey()).child("friends").child(user.getUid());
        updateOtherUserReference.setValue(myData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        openFriendsListScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not delete value");
                    }
                });

    }

    public void retrieveMyData(final int position) {
        DatabaseReference retrieveMyDataReference = database.getReference().child("users").child(user.getUid()).child("data");
        retrieveMyDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OtherUser myData = new OtherUser(dataSnapshot.getValue(UserData.class).getFirstName(), dataSnapshot.getValue(UserData.class).getLastName(), dataSnapshot.getValue(UserData.class).getImageUrl());
                updateOtherUsersFriends(position, myData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void onItemListQuery() {
        itemRecyclerAdapter = new ItemRecyclerAdapter(this, itemList);
        recyclerViewItem.setAdapter(itemRecyclerAdapter);
    }

    public void onServiceListQuery() {
        serviceRecyclerAdapter = new ServiceRecyclerAdapter(this, serviceList);
        recyclerViewService.setAdapter(serviceRecyclerAdapter);
    }
}
