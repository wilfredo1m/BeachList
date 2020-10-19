package com.example.beachlist;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;


public class ListingTitlePage extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 33;
    int callingActivity;
    EditText listingTitle;
    ImageView listingPic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_title_image_page);

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

        // Get input fields
        getUserInputs();

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
    }

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

    public void openItemDescriptionScreen(){
        Intent openScreen = new Intent(this, ListingDescriptionPage.class);
        startActivity(openScreen);
    }
    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }
    public void getUserInputs(){
        listingPic = findViewById(R.id.et_listing_pic); // will change once we figure out how to take in multiple pics
        listingTitle = findViewById(R.id.et_listing_title);
    }

}