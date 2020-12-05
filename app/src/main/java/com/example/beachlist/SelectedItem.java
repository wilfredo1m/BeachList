package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

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

public class SelectedItem extends AppCompatActivity {
    private static final String TAG = "EXCEPTION";
    ViewPager2 viewPager;
    private final ArrayList<String> itemImages = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private ListingData selectedListing;
    String userID;
    String listingOwnerID;
    ArrayList<String>friendNameArray = new ArrayList<>();
    ArrayList<String>friendIDArray = new ArrayList<>();

    ImageAdapterString adapter;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName, itemSellerEmail;
    ConstraintLayout itemPopUpWindow, mainItemWindow;
    Button reportItem, cancelReport,backButton,contactSeller,confirmReport,shareItemBtn;
    Spinner reportItemSpinner;
    String selectedItem, listingId;

    //********SHARE SCREEN *************************/
    ConstraintLayout shareScreen;
    Button cancelShareButton, confirmShareButton;
    Spinner shareItemSpinner;
    String friendPosition;
    int friendPositionIntValue;
    EditText commentForShareScreen;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_item);

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        assert user != null;
        userID = user.getUid();

        //*****************share screen info**********************
        shareScreen = findViewById(R.id.share_button_layout);
        shareItemSpinner = findViewById(R.id.selected_friend_to_share_spinner);
        commentForShareScreen = findViewById(R.id.item_comment_et);

        //*************Display Item info**************************
        contactSeller = findViewById(R.id.contact_seller_button);
        itemPopUpWindow = findViewById(R.id.fuzzy_item_layout);
        mainItemWindow = findViewById(R.id.scroll_container);
        backButton = findViewById(R.id.btn_back_from_user_item_page);
        itemTitle = findViewById(R.id.selected_item_title);
        itemDescription = findViewById(R.id.selected_item_description);
        itemPrice = findViewById(R.id.selected_item_price);
        itemCategory = findViewById(R.id.selected_item_category);
        itemSellerFirstName = findViewById(R.id.item_seller_firstname);
        itemSellerLastName = findViewById(R.id.item_seller_lastname);
        itemSellerEmail = findViewById(R.id.item_user_email);
        userPicture = findViewById(R.id.item_user_image);

        SetupFriendsArrayList();
//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
//**********************SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST***********************************************************************//
//****************************ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON************************//
        //initiate the spinner
        reportItemSpinner = findViewById(R.id.reported_item_spinner);
        //array adapter holding the array list of categories created in the strings.xml
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.report_item));
        //setup adapter to be passed to spinner
        reportItemSpinner.setAdapter(arrayAdapter);
        reportItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedItem =  reportItemSpinner.getSelectedItem().toString();
                // your code here

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        shareItemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //first,  we have to retrieve the item position as a string
                // then, we can change string value into integer
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                friendPosition = String.valueOf(position);
                friendPositionIntValue = Integer.parseInt(friendPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//



//********************************************GET VALUES FROM FIREBASE AND IMAGE SELECTION****************************************************//
//********************************************************************************************************************************************//
        // gets the item's information to display
        listingId = getIntent().getStringExtra("ListingID");

        final DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child("item").child(listingId);
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Populate image URLs in global variable
                getListingImages(snapshot.child("listingImages"));
                //Get data and display info
                selectedListing = snapshot.getValue(ListingData.class);
                assert selectedListing != null;

                displayListingInfo(selectedListing);
                listingOwnerID = selectedListing.getOwnerId();
                //display owner Info
                getOwnerInfo(selectedListing.getOwnerId());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

        //populates image of the first listing
//        if(!itemImages.isEmpty()){
//            firstImageOfItem.add(itemImages.get(0));
//        }
//********************************************************************************************************************************************//
//********************************************END VALUES FROM FIREBASE ***********************************************************************//



//*********************************BUTTON GROUP***********************************************************************************************//
//********************************************************************************************************************************************//
        // Go back to Home Screen
        final Button backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View view) {

            String callingPage = getCallingPage();
            if (callingPage.equalsIgnoreCase("HomeAfterLogin")){
                openHomeScreen();
            }else if(callingPage.equalsIgnoreCase("SelectedUser")){
                sendBackToUser();
            }else if(callingPage.equalsIgnoreCase("FilteredCategory")){
                sendBackToItemCategory();
            }
            else if(callingPage.equalsIgnoreCase("ReportedScreen")){
                sendBackToReportScreen();
            }else
                openHomeScreen();
            /*
            else if(callingPage.equalsIgnoreCase("selectedFriendPage")){
                sendToSelectedFriend();
            }else if (callingPage.equals("selectedPendingFriendPage")){
                sendToSelectedPendingFriend();
            }
        */
        }
        });

        reportItem = findViewById(R.id.report_item_button);
        reportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //populateReportScreen();
                setupPopUpScreenView();
            }
        });

        cancelReport = findViewById(R.id.cancel_report_item_button);
        cancelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
            }
        });

        confirmReport = findViewById(R.id.confirm_report_item_button);
        confirmReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banListing(listingRef.child("banned"));
                addListingToReported();
//                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();
            }
        });

        contactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageScreen();
            }
        });


        shareItemBtn = findViewById(R.id.share_item_button);
        shareItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupShareScreenPopUp();
            }
        });



        //****share screen button group******************************************//

        cancelShareButton = findViewById(R.id.cancel_share_item_btn);
        cancelShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentForShareScreen.setText("");
                setupRevertScreenView();
            }
        });
        confirmShareButton = findViewById(R.id.confirm_share_item_btn);
        confirmShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String selectedFriend = shareItemSpinner.getSelectedItem().toString();
                String friendID = friendIDArray.get(friendPositionIntValue);
                String comment = commentForShareScreen.getText().toString();
                sendToFriend(friendID, comment);
                //Toast.makeText(getBaseContext(),  friendID, Toast.LENGTH_SHORT).show();

            }
        });

