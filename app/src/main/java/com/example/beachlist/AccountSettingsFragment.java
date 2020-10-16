package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link **Fragment} subclass.
 * Use the {@link **AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "CustomAuthActivity";
    private static final long ONE_MEGABYTE = 1024 * 1024;

    private View accountSettingsScreen;
    private Button friendsListButton, backButton;
    private UserData currentUser;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private FirebaseStorage storageReference;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accountSettingsScreen = inflater.inflate(R.layout.fragment_account_settings, container, false);

        //instance of authentication
        mAuth = FirebaseAuth.getInstance();
        //instance of the database
        database = FirebaseDatabase.getInstance();

        //current user and their ID
        user = mAuth.getCurrentUser();
        final String userId = user.getUid();

        //Database reference to the users data
        final DatabaseReference userReference = database.getReference("users").child(userId).child("data");
        userReference
            .addValueEventListener(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            currentUser = snapshot.getValue(UserData.class);
                            displayUserInfo(currentUser);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.e(TAG, "The read failed: " + error.getMessage());
                        }
                    }
            );

        // Takes us to friends list screen when we click the button
        friendsListButton = accountSettingsScreen.findViewById(R.id.btn_friend_list);
        friendsListButton.setOnClickListener(this);

        return accountSettingsScreen;

    }

    @Override
    public void onClick(View view) {
        Intent openScreen = new Intent(getActivity(), FriendsListTab.class);
        startActivity(openScreen);
    }

    public void displayUserInfo(UserData userData) {
        TextView fnameTV = accountSettingsScreen.findViewById(R.id.tv_first_name);
        TextView lnameTV = accountSettingsScreen.findViewById(R.id.tv_last_name);

        fnameTV.setText(userData.firstName);
        lnameTV.setText(userData.lastName);

        final ImageView profileIV = accountSettingsScreen.findViewById(R.id.iv_user_image);

        System.out.println("Hope"+userData.getImageUrl()+"Space");

        if(userData.getImageUrl().compareTo(" ") != 0) {
            storageReference = FirebaseStorage.getInstance();
            final StorageReference imageRef = storageReference.getReferenceFromUrl(userData.imageUrl);

            imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    profileIV.setImageBitmap(bmp);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    profileIV.setImageResource(R.mipmap.ic_launcher);
                }
            });
        }
    }
}