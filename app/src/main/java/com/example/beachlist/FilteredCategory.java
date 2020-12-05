package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FilteredCategory extends AppCompatActivity {
    Toolbar toolbar;                                                                                  //toolbar to change title
    Button backBtn;                                                                                   //back button
    Intent intent;                                                                                    //intent to get calls from other pages
    RecyclerView recyclerView;
    public static List<DataSnapshot> list = new ArrayList<>();
    ItemRecyclerAdapter itemAdapter;
    ServiceRecyclerAdapter serviceAdapter;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_category);                                         //set view to activity_filtered_category xml
        toolbar = findViewById(R.id.filtered_category_toolbar);                                      //link toolbar to xml button
        toolbar.setTitle(getCategoryTitle());                                                        //set the toolbar title to the button that called the page

        // sets usersReference to either item or service
        setUserReference();

        // Filtered Category list recycler
        recyclerView = findViewById(R.id.filtered_category_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list.clear();

        final String categorySelection = getCategorySelection();

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if (child.getValue(ListingData.class).getCategory().equalsIgnoreCase(categorySelection)) {
                            list.add(child);
                        }
                    }
                }
                onFilteredListQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

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
        openScreen.putExtra("tabPosition", getCallingPage());                                  //uses get tabPosition to send back to the categoryselection page in order to open up the correct tab
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
        int pageCall = intent.getIntExtra("tabPosition",7);                       //intent to get tabPosition to be used for back button
        return pageCall;                                                                             //return tabPosition
    }

    public String getCategorySelection(){
        intent = getIntent();                                                                         //intent to get information from other screen
        String category = intent.getExtras().getString("category");
        return category;
    }

    public void onFilteredListQuery() {
        if (getCallingPage() == 1){
            itemAdapter = new ItemRecyclerAdapter(this, list);
            recyclerView.setAdapter(itemAdapter);
        }
        else if(getCallingPage() == 2){
            serviceAdapter = new ServiceRecyclerAdapter(this,list);
            recyclerView.setAdapter(serviceAdapter);
        }
    }

    public void setUserReference(){
        if (getCallingPage() == 1){
            usersReference = database.getReference().child("listings").child("item");
        }
        else if (getCallingPage() == 2){
            usersReference = database.getReference().child("listings").child("service");
        }
    }
}
