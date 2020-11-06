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

public class SelectedFriend extends AppCompatActivity {
    public static List<DataSnapshot> itemList = new ArrayList<>();
    public static List<DataSnapshot> serviceList = new ArrayList<>();
    private static final String TAG = "error";
    ImageView profilePic;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    ItemRecyclerAdapter itemRecyclerAdapter;
    ServiceRecyclerAdapter serviceRecyclerAdapter;
    RecyclerView recyclerViewItem;
    RecyclerView recyclerViewService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_friend);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        profilePic = findViewById(R.id.iv_selected_friend_image);
        firstName = findViewById(R.id.tv_full_name);
        lastName = findViewById(R.id.tv_email);

        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        Glide.with(this)
                .load(FriendsListTab.list.get(position).getValue(OtherUser.class).getImageUrl())
                .centerCrop()
                .into(profilePic);
        //profilePic.setImageBitmap(null);
        firstName.setText(FriendsListTab.list.get(position).getValue(OtherUser.class).getFirstName());
        lastName.setText(FriendsListTab.list.get(position).getValue(OtherUser.class).getLastName());

        // get selected friend's active listings
        Query itemsQuery = database.getReference().child("listings").child("item").orderByChild("ownerId").equalTo(FriendsListTab.list.get(position).getKey());
        Query serviceQuery = database.getReference().child("listings").child("service").orderByChild("ownerId").equalTo(FriendsListTab.list.get(position).getKey());

        recyclerViewItem = findViewById(R.id.friend_item_recycler);
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewService = findViewById(R.id.friend_service_recycler);
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

        // Remove friend from friends list
        Button unfriendButton = findViewById(R.id.btn_remove_friend);
        unfriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deleteFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(FriendsListTab.list.get(position).getKey());
                deleteFriendReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateOtherUsersFriends(position);
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

        // Go back to friends list
        Button backButton = findViewById(R.id.selected_friend_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendListScreen();
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

    public void openFriendListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    public void updateOtherUsersFriends(int position) {
        DatabaseReference deleteFriendReference = database.getReference().child("users").child(FriendsListTab.list.get(position).getKey()).child("friends").child(user.getUid());
        deleteFriendReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        openFriendListScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not delete value");
                    }
                });
    }
}
