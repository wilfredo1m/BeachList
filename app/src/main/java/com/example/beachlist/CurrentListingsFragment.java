package com.example.beachlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
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

    public CurrentListingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        DatabaseReference usersReference = database.getReference().child("users").child(user.getUid()).child("listings");
        // Inflate the layout for this fragment
        View currentListing= inflater.inflate(R.layout.fragment_current_listings, container, false);

        //******************************************Display Current Listings List***************************************
        recyclerView = currentListing.findViewById(R.id.current_listing_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

// *****************************BLOCK TO POPULATE SCREEN******************************************************************************************************//
        // clears list each time to make sure no duplicates are added
        list.clear();
        // adds the current listings to be the list that will be displayed
        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
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
            public void onCancelled(@NonNull DatabaseError databaseError) {
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