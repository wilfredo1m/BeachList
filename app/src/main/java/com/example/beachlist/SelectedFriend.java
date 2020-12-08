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

public class SelectedFriend extends AppCompatActivity {
    public static List<DataSnapshot> itemList = new ArrayList<>();
    public static List<DataSnapshot> serviceList = new ArrayList<>();
    private static final String TAG = "error";
    Button reportFriend, unfriendButton, cancelReport,submitReport;
    ImageView profilePic;
    TextView firstName, lastName, email;
    ConstraintLayout constraintLayout;
    FirebaseDatabase database;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Spinner reportUserSpinner;
    String reportedUser;
    ItemRecyclerAdapter itemRecyclerAdapter;
    ServiceRecyclerAdapter serviceRecyclerAdapter;
    RecyclerView recyclerViewItem;
    ScrollView listingScroll;
    RecyclerView recyclerViewService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_friend);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        constraintLayout = findViewById(R.id.popup_layout);

        profilePic = findViewById(R.id.iv_selected_friend_image);                                   //link profile pic to xml
        firstName = findViewById(R.id.tv_full_name);                                                //link firstName pic to xml
        lastName = findViewById(R.id.tv_email);                                                     //link lastName pic to xml
        listingScroll = findViewById(R.id.listing_scroll_view);                                     //listingScroll profile pic to xml
        email = findViewById(R.id.friend_email_tv);

        recyclerViewItem = findViewById(R.id.friend_item_recycler);                                 //link recyclerViewItem view to xml
        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));

        recyclerViewService = findViewById(R.id.friend_service_recycler);                           //link recyclerViewService view to xml
        recyclerViewService.setLayoutManager(new LinearLayoutManager(this));

        // gets the pic and name of the user to display
        final String friendUserId = getIntent().getStringExtra("userId");                          //retrieve position from a intent passed


        DatabaseReference userRef = database.getReference("users").child(friendUserId).child("data");
//********************************SETS PERSON INFO FROM FIREBASE**************************************************************************//
        final Context context = this;
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserData currentFriend = snapshot.getValue(UserData.class);
                Glide.with(context)
                        .load(currentFriend.getImageUrl())
                        .centerCrop()
                        .into(profilePic);
                //profilePic.setImageBitmap(null);
                firstName.setText(currentFriend.getFirstName());
                lastName.setText(currentFriend.getLastName());
                email.setText(currentFriend.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // Sets the persons info in the correct fields to be displayed
//        Glide.with(this)
//                .load(FriendsListTab.list.get(position).getValue(OtherUser.class).getImageUrl())
//                .centerCrop()
//                .into(profilePic);
//        //profilePic.setImageBitmap(null);
//        firstName.setText(FriendsListTab.list.get(position).getValue(OtherUser.class).getFirstName());
//        lastName.setText(FriendsListTab.list.get(position).getValue(OtherUser.class).getLastName());

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
                }
                //getFriendEmail(friendUserId);                                                           //get email of friend
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
        reportUserSpinner = findViewById(R.id.report_friend_spinner);                                                      //initiate the spinner

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,        //array adapter holding the array list of categories created in the strings.xml
                getResources().getStringArray(R.array.report_user));
        reportUserSpinner.setAdapter(arrayAdapter);                                                                      //setup adapter to be passed to spinner
        reportUserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {                           //set on click listener to button
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reportedUser =  reportUserSpinner.getSelectedItem().toString();                                          //set text to reportedUser from the selected field in spinner
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//


//*****************************************************BUTTON GROUP******************************************************************************//
        // Go back to friends list
        Button backButton = findViewById(R.id.selected_friend_back_button);                                      //link button to xml
        backButton.setOnClickListener(new View.OnClickListener() {                                               //set on click listener for button
            @Override
            public void onClick(View view) {
                openFriendListScreen();
            }
        });

        //user clicks on report user button in order to populate pop up menu to report user
        reportFriend = findViewById(R.id.btn_report_friend);                                                     //link button to xml
        reportFriend.setOnClickListener(new View.OnClickListener() {                                             //set on click listener for button
            @Override
            public void onClick(View v) {
                setupPopUpScreenView();
                recyclerViewItem.setVisibility(View.INVISIBLE);
                recyclerViewService.setVisibility(View.INVISIBLE);
            }
        });

        // Remove friend from friends list
        unfriendButton = findViewById(R.id.btn_remove_friend);                                                     //link button to xml
        unfriendButton.setOnClickListener(new View.OnClickListener() {                                             //set on click listener for button
            @Override
            public void onClick(View view) {
                DatabaseReference deleteFriendReference = database.getReference().child("users").child(user.getUid()).child("friends").child(friendUserId);
                deleteFriendReference.removeValue()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                updateOtherUsersFriends(friendUserId);
                                Toast.makeText(getApplicationContext(), "User Has been Removed From Friend List", Toast.LENGTH_SHORT).show();

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
        //user cancels the report to set the view back to the original view
        cancelReport = findViewById(R.id.cancel_button);                                                     //link button to xml
        cancelReport.setOnClickListener(new View.OnClickListener() {                                         //set on click listener for button
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
                recyclerViewItem.setVisibility(View.VISIBLE);
                recyclerViewService.setVisibility(View.VISIBLE);
            }
        });

        //user clicks on submit button to send the report to the admin page
        submitReport = findViewById(R.id.submit_button);                                                     //link button to xml
        submitReport.setOnClickListener(new View.OnClickListener() {                                         //set on click listener for button
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), reportedUser, Toast.LENGTH_SHORT).show();

            }
        });
//*****************************************************END BUTTON GROUP******************************************************************************//

    }


    public void onItemListQuery() {
        itemRecyclerAdapter = new ItemRecyclerAdapter(this, itemList);
        recyclerViewItem.setAdapter(itemRecyclerAdapter);
    }

    public void onServiceListQuery() {
        serviceRecyclerAdapter = new ServiceRecyclerAdapter(this, serviceList);
        recyclerViewService.setAdapter(serviceRecyclerAdapter);
    }

    public void openFriendListScreen(){
        Intent openScreen = new Intent(this, FriendsListTab.class);
        startActivity(openScreen);
    }

    public void updateOtherUsersFriends(String userId) {
        DatabaseReference deleteFriendReference = database.getReference().child("users").child(userId).child("friends").child(user.getUid());
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


    public void setupPopUpScreenView(){
        // change visibility to views
        constraintLayout.setVisibility(View.VISIBLE);
        //buttons were an issue so they need to be invisible
        reportFriend.setVisibility(View.INVISIBLE);
        unfriendButton.setVisibility(View.INVISIBLE);
        listingScroll.setVisibility(View.INVISIBLE);
    }

    public void setupRevertScreenView(){
        //set visibility to views
        reportFriend.setVisibility(View.VISIBLE);
        unfriendButton.setVisibility(View.VISIBLE);
        constraintLayout.setVisibility(View.INVISIBLE);
        listingScroll.setVisibility(View.VISIBLE);
    }

//    public void getFriendEmail(String id){
//        DatabaseReference userRef = database.getReference().child("users").child(id);
//        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                email.setText(snapshot.child("data").getValue(UserData.class).getEmail());
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//            }
//        });
//    }
}
