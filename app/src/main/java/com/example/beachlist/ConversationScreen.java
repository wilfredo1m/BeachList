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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationScreen extends AppCompatActivity {
    RecyclerView recyclerView;
    public static List<DataSnapshot> messages_list = new ArrayList<>();
    public static List<DataSnapshot> convo_list = new ArrayList<>();
    ConversationRecyclerAdapter adapter;

    //***main page items used******************//
    Button sendMessage, backButton,soldButton;                                                        //buttons to navitage screen
    ConstraintLayout mainPage;                                                                        //layout for main page incase we need to make it invisible
    String listingId, friendID, userID, ownerOfListingName,
            ownerOfListingEmail, potentialBuyerName, potentialBuyerEmail, listingType = "";
    private FirebaseDatabase firebaseDatabase;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final ArrayList<String> itemImages = new ArrayList<>();
    TextView userName,userEmail;
    ImageView listingImage;
    String convoId;
    int convoSize;
    DatabaseReference listingRef, conversationRef, messageRef;
    //**end main page items used *************//

    //****popup window items used************//
    ConstraintLayout soldView;                                                                        //layout for popup window in order to make it pop up
    Button confirmSale, cancelSale;                                                                   //buttons to confirm sale
    RatingBar rating;                                                                                 //rating bar object to be able to retrieve the rating give
    //*****end popup window items used*****//
    final boolean[] newConvoFlag = {false};





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

        // Retrieve info from previous screen
        friendID = getIntent().getStringExtra("friend ID");

        //******************************Display Conversation***************************************
        recyclerView = findViewById(R.id.conversation_recyclerView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();
        messages_list.clear();
        convo_list.clear();


        String listingOwnerId = "";
        String sellerEmail = "";
        String sellerFirstName = "";
        String sellerLastName = "";

        String listingUrl = "";

        // Check where we came from, either message tab or selected listing
        if(getIntent().getStringExtra("fromMessageTab") != null) {

            newConvoFlag[0] = false;

            // Remove "fromMessageTab" now that we have arrived to conversationScreen safely
            getIntent().removeExtra("fromMessageTab");

            // Retrieve listing image from previous screen
            listingUrl = getIntent().getStringExtra("listingUrl");

            // Display image of listing at the top of the convo screen
            displayListingImage(listingUrl);

            // Show email and name of seller of item
            userEmail.setText(getIntent().getStringExtra("ownerEmail"));
            userName.setText(getIntent().getStringExtra("ownerName"));

            // Get convoId from previous screen.
            convoId = getIntent().getStringExtra("convoId");

            // Retrieve messages for convo selected in message tab from the database
            getMessages(convoId);

        } else if (getIntent().getStringExtra("fromContactSeller") != null) {

            getIntent().removeExtra("fromContactSeller");

            // "fromMessageTab" wasnt stored in the intent, which means we are entering the
            // conversation screen from a listing

            // Get data that was stored in intent in previous screen
            listingOwnerId = getIntent().getStringExtra("listingOwnerId");
            sellerEmail = getIntent().getStringExtra("sellerEmail");
            sellerFirstName = getIntent().getStringExtra("sellerFirstName");
            sellerLastName = getIntent().getStringExtra("sellerLastName");
            listingUrl = getIntent().getStringExtra("listingImageUrl");
            listingId = getIntent().getStringExtra("listingId");

            displayListingImage(listingUrl);

            // Show email and name of seller of item
            userEmail.setText(sellerEmail);
            userName.setText(sellerFirstName + " " + sellerLastName);

            Query checkForExistingConvoOtherUserId = database.getReference("users").child(userID).child("convos").orderByChild("otherUserId").equalTo(listingOwnerId);

            final String finalListingOwnerId1 = listingOwnerId;
            checkForExistingConvoOtherUserId.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() == null) {
                        newConvoFlag[0] = true;
                    }
                    else {
                        newConvoFlag[0] = false;
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            convo_list.add(child);
                        }
                        searchConvosByListingId(finalListingOwnerId1);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("The read failed: " + databaseError.getCode());
                }
            });
        }




        //*****************main page buttons***************************//
        //*************line 34-53*************************************//
        sendMessage= findViewById(R.id.send_message_btn);                                              //link submit button to xml button
        final String finalListingUrl = listingUrl;
        final String finalSellerFirstName = sellerFirstName;
        final String finalSellerLastName = sellerLastName;
        final String finalSellerEmail = sellerEmail;
        final String finalListingOwnerId = listingOwnerId;
        sendMessage.setOnClickListener(new View.OnClickListener() {                                    //set on click listener for button
            @Override
            public void onClick(View v) {
                TextView sentMessage = findViewById(R.id.editTextTextMultiLine);
                if (newConvoFlag[0] == true) {
                    if(!sentMessage.getText().toString().equals("")) {
                        newConvoFlag[0] = false;

                        Map<String, String> newConvo = new HashMap<>();
                        newConvo.put("imageUrl", finalListingUrl);
                        newConvo.put("lastMessage", sentMessage.getText().toString());
                        newConvo.put("listingId", listingId);
                        newConvo.put("sellerName", finalSellerFirstName +" "+ finalSellerLastName);
                        newConvo.put("sellerEmail", finalSellerEmail);

                        Map<String, String> newMessage = new HashMap<>();
                        newMessage.put("message", sentMessage.getText().toString());
                        newMessage.put("senderId", user.getUid());

                        String newConvoId = database.getReference().child("convos").push().getKey();
                        database.getReference().child("convos").child(newConvoId).setValue(newConvo);
                        database.getReference().child("convos").child(newConvoId).child("members").child(finalListingOwnerId).setValue(true);
                        database.getReference().child("convos").child(newConvoId).child("members").child(user.getUid()).setValue(true);
                        database.getReference().child("messages").child(newConvoId).child("1").setValue(newMessage);

                        database.getReference().child("users").child(user.getUid()).child("convos").child(newConvoId).child("listingId").setValue(listingId);
                        database.getReference().child("users").child(user.getUid()).child("convos").child(newConvoId).child("otherUserId").setValue(finalListingOwnerId);

                        database.getReference().child("users").child(finalListingOwnerId).child("convos").child(newConvoId).child("listingId").setValue(listingId);
                        database.getReference().child("users").child(finalListingOwnerId).child("convos").child(newConvoId).child("otherUserId").setValue(user.getUid());

                        sentMessage.setText("");
                    }

                } else {
                    if(!sentMessage.getText().toString().equals("")) {
                        Map<String, String> map = new HashMap<>();
                        map.put("senderId", userID);
                        map.put("message", sentMessage.getText().toString());
                        database.getReference().child("messages").child(convoId).child(String.valueOf((convoSize+1))).setValue(map);
                        database.getReference().child("convos").child(convoId).child("lastMessage").setValue(sentMessage.getText().toString());
                        sentMessage.setText("");
                    }
                }
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

    public void searchConvosByListingId(final String otherUserId) {
        Query checkForExistingConvo = database.getReference("users").child(userID).child("convos").orderByChild("listingId").equalTo(listingId);

        checkForExistingConvo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() == null) {
                    newConvoFlag[0] = true;
                }
                else {
                    newConvoFlag[0] = false;
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.child("otherUserId").getValue(String.class).equals(otherUserId)) {
                            convoId = child.getKey();
                            break;
                        }
                    }
                    getMessages(convoId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void getMessages(String convoId) {
        Query getMessages = database.getReference("messages").child(convoId).orderByKey();

        getMessages.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                messages_list.clear();
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        messages_list.add(child);
                    }
                    convoSize = messages_list.size();
                }
                onMessagesQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    // Upon receiving messages from the database, this function is called to show the messages
    public void onMessagesQuery() {
        adapter = new ConversationRecyclerAdapter(ConversationScreen.this, messages_list);
        recyclerView.setAdapter(adapter);
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

    private void displayListingImage(String imageUrl) {
        Glide.with(getBaseContext())
                .load(imageUrl)
                .centerCrop()
                .into(listingImage);

    }
}
