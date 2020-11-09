package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FilteredCategory extends AppCompatActivity {
    Toolbar toolbar;                                                                                  //toolbar to change title
    Button backBtn;                                                                                   //back button
    Intent intent;                                                                                    //intent to get calls from other pages

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_category);                                         //set view to activity_filtered_category xml
        toolbar = findViewById(R.id.filtered_category_toolbar);                                      //link toolbar to xml button
        toolbar.setTitle(getCategoryTitle());                                                        //set the toolbar title to the button that called the page


//********************button selection***************************************************//
        backBtn = findViewById(R.id.back_btn_from_filtered_category);                                //link back button to xml button
        backBtn.setOnClickListener(new View.OnClickListener() {                                      //setup on click listener
            @Override
            public void onClick(View v) {
                openCategoryPage();                                                                  //calls function to get the tab that opened this page and sends us back to that page
            }
        });
//******************end button selection***********************************************//


    }
    // takes us back to account settings screen
    public void openCategoryPage(){
        Intent openScreen = new Intent(this, CategorySelection.class);                  //intent to send user to categorySelection page( back button)
        openScreen.putExtra("calling page", getCallingPage());                                  //uses get calling page to send back to the categoryselection page in order to open up the correct tab
        startActivity(openScreen);                                                                    //open calling screen
    }
    //get the title of the tab based on the selected button
    public String getCategoryTitle(){
        intent = getIntent();                                                                         //intent to be used to get title to populate toolbar
        String title = intent.getExtras().getString("toolbar Name");                             //get the toolbar name associate with the button press from item/service page
        return title;                                                                                 //return title
    }

    //get page called to be used in back button
    //1- item category
    //2- service category
    public int getCallingPage(){
        intent = getIntent();                                                                         //intent to get information from other screen
        int pageCall = intent.getIntExtra("calling page",7);                       //intent to get calling page to be used for back button
        return pageCall;                                                                             //return calling page
    }
}
