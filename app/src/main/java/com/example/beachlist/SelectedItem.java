package com.example.beachlist;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedItem extends AppCompatActivity {
    ViewPager2 viewPager;
    private ArrayList<String> itemImages = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    ImageAdapter adapter;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_item);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //*************Display Item info**************************

        itemTitle = findViewById(R.id.selected_item_title);
        itemDescription = findViewById(R.id.selected_item_description);
        itemPrice = findViewById(R.id.selected_item_price);
        itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.item_seller_firstname);
        itemSellerLastName = findViewById(R.id.item_seller_lastname);
        userPicture = findViewById(R.id.item_user_image);

        // gets the item's information to display
        int position = getIntent().getIntExtra("position",1);

        getListingImages(ItemHomeSearchTab.item_list.get(position).child("listingImages"));
        viewPager = findViewById(R.id.selected_item_images);
        adapter = new ImageAdapter(this, itemImages);
        viewPager.setAdapter(adapter);

        // Sets the item info in the correct fields to be displayed
        itemTitle.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getTitle());
        itemDescription.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getDescription());
        itemPrice.setText("$"+ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getPrice());
//        itemSellerFirstName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerFirstName());
//        itemSellerLastName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerLastName());
//        itemCategory.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getCategory());
        ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getOwnerId();

        //Get user info and display it to screen
        getUserInfo(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getOwnerId());
        //********************************************************


        // Go back to Home Screen
        Button backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View view) {
            openHomeScreen();
            }
        });
    }

    private void getUserInfo(String ownerId) {
        final Context context = this;
        DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(ownerId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemSellerFirstName.setText(snapshot.child("data").getValue(UserData.class).getFirstName());
                itemSellerLastName.setText(snapshot.child("data").getValue(UserData.class).getLastName());
                Glide.with(context)
                        .load(snapshot.child("data").getValue(UserData.class).getImageUrl())
                        .centerCrop()
                        .into(userPicture);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });
    }

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            //String url = (String) child.getValue();
            itemImages.add(child.getValue(String.class));
        }
    }

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        startActivity(intent);
    }
}
