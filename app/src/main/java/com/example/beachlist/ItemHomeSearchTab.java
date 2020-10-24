package com.example.beachlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ItemHomeSearchTab extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<ListingData> listing_list = new ArrayList<>();

    public ItemHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item_select_from_home, container, false);

        //***********************DISPLAY LISTING*******************************
        recyclerView = view.findViewById(R.id.item_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Test Arrays to make sure the info displays correctly
        int listingPics[][] = {{R.drawable.bulbasaur},{R.drawable.charmander}, {R.drawable.froakie}, {R.drawable.golem}, {R.drawable.jigglypuff},
                {R.drawable.pikachu}, {R.drawable.squirtle}, {R.drawable.sudowoodo}, {R.drawable.totodile}, {R.drawable.treeko}};
        String sellerFirstNames[] = getResources().getStringArray(R.array.first_names);
        String sellerLastNames[] = getResources().getStringArray(R.array.last_names);
        String listingNames[] = getResources().getStringArray(R.array.listing_names);
        String listingDescriptions[] = getResources().getStringArray(R.array.listing_descriptions);
        String listingAskingPrices[] = getResources().getStringArray(R.array.listing_asking_prices);
        String listingSoldFor[] = getResources().getStringArray(R.array.listing_sold_prices);
        String listingSoldTo[] = getResources().getStringArray(R.array.listing_sold_names);
        String listingSoldDate[] = getResources().getStringArray(R.array.listing_sold_dates);

        // clears list each time to make sure no duplicates are added
        listing_list.clear();

        for(int i = 0; i < listingNames.length; i++){
            ListingData listing = new ListingData(listingPics[i],sellerFirstNames[i], sellerLastNames[i], listingNames[i],listingDescriptions[i],listingAskingPrices[i],
                    listingSoldFor[i], listingSoldTo[i], listingSoldDate[i]);
            listing_list.add(listing);
        }

        adapter = new ItemRecyclerAdapter(getActivity(),listing_list);
        recyclerView.setAdapter(adapter);
        //***********************************************************************

        return view;
    }
}