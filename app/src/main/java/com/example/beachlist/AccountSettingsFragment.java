package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link **Fragment} subclass.
 * Use the {@link **AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "CustomAuthActivity";

    private View accountSettingsScreen;
    private UserData currentUser;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accountSettingsScreen = inflater.inflate(R.layout.fragment_account_settings, container, false);

        //instance of authentication
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //instance of the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //current user and their ID
        FirebaseUser user = mAuth.getCurrentUser();
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


//===================================================BUTTON CALL GROUP========================================================//
        // Takes us to friends list screen when we click the button
        Button friendsListButton = accountSettingsScreen.findViewById(R.id.btn_friend_list);
        friendsListButton.setOnClickListener(this);

        Button pendingFriend = accountSettingsScreen.findViewById(R.id.btn_pending_friends);
        pendingFriend.setOnClickListener(this);

        Button activeListingsButton = accountSettingsScreen.findViewById(R.id.btn_active_listings);
        activeListingsButton.setOnClickListener(this);

        Button soldListingsButton = accountSettingsScreen.findViewById(R.id.btn_sold_listings);
        soldListingsButton.setOnClickListener(this);

        final Button reportedUser = accountSettingsScreen.findViewById(R.id.btn_reported_user);
        reportedUser.setOnClickListener(this);

        final Button reportedListing = accountSettingsScreen.findViewById(R.id.btn_reported_listing);
        reportedListing.setOnClickListener(this);

        Button logout = accountSettingsScreen.findViewById(R.id.btn_log_out);
        reportedListing.setOnClickListener(this);

//===================================================END BUTTON CALL GROUP====================================================//



//***********************CHECK TO SEE IF THE USER WILL BE A ADMIN **********************************************************//
//*********************IF USER IS ADMIN DISPLAY EXTRA BUTTONS WITH ADMIN RIGHTS********************************************//
        String emailString = user.getEmail();
//        Toast.makeText(accountSettingsScreen.getContext(), emailString, Toast.LENGTH_SHORT).show();
        final DatabaseReference adminRef = database.getReference("users").child(userId).child("isAdmin");
        adminRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Boolean bool = snapshot.getValue(Boolean.class);
                if (bool.booleanValue()){
                    reportedUser.setVisibility(View.VISIBLE);
                    reportedListing.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR", error.getDetails());
            }
        });
//        if (emailString.contains("@yahoo.com")){
//            reportedUser.setVisibility(View.VISIBLE);
//            reportedListing.setVisibility(View.VISIBLE);
//        }else{
//            //dont display the screens
//        }
//***********************END CHECK TO SEE IF THE USER WILL BE A ADMIN **********************************************************//

        return accountSettingsScreen;

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_friend_list:
                Intent openScreen = new Intent(getActivity(), FriendsListTab.class);
                startActivity(openScreen);
                break;
            case R.id.btn_active_listings:
                openScreen = new Intent(getActivity(), ActiveListings.class);
                openScreen.putExtra("signedInUserListingTab", 1);
                startActivity(openScreen);
                break;
            case R.id.btn_pending_friends:
                openScreen = new Intent(getActivity(), PendingFriendsListTab.class);
                //sets which tab will be displayed
                startActivity(openScreen);
                break;
            case R.id.btn_sold_listings:
                //set intent to select position of tab when button clicked
                openScreen = new Intent( getActivity(), ActiveListings.class);
                //sets which tab will be displayed
                openScreen.putExtra("signedInUserListingTab", 2);
                startActivity(openScreen);
                break;
            case R.id.btn_reported_user:
                //set intent to select position of tab when button clicked
                openScreen = new Intent( getActivity(), ReportedScreen.class);
                //sets which tab will be displayed
                openScreen.putExtra("reportedPageTab", 1);
                startActivity(openScreen);
                break;
            case R.id.btn_reported_listing:
                //set intent to select position of tab when button clicked
                openScreen = new Intent( getActivity(), ReportedScreen.class);
                openScreen.putExtra("reportedPageTab", 2);
                //sets which tab will be displayed
                startActivity(openScreen);
                break;
           // case R.id.btn_log_out:
           //     openScreen = new Intent( getActivity(), MainActivity.class);
           //     startActivity(openScreen);

        }
    }

    public void displayUserInfo(UserData userData) {
        TextView fnameTV = accountSettingsScreen.findViewById(R.id.tv_full_name);
        TextView email = accountSettingsScreen.findViewById(R.id.tv_email);
        fnameTV.setText(String.format("%s %s", userData.firstName, userData.lastName));
        email.setText(userData.email);

        final ImageView profileIV = accountSettingsScreen.findViewById(R.id.iv_user_image);

        if(userData.getImageUrl().compareTo(" ") != 0) {
            Glide.with(this)
                    .load(userData.getImageUrl())
                    .centerCrop()
                    .into(profileIV);
        }
        else {
            profileIV.setImageResource(R.mipmap.ic_launcher);
        }
    }
}