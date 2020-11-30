package com.example.beachlist;

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


public class ReportedUserFragment extends Fragment {
    private RecyclerView recyclerView;
    public static List<DataSnapshot> user_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ReportedUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference reportedRef = database.getReference("reported").child("users");

        // Inflate the layout for this fragment
        View reportedUserPage= inflater.inflate(R.layout.fragment_reported_user, container, false);

        //*********************Display Reported Listings List********************************
        recyclerView = reportedUserPage.findViewById(R.id.reported_user_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        user_list.clear();

        reportedRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        user_list.add(child);
                    }
                }
                onUserListQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //*******************************************************************************

        return reportedUserPage;
    }

    public void onUserListQuery() {
        ReportedUserRecyclerAdapter adapter = new ReportedUserRecyclerAdapter(getActivity(), user_list);
        recyclerView.setAdapter(adapter);
    }
}