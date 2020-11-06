package com.example.beachlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class SelectedService extends AppCompatActivity {
    ViewPager2 viewPager, reportPager;
    private final ArrayList<String> serviceImages = new ArrayList<>();
    private final ArrayList<String> firstImageOfService = new ArrayList<>();

    private FirebaseDatabase firebaseDatabase;
    ImageAdapter adapter, adapter2;
    ImageView userPicture;
    TextView itemTitle, itemDescription, itemPrice, itemCategory, itemSellerFirstName, itemSellerLastName, reportedServiceTitle;
    Button reportService, cancelReport, submitReport, contactSeller, backButton;
    ConstraintLayout popupWindow, mainConstraint;
    Spinner reportServiceSpinner;
    String reportedSpinnerSelection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_other_user_service);

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
        userPicture = findViewById(R.id.service_user_image);


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
//********************************************************************************************************************************************//
//********************************END SPINNER SECTION*****************************************************************************************//


//********************************************GET VALUES FROM FIREBASE AND IMAGE SELECTION****************************************************//
//********************************************************************************************************************************************//
        String listingId = getIntent().getStringExtra("ListingID");

        DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child("service").child(listingId);
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Populate image URLs in global variable
                getListingImages(snapshot.child("listingImages"));
                //Get data and display info
                ListingData selectedListing = snapshot.getValue(ListingData.class);
                assert selectedListing != null;
                displayListingInfo(selectedListing);
                //display owner Info
                getOwnerInfo(selectedListing.getOwnerId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

        //TODO remove comment from line below once images are implemented
  //      //populates image of the first listing
  //      if (!serviceImages.isEmpty()) {
  //          firstImageOfService.add(serviceImages.get(0));
  //      }
//********************************************************************************************************************************************//
//********************************************END VALUES FROM FIREBASE ***********************************************************************//


//*********************************BUTTON GROUP***********************************************************************************************//
//********************************************************************************************************************************************//

        contactSeller = findViewById(R.id.contact_seller_button);

        // Go back to User search list (temporarily going back to home)
        backButton = findViewById(R.id.btn_back_from_user_item_page);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeScreen();
            }
        });

        //report button in main window to triggerpop up window
        reportService = findViewById(R.id.report_item_button);
        reportService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                populateReportScreen();
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
                Toast.makeText(getApplicationContext(), reportedSpinnerSelection, Toast.LENGTH_SHORT).show();
            }
        });
//*********************************END BUTTON GROUP***********************************************************************************//
//***********************************************************************************************************************************//

    }//end onCreate()

    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
            serviceImages.add(child.getValue(String.class));
            firstImageOfService.add(serviceImages.get(0));
        }
    }

    private void displayListingInfo(ListingData selectedListing) {
        itemTitle.setText(selectedListing.getTitle());
        itemDescription.setText(selectedListing.getDescription());
        itemPrice.setText("$" + selectedListing.getPrice());
        itemCategory.setText(selectedListing.getCategory());

        viewPager = findViewById(R.id.selected_service_images);
        adapter = new ImageAdapter(this, serviceImages);
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

    //service tab in HomeFragment
    public void openHomeScreen() {
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("tab", 2);
        startActivity(openScreen);
    }

    public void SendToUserPage(View view) {
        Intent intent = new Intent(this, SelectedUser.class);
        startActivity(intent);
    }

    public void populateReportScreen() {
        reportedServiceTitle = findViewById(R.id.reportedServiceTitle);
        reportedServiceTitle.setText(itemTitle.getText());
        adapter2 = new ImageAdapter(getApplicationContext(), serviceImages);
        reportPager = findViewById(R.id.reported_Service_pager);
        reportPager.setAdapter(adapter2);

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
    }
}