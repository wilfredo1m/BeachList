package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectedItem extends AppCompatActivity {
    ViewPager2 viewPager,reportedPager;
    String[] images = {"https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F1595294896?alt=media&token=c341b259-f2a5-45ad-97e1-04b770734db1",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F258260727?alt=media&token=e319e597-2fee-4790-b630-db4d6df4cf12",
            "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F267055780?alt=media&token=1e386df7-470b-431a-b58c-bb0d86450d2c"};
    private ArrayList<String> itemImages = new ArrayList<>();
    private ArrayList<String> firstImageOfItem = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    ImageAdapter adapter,adapter2;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName, reportedItemTitle;
    ConstraintLayout itemPopUpWindow, mainItemWindow;
    Button reportItem, cancelReport,backButton,contactSeller,confirmReport;
    Spinner reportItemSpinner;
    String selectedItem;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_item);

        firebaseDatabase = FirebaseDatabase.getInstance();

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
        userPicture = findViewById(R.id.item_user_image);

//***********************************INITIALIZE SPINNER SECTION************************************************************************************//
//**********************SETS UP SPINNER WITH ADAPTER TO POPULATE ARRAY LIST***********************************************************************//
//****************************ON SELECT LISTENER TO BE ABLE TO PASS THE SELECTED INFORMATION TO THE CONFIRM REPORT BUTTON************************//
        //initiate the spinner
        reportItemSpinner = findViewById(R.id.reported_item_spinner);
        //array adapter holding the array list of categories created in the strings.xml
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
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
//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//



//*****************************************GET IMAGE SELECTION SECTION************************************************************************//
//********************************************************************************************************************************************//
        // gets the item's information to display
        //int position = getIntent().getIntExtra("position",1);
        String listingId = getIntent().getStringExtra("ListingID");

        DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child("item").child(listingId);
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Populate image URLs in global variable
                getListingImages(snapshot.child("listingImages"));
                //Get data and display info
                ListingData selectedListing = snapshot.getValue(ListingData.class);
                displayListingInfo(selectedListing);
                //display owner Info
                getOwnerInfo(selectedListing.getOwnerId());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

//        getListingImages(ItemHomeSearchTab.item_list.get(position).child("listingImages"));
//        viewPager = findViewById(R.id.selected_item_images);
//        adapter = new ImageAdapter(this, itemImages);
//        viewPager.setAdapter(adapter);

        //populates image of the first listing
        if(itemImages.isEmpty()){

        }else{
            firstImageOfItem.add(itemImages.get(0));
        }
//********************************************************************************************************************************************//
//*****************************************END IMAGE SELECTION SECTION************************************************************************//



//********************************************GET VALUES FROM FIREBASE ***********************************************************************//
//********************************************************************************************************************************************//
        // Sets the item info in the correct fields to be displayed
//        itemTitle.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getTitle());
//        itemDescription.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getDescription());
//        itemPrice.setText("$"+ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getPrice());
        //itemSellerFirstName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerFirstName());
        //itemSellerLastName.setText(ItemHomeSearchTab.listing_list.get(position).getSellerLastName());
        //itemCategory.setText(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getCategory());

        //Get user info and display it to screen
//        getUserInfo(ItemHomeSearchTab.item_list.get(position).getValue(ListingData.class).getOwnerId());
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
            openHomeScreen();
            }
        });

        reportItem = findViewById(R.id.report_item_button);
        reportItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateReportScreen();
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
                Toast.makeText(getApplicationContext(), selectedItem, Toast.LENGTH_SHORT).show();

            }
        });


//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//
    }//end onCreate()

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            itemImages.add(child.getValue(String.class));
        }
    }

    private void displayListingInfo(ListingData selectedListing) {
        itemTitle.setText(selectedListing.getTitle());
        itemDescription.setText(selectedListing.getDescription());
        itemPrice.setText("$"+selectedListing.getPrice());
        itemCategory.setText(selectedListing.getCategory());

        viewPager = findViewById(R.id.selected_item_images);
        adapter = new ImageAdapter(this, itemImages);
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
        startActivity(intent);
    }

    public void populateReportScreen() {
        reportedItemTitle = findViewById(R.id.reported_item_title);
        reportedItemTitle.setText(itemTitle.getText());
        //TODO uncomment this part in once images are implemented
        if(!firstImageOfItem.isEmpty()) {
            adapter2 = new ImageAdapter(getApplicationContext(), firstImageOfItem);
            reportedPager = findViewById(R.id.reported_item_pager);
            reportedPager.setAdapter(adapter2);
        }
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
    }

}
