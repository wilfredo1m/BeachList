package com.example.beachlist;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class SelectedOwnListing extends AppCompatActivity {
//*********************main window elements***********************//
    TextView listingTitle, listingDescription, listingPrice;                                          //text views to be populated in main window
    Button backButton, shareButton,modListingButton;                                                  //buttons for main window
    ScrollView listingInfo;                                                                           //scroll view layout
    private FirebaseDatabase firebaseDatabase;                                                        //firebase call
    ViewPager2 viewPager;                                                                             //pager to view images through
    ArrayList<String> listingImages = new ArrayList<>();                                              //arraylist to hold images
    ImageAdapter adapter;                                                                             //adapter for main window
    String type,listingId;                                                                            //strings regarding listing

//*****************popup window elements*************************//
    ConstraintLayout changeListing;                                                                   //popup window layout
    ViewPager2 currentImages,updatedImages;                                                           //pop up pager
    String categoryString;                                                                            //strings to retrieve info from db to be populated in pop up window
    TextView currentTitle, currentPrice, currentDescription, currentCategory,currentListingType;      //tv to be used in pop up to populate the new screen
    Button changeTitle, changePrice, changeDescription, changeCategory, cancelUpdate,                 //buttons to enable modifying of specified fields
        confirmUpdate, updateImages,deleteListingBtn;                                                 //buttons to enable modifying of specified fields
    EditText updatedTitle, updatedPrice, updatedDescription;                                          //edit text fields to update listing info
    Spinner categorySpinner;                                                                          //to be populated based on what type of listing is displayed
    ArrayList<Bitmap> bitmaps;                                                                        //bitmap array to hold new images from the gallery
    ImageAdapter2 adapterForNewImages;                                                                //adapter that uses bitmaps instead of array of strings
    public static final int IMAGE_REQUEST = 33;
    public static final int PROCESSED_OK = -1;
    boolean pictureFlag, descriptionFlag, priceFlag, categoryFlag,titleFlag;                          //keep track of what was modified in order to update

//*************poup window for share screen****************//
    ConstraintLayout shareScreen;                                                                     //pop up window setup for share screen settings
    Spinner userSpinner;                                                                              //should be populated with fb friends
    Button cancelShare, confirmShare;                                                                 //buttons to either revert screen or to send a message
    EditText commentForShareScreen;                                                                   //be able to retrieve whatever is typed in the comment section
    String commentFromText;                                                                           //will be used to hold the comment value

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_user_listing);                                           //set view to activity_own_user_listing xml

        listingInfo = findViewById(R.id.signed_in_user_listing_sv);
        firebaseDatabase = FirebaseDatabase.getInstance();

//*****************MAIN PAGE ITEMS********************************************//
        listingTitle = findViewById(R.id.selected_listing_title);                                     // link title to xml
        listingDescription = findViewById(R.id.selected_listing_description);                         // link descirption to xml
        listingPrice = findViewById(R.id.selected_listing_price);                                     // link price to xml

//**********************POP UP WINDOW ITEMS**********************************//
        //popup window group display at layout change
        changeListing = findViewById(R.id.mod_listing_view);                                          //pop up window view
        currentTitle = findViewById(R.id.title_tv);                                                   //title textview to be populated at popup
        currentPrice= findViewById(R.id.current_price_tv);                                            //price textview to be populated at popup
        currentDescription= findViewById(R.id.current_description_tv);                                //description textview to be populated at popup
        currentCategory= findViewById(R.id.current_category_tv);                                      //category textview to be populated at popup
        currentImages = findViewById(R.id.current_pictures_pager);                                    //current images pager to be populated at popup
        //popup window group displayed at button change
        categorySpinner = findViewById(R.id.change_category_spinner);                                 //spinner to be populated at button press to modify
        updatedTitle = findViewById(R.id.edit_title_pt);                                              //update title edit text to be populated at button press
        updatedPrice = findViewById(R.id.change_price_txt);                                           //update price edit text to be populated at button press
        updatedDescription = findViewById(R.id.change_description_ml_text);                           //update description edit text to be populated at button press
        updatedImages = findViewById(R.id.new_pictures_pager);                                        //update images pager to be populated at button press
        pictureFlag=false;
        descriptionFlag = false;
        priceFlag=false;
        categoryFlag=false;
        titleFlag=false;
