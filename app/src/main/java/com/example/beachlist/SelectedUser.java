package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedUser extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic,reportedPersonImage;
    TextView firstName, lastName, reportedUserName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Button reportUser,cancelReport,submitReport,addFriendButton;
    ConstraintLayout constraintLayout;
    Spinner reportUserSpinner;
    String reportedUser;
    RecyclerView itemRecycler, serviceRecycler;
    ItemRecyclerAdapter itemAdapter;
    ServiceRecyclerAdapter serviceAdapter;
    List<DataSnapshot> itemList, serviceList;
//    ViewPager itemPager,servicePager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        //firebase values
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //this layout is the pop up menu layout
        constraintLayout = findViewById(R.id.fuzzy_background);
        profilePic = findViewById(R.id.selected_user_profile_pic);
        firstName = findViewById(R.id.selected_user_first_name);
        lastName = findViewById(R.id.selected_user_last_name);
        addFriendButton = findViewById(R.id.btn_add_user);

//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
//**********************SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST***********************************************************************//
//****************************ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON************************//
        //initiate the spinner
        reportUserSpinner = findViewById(R.id.report_user_spinner);
        //array adapter holding the array list of categories created in the strings.xml
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.report_user));
        //setup adapter to be passed to spinner
        reportUserSpinner.setAdapter(arrayAdapter);
        reportUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reportedUser =  reportUserSpinner.getSelectedItem().toString();
                // your code here

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//


//*****************************************GET USER INFO FROM FIREBASE***********************************************************************//
//********************************************************************************************************************************************//
        // gets the pic and name of the user to display
        final int position = getIntent().getIntExtra("position",1);

        // Sets the persons info in the correct fields to be displayed
        Glide.with(this)
                .load(UserHomeSearchTab.user_list.get(position).child("data").getValue(UserData.class).getImageUrl())
                .centerCrop()
                .into(profilePic);
        firstName.setText(UserHomeSearchTab.user_list.get(position).child("data").getValue(UserData.class).getFirstName());
        lastName.setText(UserHomeSearchTab.user_list.get(position).child("data").getValue(UserData.class).getLastName());
//********************************************************************************************************************************************//
//*****************************************END USER SELECTION SECTION************************************************************************//


//*****************************************GET USER LISTING FROM FIREBASE***********************************************************************//
//********************************************************************************************************************************************//
        itemRecycler = findViewById(R.id.item_recycler);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));

        serviceRecycler = findViewById(R.id.service_recycler);
        serviceRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemList = new ArrayList<>();
        serviceList = new ArrayList<>();
        //Get all snapshots of user listings
        DataSnapshot userListings = UserHomeSearchTab.user_list.get(position).child("listings");
        for (DataSnapshot child : userListings.getChildren()) {
            if(child.child("type").getValue(String.class).compareTo("item") == 0) {
                itemList.add(child);
            }
            else {
                serviceList.add(child);
            }
        }

        itemAdapter = new ItemRecyclerAdapter(this,itemList);
        itemRecycler.setAdapter(itemAdapter);

        serviceAdapter = new ServiceRecyclerAdapter(this, serviceList);
        serviceRecycler.setAdapter(serviceAdapter);
//********************************************************************************************************************************************//
//*****************************************END USER LISTING SECTION************************************************************************//


//*********************************BUTTON GROUP***********************************************************************************************//
//********************************************************************************************************************************************//
        // Send user a friend request
        addFriendButton = findViewById(R.id.btn_add_user);
        addFriendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference myDataReference = database.getReference().child("users").child(user.getUid()).child("data");
                myDataReference.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                UserData userData = dataSnapshot.getValue(UserData.class);
                                addPendingFriend(position, userData);
                                // ...
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                // Getting Post failed, log a message
                                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                                // ...
                            }
                        }
                );
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

        //user clicks on report user button in order to populate pop up menu to report user
        reportUser = findViewById(R.id.btn_report_user);
        reportUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateReportedUserScreen();
                setupPopUpScreenView();
            }
        });

        //user cancels the report to set the view back to the original view
        cancelReport = findViewById(R.id.cancel_report_button);
        cancelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
            }
        });

        //user clicks on submit button to send the report to the admin page
       submitReport = findViewById(R.id.confirm_report_user_button);
       submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), reportedUser, Toast.LENGTH_SHORT).show();

            }
        });
//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//
    }//end onCreate


    //intent to change to homeafterlogin screen with 1st menu item selected
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab",1);
        startActivity(openScreen);
    }
    //adds pending friend to firebase
    public void addPendingFriend(int position, UserData userData){
        DatabaseReference addPendingFriendReference = database.getReference().child("users").child(UserHomeSearchTab.user_list.get(position).child("data").getValue(UserData.class).getUserId()).child("pending").child(user.getUid());
        addPendingFriendReference.setValue(new OtherUser(userData.getFirstName(), userData.getLastName(), userData.getImageUrl()))
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

    //populates fields for user being reported
    public void populateReportedUserScreen(){
        //reported user information
        reportedUserName = findViewById(R.id.reported_user_name);
        reportedUserName.setText(String.format("%s %s", firstName.getText(), lastName.getText()));
        reportedPersonImage = findViewById(R.id.reportedUserImage);
        reportedPersonImage.setImageDrawable(profilePic.getDrawable());
    }
    public void setupPopUpScreenView(){
        // change visibility to views
        constraintLayout.setVisibility(View.VISIBLE);
        //buttons were an issue so they need to be invisible
        reportUser.setVisibility(View.INVISIBLE);
        addFriendButton.setVisibility(View.INVISIBLE);
    }

    public void setupRevertScreenView(){
        //set visibility to views
        reportUser.setVisibility(View.VISIBLE);
        addFriendButton.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
    }
}
