package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
        setContentView(R.layout.activity_listing_descirption_page);

//       spinner.setAdapter(adapter);

        //Radiotgroup initialization
        radioGroup = findViewById(R.id.radioGroup2);


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
    }


    public void openCreatePostScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }

    public void goBack(){
        Intent openScreen = new Intent(this, ListingTitlePage.class);
        openScreen.putExtra("screen",2);
        startActivity(openScreen);
    }
    public void goToPostListingPage(){
        Intent openScreen = new Intent(this, ListingReviewPage.class);
        startActivity(openScreen);
    }

    public void rbclick(View v){

        //initiate the spinner
        spinner = findViewById(R.id.categorie_spinner);
        //setup adapter to be passed to spinner

        int radiobuttonid = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radiobuttonid);
        String text = radioButton.getText().toString();
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

        if( text.equalsIgnoreCase("item"))
        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.items_categoies));
            spinner.setAdapter(adapter);

        }
        else if( text.equalsIgnoreCase("service"))
        {
           ArrayAdapter<String> serviceAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,
            getResources().getStringArray(R.array.service_categoies));
            spinner.setAdapter(serviceAdapter);
        }
        else
        {
           //do nothing
        }
    }
}
