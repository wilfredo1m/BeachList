package com.example.beachlist;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeScreenAfterLogin extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbar);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav_view);
        NavController navController = Navigation.findNavController(this,  R.id.menu_bar_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }
}