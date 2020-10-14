package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectedPendingFriend extends AppCompatActivity {
    ImageView profilePic;
    TextView firstName, lastName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pending_friend);

        profilePic = findViewById(R.id.iv_selected_pending_friend_image);
        firstName = findViewById(R.id.tv_pending_first_name);
        lastName = findViewById(R.id.tv_pending_last_name);

        // gets the pic and name of the user to display
        int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        profilePic.setImageResource(PendingFriendsListTab.list.get(position).getImageProfile());
        firstName.setText(PendingFriendsListTab.list.get(position).getFirstName());
        lastName.setText(PendingFriendsListTab.list.get(position).getLastName());

        // Go back to pending friends list
        Button backButton = findViewById(R.id.pending_friend_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPendingFriendListScreen();
            }
        });

        // Accept and Reject Request buttons
        Button acceptRequest = findViewById(R.id.btn_accept_user);
        acceptRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Temporarily sending to friends list to make sure it works
                openFriendsListScreen();
            }
        });

        Button rejectRequest = findViewById(R.id.btn_reject_user);
        rejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Temporarily sending to pending friends list to make sure it works
                openPendingFriendListScreen();
            }
        });
    }

    public void openPendingFriendListScreen(){
        Intent openScreen = new Intent(this, PendingFriendsListTab.class);
        startActivity(openScreen);
    }

    public void openFriendsListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }
}
