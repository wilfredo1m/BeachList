package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

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
import java.util.HashMap;
import java.util.List;

public class SelectedUser extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic,reportedPersonImage;
    TextView firstName, lastName, selectedUserEmail;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    UserData selectedUser;
    Button reportUser,cancelReport,submitReport,addFriendButton;
    ConstraintLayout constraintLayout;
    Spinner reportUserSpinner;
    String reportedUserReason, listingID;
    RecyclerView itemRecycler, serviceRecycler;
    ItemRecyclerAdapter itemAdapter;
    ServiceRecyclerAdapter serviceAdapter;
    List<DataSnapshot> itemList, serviceList;
    ScrollView scrollView;
//    ViewPager itemPager,servicePager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_user);

        listingID = getIntent().getStringExtra("listingID");
        final int callingActivity = getIntent().getIntExtra("screen",7);

        //firebase values
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        //this layout is the pop up menu layout
        scrollView = findViewById(R.id.user_listing_scroll_view);                                     //link scrollView to xml
        constraintLayout = findViewById(R.id.fuzzy_background);                                       //link constraintLayout to xml
        profilePic = findViewById(R.id.selected_user_profile_pic);                                    //link profilePic to xml
        firstName = findViewById(R.id.selected_user_first_name);                                      //link firstName to xml
        lastName = findViewById(R.id.selected_user_last_name);                                        //link lastName to xml
        addFriendButton = findViewById(R.id.btn_add_user);                                            //link addFriendButton to xml
        selectedUserEmail = findViewById(R.id.selected_user_email_tv);

//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
        //SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST
        //ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON
        reportUserSpinner = findViewById(R.id.report_user_spinner);                                                        //initiate the spinner
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,        //array adapter holding the array list of categories created in the strings.xml
                getResources().getStringArray(R.array.report_user));
        reportUserSpinner.setAdapter(arrayAdapter);                                                                       //setup adapter to be passed to spinner
        reportUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {                            //set listener to item selected
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reportedUserReason =  reportUserSpinner.getSelectedItem().toString();                                          //set selected value in spinner to the reportedUser string
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
        //final int position = getIntent().getIntExtra("position",1);

        //Get display information
        final String selectedUserId = getIntent().getStringExtra("selectedUserId");

        DatabaseReference selectedUserRef = database.getReference("users").child(selectedUserId).child("data");

        selectedUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                selectedUser = snapshot.getValue(UserData.class);
                displayUserInfo();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: "+error.getMessage(), error.toException());
            }
        });
//********************************************************************************************************************************************//
//*****************************************END USER INFO SECTION************************************************************************//


//*****************************************GET USER LISTING FROM FIREBASE***********************************************************************//
//********************************************************************************************************************************************//
        itemRecycler = findViewById(R.id.item_recycler);                                              //link itemRecycler to xml
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));                         //set recycler to the layout

        final Context context = this;
        serviceRecycler = findViewById(R.id.service_recycler);                                        //link serviceRecycler to xml
        serviceRecycler.setLayoutManager(new LinearLayoutManager(this));                      //set recycler to the layout
        itemList = new ArrayList<>();                                                                 //initialize itemList arraylist
        serviceList = new ArrayList<>();//initialize serviceList arraylist
        DatabaseReference selectedUserListingRef = database.getReference("users").child(selectedUserId).child("listings"); //Get all snapshots of user listings
        selectedUserListingRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child : snapshot.getChildren()) {                                               //for loop to populate lists
                    if(child.child("type").getValue(String.class).compareTo("item") == 0) {
                        itemList.add(child);                                                                  //add item to item arraylist
                    }
                    else {
                        serviceList.add(child);                                                               //add item to service arraylist
                    }
                }
                itemAdapter = new ItemRecyclerAdapter(context,itemList);                                 //set adapter to itemlist
                itemRecycler.setAdapter(itemAdapter);                                                         //pass recycler to the adapter

                serviceAdapter = new ServiceRecyclerAdapter(context, serviceList);                       //set adapter to servicelist
                serviceRecycler.setAdapter(serviceAdapter);                                                   //pass recycler to the adapter
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: "+error.getMessage(), error.toException());
            }
        });

//********************************************************************************************************************************************//
//*****************************************END USER LISTING SECTION************************************************************************//


