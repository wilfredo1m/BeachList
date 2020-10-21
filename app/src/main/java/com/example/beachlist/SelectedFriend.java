package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectedFriend extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_friend);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        profilePic = findViewById(R.id.iv_selected_friend_image);
        firstName = findViewById(R.id.tv_first_name);
        lastName = findViewById(R.id.tv_last_name);

        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        Glide.with(this)
                .load(FriendsListTab.list.get(position).getImageProfile())
                .centerCrop()
                .into(profilePic);
        //profilePic.setImageBitmap(null);
        firstName.setText(FriendsListTab.list.get(position).getFirstName());
        lastName.setText(FriendsListTab.list.get(position).getLastName());

        // Remove friend from friends list
        Button unfriendButton = findViewById(R.id.btn_remove_friend);
        unfriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deleteFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(FriendsListTab.list.get(position).userId);
                deleteFriendReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openFriendListScreen();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "could not delete value");
                            }
                        });
            }
        });

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
