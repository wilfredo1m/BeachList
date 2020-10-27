package com.example.beachlist;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public class SelectedItem extends AppCompatActivity {
    ViewPager2 viewPager;
    String[] images = {"https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F258260727?alt=media&token=e319e597-2fee-4790-b630-db4d6df4cf12",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F267055780?alt=media&token=1e386df7-470b-431a-b58c-bb0d86450d2c"};
    private ArrayList<String> itemImages = new ArrayList<>();
    ImageAdapter adapter;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_item);

        //*************Display Item info**************************

        itemTitle = findViewById(R.id.selected_item_title);
        itemDescription = findViewById(R.id.selected_item_description);
        itemPrice = findViewById(R.id.selected_item_price);
        itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.item_seller_firstname);
        itemSellerLastName = findViewById(R.id.item_seller_lastname);

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
