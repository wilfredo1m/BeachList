package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListingDescriptionPage extends AppCompatActivity {
    Button cancel_button,backButton,nextButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_descirption_page);




        cancel_button = findViewById(R.id.btn_cancel);
        cancel_button.requestFocus();
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeScreen();
            }
        });
        backButton = findViewById(R.id.btn_back);
        backButton .requestFocus();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
        nextButton = findViewById(R.id.btn_next_to_review);
        nextButton.requestFocus();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPostListingPage();
            }
        });
    }


    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }

    public void goBack(){
        Intent openScreen = new Intent(this, ListingTitlePage.class);
        startActivity(openScreen);
    }
    public void goToPostListingPage(){
        Intent openScreen = new Intent(this, PostListing.class);
        startActivity(openScreen);
    }

}
