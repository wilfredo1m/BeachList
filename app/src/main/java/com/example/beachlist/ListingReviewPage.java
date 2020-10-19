package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListingReviewPage extends AppCompatActivity {
    Button submitPost, cancelButton, backButton;
    ImageView listingPic;
    TextView listingTitle, listingDescription, listingPrice, listingCategory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_review_post_page);

        // Get input fields
        getUserInputs();

        // Create Post
        submitPost = findViewById(R.id.btn_submit_post);
        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds post to database
                submitPost();

                // Return to home screen
                openHomeScreen();
            }
        });

        // Cancels the post being created / clears all fields entered
        cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatePostScreen();
            }
        });

        // Go back to the Listing Description Page
        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


    }


    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen", 2);
        startActivity(openScreen);
    }
    public void submitPost(){
        // adds post and its information to database
    }
    public void goBack(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        startActivity(openScreen);
    }
    public void getUserInputs(){
        listingPic = findViewById(R.id.et_listing_pic_review); // will change once we figure out how to take in multiple pics
        listingTitle = findViewById(R.id.et_listing_title_review);
        listingPrice = findViewById(R.id.et_listing_price_review);
        listingDescription = findViewById(R.id.et_listing_description);
        listingCategory = findViewById(R.id.et_listing_category_review);
    }


}
