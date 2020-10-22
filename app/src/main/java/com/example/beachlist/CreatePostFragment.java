package com.example.beachlist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;


public class CreatePostFragment extends Fragment implements View.OnClickListener {
    String[] descriptionData= {"Select Photo","Item Description","Confirm"};
    private Button fromGalleryButton,cameraRoll;
    public CreatePostFragment() {
        // Required empty public constructor
    }
    //TODO add the screen efffect of the select picture from gallery or from camera to go to next page by bottom to up effect
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View createPostScreen=  inflater.inflate(R.layout.fragment_create_post, container, false);
        //takes us to the tittle page
        fromGalleryButton = (Button) createPostScreen.findViewById(R.id.btn_gallery_button);
        fromGalleryButton.setOnClickListener(this);
        SeekBar seekBar = createPostScreen.findViewById(R.id.title_seek_bar);
        seekBar.setClickable(false);
        return createPostScreen;
    } //end on CreateView

    @Override
    public void onClick(View view) {
        Intent openScreen = new Intent(getActivity(), ListingTitlePage.class);
        openScreen.putExtra("screen",1);
        startActivity(openScreen);
    }

}