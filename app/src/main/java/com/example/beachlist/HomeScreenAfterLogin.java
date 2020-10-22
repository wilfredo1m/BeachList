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
    int callingActivity, startTab;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this,  R.id.menu_bar_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // CallingActivity will tell us which screen we just switched from
        // and what screen from the bottom nav should be displayed
        callingActivity = checkCallingActivity();

        // Display Home Tabs screen
        if(callingActivity == 0){
            startTab = startTab();

            // Display Users tab
            if(startTab == 1){
                Intent intent = new Intent(this, HomeFragment.class);
                intent.putExtra("tab", 1);
                startActivity(intent);
            }

            // Display Services tab
            else if(startTab == 2){
                Intent intent = new Intent(this, HomeFragment.class);
                intent.putExtra("tab", 2);
                startActivity(intent);
            }
        }

        // Display Messages screen
        if(callingActivity == 1){
            bottomNavigationView.setSelectedItemId(R.id.fragment_messages);
        }
        // Display Create Post screen
        else if(callingActivity == 2){
            bottomNavigationView.setSelectedItemId(R.id.fragment_create_post);
        }

        // Display Account Settings screen
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

    // Will tell us which tab to open
    // 1 is Users
    // 2 is Services
    public int startTab(){
        startTab = getIntent().getIntExtra("tab",7);
        return startTab;
    }
}