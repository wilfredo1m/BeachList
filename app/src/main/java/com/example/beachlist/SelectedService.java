package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class SelectedService extends AppCompatActivity {
    ViewPager2 viewPager;
    int[] images = {R.drawable.pokemon1, R.drawable.pokemon2,R.drawable.pokemon3,R.drawable.pokemon4,R.drawable.pokemon5};
    ImageAdapter adapter;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_service);

        //*************Display Service info**************************

        viewPager = findViewById(R.id.selected_service_images);
        adapter = new ImageAdapter(images);
        viewPager.setAdapter(adapter);

        itemTitle = findViewById(R.id.selected_service_title);
        itemDescription = findViewById(R.id.selected_service_description);
        itemPrice = findViewById(R.id.selected_service_price);
        itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.service_seller_firstname);
        itemSellerLastName = findViewById(R.id.service_seller_lastname);

        // gets the service's information to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the service info in the correct fields to be displayed
        itemTitle.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getTitle());
        itemDescription.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getDescription());
        itemPrice.setText("$"+ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getPrice());
//        itemSellerFirstName.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getSellerFirstName());
//        itemSellerLastName.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getSellerLastName());
        itemCategory.setText(ServiceHomeSearchTab.listing_list.get(position).getValue(ListingData.class).getCategory());

        //********************************************************

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