package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        profilePic.setImageResource(UserHomeSearchTab.user_list.get(position).getImageProfile());
        firstName.setText(UserHomeSearchTab.user_list.get(position).getFirstName());
        lastName.setText(UserHomeSearchTab.user_list.get(position).getLastName());

        // Send user a friend request
        Button addFriendButton = findViewById(R.id.btn_add_user);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        // Go back to User search list (temporarily going back to home)
        Button backButton = findViewById(R.id.selected_user_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });
    }

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }
}
