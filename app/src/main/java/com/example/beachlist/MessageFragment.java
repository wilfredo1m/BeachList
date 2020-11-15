package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class MessageFragment extends Fragment {
    Button testBtn;
    View messageView;

    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        messageView =inflater.inflate(R.layout.fragment_messages, container, false);

        // Inflate the layout for this fragment
        return messageView;
    }


}