//****************************END POP UP WINDOW ITEMS************************//

//**************************SHARE SCREEN ITEMS******************************/
        shareScreen = findViewById(R.id.share_own_button_layout);
        commentForShareScreen = findViewById(R.id.comment_description_ET);


//******************END SHARE SCREEN ITEMS******************************//


        // gets the listing's information to display
        type = getIntent().getStringExtra("type");                                             //get type of listing( item or service) from an intent
        listingId = getIntent().getStringExtra("listingID");                                   //get listing id from an intent


        DatabaseReference listingRef = firebaseDatabase.getReference().child("listings").child(type).child(listingId);    //listing reference from firebase
        listingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getListingImages(snapshot.child("listingImages"));                                    //Populate image URLs in global variable
                ListingData selectedListing = snapshot.getValue(ListingData.class);                   //Get data and display info
                assert selectedListing != null;                                                       //check to see that selected listing is not null
                displayListingInfo(selectedListing);                                                  //call listing information by passing selected listing ListingData
                setCategory(selectedListing.getCategory());                                           //call set category
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //TODO Handle this error
            }
        });

//******************************** BUTTON GROUP*******************************************************//
//********************************START AT LINE 136- 168*********************************************//
        // Modify current listing
        modListingButton = findViewById(R.id.selected_own_listing_modify_btn);                        //set modListingButton to the xml
        modListingButton.setOnClickListener(new View.OnClickListener() {                              //set on click listener to button
            @Override
            public void onClick(View view) {
                openModifyListingScreen();                                                            //call method to bring up popup screen
                populateCurrentListingInformation();                                                  //call method to populate fields in popup screen
                PopulateSpinner(type);                                                                //call method to find type of listing and populate spinner accordingly

            }
        });

        // Go back to current listings list
        backButton = findViewById(R.id.selected_own_listing_back_btn);                                //set backButton to the xml
        backButton.setOnClickListener(new View.OnClickListener() {                                    //set on click listener to button
            @Override
            public void onClick(View view) {
                openCurrentListingsListScreen();                                                     //call method to hide the popup screen
            }
        });

        // Share listing through messages
        shareButton = findViewById(R.id.selected_listing_share_btn);                                  //set shareButton to the xml
        shareButton.setOnClickListener(new View.OnClickListener() {                                   //set on click listener to button
            @Override
            public void onClick(View view) {
                displayShareScreen();
                //openMessagesScreen();                                                                 //call method to send user to message screen
                modListingButton.setClickable(false);
            }
        });
//****************************END BUTTON GROUP*********************************************************//

