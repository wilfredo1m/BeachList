package com.example.beachlist;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;


public class ListingTitlePage extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 33;
    public static final int PROCESSED_OK = -1;
    private Uri filePath;
    int callingActivity;
    ImageView listingPic;
    ViewPager2 viewPager;
    ImageAdapterBitmap adapter;
    ArrayList<Bitmap> bitmaps;
    ArrayList<String> imageUriList;

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


//        listingPic = findViewById(R.id.listing_images);



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
        openCameraRoll.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

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

          //  listingPic = findViewById(R.id.listing_images);

            bitmaps = new ArrayList<>();
            imageUriList = new ArrayList<>();
            ClipData clipData = data.getClipData();
            if(clipData != null) {
                for(int i=0;i <clipData.getItemCount();i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    imageUriList.add(imageUri.toString());
                    try{
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
        //*************Display Listing Images********************
                        viewPager = findViewById(R.id.selected_images_pager);
                        adapter = new ImageAdapterBitmap(this,bitmaps);
                        viewPager.setAdapter(adapter);
//        //********************************************************
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }else{
                Uri imageUri = data.getData();
                imageUriList.add(imageUri.toString());
                try{
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    bitmaps.add(bitmap);
//        //*************Display Listing Images********************
                    viewPager = findViewById(R.id.selected_images_pager);
                    adapter = new ImageAdapterBitmap(this,bitmaps);
                    viewPager.setAdapter(adapter);
//        //********************************************************
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }
            }



            }
        }
    }

    public void openItemDescriptionScreen(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        TextView titleTextView = findViewById(R.id.et_listing_title);
        openScreen.putExtra("ListingTitle", titleTextView.getText().toString());
        openScreen.putStringArrayListExtra("Listing Images", imageUriList);
        startActivity(openScreen);
    }

    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }

}