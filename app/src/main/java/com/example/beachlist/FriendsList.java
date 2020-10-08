package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity {
    RecyclerView recyclerView;
    List<FriendsData> list = new ArrayList<>();
    RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);

        //******************************************Display Friends List***************************************
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String firstNames[] = getResources().getStringArray(R.array.first_names);
        int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
                                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        for(int i = 0; i < firstNames.length; i++){
            FriendsData friend = new FriendsData(profilePics[i],firstNames[i],lastNames[i]);
            list.add(friend);
        }
        adapter = new RecyclerViewAdapter(this,list);
        recyclerView.setAdapter(adapter);

        //***********************************************Tab Setup********************************************
        TabLayout tabs = findViewById(R.id.friends_list_both_tabs);
        //ViewPager viewPage = (ViewPager) findViewById(R.id.pager);
        //tabs.setupWithViewPager(viewPage);

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
    }

    public void openPendingFriends(){
        Intent openScreen = new Intent(this, PendingFriendsList.class);
        startActivity(openScreen);
    }
}