//******************************POP UP MENU BUTTONS***************************************************//
//********************************START AT LINE 170- 283**********************************************//
        changeTitle = findViewById(R.id.change_title_button);                                         //set changeTitle to the xml
        changeTitle.setOnClickListener(new View.OnClickListener() {                                   //set on click listener to button
            @Override
            public void onClick(View v) {
                titleFlag= true;                                                                      //set flag to true meaning that there is an updated being performed
                currentTitle.setVisibility(View.GONE);                                                //remove currentTitle textView from screen and layout weight
                updatedTitle.setVisibility(View.VISIBLE);                                             //place updatedTitle textview in screen in place of currentTitle
                changeTitle.setClickable(false);                                                      //set button to not be clickable
            }
        });
        changePrice= findViewById(R.id.change_price_btn);                                             //set changePrice to the xml
        changePrice.setOnClickListener(new View.OnClickListener() {                                   //set on click listener to button
            @Override
            public void onClick(View v) {
                priceFlag= true;                                                                      //set flag to true meaning that there is an updated being performed
                currentPrice.setVisibility(View.GONE);                                                //remove currentPrice textView from screen and layout weight
                updatedPrice.setVisibility(View.VISIBLE);                                             //place updatedPrice textview in screen in place of currentPrice
                changePrice.setClickable(false);                                                      //set button to not be clickable
            }
        });
        changeDescription = findViewById(R.id.change_description_btn);                                //set changeDescription to the xml
        changeDescription.setOnClickListener(new View.OnClickListener() {                             //set on click listener to button
            @Override
            public void onClick(View v) {
                descriptionFlag=true;                                                                 //set flag to true meaning that there is an updated being performed
                currentDescription.setVisibility(View.GONE);                                          //remove currentDescription textView from screen and layout weight
                updatedDescription.setVisibility(View.VISIBLE);                                       //place updatedDescription textview in screen in place of currentDescription
                changeDescription.setClickable(false);                                                //set button to not be clickable

            }
        });
        changeCategory = findViewById(R.id.change_category_btn);                                      //set changeCategory to the xml
        changeCategory.setOnClickListener(new View.OnClickListener() {                                //set on click listener to button
            @Override
            public void onClick(View v) {
                categoryFlag = true;                                                                  //set flag to true meaning that there is an updated being performed                                                                 //set flag to true so that we know we need to update this system
                currentCategory.setVisibility(View.GONE);                                             //remove currentCategory textview from screen and layout weight
                categorySpinner.setVisibility(View.VISIBLE);                                          //place categorySpinner spinner in screen in place of currentCategory textview
                changeCategory.setClickable(false);                                                   //set button to not be clickable
//
            }
        });
        cancelUpdate = findViewById(R.id.cancel_changes_btn);                                         //set cancelUpdate to the xml
        cancelUpdate.setOnClickListener(new View.OnClickListener() {                                  //set on click listener to button
            @Override
            public void onClick(View v) {
                RevertScreens();                                                                     //call method to change screen layout back to normal
                revertButtonStatus();                                                                //call method to make buttons clickable again
                pictureFlag=false;                                                                   //set flag back to false since no update took place
                descriptionFlag = false;                                                             //set flag back to false since no update took place
                priceFlag=false;                                                                     //set flag back to false since no update took place
                categoryFlag=false;                                                                  //set flag back to false since no update took place
                titleFlag=false;                                                                     //set flag back to false since no update took place
            }
        });

        //TODO finish functionality for this button
        confirmUpdate = findViewById(R.id.accept_changes_btn);                                        //set confirmUpdate to the xml
        confirmUpdate.setOnClickListener(new View.OnClickListener() {                                 //set on click listener to button
            @Override
            public void onClick(View v) {

                //Content fields that hold updated values
                //bitmaps                                                                            //this is the array holding the new arrays. its a bitmap array so you will prob need to add the lines at the bottom to have a string array
                //updatedPrice.getText().toString();                                                 //this is the updated edittext for price as a string
                //updatedDescription.getText().toString();                                           //this is the updated edittext for description as a string
                //updatedPrice.getText().toString();                                                 //this is the updated edittext for price as a string
                //updatedTitle.getText().toString();                                                 //this is the updated edittext for title as a string
                String newCategory = categorySpinner.getSelectedItem().toString();
                //todo use the flags that have been created to check which item was modified
                //todo this group can be nested or whatever method you want to implent it as, this just felt like the best way instead of updating blank fields or all fields
                /*
                if(!titleFlag){

                }
                if(!categoryFlag){

                }
                if(!priceFlag){

                }
                if(!pictureFlag){

                }
                if(!descriptionFlag){

                }
                 */
                Toast.makeText(getApplicationContext(),newCategory , Toast.LENGTH_SHORT).show();
            }
        });

        updateImages = findViewById(R.id. change_image_btn);                                          //set updateImages to the xml
        updateImages.setOnClickListener(new View.OnClickListener() {                                  //set on click listener to button
            @Override
            public void onClick(View v) {
                pictureFlag= true;
                currentImages.setVisibility(View.GONE);                                               //remove currentImages pager from screen and layout weight
                updatedImages.setVisibility(View.VISIBLE);                                            //place updatedImages pager in screen in place of currentImages pager
                openCameraRoll();                                                                     //call function to open camera
            }
        });

        //TODO finish functionality for this button
        deleteListingBtn = findViewById(R.id.delete_listing_btn);                                     //set deleteListingBtn to the xml
        deleteListingBtn.setOnClickListener(new View.OnClickListener() {                              //set on click listener to button
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "testing button", Toast.LENGTH_SHORT).show();
            }
        });
//************************************END POP UP MENU BUTTONS********************************************//

        cancelShare = findViewById(R.id.cancel_share_own_listing_btn);
        cancelShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RevertScreens();
                revertButtonStatus();


            }
        });
        confirmShare = findViewById(R.id.confirm_share_own_listing_btn);
        confirmShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
