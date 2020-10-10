package com.example.beachlist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectedFriend extends AppCompatActivity {
    ImageView profilePic;
    TextView firstName, lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_friend);

        profilePic = findViewById(R.id.iv_selected_friend_image);
        firstName = findViewById(R.id.tv_first_name);
        lastName = findViewById(R.id.tv_last_name);

        int image = getIntent().getIntExtra("Profile Picture",1);
        String fName = getIntent().getStringExtra("First Name");
        String lName = getIntent().getStringExtra("Last Name");

        profilePic.setImageResource(image);
        firstName.setText(fName);
        lastName.setText(lName);
    }
}