//*********************************BUTTON GROUP***********************************************************************************************//
//********************************************************************************************************************************************//
        // Send user a friend request
        addFriendButton = findViewById(R.id.btn_add_user);                                                                        //link button to xml
        addFriendButton.setOnClickListener(new View.OnClickListener() {                                                           //set on click listener for button
            @Override
            public void onClick(View view) {
                DatabaseReference myDataReference = database.getReference().child("users").child(user.getUid()).child("data");
                myDataReference.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                // Get Post object and use the values to update the UI
                                UserData userData = dataSnapshot.getValue(UserData.class);
                                addPendingFriend(userData);
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
        Button backButton = findViewById(R.id.selected_user_back_button);                               //link button to xml
        backButton.setOnClickListener(new View.OnClickListener() {                                      //set on click listener for button
            @Override
            public void onClick(View view) {
                if (callingActivity == 1) {            //
                    openItemScreen();
                }
                else if (callingActivity == 2){
                    openServiceScreen();
                }
                else if(callingActivity ==3){         // 3 is a item/service within a user
                    openHomeScreen();
                }
                else
                    openHomeScreen();
            }
        });

        //user clicks on report user button in order to populate pop up menu to report user
        reportUser = findViewById(R.id.btn_report_user);                                              //link button to xml
        reportUser.setOnClickListener(new View.OnClickListener() {                                    //set on click listener for button
            @Override
            public void onClick(View v) {
                //populateReportedUserScreen();
                setupPopUpScreenView();
                itemRecycler.setVisibility(View.INVISIBLE);
                serviceRecycler.setVisibility(View.INVISIBLE);
            }
        });

        //user cancels the report to set the view back to the original view
        cancelReport = findViewById(R.id.cancel_report_button);                                           //link button to xml
        cancelReport.setOnClickListener(new View.OnClickListener() {                                      //set on click listener for button
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
                itemRecycler.setVisibility(View.VISIBLE);
                serviceRecycler.setVisibility(View.VISIBLE);
            }
        });

        //user clicks on submit button to send the report to the admin page
       submitReport = findViewById(R.id.confirm_report_user_button);                                     //link button to xml
       submitReport.setOnClickListener(new View.OnClickListener() {                                      //set on click listener for button
            @Override
            public void onClick(View v) {
                //banUser(database.getReference("users").child(selectedUser.getUserId()).child("banned"));
                addUserToReported();
            }
        });
//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//
    }//end onCreate

    private void displayUserInfo() {
        // Sets the persons info in the correct fields to be displayed
        Glide.with(this)
                .load(selectedUser.getImageUrl())
                .centerCrop()
                .into(profilePic);
        firstName.setText(selectedUser.getFirstName());
        lastName.setText(selectedUser.getLastName());
        selectedUserEmail.setText(selectedUser.getEmail());
    }

    private void addUserToReported() {
        DatabaseReference reportedRef = database.getReference("reported").child("users").child(selectedUser.getUserId());
        HashMap<String, String> reportedListing = new HashMap<>();
        reportedListing.put("name", selectedUser.getFirstName() + " " + selectedUser.getLastName());
        reportedListing.put("imageUrl", selectedUser.getImageUrl());
        reportedListing.put("reason", reportedUserReason);
        reportedRef.setValue(reportedListing).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                openHomeScreen();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: "+e.getLocalizedMessage());
            }
        });
    }

//    private void banUser(DatabaseReference bannedRef) {
//        bannedRef.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                addUserToReported();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.e(TAG, "onFailure: "+e.getLocalizedMessage(), e.getCause());
//            }
//        });
//    }

    //intent to change to home after login screen with 1st menu item selected
    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab",1);
        startActivity(openScreen);
    }

    // Go back to selected listing user was just at
    public void openItemScreen(){
        Intent openScreen = new Intent(this, SelectedItem.class);
        openScreen.putExtra("ListingID",listingID);
        startActivity(openScreen);
    }
    public void openServiceScreen(){
        Intent openScreen = new Intent(this, SelectedService.class);
        openScreen.putExtra("ListingID",listingID);
        startActivity(openScreen);
    }

    //adds pending friend to firebase
    public void addPendingFriend(UserData userData){
        DatabaseReference addPendingFriendReference = database.getReference().child("users").child(selectedUser.getUserId()).child("pending").child(user.getUid());
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
