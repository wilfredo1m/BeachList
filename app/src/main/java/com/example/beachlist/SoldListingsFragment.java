package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class SoldListingsFragment extends Fragment {
    RecyclerView recyclerView;
    SoldListingsRecyclerAdapter adapter;
    public static List<ListingData> list = new ArrayList<>();


    public SoldListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View soldFragment=  inflater.inflate(R.layout.fragment_sold_listings, container, false);

        //******************************************Display Friends List***************************************
        recyclerView = soldFragment.findViewById(R.id.sold_listing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // This is temporary, added these to test the list would display
        int listingPics[][] = {{R.drawable.bulbasaur},{R.drawable.charmander}, {R.drawable.froakie}, {R.drawable.golem}, {R.drawable.jigglypuff},
                {R.drawable.pikachu}, {R.drawable.squirtle}, {R.drawable.sudowoodo}, {R.drawable.totodile}, {R.drawable.treeko}};
        String listingNames[] = getResources().getStringArray(R.array.listing_names);
        String listingDescriptions[] = getResources().getStringArray(R.array.listing_descriptions);
        String listingAskingPrices[] = getResources().getStringArray(R.array.listing_asking_prices);
        String listingSoldFor[] = getResources().getStringArray(R.array.listing_sold_prices);
        String listingSoldTo[] = getResources().getStringArray(R.array.listing_sold_names);
        String listingSoldDate[] = getResources().getStringArray(R.array.listing_sold_dates);

        // clears list each time to make sure no duplicates are added
        list.clear();

        // adds the friends to be the list that will be displayed
        for(int i = 0; i < listingNames.length; i++){
            ListingData listing = new ListingData(listingPics[i],listingNames[i],listingDescriptions[i],listingAskingPrices[i],
                    listingSoldFor[i], listingSoldTo[i], listingSoldDate[i]);
            list.add(listing);
        }

        // Links recycler view adapter
        adapter = new SoldListingsRecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

//        recyclerView = soldFragment.findViewById(R.id.friends_recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // This is temporary, added these to test the list would display
//        String firstNames[] = getResources().getStringArray(R.array.first_names);
//        int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
//                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
//        String lastNames[] = getResources().getStringArray(R.array.last_names);
//
//        // clears list each time to make sure no duplicates are added
//        list.clear();
//
//        // adds the friends to be the list that will be displayed
//        for(int i = 0; i < firstNames.length; i++){
//            FriendsData friend = new FriendsData(profilePics[i],firstNames[i],lastNames[i]);
//            list.add(friend);
//        }
//
//        // Links recycler view adapter
//        adapter = new FriendsRecyclerAdapter(getContext(),list);
//        recyclerView.setAdapter(adapter);


        return  soldFragment;
    }
}