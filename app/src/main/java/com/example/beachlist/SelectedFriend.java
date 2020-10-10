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

        int position = getIntent().getIntExtra("position",1);

        profilePic.setImageResource(FriendsList.list.get(position).getImageProfile());
        firstName.setText(FriendsList.list.get(position).getFirstName());
        lastName.setText(FriendsList.list.get(position).getLastName());
    }
}
