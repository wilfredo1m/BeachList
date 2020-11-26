package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ReportedListingsFragment extends Fragment {
    private RecyclerView recyclerView;
    public static List<DataSnapshot> listings = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ReportedListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference reportedRef = database.getReference("reported").child("listings");
        // Inflate the layout for this fragment
        View reportedListingPage =  inflater.inflate(R.layout.fragment_reported_listings, container, false);

        //*********************Display Reported Listings List********************************
        recyclerView = reportedListingPage.findViewById(R.id.reported_listings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);


        listings.clear();

        reportedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        listings.add(child);
                    }
                }
                onUserListQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //*******************************************************************************

        return reportedListingPage;
    }

    public void onUserListQuery() {
        ReportedListingsRecyclerAdapter adapter = new ReportedListingsRecyclerAdapter(getActivity(), listings);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}


