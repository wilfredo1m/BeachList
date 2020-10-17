package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CurrentListingsFragment extends Fragment {
    private TextView textview;
    public CurrentListingsFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View currentListing= inflater.inflate(R.layout.fragment_current_listings, container, false);
        textview= currentListing.findViewById(R.id.test_current_listing_id);
        String data=getArguments().getString("key");
        textview.setText(data);


        return currentListing;
    }
}