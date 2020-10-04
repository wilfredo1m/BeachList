package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.nav_message:
                        startActivity(new Intent(getApplicationContext(),MessagesFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_account_settings:
                        startActivity(new Intent(getApplicationContext(),AccountSettingsFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_create_post:
                        startActivity(new Intent(getApplicationContext(),CreatePostFragment.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        return true;
                }
                return false;
            }
        });
    }
}
