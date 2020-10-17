package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SoldListingsFragment extends Fragment {

    public SoldListingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View soldFragment=  inflater.inflate(R.layout.fragment_sold_listings, container, false);




        return  soldFragment;
    }
}