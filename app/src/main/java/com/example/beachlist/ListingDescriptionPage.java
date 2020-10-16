package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListingDescriptionPage extends AppCompatActivity {
    Button cancelButton,backButton,nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_descirption_page);



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

}
