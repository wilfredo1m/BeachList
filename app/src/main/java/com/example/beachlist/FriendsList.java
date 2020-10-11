package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<FriendsData> list = new ArrayList<>();
    FriendsRecyclerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);

        //******************************************Display Friends List***************************************
        recyclerView = findViewById(R.id.friends_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String firstNames[] = getResources().getStringArray(R.array.first_names);
        int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
                                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        //list.clear();
        for(int i = 0; i < firstNames.length; i++){
            FriendsData friend = new FriendsData(profilePics[i],firstNames[i],lastNames[i]);
            list.add(friend);
        }
        adapter = new FriendsRecyclerAdapter(this,list);
        recyclerView.setAdapter(adapter);

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

    public void openAccountSettingsScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",3);
        startActivity(openScreen);
    }

    public void openPendingFriends(){
        Intent openScreen = new Intent(this, PendingFriendsList.class);
        startActivity(openScreen);
    }

    public void openFriends(){
        Intent openScreen = new Intent(this, FriendsList.class);
        startActivity(openScreen);
    }

    public void tabMenuFunctionality(){
        //***********************************************Tab Setup********************************************
        TabLayout tabs = findViewById(R.id.friends_list_both_tabs);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1) {
                    list.clear();
                    openPendingFriends();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                list.clear();
                openFriends();
            }
        });
    }
}
