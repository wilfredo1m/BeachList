package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ItemsCategory extends Fragment {
    View view;
    Button textbookBtn, electronicBtn, furnitureBtn,gameBtn,                                        //group of buttons to be used
            collectibleBtn, artBtn, appliancesBtn, jewelryBtn, miscBtn;                             //group of buttons to be used
    Intent openScreen;                                                                              //intent to pass information to another screen


    public ItemsCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_items_category, container, false);

//**********************BUTTON GROUP*****************************************************************************************//
        //set intent to filtered category page for each button
        //each button will add a appropriate filter to the intent to be used as a filter
        openScreen = new Intent(getActivity(), FilteredCategory.class);                                //intent to open page Filtered Category
        textbookBtn = view.findViewById(R.id.book_btn) ;                                               //textbook category btn linked to xml
        textbookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                              //on click listener for button
                openScreen.putExtra("calling page", 1);                                   //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "Textbook");
                openScreen.putExtra("toolbar Name", "Filtered Textbooks");                //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                             //open next screen with the put values
            }
        });
        electronicBtn = view.findViewById(R.id.electronic_btn) ;                                      //electronics button linked to xml
        electronicBtn.setOnClickListener(new View.OnClickListener() {                                 //on click listener for button
            @Override
            public void onClick(View v) {
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "electronic");
                openScreen.putExtra("toolbar Name", "Filtered Electronics");             //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        furnitureBtn = view.findViewById(R.id.furniture_btn);                                         //furniture category btn linked to xml
        furnitureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                       //on click listener for button
                openScreen.putExtra("calling page", 1);                                   //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "furniture");
                openScreen.putExtra("toolbar Name", "Filtered Furniture");                //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                             //open next screen with put values
            }
        });
        gameBtn = view.findViewById(R.id.games_btn) ;                                                  //game button linked to xml
        gameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                             //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "game");
                openScreen.putExtra("toolbar Name", "Filtered Games");                   //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        collectibleBtn = view.findViewById(R.id.collectibles_btn);                                    //collectible button linked to xml
        collectibleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                     //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "collectible");
                openScreen.putExtra("toolbar Name", "Filtered Collectibles");            //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        artBtn = view.findViewById(R.id.art_btn);                                                     //art/craft button linked to xml
        artBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                             //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "art");
                openScreen.putExtra("toolbar Name", "Filtered Art/Crafts");              //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        appliancesBtn = view.findViewById(R.id.appliances_btn);                                       //appliances button linked to xml
        appliancesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                      //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "appliances");
                openScreen.putExtra("toolbar Name", "Filtered Appliances");              //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        jewelryBtn = view.findViewById(R.id.jewelry_btn);                                             //jewelry button linked to xml
        jewelryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                         //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "jewelry");
                openScreen.putExtra("toolbar Name", "Filtered Jewelry");                 //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
        miscBtn = view.findViewById(R.id.misc_btn);                                                   //
        miscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                            //on click listener for button
                openScreen.putExtra("calling page", 1);                                  //Assigns calling page to 1(item category) for use in back button in next page
                openScreen.putExtra("category", "misc");
                openScreen.putExtra("toolbar Name", "Filtered miscellaneous ");          //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with put values
            }
        });
//************************END BUTTON GROUP********************************************************************************//

    return view;
    }
}