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
     //   BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
      //  bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavigationMethod);
       // getSupportFragmentManager().beginTransaction().replace(R.id.fixing_this_fragment,new fragment1());
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        NavController navController = Navigation.findNavController(this,  R.id.fixing_this_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
    }

/*
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment fragment= null;
                    switch(menuItem.getItemId())
                    {
                        case R.id.fragment1:
                            fragment= new fragment1();
                            break;
                        case R.id.fragment2:
                            fragment= new fragment2();
                            break;
                        case R.id.fragment3:
                            fragment= new fragment3();
                            break;
                        case R.id.fragment4:
                            fragment= new fragment4();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fixing_this_fragment,fragment).commit();
                    return false;
                }


            };
*/

}