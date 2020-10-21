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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SelectedPendingFriend extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pending_friend);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        profilePic = findViewById(R.id.iv_selected_pending_friend_image);
        firstName = findViewById(R.id.tv_pending_first_name);
        lastName = findViewById(R.id.tv_pending_last_name);

        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        profilePic.setImageBitmap(PendingFriendsListTab.list.get(position).getImageProfile());
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
                DatabaseReference newFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(PendingFriendsListTab.list.get(position).userId);
                newFriendReference.setValue(new OtherUser(PendingFriendsListTab.list.get(position).getFirstName(), PendingFriendsListTab.list.get(position).getLastName(), "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1"))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(PendingFriendsListTab.list.get(position).userId);
                                deletePendingReference.removeValue()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                openFriendsListScreen();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "could not delete value");
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "could not add value");
                            }
                        });
            }
        });

        Button rejectRequest = findViewById(R.id.btn_reject_user);
        rejectRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(PendingFriendsListTab.list.get(position).userId);
                deletePendingReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                openFriendsListScreen();
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
