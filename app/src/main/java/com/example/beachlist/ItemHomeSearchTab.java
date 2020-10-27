package com.example.beachlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ItemHomeSearchTab extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<DataSnapshot> item_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public ItemHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference usersReference = database.getReference().child("listings").child("item");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_select_from_home, container, false);

        //***********************DISPLAY LISTING*******************************
        recyclerView = view.findViewById(R.id.item_tab_recycler);
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
        item_list.clear();

//        for(int i = 0; i < listingNames.length; i++){
//            ListingData listing = new ListingData("https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F997005285?alt=media&token=af988212-f736-4eec-86e6-fd3ffb521547",sellerFirstNames[i], listingNames[i],listingDescriptions[i],listingAskingPrices[i], "another",
//                    listingSoldFor[i], "yump", listingSoldTo[i], listingSoldDate[i]);
//            listing_list.add(listing);
//        }

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        item_list.add(child);
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
        adapter = new ItemRecyclerAdapter(getActivity(),item_list);
        recyclerView.setAdapter(adapter);
    }
}