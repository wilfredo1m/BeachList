package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ServicesCategory extends Fragment {
    View view;                                                                                        //view to inflate screen
    Button tutoringBtn, commissionBtn, repairBtn;                                                     //button group
    Intent openScreen;                                                                                //intent to go to new screen
    public ServicesCategory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_services_category, container, false);


//************************BTN GROUP********************************************************************//
        //set intent to filtered category page for each button
        //each button will add a appropriate filter to the intent to be used as a filter
        openScreen = new Intent(getActivity(), FilteredCategory.class);                               //intent to go to filteredCategory page

        //tutoring button
        tutoringBtn = view.findViewById(R.id.tutoring_btn);                                           //link tutoring btn to xml
        tutoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                        //setup on click listener
                openScreen.putExtra("calling page", 2);                                  //Assigns calling page to 2(service category) for use in back button in next page
                openScreen.putExtra("toolbar Name", "Filtered Tutoring");                //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with the put values
            }
        });

        //commission button
        commissionBtn= view.findViewById(R.id.commission_btn);                                        //link commission btn to xml
        commissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                      //setup on click listener
                openScreen.putExtra("calling page", 2);                                  //Assigns calling page to 2(service category) for use in back button in next page
                openScreen.putExtra("toolbar Name", "Filtered Commission");              //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with the put values
            }
        });

        //repair button
        repairBtn= view.findViewById(R.id.repair_btn);                                                //link repair btn to xml
        repairBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {                                                          //setup on click listener
                openScreen.putExtra("calling page", 2);                                  //Assigns calling page to 2(service category) for use in back button in next page
                openScreen.putExtra("toolbar Name", "Filtered Repair");                  //keeps track of which button is pressed to change title
                startActivity(openScreen);                                                            //open next screen with the put values
            }
        });

        return view;
    }
}