package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class CreateAccount extends AppCompatActivity {
    private Button createAccountButton, cancelButton;
    private EditText fName, lName, email, password, gradDate, phoneNum;
    private Button profilePic;
    public static final int IMAGE_REQUEST = 33;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Set Profile Picture
        profilePic = (Button)  findViewById(R.id.button2);
        profilePic.setOnClickListener(new View.OnClickListener() {
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

                // Check if email is valid
                // else if(){
                //
                //}

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

        startActivityForResult(openCameraRoll, IMAGE_REQUEST);
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
}