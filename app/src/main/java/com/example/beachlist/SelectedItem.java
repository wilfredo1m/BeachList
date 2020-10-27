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

public class SelectedItem extends AppCompatActivity {
    ViewPager2 viewPager;
    int[] images = {R.drawable.pokemon1, R.drawable.pokemon2,R.drawable.pokemon3,R.drawable.pokemon4,R.drawable.pokemon5};
    ImageAdapter adapter;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_item);

        //*************Display Item info**************************

        viewPager = findViewById(R.id.selected_item_images);
        adapter = new ImageAdapter(images);
        viewPager.setAdapter(adapter);

        itemTitle = findViewById(R.id.selected_item_title);
        itemDescription = findViewById(R.id.selected_item_description);
        itemPrice = findViewById(R.id.selected_item_price);
        //itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.item_seller_firstname);
        itemSellerLastName = findViewById(R.id.item_seller_lastname);

        // gets the item's information to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the item info in the correct fields to be displayed
        itemTitle.setText(ItemHomeSearchTab.listing_list.get(position).getTitle());
        itemDescription.setText(ItemHomeSearchTab.listing_list.get(position).getDescription());
        itemPrice.setText("$"+ItemHomeSearchTab.listing_list.get(position).getPrice());
//        itemSellerFirstName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerFirstName());
//        itemSellerLastName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerLastName());
        //itemCategory.setText(ServiceHomeSearchTab.listing_list.get(position).getCategory());

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

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        startActivity(intent);
    }
}
