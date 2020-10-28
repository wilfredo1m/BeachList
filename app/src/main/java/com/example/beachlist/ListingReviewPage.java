package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ListingReviewPage extends AppCompatActivity {
    Button submitPost, cancelButton;
    ImageView listingPic;
    ViewPager2 viewPager;
    ImageAdapter adapter;
    TextView listingTitle, listingDescription, listingPrice, listingCategory, listingType;
    String title, description, category, price, type;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_review_post_page);
        //listingPic = findViewById(R.id.review_listing_images);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
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
        //Get current date and format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        String postDate = dateFormat.format(date);

        final ListingData currentListing = new ListingData();

        currentListing.setTitle(title);
        currentListing.setCategory(category);
        currentListing.setDescription(description);
        currentListing.setPrice(price);
        currentListing.setOwnerId(mAuth.getCurrentUser().getUid());
        currentListing.setPostDate(postDate);

        //This should become obsolete but i didn't want to change listing data again right now
        currentListing.setImageUrl(" ");

        //To be updated at a later interaction with the item
        currentListing.setBuyerId(" ");
        currentListing.setSellDate(" ");
        currentListing.setSellPrice(" ");

        DatabaseReference listingReference = database.getReference("listings").child(type).push();
        //TODO test this set up of the setvalue
        listingReference.setValue(currentListing);
        //It should only be a few more lines if even that
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
        price = intent.getExtras().getString("ListingPrice");
        title = intent.getExtras().getString("ListingTitle");
        description = intent.getExtras().getString("ListingDescription");
        category = intent.getExtras().getString("ListingCategory");
        type = intent.getExtras().getString("ListingType");


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
