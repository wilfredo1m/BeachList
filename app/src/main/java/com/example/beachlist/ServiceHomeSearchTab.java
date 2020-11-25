package com.example.beachlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceHomeSearchTab extends Fragment {
    private RecyclerView recyclerView;
    public static List<DataSnapshot> service_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();


    public ServiceHomeSearchTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference usersReference = database.getReference().child("listings").child("service");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_select_from_home, container, false);

        //***********************DISPLAY LISTING*******************************
        recyclerView = view.findViewById(R.id.service_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // clears list each time to make sure no duplicates are added
        service_list.clear();

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        if(child.child("banned").getValue(Boolean.class).equals(false)) {
                            service_list.add(child);
                        }
                    }
                }
                onServiceListQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return view;
    }

    public void onServiceListQuery() {
        ServiceRecyclerAdapter adapter = new ServiceRecyclerAdapter(getActivity(), service_list);
        recyclerView.setAdapter(adapter);
    }
}