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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectedPendingFriend extends AppCompatActivity {
    private static final String TAG = "error";
    ImageView profilePic, reportedPersonImage;
    TextView firstName, lastName;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    public static List<DataSnapshot> itemList = new ArrayList<>();
    public static List<DataSnapshot> serviceList = new ArrayList<>();
    ItemRecyclerAdapter itemRecyclerAdapter;
    ServiceRecyclerAdapter serviceRecyclerAdapter;
    RecyclerView recyclerViewItem;
    RecyclerView recyclerViewService;
    ScrollView listingView;
    Spinner reportUserSpinner;
    Button reportButton, rejectRequest,acceptRequest,backButton,submitReport, cancelReport;
    TextView email;
    ConstraintLayout popupWindow;
    String reportedUser;
    UserData pendingFriend;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_pending_friend);
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        profilePic = findViewById(R.id.iv_selected_pending_friend_image);                                    //link profile pic to xml
        firstName = findViewById(R.id.tv_pending_first_name);                                                //link firstName to xml
        lastName = findViewById(R.id.tv_pending_last_name);                                                  //link lastName to xml
        listingView = findViewById(R.id.listing_scroll_view);                                                //link listingView to xml
        popupWindow = findViewById(R.id.popup_window);                                                       //link popupWindow view to xml
        email = findViewById(R.id.pending_friend_email);

        recyclerViewItem = findViewById(R.id.pending_friend_item_recycler);                                 //link recyclerViewItem view to xml
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewService = findViewById(R.id.pending_friend_service_recycler);                           //link recyclerViewService view to xml
        recyclerViewService.setLayoutManager(new LinearLayoutManager(this));

        final String friendUserId = getIntent().getStringExtra("userId");                          //retrieve position from a intent passed

        DatabaseReference userRef = database.getReference("users").child(friendUserId).child("data");
//********************************SETS PERSON INFO FROM FIREBASE**************************************************************************//
        final Context context = this;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pendingFriend = snapshot.getValue(UserData.class);
                Glide.with(context)
                        .load(pendingFriend.getImageUrl())
                        .centerCrop()
                        .into(profilePic);
                //profilePic.setImageBitmap(null);
                firstName.setText(pendingFriend.getFirstName());
                lastName.setText(pendingFriend.getLastName());
                email.setText(pendingFriend.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // get selected friend's active listings
        Query itemsQuery = database.getReference().child("listings").child("item").orderByChild("ownerId").equalTo(friendUserId);
        Query serviceQuery = database.getReference().child("listings").child("service").orderByChild("ownerId").equalTo(friendUserId);

        itemsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    itemList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        itemList.add(child);
                    }

                    onItemListQuery();
                }                     //get id of friend
                //Toast.makeText(getApplicationContext(), friendUserID, Toast.LENGTH_SHORT).show();
                //getFriendEmail(friendUserId);                                                                   //get the email address of friend to populate field
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        serviceQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    serviceList.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        serviceList.add(child);
                    }

                    onServiceListQuery();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
//********************************END SETS PERSON INFO FROM FIREBASE**************************************************************************//


//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
        //SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST
        //ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON
        reportUserSpinner = findViewById(R.id.report_reason_spinner);                                                     //link reportUserSpinner to xml

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.report_user));
        reportUserSpinner.setAdapter(arrayAdapter);                                                                      //setup adapter to be passed to spinner
        reportUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reportedUser =  reportUserSpinner.getSelectedItem().toString();                                         //set reportedUser to the string selected
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//


