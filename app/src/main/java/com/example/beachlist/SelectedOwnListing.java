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

import java.util.ArrayList;

public class SelectedOwnListing extends AppCompatActivity {
//    ImageView listingPic;
    TextView listingTitle, listingDescription, listingPrice;
    Button backButton, shareButton,modListingButton;

    ViewPager2 viewPager;
    String[] images = {"https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F258260727?alt=media&token=e319e597-2fee-4790-b630-db4d6df4cf12",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F267055780?alt=media&token=1e386df7-470b-431a-b58c-bb0d86450d2c"};
    ArrayList<String> listingImages = new ArrayList<>();
    ImageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_listing);

        //*************Display Listing Images********************
        viewPager = findViewById(R.id.own_listing_images);
        adapter = new ImageAdapter(this, listingImages);
        viewPager.setAdapter(adapter);

        //listingPic = findViewById(R.id.selected_listing_pic);
        listingTitle = findViewById(R.id.selected_listing_title);
        listingDescription = findViewById(R.id.selected_listing_description);
        listingPrice = findViewById(R.id.selected_listing_price);

        // gets the listing's information to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the listings info in the correct fields to be displayed
        //listingPic.setImageResource(CurrentListingsFragment.list.get(position).getListingPhotos()[0]);
//        listingTitle.setText(CurrentListingsFragment.list.get(position).getTitle());
//        listingDescription.setText(CurrentListingsFragment.list.get(position).getDescription());
//        listingPrice.setText("$"+CurrentListingsFragment.list.get(position).getPrice());
        //********************************************************

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
