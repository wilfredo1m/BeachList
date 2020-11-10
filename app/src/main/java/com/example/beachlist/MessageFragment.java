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
        testBtn= messageView.findViewById(R.id.test_button);

        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectedMessageScreen();
            }
        });
        // Inflate the layout for this fragment
        return messageView;
    }

    //screen to be populated by message between user and signed in user
    public void openSelectedMessageScreen(){
        Intent openScreen = new Intent(getContext(), Conversation.class);
        startActivity(openScreen);
    }

}