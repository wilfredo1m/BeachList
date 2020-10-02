package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BottomSearchbar extends AppCompatActivity {
    private Button homeButton, createPostButton, messagesButton, accountSettingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);

        // Home Screen Button
        homeButton = (Button) findViewById(R.id.btnHome);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        // Create Post Button
        createPostButton = (Button) findViewById(R.id.btnCreatePost);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCreatePostScreen();
            }
        });

        // Messages Button
        messagesButton = (Button) findViewById(R.id.btnMessages);
        messagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMessagesScreen();
            }
        });

        // Account Settings Button
        accountSettingsButton = (Button) findViewById(R.id.btnAccountSettings);
        accountSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAccountSettingsScreen();
            }
        });
    }

    // Displays Home Screen
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreen.class);
        startActivity(openScreen);
    }

    // Displays Messages Screen
    public void openMessagesScreen(){
        Intent openScreen = new Intent(this, MessageScreen.class);
        startActivity(openScreen);
    }

    // Displays Account Settings Screen
    public void openAccountSettingsScreen(){
        Intent openScreen = new Intent(this, AccountSettings.class);
        startActivity(openScreen);
    }

    // Displays Create Post Screen
    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, CreatePost.class);
        startActivity(openScreen);
    }
}