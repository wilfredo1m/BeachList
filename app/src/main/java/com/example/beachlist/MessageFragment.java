package com.example.beachlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class MessageFragment extends Fragment {
    Button testBtn;
    View messageView;
    MessageRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    String userID;
    public static List<DataSnapshot> convo_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        messageView =inflater.inflate(R.layout.fragment_messages, container, false);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //***********************DISPLAY LIST OF MESSAGES*******************************
        recyclerView = messageView.findViewById(R.id.message_screen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // Filter out convos that dont belong to the user.
        Query getUserConvos = database.getReference("convos").orderByChild("members/" + mAuth.getUid()).startAt(true);

        convo_list.clear();

        getUserConvos.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        convo_list.add(child);
                    }
                }
                onConvoDataQuery();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        // Inflate the layout for this fragment
        return messageView;
    }

    public void onConvoDataQuery() {
        adapter = new MessageRecyclerAdapter(getContext(), convo_list);
        recyclerView.setAdapter(adapter);
    }
}