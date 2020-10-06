package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettingsFragment extends Fragment implements View.OnClickListener{
    private Button friendsListButton;

    public AccountSettingsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View accountSettingsScreen = inflater.inflate(R.layout.fragment_account_settings, container, false);

        friendsListButton = (Button) accountSettingsScreen.findViewById(R.id.btn_friend_list);
        friendsListButton.setOnClickListener(this);

        return accountSettingsScreen;

    }


    @Override
    public void onClick(View view) {
        Intent openScreen = new Intent(getActivity(),FriendsList.class);
        startActivity(openScreen);
    }
}