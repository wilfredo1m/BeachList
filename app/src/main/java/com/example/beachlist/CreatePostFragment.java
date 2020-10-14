package com.example.beachlist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.beachlist.databinding.ActivityMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePostFragment extends Fragment {
    ActivityMainBinding binding;
    String[] descriptionData= {"Select Photo","Item Description","Confirm"};
    int current_state = 0;

    public CreatePostFragment() {
        // Required empty public constructor
    }
    //TODO add the screen efffect of the select picture from gallery or from camera to go to next page by bottom to up effect
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= ActivityMainBinding.inflate(getLayoutInflater());
        //when u do a return it makes me question wehre teh fuck is the view here haha (3:42 in the vid)
        //setContentView(binding.getRoot());
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_create_post, container, false);
    }
}