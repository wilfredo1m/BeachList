package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeScreenAfterLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen_after_login);

        HomeFragmentTest homeScreen = new HomeFragmentTest();
        getSupportFragmentManager().beginTransaction().add(R.id.container,homeScreen).commit();
    }
}