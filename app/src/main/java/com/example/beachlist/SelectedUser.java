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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SelectedUser extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        profilePic = findViewById(R.id.selected_user_profile_pic);
        firstName = findViewById(R.id.selected_user_first_name);
        lastName = findViewById(R.id.selected_user_last_name);

        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        profilePic.setImageResource(UserHomeSearchTab.user_list.get(position).getImageProfile());
        firstName.setText(UserHomeSearchTab.user_list.get(position).getFirstName());
        lastName.setText(UserHomeSearchTab.user_list.get(position).getLastName());

        // Send user a friend request
        Button addFriendButton = findViewById(R.id.btn_add_user);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deleteFriendReference = database.getReference().child("users").child(user.getUid()).child("pending").child(UserHomeSearchTab.user_list.get(position).userId);
                deleteFriendReference.setValue(new OtherUser(UserHomeSearchTab.user_list.get(position).getFirstName(), UserHomeSearchTab.user_list.get(position).getLastName(), "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1"))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openHomeScreen();
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
