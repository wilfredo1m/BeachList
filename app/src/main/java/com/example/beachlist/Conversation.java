package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Conversation extends AppCompatActivity {

    //***main page items used******************//
    Button sendMessage, backButton,soldButton;                                                        //buttons to navitage screen
    ConstraintLayout mainPage;                                                                        //layout for main page incase we need to make it invisible
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

        mainPage = findViewById(R.id.main_page_layout);
        soldView = findViewById(R.id.sold_page_layout);
        rating = findViewById(R.id.rate_user_rb);


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



}
