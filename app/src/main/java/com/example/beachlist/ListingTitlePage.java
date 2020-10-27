package com.example.beachlist;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class ListingTitlePage extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 33;
    public static final int PROCESSED_OK = -1;
    Bitmap profileImage;
    InputStream input;
    private Uri filePath;
    int callingActivity;
    ImageView listingPic;
    ViewPager2 viewPager;
    ImageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_title_image_page);

        // Progress bar for creating a post
        SeekBar seekBar = findViewById(R.id.title_seek_bar);
        seekBar.setClickable(false);


        // Check which screen we just came from to determine whether we need to access the camera gallery
        callingActivity = checkCallingActivity();
        // If we came from CreatePostFragment, we want to open the gallery to let user pick pictures
        if(callingActivity == 1){
            // Open camera roll
            openCameraRoll();
        }
        // If we came back from ListingDescriptionPage, we do not want the gallery to be accessed
        else if(callingActivity == 2){
            // Do nothing
        }


        listingPic = findViewById(R.id.listing_images);
//        //*************Display Listing Images********************
//        viewPager = findViewById(R.id.listing_images);
//        adapter = new ImageAdapter(images);
//        viewPager.setAdapter(adapter);
//        //********************************************************


//*************************BUTTON BLOCK***********************************************************//
        // Continue to the Listing Description Page
        Button nextButton = findViewById(R.id.btn_next_page_desc);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemDescriptionScreen();
            }
        });

        // Cancels the post being created / clears all fields entered
        Button cancel_button = findViewById(R.id.btn_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  openCreatePostScreen();
            }
        });
//*************************END BUTTON BLOCK*******************************************************//

    }//end onCreate()

    // Check what activity sent us here
    // 1 = CreatePostFragment
    // 2 = ListingDescription
    public int checkCallingActivity() {
        callingActivity = getIntent().getIntExtra("screen",7);
        return callingActivity;
    }

    // Opens Camera Roll
    public void openCameraRoll() {
        Intent openCameraRoll = new Intent(Intent.ACTION_PICK);
        // Location of where to find the pictures
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        // User is able to choose more than one picture from gallery
//        openCameraRoll.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        //openCameraRoll.setAction(Intent.ACTION_GET_CONTENT);
        // startActivityForResult(Intent.createChooser(openCameraRoll,"Select Picture"), 1);

        // URI of the path to pictures
        Uri data = Uri.parse(pictureDirectoryPath);
        // Set data and type (* means all image types)
        openCameraRoll.setDataAndType(data,"image/*");
        // start the activity (accessing camera roll)
        startActivityForResult(openCameraRoll, IMAGE_REQUEST);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == PROCESSED_OK) {
            // Accessed camera roll successfully
            if(requestCode == IMAGE_REQUEST) {
                // Camera roll sent back a picture

                // Address of image in phone
                filePath = data.getData();

                try {
                    input = getContentResolver().openInputStream(filePath);

                    // Get Bitmap from InputStream
                    profileImage = BitmapFactory.decodeStream(input);

                    // Displays image in the application
                    listingPic.setImageBitmap(profileImage);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void openItemDescriptionScreen(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        TextView titleTextView = findViewById(R.id.et_listing_title);
        openScreen.putExtra("ListingTitle", titleTextView.getText().toString());

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        profileImage.compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        openScreen.putExtra("ListingPics", byteArray);

        startActivity(openScreen);
    }

    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }

}