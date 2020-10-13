package com.example.beachlist;

import android.content.Intent;
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
 * A simple {@link Fragment} subclass.
 * Use the {@link UserHomeSearchTab#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserHomeSearchTab extends Fragment implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<FriendsData> list = new ArrayList<>();
//    SelectedUserRecyclerAdapter adapter;
    //Button selectedUserButton;

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
        int profilePics[] = {R.drawable.bulbasaur, R.drawable.charmander, R.drawable.froakie, R.drawable.golem, R.drawable.jigglypuff,
                R.drawable.pikachu, R.drawable.squirtle, R.drawable.sudowoodo, R.drawable.totodile, R.drawable.treeko};
        String lastNames[] = getResources().getStringArray(R.array.last_names);

        // clears list each time to make sure no duplicates are added
        list.clear();

        // adds the pending friends to be the list that will be displayed
        for(int i = 0; i < firstNames.length; i++){
            FriendsData selectedUser = new FriendsData(profilePics[i],firstNames[i],lastNames[i]);
            list.add(selectedUser);
        }
//
//        // Links recycler view adapter
        adapter = new SelectedUserRecyclerAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);

//        selectedUserButton = (Button) view.findViewById(R.id.test_to_selected_user);
//        selectedUserButton.setOnClickListener(this);


        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SelectedUser.class);
        startActivity(intent);
    }
}