//*****************************************************BUTTON GROUP******************************************************************************//
        // Go back to pending friends list
        backButton = findViewById(R.id.pending_friend_back_button);                                                   //link button to xml
        backButton.setOnClickListener(new View.OnClickListener() {                                                   //set onclicklistner to button
            @Override
            public void onClick(View view) {
                openPendingFriendListScreen();
            }
        });

        // Accept and Reject Request buttons
        acceptRequest = findViewById(R.id.btn_accept_user);                                                       //link button to xml
        acceptRequest.setOnClickListener(new View.OnClickListener() {                                            //set onclicklistner to button
            @Override
            public void onClick(View view) {
                acceptRequest(friendUserId);
            }
        });

        rejectRequest = findViewById(R.id.btn_reject_user);                                                     //link button to xml
        rejectRequest.setOnClickListener(new View.OnClickListener() {                                           //set onclicklistner to button
            @Override
            public void onClick(View view) {
                DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(friendUserId);
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
        reportButton = findViewById(R.id.btn_report_user);                                                    //link button to xml
        reportButton.setOnClickListener(new View.OnClickListener() {                                          //set onclicklistner to button
            @Override
            public void onClick(View v) {
                //populateReportedUserScreen();
                setupPopUpScreenView();
                recyclerViewItem.setVisibility(View.INVISIBLE);
                recyclerViewService.setVisibility(View.INVISIBLE);
            }
        });

        submitReport = findViewById(R.id.submit_report_btn);                                                   //link button to xml
        submitReport.setOnClickListener(new View.OnClickListener() {                                          //set onclicklistner to button
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), reportedUser, Toast.LENGTH_SHORT).show();
            }
        });

        cancelReport = findViewById(R.id.cancel_report_btn);                                                   //link button to xml
        cancelReport.setOnClickListener(new View.OnClickListener() {                                          //set onclicklistner to button
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
                recyclerViewItem.setVisibility(View.VISIBLE);
                recyclerViewService.setVisibility(View.VISIBLE);
            }
        });

//*****************************************************END BUTTON GROUP******************************************************************************//

    }//end onCreate()


    //pending friendlist
    public void openPendingFriendListScreen(){
        Intent openScreen = new Intent(this, PendingFriendsListTab.class);
        startActivity(openScreen);
    }
    //friendlist
    public void openFriendsListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    public void acceptRequest(final String friendUserId) {
        DatabaseReference newFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(friendUserId);
        newFriendReference.setValue(new OtherUser(pendingFriend.getFirstName(), pendingFriend.getLastName(), pendingFriend.getImageUrl()))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        removeFromPending(friendUserId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not add value");
                    }
                });
    }

    public void removeFromPending(final String friendUserId) {
        DatabaseReference deletePendingReference = database.getReference().child("users").child(user.getUid()).child("pending").child(friendUserId);
        deletePendingReference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        retrieveMyData(friendUserId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "could not delete value");
                    }
                });
    }

    public void updateOtherUsersFriends(final String friendUserId, OtherUser myData) {
        DatabaseReference updateOtherUserReference = database.getReference().child("users").child(friendUserId).child("friends").child(user.getUid());
        updateOtherUserReference.setValue(myData)
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

    public void retrieveMyData(final String friendUserId) {
        DatabaseReference retrieveMyDataReference = database.getReference().child("users").child(user.getUid()).child("data");
        retrieveMyDataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                OtherUser myData = new OtherUser(dataSnapshot.getValue(UserData.class).getFirstName(), dataSnapshot.getValue(UserData.class).getLastName(), dataSnapshot.getValue(UserData.class).getImageUrl());
                updateOtherUsersFriends(friendUserId, myData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void onItemListQuery() {
        itemRecyclerAdapter = new ItemRecyclerAdapter(this, itemList);
        recyclerViewItem.setAdapter(itemRecyclerAdapter);
    }

    public void onServiceListQuery() {
        serviceRecyclerAdapter = new ServiceRecyclerAdapter(this, serviceList);
        recyclerViewService.setAdapter(serviceRecyclerAdapter);
    }

    public void setupPopUpScreenView(){
        // change visibility to views
        popupWindow.setVisibility(View.VISIBLE);
        //buttons were an issue so they need to be invisible
        reportButton.setVisibility(View.INVISIBLE);
        rejectRequest.setVisibility(View.INVISIBLE);
        acceptRequest.setVisibility(View.INVISIBLE);
        listingView.setVisibility(View.INVISIBLE);
    }

    public void setupRevertScreenView(){
        // change visibility to views
        popupWindow.setVisibility(View.INVISIBLE);
        //buttons were an issue so they need to be invisible
        reportButton.setVisibility(View.VISIBLE);
        rejectRequest.setVisibility(View.VISIBLE);
        acceptRequest.setVisibility(View.VISIBLE);
        listingView.setVisibility(View.VISIBLE);
    }

    public void getFriendEmail(String id){
        DatabaseReference userRef = database.getReference().child("users").child(id);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email.setText(snapshot.child("data").getValue(UserData.class).getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }


}
