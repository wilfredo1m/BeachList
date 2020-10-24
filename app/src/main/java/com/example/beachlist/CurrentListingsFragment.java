package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CurrentListingsFragment extends Fragment {
    RecyclerView recyclerView;
    CurrentListingsRecyclerAdapter adapter;
    public static List<ListingData> list = new ArrayList<>();

    private TextView textview;
    public CurrentListingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentListing= inflater.inflate(R.layout.fragment_current_listings, container, false);

        //******************************************Display Current Listings List***************************************
        recyclerView = currentListing.findViewById(R.id.current_listing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//TODO  REMOVE ALL THIS STUFF WHEN YOU ARE READY TO IMPLEMENT THE FIREBASE STUFF INTO THE PROGRAM. THIS IS BLOCK IS STRICTLY FOR TESTING*************************//
        // This is temporary, added these to test the list would display
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
//TODO END BLOCK HERE******************************************************************************************************************************************//




// *****************************BLOCK TO POPULATE SCREEN******************************************************************************************************//
        // clears list each time to make sure no duplicates are added
        list.clear();
        // adds the current listings to be the list that will be displayed
        for(int i = 0; i < listingNames.length; i++){
            ListingData listing = new ListingData(listingPics[i],sellerFirstNames[i], sellerLastNames[i], listingNames[i],listingDescriptions[i],listingAskingPrices[i],
                    listingSoldFor[i], listingSoldTo[i], listingSoldDate[i]);
            list.add(listing);
        }

        // Links recycler view adapter
        adapter = new CurrentListingsRecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
// *****************************END BLOCK TO POPULATE SCREEN******************************************************************************************************//
        return currentListing;
    } //end onCreateView()

}//end CurrentListingFragment()