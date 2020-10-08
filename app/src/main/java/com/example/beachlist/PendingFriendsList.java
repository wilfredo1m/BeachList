package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class PendingFriendsList extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_friend);

        final TabLayout tabs = findViewById(R.id.friends_list_both_tabs);
        final ViewPager viewPage = (ViewPager) findViewById(R.id.pager);
        tabs.setupWithViewPager(viewPage);
        tabs.setScrollPosition(1,0f,true);
        //viewPage.setCurrentItem(1);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition() == 0)
                    openFriends();
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

    public void openFriends(){
        Intent openScreen = new Intent(this, FriendsList.class);
        startActivity(openScreen);
    }
}
