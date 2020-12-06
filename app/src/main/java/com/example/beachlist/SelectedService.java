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

public class SelectedService extends AppCompatActivity {
    private static final String TAG = "EXCEPTION";
    ViewPager2 viewPager, reportPager;
    private final ArrayList<String> serviceImages = new ArrayList<>();
    private final ArrayList<String> firstImageOfService = new ArrayList<>();
    private ListingData selectedListing;
    String listingOwnerID;


    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String userID;
    String ownerID;
    ArrayList<String>friendNameArray = new ArrayList<>();
    ArrayList<String>friendIDArray = new ArrayList<>();

    ImageAdapterString adapter;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName, reportedServiceTitle, itemSellerEmail;
    Button reportService, cancelReport, submitReport, contactSeller, backButton,shareServiceButton;
    ConstraintLayout popupWindow, mainConstraint;
    Spinner reportServiceSpinner;
    String reportedSpinnerSelection,listingId;

    //*****share screen info********//
    ConstraintLayout shareScreen;
    Button cancelShare, confirmShare;
    Spinner shareServiceSpinner;
    String item_position;
    int positionInt;
    EditText commentForShareScreen;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();



        setContentView(R.layout.activity_selected_other_user_service);

        //**share screen setup*********************************//
        shareScreen =findViewById(R.id.share_service_layout);
        shareServiceSpinner = findViewById(R.id.seleted_friend_to_share_spinner);
        commentForShareScreen = findViewById(R.id.service_comment_et);
        //****************************************************//
        firebaseDatabase = FirebaseDatabase.getInstance();
        //setup for visibility of main view in the page
        mainConstraint = findViewById(R.id.scroll_container);
        popupWindow = findViewById(R.id.fuzzy_layout_service);

        //*************Display Service info**************************
        itemTitle = findViewById(R.id.selected_service_title);
        itemDescription = findViewById(R.id.selected_service_description);
        itemPrice = findViewById(R.id.selected_service_price);
        itemCategory = findViewById(R.id.selected_service_category);
        itemSellerFirstName = findViewById(R.id.service_seller_firstname);
        itemSellerLastName = findViewById(R.id.service_seller_lastname);
        itemSellerEmail = findViewById(R.id.service_user_email);
        userPicture = findViewById(R.id.service_user_image);

        SetupFriendsArrayList();


//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
//**********************SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST***********************************************************************//
//****************************ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON************************//
        //initiate the spinner
        reportServiceSpinner = findViewById(R.id.reported_service_spinner);
        //array adapter holding the array list of categories created in the strings.xml
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.report_service));
        //setup adapter to be passed to spinner
        reportServiceSpinner.setAdapter(arrayAdapter);
        reportServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                reportedSpinnerSelection = reportServiceSpinner.getSelectedItem().toString();
                // your code here

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        shareServiceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //first,  we have to retrieve the item position as a string
                // then, we can change string value into integer
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                item_position = String.valueOf(position);
                positionInt = Integer.valueOf(item_position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//


//********************************************GET VALUES FROM FIREBASE AND IMAGE SELECTION****************************************************//
//********************************************************************************************************************************************//
        listingId = getIntent().getStringExtra("ListingID");
        assert listingId != null;

        
        final DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child("service").child(listingId);
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Populate image URLs in global variable
                getListingImages(snapshot.child("listingImages"));
                //Get data and display info
                selectedListing = snapshot.getValue(ListingData.class);
                assert selectedListing != null;
                displayListingInfo(selectedListing);
                //display owner Info
                listingOwnerID = selectedListing.getOwnerId();
                getOwnerInfo(selectedListing.getOwnerId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

//********************************************************************************************************************************************//
//********************************************END VALUES FROM FIREBASE ***********************************************************************//


//*********************************BUTTON GROUP***********************************************************************************************//
//********************************************************************************************************************************************//

        contactSeller = findViewById(R.id.contact_seller_button);
        contactSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMessageScreen();
            }
        });

        // Go back to User search list (temporarily going back to home)
        backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String callingPage = getCallingPage();

                if (callingPage.equalsIgnoreCase("HomeScreenAfterLogin")){
                    openHomeScreen();
                }else if(callingPage.equalsIgnoreCase("SelectedUser")){
                    sendBackToUser();
                }else if(callingPage.equalsIgnoreCase("FilteredCategory")){
                    sendBackToServiceCategory();
                }else if(callingPage.equalsIgnoreCase("ReportedScreen")){
                    sendBackToReportScreen();
                }else if(callingPage.equalsIgnoreCase("SelectedFriend")){
                    sendToSelectedFriend();
                }else if (callingPage.equals("SelectedPendingFriend")){
                    sendToSelectedPendingFriend();
                }else
                    openHomeScreen();


            }


        });

        //report button in main window to triggerpop up window
        reportService = findViewById(R.id.report_item_button);
        reportService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //populateReportScreen();
                setupPopUpScreenView();

            }
        });
        //Cancel Report button in pop up window
        cancelReport = findViewById(R.id.cancer_report_service_button);
        cancelReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupRevertScreenView();
            }
        });

        //Submit Report button in pop up window
        submitReport = findViewById(R.id.submit_service_report);
        submitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banListing(listingRef.child("banned"));
            }
        });

        shareServiceButton= findViewById(R.id.share_item_button);
        shareServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupShareServiceScreenView();
            }
        });

        //************Share screen buton group******************
        cancelShare = findViewById(R.id.cancel_share_service_button);
        cancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commentForShareScreen.setText("");
                setupRevertScreenView();
            }
        });

        confirmShare = findViewById(R.id.confirm_share_service_btn);
        confirmShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedFriend = shareServiceSpinner.getSelectedItem().toString();
                String friendID = friendIDArray.get(positionInt);
                String comment = commentForShareScreen.getText().toString();
               // Toast.makeText(getBaseContext(),  friendID, Toast.LENGTH_SHORT).show();
                sendToFriend(friendID,comment);
            }
        });


