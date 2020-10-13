package com.example.beachlist;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectedUser extends AppCompatActivity {
    ImageView profilePic;
    TextView firstName, lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        profilePic = findViewById(R.id.selected_user_profile_pic);
        firstName = findViewById(R.id.selected_user_first_name);
        lastName = findViewById(R.id.selected_user_last_name);

        // gets the pic and name of the user to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        profilePic.setImageResource(FriendsListTab.list.get(position).getImageProfile());
        firstName.setText(FriendsListTab.list.get(position).getFirstName());
        lastName.setText(FriendsListTab.list.get(position).getLastName());


    }
}
