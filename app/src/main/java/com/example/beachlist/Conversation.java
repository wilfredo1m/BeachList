package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Conversation extends AppCompatActivity {

Button submitButton, backButton;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_conversation);                                      //used to display the activity_message_conversation.xml


        submitButton= findViewById(R.id.send_message_btn);                                              //link submit button to xml button
        submitButton.setOnClickListener(new View.OnClickListener() {                                    //set on click listener for button
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


    }

    // takes us back to account settings screen
    public void openMessagesScreen(){
        Intent openScreen = new Intent(this, HomeScreenAfterLogin.class);              //intent to open HomeScreen
        openScreen.putExtra("screen",1);                                                //intent passed in order to move to 3rd position in the menu bar
        startActivity(openScreen);                                                                   //start change screen
    }

}
