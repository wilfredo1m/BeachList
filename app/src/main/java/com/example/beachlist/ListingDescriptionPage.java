package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListingDescriptionPage extends AppCompatActivity {
    Button cancelButton,nextButton;
    Spinner spinner;
    RadioGroup radioGroup;
    RadioButton radioButton;
    TextView listingPrice,description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ArrayList<String> test = this.getIntent().getStringArrayListExtra("Listing Images");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_description_page);

        //Radio group initialization
        radioGroup = findViewById(R.id.listing_type_radio_group);
        //pull typed value for listing price
        listingPrice = findViewById(R.id.et_listing_price);
        description = findViewById(R.id.et_listing_description);
        spinner = findViewById(R.id.categorie_spinner);
//************************************BUTTON GROUP ************************************************//
        // Cancels the post being created / clears all fields entered
        cancelButton = findViewById(R.id.btn_cancel);
        cancelButton.requestFocus();
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreatePostScreen();
            }
        });

        // Continue to the Listing Review Page
        nextButton = findViewById(R.id.btn_next_to_review);
        nextButton.requestFocus();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((TextUtils.isEmpty(listingPrice.getText().toString()) )
                        || (TextUtils.isEmpty(description.getText().toString()))
                        || (radioGroup.getCheckedRadioButtonId() == -1)
                        || (spinner.getSelectedItemPosition()==0)) {
                    Toast.makeText(getApplicationContext(), "Please Enter Values for All Fields", Toast.LENGTH_SHORT).show();
                }else{
                    goToPostListingPage();
                }

            }
        });
//************************************END BUTTON GROUP**********************************************//
    }

    //set intent to open create post screen
    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }

    //set intent to open listing title page
    public void goBack(){
        Intent openScreen = new Intent(this, ListingTitlePage.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }
    //set intent to open listing review page
    public void goToPostListingPage(){
        //get intent from previous screen
        Intent intent = getIntent();
        String title = intent.getExtras().getString("ListingTitle");
        //byte[] byteArray = getIntent().getByteArrayExtra("ListingPics");
        //Bitmap images = intent.getParcelableExtra("ListingPics");

        //get the filled information from this screen
        //TODO this radiobutton & spinner call crashes it if you go back and replace with something else. We will prob need an intent to select upon back in order to fix this from crashing
        String category = spinner.getSelectedItem().toString();
        String listingType = radioButton.getText().toString();
        //Toast.makeText(getApplicationContext(), listingType, Toast.LENGTH_SHORT).show();

        //begin intent to change screen
        Intent openScreen = new Intent(this, ListingReviewPage.class);
        //block to pass intents to the next screen
      //  openScreen.putExtra("ListingPics", byteArray);
        openScreen.putExtra("ListingPrice", listingPrice.getText().toString());
        openScreen.putExtra("ListingTitle", title);
        openScreen.putExtra("ListingCategory", category);
        openScreen.putExtra("ListingType", listingType);
        openScreen.putExtra("ListingDescription", description.getText().toString());
        openScreen.putStringArrayListExtra("Listing Images", this.getIntent().getStringArrayListExtra("Listing Images"));
        //go to next screen
        startActivity(openScreen);
    }//end goToPostListingPage()

    //radio button on click listener
    public void rbclick(View v){

        //initiate the spinner

        //get the id of the radio button that is pressed ( not value we assign)
        int radiobuttonid = radioGroup.getCheckedRadioButtonId();
        //radio button holds the id value of selected (this is the value we assign to it (the name))
        radioButton = findViewById(radiobuttonid);
        //convert id value to a string
        String text = radioButton.getText().toString();
        //print as a test statement to see which id is being pressed
        //Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        //if blocks to check which radio button is pressed to populate the spinner
        if( text.equalsIgnoreCase("item"))
        {
            //array adapter holding the array list of categories created in the strings.xml
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.items_categoies));
            //setup adapter to be passed to spinner
            spinner.setAdapter(adapter);

        }
        else if( text.equalsIgnoreCase("service"))
        {
            //array adapter holding the array list of services created in the strings.xml
            ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.service_categoies));
            //setup adapter to be passed to spinner
            spinner.setAdapter(serviceAdapter);
        }
        else
        {
           //do nothing
        }
    }
}
