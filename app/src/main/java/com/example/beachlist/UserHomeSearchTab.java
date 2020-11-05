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

/**
 * A simple {@link *Fragment} subclass.
 * Use the {@link *UserHomeSearchTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeSearchTab extends Fragment{
    private RecyclerView recyclerView;
    public static List<DataSnapshot> user_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public UserHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseReference usersReference = database.getReference().child("users");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_select_from_home, container, false);

        //******************************************Display Pending Friends List*******************************
        recyclerView = view.findViewById(R.id.user_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // clears list each time to make sure no duplicates are added
        user_list.clear();

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //FriendsData friend = new FriendsData(child.child("data").getValue(UserData.class).imageUrl, child.child("data").getValue(UserData.class).firstName, child.child("data").getValue(UserData.class).lastName, child.getKey());
                        user_list.add(child);
                    }
                }
                onUserListQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return view;
    }

    public void onUserListQuery() {
        RecyclerView.Adapter adapter = new UserRecyclerAdapter(getActivity(), user_list);
        recyclerView.setAdapter(adapter);
    }
}