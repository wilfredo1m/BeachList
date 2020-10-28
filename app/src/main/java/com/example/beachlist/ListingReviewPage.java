package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class ListingReviewPage extends AppCompatActivity {
    Button submitPost, cancelButton;
    ImageView listingPic;
    ViewPager2 viewPager;
    ImageAdapter adapter;
    TextView listingTitle, listingDescription, listingPrice, listingCategory, listingType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_review_post_page);
        //listingPic = findViewById(R.id.review_listing_images);

        // Get input fields
        getUserInputs();


//************************BUTTON BLOCK********************************************************//
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

//************************END BUTTON BLOCK****************************************************//

    }

    //intent to open home screen
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
    //intent to open create post screen
    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen", 2);
        startActivity(openScreen);
    }
    //TODO to be populated by randy and bernie to save content to firebase
    public void submitPost(){
        // adds post and its information to database
    }

    //intent to go back to listing description page
    public void goBack(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        startActivity(openScreen);
    }
    //retrieve all inputs from user
    public void getUserInputs(){
        //INTENT IN ORDER TO RECEIVE THE TITLE FROM THE HOME PAGE
        Intent intent = getIntent();
        //pull value of the listing price to the screen
//        int pic = intent.getExtras().getInt("ListingPics");
        String price = intent.getExtras().getString("ListingPrice");
        String title = intent.getExtras().getString("ListingTitle");
        String description = intent.getExtras().getString("ListingDescription");
        String category = intent.getExtras().getString("ListingCategory");
        String type = intent.getExtras().getString("ListingType");


        //working group that populates the screen***************************************//

       // byte[] byteArray = getIntent().getByteArrayExtra("ListingPics");
       // Bitmap images = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
       // listingPic.setImageBitmap(images);



        //works
        listingTitle = findViewById(R.id.et_listing_title_review);
        listingTitle.setText(title);
        //works
        listingPrice = findViewById(R.id.et_listing_price_review);
        listingPrice.setText(price);
        //works
        listingDescription = findViewById(R.id.et_listing_description_review);
        listingDescription.setText(description);
        //works
        listingCategory = findViewById(R.id.et_listing_category_review);
        listingCategory.setText(category);
        //works
        listingType = findViewById(R.id.et_listing_type_review);
        listingType.setText(type);
//end working group*********************************************************//
    }


}
