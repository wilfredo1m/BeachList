package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class SelectedOwnListing extends AppCompatActivity {
//    ImageView listingPic;
    TextView listingTitle, listingDescription, listingPrice;
    Button backButton, shareButton,modListingButton;

    ViewPager2 viewPager;
    int[] images = {R.drawable.pokemon1, R.drawable.pokemon2,R.drawable.pokemon3,R.drawable.pokemon4,R.drawable.pokemon5};
    ImageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_listing);

        //*************Display Listing Images********************
        viewPager = findViewById(R.id.own_listing_images);
        adapter = new ImageAdapter(images);
        viewPager.setAdapter(adapter);

        //listingPic = findViewById(R.id.selected_listing_pic);
        listingTitle = findViewById(R.id.selected_listing_title);
        listingDescription = findViewById(R.id.selected_listing_description);
        listingPrice = findViewById(R.id.selected_listing_price);

        // gets the listing's information to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the listings info in the correct fields to be displayed
        //listingPic.setImageResource(CurrentListingsFragment.list.get(position).getListingPhotos()[0]);
        listingTitle.setText(CurrentListingsFragment.list.get(position).getListingTitle());
        listingDescription.setText(CurrentListingsFragment.list.get(position).getListingDescription());
        listingPrice.setText("$"+CurrentListingsFragment.list.get(position).getAskingPrice());

        // Modify current listing
        modListingButton = findViewById(R.id.selected_own_listing_modify_btn);
        modListingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openModifyListingScreen();
            }
        });

        // Go back to current listings list
        backButton = findViewById(R.id.selected_own_listing_back_btn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCurrentListingsListScreen();
            }
        });

        // Share listing through messages
        shareButton = findViewById(R.id.selected_listing_share_btn);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessagesScreen();
            }
        });
    }

    public void openModifyListingScreen(){
        Intent openScreen = new Intent(this, ModifyListing.class);
        startActivity(openScreen);
    }

    public void openCurrentListingsListScreen(){
        Intent openScreen = new Intent(this, ActiveListings.class);
        startActivity(openScreen);
    }

    public void openMessagesScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",1);
        startActivity(openScreen);
    }
}
