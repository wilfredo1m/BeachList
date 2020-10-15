package com.example.beachlist;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ListingDescriptionPage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_descirption_page);

        SeekBar seekBar = findViewById(R.id.title_seek_bar);
        seekBar.setEnabled(false);

    }

}