//***********************************SHARE SCREEN BUTTONS**********************************************//

    }//end on create()

    //get the listing images from firebase user info
    private void getListingImages(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {                                       //go through user listings images and add to array
            listingImages.add(child.getValue(String.class));                                          //add images to listingImages array
        }
    }
    //go back to all current listings page
    public void openCurrentListingsListScreen(){
        Intent openScreen = new Intent(this, ActiveListings.class);                    //intent to open ActiveListings page (back button press)
        startActivity(openScreen);                                                                    //start screen change
    }

    //go to messages screen
    //TODO implement the messaging screen to link this
    public void openMessagesScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);               //open screen intent to send user back to home screen
        openScreen.putExtra("screen",1);                                                 //pass intent to open the first tab
        startActivity(openScreen);                                                                    //start screen change
    }

    //display all information retrieved from firebase to the screen
    private void displayListingInfo(ListingData selectedListing) {
        listingTitle.setText(selectedListing.getTitle());                                             // set text of listing title to main screen
        listingDescription.setText(selectedListing.getDescription());                                 // set text of listingDescription to main screen
        listingPrice.setText("$"+selectedListing.getPrice());                                         // set text of listingPrice to main screen
        viewPager = findViewById(R.id.own_listing_images);                                            // link viewpager to xml
        adapter = new ImageAdapter(this, listingImages);                                      // add listingImages array to adapter
        viewPager.setAdapter(adapter);                                                                // set viewpager to the adapter to display images in pager screen
    }

