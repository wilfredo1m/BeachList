package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ConversationScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<Message> conversation_list = new ArrayList<>();
    ConversationRecyclerAdapter adapter;

    //***main page items used******************//
    Button sendMessage, backButton,soldButton;                                                        //buttons to navitage screen
    ConstraintLayout mainPage;                                                                        //layout for main page incase we need to make it invisible
    String listingId, friendID, userID, ownerOfListingName,
            ownerOfListingEmail, potentialBuyerName, potentialBuyerEmail, listingType;
    private FirebaseDatabase firebaseDatabase;
    private final ArrayList<String> itemImages = new ArrayList<>();
    TextView userName,userEmail;
    ImageView listingImage;
    DatabaseReference listingRef, conversationRef, messageRef;
    //**end main page items used *************//

    //****popup window items used************//
    ConstraintLayout soldView;                                                                        //layout for popup window in order to make it pop up
    Button confirmSale, cancelSale;                                                                   //buttons to confirm sale
    RatingBar rating;                                                                                 //rating bar object to be able to retrieve the rating give
    //*****end popup window items used*****//





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_conversation);                                      //used to display the activity_message_conversation.xml
        userName = findViewById(R.id.user_full_name_tv);
        mainPage = findViewById(R.id.main_page_layout);
        soldView = findViewById(R.id.sold_page_layout);
        rating = findViewById(R.id.rate_user_rb);
        userEmail = findViewById(R.id.user_email);
        listingImage = findViewById(R.id.listing_image_iv);
        firebaseDatabase = FirebaseDatabase.getInstance();


        friendID = getIntent().getStringExtra("friend ID");
        // gets the item's information to display
//        friendID = getIntent().getStringExtra("UserID");
//        listingId = getIntent().getStringExtra("ListingID");
//        listingType = getIntent().getStringExtra("listingType");

//        listingRef = firebaseDatabase.getReference().child("listings").child(listingType).child(listingId);


        //******************************Display Conversation***************************************
        recyclerView = findViewById(R.id.conversation_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();


        //*******************HARD CODED MESSAGES********************************
        Message message1 = new Message("Viet", "bernie", "Hello there");
        Message message2 = new Message("bernie", "Viet", "Hiya");
        Message message3 = new Message("Viet", "bernie", "I'll give you $20 for stuff");
        Message message4 = new Message("bernie", "Viet", "$55 is the lowest i can go");

        conversation_list.add(message1);
        conversation_list.add(message2);
        conversation_list.add(message3);
        conversation_list.add(message4);

        adapter = new ConversationRecyclerAdapter(ConversationScreen.this, conversation_list);
        recyclerView.setAdapter(adapter);
        //**********************************************************************


//        messageRef = firebaseDatabase.getReference("/users/" + userID + "/chats/" + friendID);
//        messageRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                conversation_list.clear();
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    Message message = dataSnapshot.getValue(Message.class);
//                    conversation_list.add(message);
//
//                    adapter = new ConversationRecyclerAdapter(ConversationScreen.this, conversation_list);
//                    recyclerView.setAdapter(adapter);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
        //*****************************************************************************************

//        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                //Populate image URLs in global variable
//                getListingImages(snapshot.child("listingImages"));
//                //Get data and display info
//                ListingData selectedListing = snapshot.getValue(ListingData.class);
//                assert selectedListing != null;
//                displayProfileImage();
//                //display owner Info
//                getOwnerInfo(friendID);
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                //TODO Handle this error
//            }
//        });







        //*****************main page buttons***************************//
        //*************line 34-53*************************************//
        sendMessage= findViewById(R.id.send_message_btn);                                              //link submit button to xml button
        sendMessage.setOnClickListener(new View.OnClickListener() {                                    //set on click listener for button
            @Override
            public void onClick(View v) {

            }
        });

        backButton= findViewById(R.id.back_to_messaging_home_btn);                                     //link submit button to xml button
        backButton.setOnClickListener(new View.OnClickListener() {                                    //set on click listener for button
            @Override
            public void onClick(View v) {
                openMessagesScreen();                                                                //calls method to change page back to home messaging screen
            }
        });

        soldButton = findViewById(R.id.sold_listing_btn);
        soldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayPopUpWindow();
                deactivateButtons();
            }
        });
        //**************END MAIN PAGE BUTTONS*********************//

        //************popup window sale page button**************//
        //
        confirmSale = findViewById(R.id.submit_sale_btn);
        confirmSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userRating = String.valueOf(rating.getRating());                               //to be used to update that users rating
               Toast.makeText(getApplicationContext(), userRating, Toast.LENGTH_SHORT).show();        //testing userRating is picking up correct value

            }
        });

        cancelSale = findViewById(R.id.cancel_sale_btn);
        cancelSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                revertButtonStatus();                                                                 //reactivate buttons canceled for the popup window
                revertDisplayWindow();                                                                //remove popup display
                rating.setRating(0);                                                                  //reset the rating bar upon a cancel
            }
        });



    }

    // takes us back to account settings screen
    public void openMessagesScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);              //intent to open HomeScreen
        openScreen.putExtra("screen",1);                                                //intent passed in order to move to 3rd position in the menu bar
        startActivity(openScreen);                                                                   //start change screen
    }

    public void displayPopUpWindow(){
        soldView.setVisibility(View.VISIBLE);
    }

    public void deactivateButtons(){
        soldButton.setClickable(false);
        backButton.setClickable(false);
        soldButton.setClickable(false);
    }

    public void revertButtonStatus(){
        soldButton.setClickable(true);
        backButton.setClickable(true);
        soldButton.setClickable(true);
    }

    public void revertDisplayWindow(){
        soldView.setVisibility(View.INVISIBLE);
    }

    private void getOwnerInfo(String ownerId) {
        final Context context = this;
        DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(ownerId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userName.setText(snapshot.child("data").getValue(UserData.class).getFirstName() + " "+snapshot.child("data").getValue(UserData.class).getLastName());
                userEmail.setText(snapshot.child("data").getValue(UserData.class).getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            itemImages.add(child.getValue(String.class));
        }
    }

    private void displayProfileImage() {
        Glide.with(getBaseContext())
                .load(itemImages.get(0))
                .centerCrop()
                .into(listingImage);

    }
}
