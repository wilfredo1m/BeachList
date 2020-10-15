package com.example.beachlist;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;


public class ListingTitlePage extends AppCompatActivity {
    public static final int IMAGE_REQUEST = 33;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_title_image_page);

        SeekBar seekBar = findViewById(R.id.title_seek_bar);
        seekBar.setClickable(false);

        //open camera roll
        //camera roll able to get more than one picture from gallery
        openCameraRoll();


        Button nextButton = findViewById(R.id.btn_next_page_desc);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openItemDescriptionScreen();
            }
        });

        Button cancel_button = findViewById(R.id.btn_cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  openHomeScreen();
            }
        });
    }

    // Opens Camera Roll
    public void openCameraRoll() {
        Intent openCameraRoll = new Intent(Intent.ACTION_PICK);
        // Location of where to find the pictures
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

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
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);

    }

}