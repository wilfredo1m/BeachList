package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateAccount extends AppCompatActivity {
    private Button createAccountButton, cancelButton, profilePicButton;
    private EditText fName, lName, email, password, gradDate, phoneNum;
    private ImageView profilePicture;
    public static final int IMAGE_REQUEST = 33;
    public static final int PROCESSED_OK = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        profilePicture = (ImageView) findViewById(R.id.ivProfileImage);

        // Set Profile Picture Button
        profilePicButton = (Button) findViewById(R.id.button2);
        profilePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access the phones camera roll to let user pick their profile picture
                openCameraRoll();
            }
        });

        // Create Account Button
        createAccountButton = (Button) findViewById(R.id.btnCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets input entered in each field
                fName = (EditText) findViewById(R.id.etFirstName);
                lName = (EditText) findViewById(R.id.etLastName);
                email = (EditText) findViewById(R.id.etEmail);
                password = (EditText) findViewById(R.id.etPassword);
                gradDate = (EditText) findViewById(R.id.etGradDate);
                phoneNum = (EditText) findViewById(R.id.etPhone);

                // Check if any field left blank
                if(fName.getText().toString().isEmpty()
                        || lName.getText().toString().isEmpty()
                        || fName.getText().toString().isEmpty()
                        || email.getText().toString().isEmpty()
                        || password.getText().toString().isEmpty()
                        || gradDate.getText().toString().isEmpty()
                        || phoneNum.getText().toString().isEmpty()){
                    displayEmptyFieldError();
                }
                else if (email.getText().toString().length() <= 18) {
                    // Provided email cant be shorter than 19 characters since "@student.csulb.edu" is fixed at 18 characters
                    displayMalformedEmailError();
                }
                else if (!email.getText().toString().substring(email.getText().toString().length() - 18).equals("@student.csulb.edu")) {
                    displayMalformedEmailError();
                }
                else {
                    openLoginScreen();
                }
            }
        });

        // Cancel Button
        cancelButton = (Button) findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen();
            }
        });
    }

    // Opens Camera Roll
    public void openCameraRoll(){
        Intent openCameraRoll = new Intent(Intent.ACTION_PICK);

        // Location of where to find the pictures
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();

        // URI of the path to pictures
        Uri data = Uri.parse(pictureDirectoryPath);

        // Set data and type (* means all image types)
        openCameraRoll.setDataAndType(data,"image/*");

        // start the activity (accessing camera roll)
        startActivityForResult(openCameraRoll, IMAGE_REQUEST);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == PROCESSED_OK){
            // Accessed camera roll successfully
            if(requestCode == IMAGE_REQUEST){
                // Camera roll sent back a picture

                // Address of image in phone
                Uri imageUri = data.getData();

                // Stream to read image data
                InputStream input;

                try {
                    input = getContentResolver().openInputStream(imageUri);

                    // Get Bitmap from InputStream
                    Bitmap image = BitmapFactory.decodeStream(input);

                    // Displays image in the application
                    profilePicture.setImageBitmap(image);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Unable to load image", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // Opens Login Screen
    public void openLoginScreen(){
        Intent openScreen = new Intent(this, MainActivity.class);
        startActivity(openScreen);
    }

    // Displays when any field is left blank
    public void displayEmptyFieldError(){
        Toast.makeText(CreateAccount.this, "Fill in all information",Toast.LENGTH_SHORT).show();
    }

    // Displays when an incorrect email is provided
    public void displayMalformedEmailError(){
        Toast.makeText(CreateAccount.this, "Must provide an email ending in @student.csulb.edu",Toast.LENGTH_SHORT).show();
    }
}