//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//

    }//end onCreate()

    private void addListingToReported() {
        DatabaseReference reportedRef = firebaseDatabase.getReference("reported").child("listings").child(listingId);
        HashMap<String, String> reportedListing = new HashMap<>();
        reportedListing.put("title", selectedListing.getTitle());
        reportedListing.put("type", "item");
        reportedListing.put("imageUrl", selectedListing.getListingImages().get(1));
        reportedListing.put("reason", selectedItem);
        reportedListing.put("ownerId", selectedListing.getOwnerId());
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

    private void banListing(@NonNull DatabaseReference bannedRef) {
        bannedRef.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    private void getListingImages(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            itemImages.add(child.getValue(String.class));
        }
    }

    private void displayListingInfo(ListingData selectedListing) {
        itemTitle.setText(selectedListing.getTitle());
        itemDescription.setText(selectedListing.getDescription());
        itemPrice.setText(String.format("$%s", selectedListing.getPrice()));
        itemCategory.setText(selectedListing.getCategory());

        viewPager = findViewById(R.id.selected_item_images);
        adapter = new ImageAdapterString(this, itemImages);
        viewPager.setAdapter(adapter);
    }

    private void getOwnerInfo(String ownerId) {
        final Context context = this;
        DatabaseReference userRef = firebaseDatabase.getReference().child("users").child(ownerId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemSellerFirstName.setText(snapshot.child("data").getValue(UserData.class).getFirstName());
                itemSellerLastName.setText(snapshot.child("data").getValue(UserData.class).getLastName());
                Glide.with(context)
                        .load(snapshot.child("data").getValue(UserData.class).getImageUrl())
                        .centerCrop()
                        .into(userPicture);
                itemSellerEmail.setText(snapshot.child("data").getValue(UserData.class).getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });
    }

    public void openHomeScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        intent.putExtra("selectedUserId", listingOwnerID);
        intent.putExtra("listingID", listingId);
        intent.putExtra("screen", 1);
        startActivity(intent);
    }



    public void setupPopUpScreenView() {
        itemPopUpWindow.setVisibility(View.VISIBLE);
        mainItemWindow.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        contactSeller.setVisibility(View.INVISIBLE);
    }


    public void setupRevertScreenView() {
        itemPopUpWindow.setVisibility(View.INVISIBLE);
        mainItemWindow.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        contactSeller.setVisibility(View.VISIBLE);
        shareScreen.setVisibility(View.INVISIBLE);
    }

    public void goToMessageScreen(){
        Intent intent = new Intent(this, ConversationScreen.class);
        intent.putExtra("UserID",listingOwnerID );
        intent.putExtra("ListingID",listingId );
        intent.putExtra("listingType", "item");
        Toast.makeText(getApplicationContext(), listingId, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void setupShareScreenPopUp(){
        shareScreen.setVisibility(View.VISIBLE);
        mainItemWindow.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
        contactSeller.setVisibility(View.INVISIBLE);
    }

    public void SetupFriendsArrayList(){
        //instance of authentication
        DatabaseReference friendsRef = firebaseDatabase.getReference("/users/" + userID + "/friends");
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        friendNameArray.add(child.child("firstName").getValue(String.class) + " " + child.child("lastName").getValue(String.class));
                        friendIDArray.add(child.getKey());
                    }
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<>(getBaseContext(), android.R.layout.simple_spinner_item,     //array adapter holding the array list of categories created in the strings.xml
                            friendNameArray);                                                                                              //adapter to be populated with items_categoies array list
                    shareItemSpinner.setAdapter(itemAdapter);                                                                              //setup adapter to be passed to spinner
                   // Toast.makeText(getBaseContext(), friendNameArray.get(0), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public void sendToFriend(String fID, String cmnt){
        Intent intent = new Intent(this, ConversationScreen.class);
        intent.putExtra("UserID", fID);
        intent.putExtra("send message to friend", cmnt);
        intent.putExtra("ListingID",listingId );
        intent.putExtra("listingType", "item");
        Toast.makeText(getApplicationContext(), listingId, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
    public String getCallingPage(){
        String callingIntent = getIntent().getStringExtra("callingPage");
        return callingIntent;

    }

    public void sendBackToUser() {
        Intent intent = new Intent(this, SelectedUser.class);
        intent.putExtra("selectedUserId", listingOwnerID);
        intent.putExtra("screen", 3);
        startActivity(intent);
    }

    /*
    public void sendToSelectedFriend(){
        Intent intent = new Intent(this, SelectedFriend.class);
        intent.putExtra("selectedUserId", listingOwnerID);
        startActivity(intent);
    }
    public void sendToSelectedPendingFriend(){
        Intent intent = new Intent(this, SelectedPendingFriend.class);
        int position = getIntent().getIntExtra("position", 7);
        Toast.makeText(getApplicationContext(), position, Toast.LENGTH_SHORT).show();
        intent.putExtra("selectedUserId", listingOwnerID);
        startActivity(intent);
    }
     */
    public void sendBackToReportScreen(){
        Intent intent = new Intent(this, ReportedScreen.class);
        intent.putExtra("reportedPageTab", 2);
        startActivity(intent);
    }


    public void sendBackToItemCategory(){
        String category =  itemCategory.getText().toString();
        Intent categoryIntent = new Intent(this, FilteredCategory.class);
        categoryIntent.putExtra("tabPosition", 1);
        categoryIntent.putExtra("category",category);
        categoryIntent.putExtra("toolbar Name", "Filtered " + category);
        startActivity(categoryIntent);
    }

}
