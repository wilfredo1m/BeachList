package com.example.beachlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SoldListingsFragment extends Fragment {
    RecyclerView recyclerView;
    SoldListingsRecyclerAdapter adapter;
    public static List<SoldData> list = new ArrayList<>();

    //temporary
    //ArrayList<String> listingImages = new ArrayList();


    public SoldListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        // Inflate the layout for this fragment
        View soldFragment=  inflater.inflate(R.layout.fragment_sold_listings, container, false);

        //******************************************Display Sold Listings List***************************************
        recyclerView = soldFragment.findViewById(R.id.sold_listing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//TODO REMOVE THIS BLOCK AND REPLACE WITH FIREBASE************************************************************************************************//
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
//TODO END*************************************************************************************************************************************//

        DatabaseReference soldRef = firebaseDatabase.getReference("users").child(userId).child("sold");

        // clears list each time to make sure no duplicates are added
        list.clear();

        Time current = new Time(Time.getCurrentTimezone());


//        SoldData sold = new SoldData();
//        sold.setImageUrl("https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F997005285?alt=media&token=af988212-f736-4eec-86e6-fd3ffb521547");
//        sold.setTitle("That Sauce");
//        sold.setSellPrice(69);
//        sold.setSoldTo("Harry Johnson");
//        sold.setSoldDate(current);
//
//        soldRef.child("thisIsMadeUp").setValue(sold).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()) {
//                    System.out.println("Success!");
//                }
//                else {
//                    System.out.println("not great");
//                }
//            }
//        });

        //listingImages.add("https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F997005285?alt=media&token=af988212-f736-4eec-86e6-fd3ffb521547");

        soldRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        SoldData thisListing = child.getValue(SoldData.class);
                        list.add(thisListing);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        // adds the sold listings to be the list that will be displayed
//        for(int i = 0; i < listingNames.length; i++){
//            ListingData listing = new ListingData(listingImages, sellerLastNames[i], listingNames[i],listingDescriptions[i],listingAskingPrices[i],
//                    listingSoldFor[i], listingSoldTo[i], listingSoldDate[i], "filler", "filler", false);
//            list.add(listing);
//        }

        // Links recycler view adapter
        adapter = new SoldListingsRecyclerAdapter(getContext(),list);
        recyclerView.setAdapter(adapter);

        return  soldFragment;
    }
}