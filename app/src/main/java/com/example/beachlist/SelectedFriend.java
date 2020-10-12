package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

        // gets the pic and name of the user to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        profilePic.setImageResource(FriendsListTab.list.get(position).getImageProfile());
        firstName.setText(FriendsListTab.list.get(position).getFirstName());
        lastName.setText(FriendsListTab.list.get(position).getLastName());

        // Go back to friends list
        Button backButton = findViewById(R.id.selected_friend_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFriendListScreen();
            }
        });
    }

    public void openFriendListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }
}
