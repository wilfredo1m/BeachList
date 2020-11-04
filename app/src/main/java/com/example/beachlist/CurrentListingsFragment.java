package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CurrentListingsFragment extends Fragment {
    RecyclerView recyclerView;
    CurrentListingsRecyclerAdapter adapter;
    public static List<DataSnapshot> list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public CurrentListingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        DatabaseReference usersReference = database.getReference().child("users").child(user.getUid()).child("listings");
        // Inflate the layout for this fragment
        View currentListing= inflater.inflate(R.layout.fragment_current_listings, container, false);

        //******************************************Display Current Listings List***************************************
        recyclerView = currentListing.findViewById(R.id.current_listing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//// REMOVE ALL THIS STUFF WHEN YOU ARE READY TO IMPLEMENT THE FIREBASE STUFF INTO THE PROGRAM. THIS IS BLOCK IS STRICTLY FOR TESTING*************************//
//        // This is temporary, added these to test the list would display
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
//// END BLOCK HERE******************************************************************************************************************************************//


// *****************************BLOCK TO POPULATE SCREEN******************************************************************************************************//
        // clears list each time to make sure no duplicates are added
        list.clear();
        // adds the current listings to be the list that will be displayed
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        list.add(child);
                        //getChildImages(child.getRef().child("listingImages"));
                    }
                }
                // Links recycler view adapter
                onServiceListQuery();
                //***********************************************************************
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
// *****************************END BLOCK TO POPULATE SCREEN******************************************************************************************************//
        return currentListing;
    } //end onCreateView()

    public void onServiceListQuery() {
        adapter = new CurrentListingsRecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);
    }

}//end CurrentListingFragment()