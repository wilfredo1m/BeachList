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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MessageFragment extends Fragment {
    Button testBtn;
    View messageView;
    private RecyclerView recyclerView;
    String userID;
    public static List<Map<String, String>> message_list = new ArrayList<>();
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MessageFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        messageView =inflater.inflate(R.layout.fragment_messages, container, false);

        DatabaseReference usersReference = database.getReference("/users/" + userID + "/chats");;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        userID = user.getUid();

        //***********************DISPLAY LIST OF MESSAGES*******************************
        recyclerView = messageView.findViewById(R.id.message_screen_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        // clears list each time to make sure no duplicates are added
        message_list.clear();

        // Map that acts as temporary placeholder for convos
        // This will be replaced by a db call later
        Map<String, String> test_message_one = new HashMap<String, String>();
        Map<String, String> test_message_two = new HashMap<String, String>();

        // Sample Url and Full Name
        test_message_one.put("url", "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F2001775738?alt=media&token=6bfdb931-c5d5-488d-ad0e-8f85d10246ce");
        test_message_one.put("fullname", "Sample Full Name one");

        test_message_two.put("url", "https://firebasestorage.googleapis.com/v0/b/beachlist-26c5b.appspot.com/o/images%2F2001775738?alt=media&token=6bfdb931-c5d5-488d-ad0e-8f85d10246ce");
        test_message_two.put("fullname", "Sample Full Name two");

        message_list.add(test_message_one);
        message_list.add(test_message_two);

        onServiceListQuery();

        // Inflate the layout for this fragment
        return messageView;
    }

    public void onServiceListQuery() {
        RecyclerView.Adapter adapter = new MessageRecyclerAdapter(getActivity(), message_list);
        recyclerView.setAdapter(adapter);
    }


}