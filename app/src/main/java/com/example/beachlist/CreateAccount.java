package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateAccount extends AppCompatActivity {
    private Button createAccountButton, homeScreenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Create Account Button
        createAccountButton = (Button) findViewById(R.id.btnCreateAccount2);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLoginScreen();
            }
        });

        // Cancel Button
        createAccountButton = (Button) findViewById(R.id.btnCancel);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
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
}