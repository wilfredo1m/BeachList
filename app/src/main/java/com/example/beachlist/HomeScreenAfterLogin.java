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

        callingActivity = checkCallingActivity();
        //callingActivity is assigned the index based on which screen we want to go back to (0-3)
        if(callingActivity == 3){
            bottomNavigationView.setSelectedItemId(R.id.fragment_account_settings);
        }

    }
    public int checkCallingActivity() {
        callingActivity = getIntent().getIntExtra("screen",7);
        return callingActivity;
    }
}