//************************POP UP MENU FUNCTIONS*************************************//
//************************starts line 300 - 473*************************************//
    //set category
    public void setCategory(String cat){
        categoryString = cat;                                                                         //set categoryString to category passed in
    }

    //get category
    public String getCategory(){
        return categoryString;                                                                        //return category
    }

    //change layout visibiliy to the popup window
    public void openModifyListingScreen(){
        changeListing.setVisibility(View.VISIBLE);                                                    //set popup window to visible
        listingInfo.setVisibility(View.INVISIBLE);                                                    //set old window to invisible
    }

    //populate the popup window with current information to see what needs to be edited
    public void populateCurrentListingInformation(){
        currentTitle.setText(listingTitle.getText().toString());                                      //set current title text
        currentPrice.setText(listingPrice.getText().toString());                                      //set current price text
        currentDescription.setText(listingDescription.getText().toString());                          //set current description text
        currentCategory.setText(getCategory());                                                       //set currentCategory text
        adapter = new ImageAdapter(this, listingImages);                                      //add listingImages array to the adapter
        currentImages.setAdapter(adapter);                                                            //set currentImages pager to the adapter to swipe through pictures and display in pager
    }

    //method to revert screens back to display the original content and remove editable fields
    public void RevertScreens(){
        //visibility of layout windows
        changeListing.setVisibility(View.INVISIBLE);                                                  //change item visibility to invisible
        listingInfo.setVisibility(View.VISIBLE);                                                      //change item visibility to visible
        //visibility of price
        currentPrice.setVisibility(View.VISIBLE);                                                     //change item visibility to visible
        updatedPrice.setVisibility(View.GONE);                                                        //change item visibility to Invisible and remove layout location
        //visibility of title
        currentTitle.setVisibility(View.VISIBLE);                                                     //change item visibility to visible
        updatedTitle.setVisibility(View.GONE);                                                        //change item visibility to Invisible and remove layout location
        //visibility of description
        currentDescription.setVisibility(View.VISIBLE);                                               //change item visibility to visible
        updatedDescription.setVisibility(View.GONE);                                                  //change item visibility to Invisible and remove layout location
        //visibility of category
        currentCategory.setVisibility(View.VISIBLE);                                                  //change item visibility to visible
        categorySpinner.setVisibility(View.GONE);                                                     //change item visibility to Invisible and remove layout location
        //visibility of image pagers
        currentImages.setVisibility(View.VISIBLE);                                                    //change item visibility to visible
        updatedImages.setVisibility(View.GONE);                                                       //change item visibility to Invisible and remove layout location

        shareScreen.setVisibility(View.INVISIBLE);                                                    //remove the popup screen from the view

    }


    //revert clickable status for the buttons
    //to be used when:
    //     1. cancel button is pressed
    //     2. confirm changes button is pressed
    public void revertButtonStatus(){
        changeTitle.setClickable(true);                                                               //set clickable status back to true
        changePrice.setClickable(true);                                                               //set clickable status back to true
        changeDescription.setClickable(true);                                                         //set clickable status back to true
        changeCategory.setClickable(true);                                                            //set clickable status back to true
        modListingButton.setClickable(true);


    }

    // Opens Camera Roll
    public void openCameraRoll() {
        Intent openCameraRoll = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);     // Location of where to find the pictures
        String pictureDirectoryPath = pictureDirectory.getPath();
        openCameraRoll.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);                                         // User is able to choose more than one picture from gallery
        Uri data = Uri.parse(pictureDirectoryPath);                                                               // URI of the path to pictures
        openCameraRoll.setDataAndType(data,"image/*");                                                      // Set data and type (* means all image types)
        startActivityForResult(openCameraRoll, IMAGE_REQUEST);                                                   // start the activity (accessing camera roll)
    }


    public void PopulateSpinner(String stringType){
        if(stringType.equalsIgnoreCase("item"))                                                              //check if type = item
        {
            ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,     //array adapter holding the array list of categories created in the strings.xml
                    getResources().getStringArray(R.array.items_categoies));                                              //adapter to be populated with items_categoies array list
            categorySpinner.setAdapter(itemAdapter);                                                                      //setup adapter to be passed to spinner
        }
        else if(stringType.equalsIgnoreCase("service"))                                                      //check if type= service
        {
            ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,  //array adapter holding the array list of services created in the strings.xml
                    getResources().getStringArray(R.array.service_categoies));                                            //adapter to be populated with service_categoies array list
            categorySpinner.setAdapter(serviceAdapter);                                                                   //setup adapter to be passed to spinner
        }
        else
        {
            //do nothing
        }

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == PROCESSED_OK)
        {
            // Accessed camera roll successfully
            if(requestCode == IMAGE_REQUEST)
            {
                bitmaps = new ArrayList<>();                                                          //initialize bitmaps as an arraylist
                ClipData clipData = data.getClipData();                                               //
                if(clipData != null)                                                                  //check if clickpadata is not null
                {
                    for(int i=0;i <clipData.getItemCount();i++)                                      //for loop until it reaches clipdata item count
                    {
                        Uri imageUri = clipData.getItemAt(i).getUri();                                //assign imageUir to item at clipdata position
                        try{
                            InputStream is = getContentResolver().openInputStream(imageUri);          //input stream to access gallery content
                            Bitmap bitmap = BitmapFactory.decodeStream(is);                           //Creates Bitmap objects from various sources, including files, streams(is), and byte-arrays.
                            bitmaps.add(bitmap);                                                      //add bitmaps to the bitmaps array
                            //*************Display Listing Images********************
                            adapterForNewImages = new ImageAdapter2(this,bitmaps);            //adapter takes in bitmap array to display in pager
                            updatedImages.setAdapter(adapterForNewImages);                            //set pager to the adapter to swipe images
                            //********************************************************
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }//end for loop
                }//end if(clipData != null)
                else
                    {
                    Uri imageUri = data.getData();                                                    //assign imageuri to the image selected from the gallery
                    try
                    {
                        InputStream is = getContentResolver().openInputStream(imageUri);              //input stream to access gallery content
                        Bitmap bitmap = BitmapFactory.decodeStream(is);                               //Creates Bitmap objects from various sources, including files, streams(is), and byte-arrays.
                        bitmaps.add(bitmap);                                                          //add bitmaps to the bitmaps array
                        //*************Display Listing Images********************
                        adapterForNewImages = new ImageAdapter2(this,bitmaps);                //adapter takes in bitmap array to display in pager
                        updatedImages.setAdapter(adapterForNewImages);                                //set pager to the adapter to swipe images
                         //********************************************************
                    }//end try
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }//end catch
                }//end else



            }// end (requestCode == IMAGE_REQUEST)
        }// end if(resultCode == PROCESSED_OK)
    }

//********************************************************SHARE POPUP SCREEN FUNCTIONS*************************************************//
    public void displayShareScreen(){
        //listingInfo.setVisibility(View.INVISIBLE);                                                    //set old window to invisible
        shareScreen.setVisibility(View.VISIBLE);
    }


}
