package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

public class ListingDescriptionPage extends AppCompatActivity {
    Button cancelButton,backButton,nextButton;
    Spinner spinner;
    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_description_page);

        //Radio group initialization
        radioGroup = findViewById(R.id.radioGroup2);

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

        // Go back to the Listings Title Page
        backButton = findViewById(R.id.btn_back);
        backButton .requestFocus();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        // Continue to the Listing Review Page
        nextButton = findViewById(R.id.btn_next_to_review);
        nextButton.requestFocus();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPostListingPage();
            }
        });
//************************************END BUTTON GROUP**********************************************//
    }

    //set intent to open createpost screen
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
        byte[] byteArray = getIntent().getByteArrayExtra("ListingPics");
        //Bitmap images = intent.getParcelableExtra("ListingPics");

        //get the filled information from this screen
        //TODO this radiobutton & spinner call crashes it if you go back and replace with something else. We will prob need an intent to select upon back in order to fix this from crashing
        String category = spinner.getSelectedItem().toString();
        String listingType = radioButton.getText().toString();
       //TODO end problem children
        //Toast.makeText(getApplicationContext(), listingType, Toast.LENGTH_SHORT).show();

        //pull typed value for listing price
        TextView listingPrice = findViewById(R.id.et_listing_price);
        TextView description = findViewById(R.id.et_listing_description);

        //begin intent to change screen
        Intent openScreen = new Intent(this, ListingReviewPage.class);



        //block to pass intents to the next screen
        openScreen.putExtra("ListingPics", byteArray);
        openScreen.putExtra("ListingPrice", listingPrice.getText().toString());
        openScreen.putExtra("ListingTitle", title);
        openScreen.putExtra("ListingCategory", category);
        openScreen.putExtra("ListingType", listingType);
        openScreen.putExtra("ListingDescription", description.getText().toString());
        //go to next screen
        startActivity(openScreen);
    }//end goToPostListingPage()

    //radio button on click listener
    public void rbclick(View v){

        //initiate the spinner
        spinner = findViewById(R.id.categorie_spinner);

        //get the id of the radio button that is pressed ( not value we assign)
        int radiobuttonid = radioGroup.getCheckedRadioButtonId();
        //radio button holds the id value of selected (this is the value we assign to it(the name)
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
