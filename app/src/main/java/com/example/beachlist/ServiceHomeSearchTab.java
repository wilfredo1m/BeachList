package com.example.beachlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ServiceHomeSearchTab extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<DataSnapshot> service_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;


    public ServiceHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference usersReference = database.getReference().child("listings").child("service");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_select_from_home, container, false);

        //***********************DISPLAY LISTING*******************************
        recyclerView = view.findViewById(R.id.service_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

//        // Test Arrays to make sure the info displays correctly
//        int listingPics[][] = {{R.drawable.bulbasaur},{R.drawable.charmander}, {R.drawable.froakie}, {R.drawable.golem}, {R.drawable.jigglypuff},
//                {R.drawable.pikachu}, {R.drawable.squirtle}, {R.drawable.sudowoodo}, {R.drawable.totodile}, {R.drawable.treeko}};
//        String sellerFirstNames[] = getResources().getStringArray(R.array.first_names);
//        String sellerLastNames[] = getResources().getStringArray(R.array.last_names);
//        String listingNames[] = getResources().getStringArray(R.array.listing_names);
//        String listingDescriptions[] = getResources().getStringArray(R.array.listing_descriptions);
//        String listingAskingPrices[] = getResources().getStringArray(R.array.listing_asking_prices);
//        String listingSoldFor[] = getResources().getStringArray(R.array.listing_sold_prices);
//        String listingSoldTo[] = getResources().getStringArray(R.array.listing_sold_names);
//        String listingSoldDate[] = getResources().getStringArray(R.array.listing_sold_dates);

        // clears list each time to make sure no duplicates are added
        service_list.clear();

//        for(int i = 0; i < listingNames.length; i++){
//            ListingData listing = new ListingData(listingPics[i],sellerFirstNames[i], sellerLastNames[i], listingNames[i],listingDescriptions[i],listingAskingPrices[i],
//                    listingSoldFor[i], listingSoldTo[i], listingSoldDate[i]);
//            listing_list.add(listing);
//        }

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        service_list.add(child);
                        //getChildImages(child.getRef().child("listingImages"));
                    }
                }
                onServiceListQuery();
                //***********************************************************************
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return view;
    }

    public void onServiceListQuery() {
        adapter = new ServiceRecyclerAdapter(getActivity(),service_list);
        recyclerView.setAdapter(adapter);
    }
}