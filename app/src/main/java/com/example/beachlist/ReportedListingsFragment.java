package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ReportedListingsFragment extends Fragment {


    public ReportedListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View reportedListingPage=  inflater.inflate(R.layout.fragment_reported_listings, container, false);


        return reportedListingPage;
    }





}