//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//

    }//end onCreate()

    private void addListingToReported() {
        DatabaseReference reportedRef = firebaseDatabase.getReference("reported").child("listings").child(listingId);
        HashMap<String, String> reportedListing = new HashMap<>();
        reportedListing.put("title", selectedListing.getTitle());
        reportedListing.put("type", "service");
        reportedListing.put("imageUrl", selectedListing.getListingImages().get(1));
        reportedListing.put("reason", reportedSpinnerSelection);
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

    private void banListing(DatabaseReference bannedRef) {
        bannedRef.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                addListingToReported();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure: "+e.getLocalizedMessage(), e.getCause());
            }
        });
    }

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            serviceImages.add(child.getValue(String.class));
            firstImageOfService.add(serviceImages.get(0));
        }
    }

    private void displayListingInfo(ListingData selectedListing) {
        itemTitle.setText(selectedListing.getTitle());
        itemDescription.setText(selectedListing.getDescription());
        itemPrice.setText(String.format("$%s", selectedListing.getPrice()));
        itemCategory.setText(selectedListing.getCategory());

        viewPager = findViewById(R.id.selected_service_images);
        adapter = new ImageAdapterString(this, serviceImages);
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

    //service tab in HomeFragment
    public void openHomeScreen() {
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab", 2);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        intent.putExtra("selectedUserId", ownerID);
        intent.putExtra("listingID", listingId);
        intent.putExtra("screen", 2);
        startActivity(intent);
    }



    public void setupPopUpScreenView() {
        // ****group to fix visibilities for screen ****//
        popupWindow.setVisibility(View.VISIBLE);
        contactSeller.setVisibility(View.INVISIBLE);
        mainConstraint.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
    }

    public void setupRevertScreenView() {
        // ****group to fix visibilities for screen ****//
        popupWindow.setVisibility(View.INVISIBLE);
        mainConstraint.setVisibility(View.VISIBLE);
        contactSeller.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);
        shareScreen.setVisibility(View.INVISIBLE);

    }

    public void goToMessageScreen(){
        Intent intent = new Intent(this, ConversationScreen.class);
        intent.putExtra("listingOwnerId", listingOwnerID);
        intent.putExtra("sellerFirstName", itemSellerFirstName.getText().toString());
        intent.putExtra("sellerLastName", itemSellerLastName.getText().toString());
        intent.putExtra("sellerEmail", itemSellerEmail.getText().toString());
        intent.putExtra("listingImageUrl", selectedListing.getListingImages().get(1));
        intent.putExtra("listingId",listingId );
        intent.putExtra("listingType", "service");
        intent.putExtra("fromContactSeller", "true");
        Toast.makeText(getApplicationContext(), listingId, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }


    public void setupShareServiceScreenView() {
        // ****group to fix visibilities for screen ****//
        shareScreen.setVisibility(View.VISIBLE);
        contactSeller.setVisibility(View.INVISIBLE);
        mainConstraint.setVisibility(View.INVISIBLE);
        backButton.setVisibility(View.INVISIBLE);
    }

    public void SetupFriendsArrayList(){
        //instance of authentication
        DatabaseReference friendsRef = firebaseDatabase.getReference("/users/" + userID + "/friends");
        friendsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        friendNameArray.add(child.child("firstName").getValue(String.class) + " " + child.child("lastName").getValue(String.class));
                        friendIDArray.add(child.getKey());
                    }
                    ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item,     //array adapter holding the array list of categories created in the strings.xml
                            friendNameArray);                                                                                              //adapter to be populated with items_categoies array list
                    shareServiceSpinner.setAdapter(itemAdapter);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });


    }

    public void sendToFriend(String fID, String cmnt){
        String FriendID = fID;
        String comment = cmnt;
        Intent intent = new Intent(this, ConversationScreen.class);
        intent.putExtra("UserID",FriendID );
        intent.putExtra("send message to friend", comment);
        intent.putExtra("ListingID",listingId );
        intent.putExtra("listingType", "service");
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
    public void sendBackToServiceCategory(){
        String category =  itemCategory.getText().toString();
        Intent categoryIntent = new Intent(this, FilteredCategory.class);
        categoryIntent.putExtra("tabPosition", 2);
        categoryIntent.putExtra("category",category);
        categoryIntent.putExtra("toolbar Name", "Filtered " + category);
        startActivity(categoryIntent);
    }
    public void sendBackToReportScreen(){
        Intent intent = new Intent(this, ReportedScreen.class);
        intent.putExtra("reportedPageTab", 2);
        startActivity(intent);
    }


    public void sendToSelectedFriend(){
        Intent intent = new Intent(this, SelectedFriend.class);
        intent.putExtra("userId", listingOwnerID);
        startActivity(intent);
    }
    public void sendToSelectedPendingFriend(){
        Intent intent = new Intent(this, SelectedPendingFriend.class);
        intent.putExtra("userId", listingOwnerID);
        startActivity(intent);
    }

}