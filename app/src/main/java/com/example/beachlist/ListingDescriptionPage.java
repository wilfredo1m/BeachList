package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListingDescriptionPage extends AppCompatActivity {
    Button cancelButton,backButton,nextButton;
    EditText listingPrice, listingDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_description_page);

        // Get input fields
        getUserInputs();

        // Cancels the post being created / clears all fields entered
        cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.requestFocus();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatePostScreen();
            }
        });

        // Go back to the Listings Title Page
        backButton = findViewById(R.id.btn_back);
        backButton .requestFocus();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        // Continue to the Listing Review Page
        nextButton = findViewById(R.id.btn_next_to_review);
        nextButton.requestFocus();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPostListingPage();
            }
        });
    }


    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }

    public void goBack(){
        Intent openScreen = new Intent(this, ListingTitlePage.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }
    public void goToPostListingPage(){
        Intent openScreen = new Intent(this, ListingReviewPage.class);
        startActivity(openScreen);
    }
    public void getUserInputs(){
        // STILL NEED LISTING TYPE AND CATEGORY
        listingPrice = findViewById(R.id.et_listing_price);
        listingDescription = findViewById(R.id.et_listing_description);
    }

}
