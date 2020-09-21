package com.example.beachlist;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
// this is neeeded in order to run the program. if no main activity is set then nothing will run
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this line makes it so the first page to be displayed is the one below.
        setContentView(R.layout.activity_main);

    }
}