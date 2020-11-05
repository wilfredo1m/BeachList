package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ListingReviewPage extends AppCompatActivity {
    Button submitPost, cancelButton;
    TextView listingTitle, listingDescription, listingPrice, listingCategory, listingType;
    String title, description, category, price, type;
    Map<String, String> listingImages;
    NewListingData currentListing;
    ArrayList<Bitmap> bitmaps;
    ViewPager2 viewPager;
    ImageAdapter2 adapter;

    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_review_post_page);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        // Get input fields
        getUserInputs();

        ArrayList<String> imageUris = this.getIntent().getStringArrayListExtra("Listing Images");
        bitmaps = new ArrayList<>();

        for (int i = 0; i < imageUris.size(); i++) {
            try{
                InputStream is = getContentResolver().openInputStream(Uri.parse(imageUris.get(i)));
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                bitmaps.add(bitmap);
                //*************Display Listing Images********************
                viewPager = findViewById(R.id.final_images_pager);
                adapter = new ImageAdapter2(this,bitmaps);
                viewPager.setAdapter(adapter);
//        //********************************************************
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }


//************************BUTTON BLOCK********************************************************//
        // Create Post
        submitPost = findViewById(R.id.btn_submit_post);
        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Adds post to database
                submitPost();

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
    public void openHomeScreen() {
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
    //intent to open create post screen
    public void openCreatePostScreen() {
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen", 2);
        startActivity(openScreen);
    }
    //Submit post to firebase
    public void submitPost() {
        //Get current date and format
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        Date date = new Date();
        String postDate = dateFormat.format(date);

        currentListing = new NewListingData();

        currentListing.setTitle(title);
        currentListing.setCategory(category);
        currentListing.setDescription(description);
        currentListing.setPrice(price);
        currentListing.setOwnerId(mAuth.getCurrentUser().getUid());
        currentListing.setPostDate(postDate);

        //This should become obsolete but i didn't want to change listing data again right now
        listingImages = new HashMap<>();
        ArrayList<String> imageUris = this.getIntent().getStringArrayListExtra("Listing Images");

        getUrls(imageUris, imageUris.size());

    }

    public void getUrls(final ArrayList<String> imageUrls, final int n) {
        if ((n - 1) >= 0) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            final StorageReference imageRef = storageReference.child("images/" + Uri.parse(imageUrls.get(n-1)).getLastPathSegment());
            UploadTask uploadTask = imageRef.putFile(Uri.parse(imageUrls.get(n-1)));

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.w(" ", "signInWithCustomToken: failure", e.getCause());
                    Toast.makeText(ListingReviewPage.this, "Failed to Store Image", Toast.LENGTH_SHORT).show();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d(" ", "uploadImageWithCustomToken: success");
                    Task<Uri> downloadUrl = imageRef.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageReference = uri.toString();
                            listingImages.put(String.valueOf(n), imageReference);
                            getUrls(imageUrls, n - 1);
                        }
                    });
                }
            });
        }
        else {
            currentListing.setListingImages(" ");

            //To be updated at a later interaction with the item
            currentListing.setBuyerId(" ");
            currentListing.setSellDate(" ");
            currentListing.setSellPrice(" ");

            DatabaseReference listingReference = database.getReference("listings").child(type);
            final String key = listingReference.push().getKey();
            assert key != null;
            listingReference = listingReference.child(key);

            final DatabaseReference finalListingReference = listingReference;
            listingReference.setValue(currentListing)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            finalListingReference.child("listingImages").setValue(listingImages)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            //Create listing reference under user
                                            createListingUnderUser(key);
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            // Write failed
                                            // ...
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Write failed
                            // ...
                        }
                    });
        }
    }

    public void createListingUnderUser(String listingID) {
        Map<String, String> userListingData = new HashMap<>();
        userListingData.put("title", title);
        userListingData.put("price", price);
        userListingData.put("imageUrl", listingImages.get("1"));
        userListingData.put("type", type);
        final DatabaseReference userListingRef = database.getReference("users").child(currentListing.getOwnerId()).child("listings").child(listingID);
        userListingRef.setValue(userListingData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Return to home screen
                        openHomeScreen();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                    }
                });
    }

    //intent to go back to listing description page
    public void goBack() {
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        startActivity(openScreen);
    }
    //retrieve all inputs from user
    public void getUserInputs() {
        //INTENT IN ORDER TO RECEIVE THE TITLE FROM THE HOME PAGE
        Intent intent = getIntent();
        //pull value of the listing price to the screen
//        int pic = intent.getExtras().getInt("ListingPics");
        price = intent.getExtras().getString("ListingPrice");
        title = intent.getExtras().getString("ListingTitle");
        description = intent.getExtras().getString("ListingDescription");
        category = intent.getExtras().getString("ListingCategory");
        type = intent.getExtras().getString("ListingType").toLowerCase();

        //working group that populates the screen***************************************//
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
