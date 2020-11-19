package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class ReportedListingsFragment extends Fragment {
    private RecyclerView recyclerView;
    public static List<DataSnapshot> user_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ReportedListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View reportedListingPage =  inflater.inflate(R.layout.fragment_reported_listings, container, false);

        //*********************Display Reported Listings List********************************
        recyclerView = reportedListingPage.findViewById(R.id.reported_listings_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        onUserListQuery();
        //*******************************************************************************

        return reportedListingPage;
    }

    public void onUserListQuery() {
        ReportedListingsRecyclerAdapter adapter = new ReportedListingsRecyclerAdapter(getActivity(), user_list);
        recyclerView.setAdapter(adapter);
    }



}


