package com.example.beachlist;

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

public class SelectedService extends AppCompatActivity {
    ViewPager2 viewPager;
    String[] images = {"https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F258260727?alt=media&token=e319e597-2fee-4790-b630-db4d6df4cf12",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F267055780?alt=media&token=1e386df7-470b-431a-b58c-bb0d86450d2c"};
    private ArrayList<String> serviceImages = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    ImageAdapter adapter;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_service);

        firebaseDatabase = FirebaseDatabase.getInstance();

        //*************Display Service info**************************

        itemTitle = findViewById(R.id.selected_service_title);
        itemDescription = findViewById(R.id.selected_service_description);
        itemPrice = findViewById(R.id.selected_service_price);
        itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.service_seller_firstname);
        itemSellerLastName = findViewById(R.id.service_seller_lastname);
        userPicture = findViewById(R.id.service_user_image);

        // gets the service's information to display
        int position = getIntent().getIntExtra("position",1);

        getListingImages(ServiceHomeSearchTab.service_list.get(position).child("listingImages"));
        viewPager = findViewById(R.id.selected_service_images);
        adapter = new ImageAdapter(this, serviceImages);
        viewPager.setAdapter(adapter);

        // Sets the service info in the correct fields to be displayed
        itemTitle.setText(ServiceHomeSearchTab.service_list.get(position).getValue(ListingData.class).getTitle());
        itemDescription.setText(ServiceHomeSearchTab.service_list.get(position).getValue(ListingData.class).getDescription());
        itemPrice.setText("$"+ServiceHomeSearchTab.service_list.get(position).getValue(ListingData.class).getPrice());
//        itemSellerFirstName.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getSellerFirstName());
//        itemSellerLastName.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getSellerLastName());
        itemCategory.setText(ServiceHomeSearchTab.service_list.get(position).getValue(ListingData.class).getCategory());
        //********************************************************

        //Get user info and display it to screen
        getUserInfo(ServiceHomeSearchTab.service_list.get(position).getValue(ListingData.class).getOwnerId());

//*********************************BUTTON GROUP*******************************************//
        // Go back to User search list (temporarily going back to home)
        Button backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });
//*********************************END BUTTON GROUP*******************************************//

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
            serviceImages.add(child.getValue(String.class));
        }
    }

    //service tab in HomeFragment
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab",2);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        startActivity(intent);
    }
}