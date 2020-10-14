package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FriendsListTab extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<FriendsData> list = new ArrayList<>();
    FriendsRecyclerAdapter adapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference friendsRef = database.getReference("/users/" + userId + "/friends");
        //******************************************Display Friends List***************************************
        recyclerView = findViewById(R.id.friends_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // This is temporary, added these to test the list would display
        final String firstNames[] = getResources().getStringArray(R.array.first_names);
        final int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
                                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
        final String lastNames[] = getResources().getStringArray(R.array.last_names);

        // clears list each time to make sure no duplicates are added
        list.clear();
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        FriendsData friend = new FriendsData(profilePics[0], child.getValue(OtherUser.class).firstName, child.getValue(OtherUser.class).lastName);
                        list.add(friend);
                    }
                }

                onFriendsListQuery();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });



        //****************************************Back Button*************************************************
        Button backButton = findViewById(R.id.friends_btn_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountSettingsScreen();
            }
        });

        //***********************************************Tab Setup********************************************
        tabMenuFunctionality();
        /*
        TabLayout tabs = findViewById(R.id.friends_list_both_tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1)
                    openPendingFriends();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
*/
        // Cancel Button
        //Button accountPictureButton = findViewById(R.id.btn_account_select_btn);
        //accountPictureButton.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //    public void onClick(View view) {
        //        ();
        //    }
        //});
    }

    public void onFriendsListQuery() {
        adapter = new FriendsRecyclerAdapter(this, list);
        recyclerView.setAdapter(adapter);
    }
    // takes us back to Account Settings screen
    public void openAccountSettingsScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",3);
        startActivity(openScreen);
    }

    // Opens pending friends list tab
    public void openPendingFriends(){
        Intent openScreen = new Intent(this, PendingFriendsListTab.class);
        startActivity(openScreen);
    }

    // Opens friends list tab
    public void openFriends(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    // Sets what to do when each tab is selected / reselected from the friends list tab
    public void tabMenuFunctionality(){
        //***********************************************Tab Setup********************************************
        TabLayout tabs = findViewById(R.id.friends_list_both_tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // if pending friends tab is selected
                if(tab.getPosition() == 1) {
                    openPendingFriends();
                }
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
}
