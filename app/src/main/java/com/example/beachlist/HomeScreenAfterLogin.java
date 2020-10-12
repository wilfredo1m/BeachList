package com.example.beachlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenAfterLogin extends AppCompatActivity {
    int callingActivity;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this,  R.id.menu_bar_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // callingActivity will tell us which screen we just switched from
        // and what screen from the bottom nav should be displayed
        callingActivity = checkCallingActivity();
        // display Messages Screen
//        if(callingActivity == 1){
//            bottomNavigationView.setSelectedItemId(R.id.fragment_messages);
//        }
        // display create post screen
//        else if(callingActivity == 2){
//            bottomNavigationView.setSelectedItemId(R.id.fragment_create_post);
//        }
        //display account settings screen
        if(callingActivity == 3){
            bottomNavigationView.setSelectedItemId(R.id.fragment_account_settings);
        }


    }

    // Will get the screen that has sent us back to the base 4 screens
    // 1 is messages
    // 2 is create post
    // 3 is account settings
    public int checkCallingActivity() {
        callingActivity = getIntent().getIntExtra("screen",7);
        return callingActivity;
    }
}