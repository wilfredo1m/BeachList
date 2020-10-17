package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class ActiveListings extends AppCompatActivity {
    Button backButton;
    private TabLayout tabLayout;
    private ViewPager viewpager;
    private ListingFragmentCollectionAdapter listingViewPagerAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_listings);

        //variables initialized
        viewpager = findViewById(R.id.listings_view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        listingViewPagerAdapter= new ListingFragmentCollectionAdapter(getSupportFragmentManager());

        tabLayout.setupWithViewPager(viewpager);
        viewpager.setAdapter(listingViewPagerAdapter);

//****************************************Back Button and Accept/Reject Buttons****************************************************//
        backButton = findViewById(R.id.btn_back_from_current_listing);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountSettingsScreen();
            }
        });
//**************************************************END BUTTON GROUP**************************************************************//
    }

    // takes us back to account settings screen
    public void openAccountSettingsScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",3);
        startActivity(openScreen);
    }
}
