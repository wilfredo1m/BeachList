package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Scanner;

// this is needed in order to run the program. if no main activity is set then nothing will run
public class MainActivity extends AppCompatActivity {
    private Button createAccountButton, homeScreenButton;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this line makes it so the first page to be displayed is the one below.
        setContentView(R.layout.activity_main);

        // Create Account Button
        createAccountButton = (Button) findViewById(R.id.btnCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreateAccountScreen();
            }
        });

        // Home Screen Button (Needs to add the check for valid login)
        homeScreenButton = (Button) findViewById(R.id.btnLogin);
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = (EditText) findViewById(R.id.etEmail);
                password = (EditText) findViewById(R.id.etPassword);

                // Check if email or password field left blank
                if((email.getText().toString().isEmpty()) || (password.getText().toString().isEmpty())){
                    displayEmptyFieldError();
                }

                // Check if email and password are valid
                // else if(){
                //
                //}

                else{
                    openHomeScreen();
                }
            }
        });


    }
    // Displays when Email and/or Password field are left blank
    public void displayEmptyFieldError(){
        Toast.makeText(MainActivity.this, "Enter Email and Password",Toast.LENGTH_SHORT).show();
    }

    // Opens Create Account Screen
    public void openCreateAccountScreen(){
        Intent openScreen = new Intent(this, CreateAccount.class);
        startActivity(openScreen);
    }

    // Opens Home Screen
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreen.class);
        startActivity(openScreen);
    }
}