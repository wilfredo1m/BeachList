package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class PostListing  extends AppCompatActivity {
Button submitPost, cancelButton,backButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_review_post_page);


        //submit post button
        //functionality to submit post should be added below under the submitPost function.



        submitPost = findViewById(R.id.btn_submit_post);
        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //submitPost();
                returnHome();
            }
        });

        cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnHome();
            }
        });

        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


    }


    public void returnHome(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
    public void submitPost(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
    public void goBack(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        startActivity(openScreen);
    }



}
