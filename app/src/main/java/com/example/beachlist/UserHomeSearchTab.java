package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeSearchTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeSearchTab extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<FriendsData> user_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseUser user;
    private FirebaseAuth mAuth;

    public UserHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        String userId = user.getUid();
        DatabaseReference usersReference = database.getReference().child("users");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_select_from_home, container, false);

        //******************************************Display Pending Friends List*******************************
        recyclerView = view.findViewById(R.id.user_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // This is temporary, added these to test the list would display
        String firstNames[] = getResources().getStringArray(R.array.first_names);
        final int profilePics[] = {R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur,
                R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        // clears list each time to make sure no duplicates are added
        user_list.clear();

        usersReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        FriendsData friend = new FriendsData(profilePics[0], child.child("data").getValue(UserData.class).firstName, child.child("data").getValue(UserData.class).lastName, child.getKey());
                        user_list.add(friend);
                    }
                }

                onUserListQuery();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        return view;
    }

    public void onUserListQuery() {
        adapter = new SelectedUserRecyclerAdapter(getActivity(),user_list);
        recyclerView.setAdapter(adapter);
    }
}