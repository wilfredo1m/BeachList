package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ServiceHomeSearchTab extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    public ServiceHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_select_from_home, container, false);
        recyclerView = view.findViewById(R.id.service_list_recycler);

        //------------------confirm with viet regarding the getActivity()--------------------
        layoutManager = new GridLayoutManager(getActivity(), 3);
        //-----------------------------------------------------------------------------------
        recyclerView.setLayoutManager(layoutManager);



        return view;
    }
}