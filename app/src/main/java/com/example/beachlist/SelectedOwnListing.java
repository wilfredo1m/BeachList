package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedOwnListing extends AppCompatActivity {
//    ImageView listingPic;
    TextView listingTitle, listingDescription, listingPrice;
    Button backButton, shareButton,modListingButton;
    private FirebaseDatabase firebaseDatabase;

    ViewPager2 viewPager;
    ArrayList<String> listingImages = new ArrayList<>();
    ImageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_listing);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //*************Display Listing Images********************
//        viewPager = findViewById(R.id.own_listing_images);
//        adapter = new ImageAdapter(this, listingImages);
//        viewPager.setAdapter(adapter);

        //listingPic = findViewById(R.id.selected_listing_pic);
        listingTitle = findViewById(R.id.selected_listing_title);
        listingDescription = findViewById(R.id.selected_listing_description);
        listingPrice = findViewById(R.id.selected_listing_price);

        // gets the listing's information to display
        String type = getIntent().getStringExtra("type");
        String listingId = getIntent().getStringExtra("listingID");

        // Sets the listings info in the correct fields to be displayed
        //listingPic.setImageResource(CurrentListingsFragment.list.get(position).getListingPhotos()[0]);
//        listingTitle.setText(CurrentListingsFragment.list.get(position).getTitle());
//        listingDescription.setText(CurrentListingsFragment.list.get(position).getDescription());
//        listingPrice.setText("$"+CurrentListingsFragment.list.get(position).getPrice());
        //********************************************************

        DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child(type).child(listingId);
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Populate image URLs in global variable
                getListingImages(snapshot.child("listingImages"));
                //Get data and display info
                ListingData selectedListing = snapshot.getValue(ListingData.class);
                assert selectedListing != null;
                displayListingInfo(selectedListing);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

//        viewPager = findViewById(R.id.selected_service_images);
//        adapter = new ImageAdapter(this, listingImages);
//        viewPager.setAdapter(adapter);

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

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            listingImages.add(child.getValue(String.class));
        }
    }

    private void displayListingInfo(ListingData selectedListing) {
        listingTitle.setText(selectedListing.getTitle());
        listingDescription.setText(selectedListing.getDescription());
        listingPrice.setText("$"+selectedListing.getPrice());

        viewPager = findViewById(R.id.own_listing_images);
        adapter = new ImageAdapter(this, listingImages);
        viewPager.setAdapter(adapter);
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
