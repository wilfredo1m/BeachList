package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CreateAccount extends AppCompatActivity {
    private Button createAccountButton, cancelButton;
    private EditText fName, lName, email, password, gradDate, phoneNum;
    private ImageButton profilePic;
    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Set Profile Picture
        profilePic = (ImageButton) findViewById(R.id.btnProfilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Access the phones camera roll to let user pick their profile picture
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        // Create Account Button
        createAccountButton = (Button) findViewById(R.id.btnCreateAccount2);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gets input entered in each field
                fName = (EditText) findViewById(R.id.etFirstName);
                lName = (EditText) findViewById(R.id.etLastName);
                email = (EditText) findViewById(R.id.etEmail2);
                password = (EditText) findViewById(R.id.etPassword2);
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