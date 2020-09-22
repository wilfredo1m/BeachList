package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// this is neeeded in order to run the program. if no main activity is set then nothing will run
public class MainActivity extends AppCompatActivity {
    private Button createAccountButton, homeScreenButton;

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

        // Home Screen Button
        homeScreenButton = (Button) findViewById(R.id.btnLogin);
        homeScreenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

    }
    // Opens Create Account Screen
    public void openCreateAccountScreen(){
        Intent openScreen = new Intent(this, CreateAccount.class);
        startActivity(openScreen);
    }

    // Opens Home Screen (Needs to add the check for valid login)
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreen.class);
        startActivity(openScreen);
    }
}