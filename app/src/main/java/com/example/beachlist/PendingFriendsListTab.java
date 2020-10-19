package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class PendingFriendsListTab extends AppCompatActivity {
    private static final long ONE_MEGABYTE = 1024 * 1024;

    RecyclerView recyclerView;
    public static List<FriendsData> list = new ArrayList<>();
    PendingFriendsRecyclerAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private FirebaseStorage storageReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_friend);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference pendingRef = database.getReference("/users/" + userId + "/pending");

        //******************************************Display Pending Friends List*******************************
        recyclerView = findViewById(R.id.pending_friends_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // This is temporary, added these to test the list would display
        String firstNames[] = getResources().getStringArray(R.array.first_names);
        final int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        // clears list each time to make sure no duplicates are added
        list.clear();
        final Context context = this;

        pendingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
//                        OtherUser friend = child.getValue(OtherUser.class);
//                        getFriendInfoForDisplay(friend);
                        FriendsData friend = new FriendsData(null, child.getValue(OtherUser.class).firstName, child.getValue(OtherUser.class).lastName);
                        list.add(friend);
                    }
                }

                // Links recycler view adapter
                adapter = new PendingFriendsRecyclerAdapter(context, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //****************************************Back Button and Accept/Reject Buttons*************************************************
        Button backButton = findViewById(R.id.pending_btn_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountSettingsScreen();
            }
        });

        //***********************************************Tab Setup********************************************
        tabFunctionality();
        /*
        final TabLayout tabs = findViewById(R.id.friends_list_both_tabs);
        tabs.setScrollPosition(1,0f,true);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                openFriends();
            }
        });
        */
        // Cancel Button
        //Button accountPictureButton = findViewById(R.id.btn_account_picture_button);
        //accountPictureButton.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //    public void onClick(View view) {
        //        ();
        //    }
        //});

    }

    public void getFriendInfoForDisplay(final OtherUser friendsData) {
        if(friendsData.getImageUrl().compareTo(" ") != 0) {
            storageReference = FirebaseStorage.getInstance();
            final StorageReference imageRef = storageReference.getReferenceFromUrl(friendsData.getImageUrl());
            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    FriendsData friend = new FriendsData(bmp, friendsData.firstName, friendsData.lastName);
                    list.add(friend);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    System.out.println("It did not work");
                }
            });
        }
    }

    // takes us back to account settings screen
    public void openAccountSettingsScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",3);
        startActivity(openScreen);
    }

    // Sets what to do when each tab is selected / reselected from the pending friends list tab
    public void tabFunctionality(){
        final TabLayout tabs = findViewById(R.id.friends_list_both_tabs);
        tabs.setScrollPosition(1,0f,true);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                openPendingFriends();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                openFriends();
            }
        });
    }

    // opens friends list tab
    public void openFriends(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    // opens pending friends list tab
    public void openPendingFriends(){
        Intent openScreen = new Intent(this, PendingFriendsListTab.class);
        startActivity(openScreen);
    }

}
