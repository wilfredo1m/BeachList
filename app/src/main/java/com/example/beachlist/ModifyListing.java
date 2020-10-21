package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyListing extends AppCompatActivity {
    Button cancelButton,submitChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_listing);

        //button functionality
        cancelButton = findViewById(R.id.btn_cancel_mod);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListingsScreen();
            }
        });

        submitChangeButton= findViewById(R.id.btn_update_listing);
        submitChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListingsScreen();
                //add the firebase shit here
            }
        });

    }
    public void openListingsScreen(){
        Intent openScreen = new Intent(this, ActiveListings.class);
        startActivity(openScreen);
    }
}
