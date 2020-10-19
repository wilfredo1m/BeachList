package com.example.beachlist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link *Fragment} subclass.
 * Use the {@link *UserHomeSearchTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeSearchTab extends Fragment{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<FriendsData> user_list = new ArrayList<>();

    public UserHomeSearchTab() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_select_from_home, container, false);

        //******************************************Display Pending Friends List*******************************
        recyclerView = view.findViewById(R.id.user_tab_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // This is temporary, added these to test the list would display
        String firstNames[] = getResources().getStringArray(R.array.first_names);
        int profilePics[] = {R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur,
                R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur, R.drawable.bulbasaur};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        // clears list each time to make sure no duplicates are added
        user_list.clear();

        // adds the pending friends to be the list that will be displayed
        for(int i = 0; i < firstNames.length; i++){
            FriendsData selectedUser = new FriendsData(null,firstNames[i],lastNames[i]);
            user_list.add(selectedUser);
        }

        // Links recycler view adapter
        adapter = new SelectedUserRecyclerAdapter(getActivity(),user_list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}