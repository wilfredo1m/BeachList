package com.example.beachlist;

import android.graphics.Bitmap;

public class FriendsData {
    private Bitmap imageProfile;
    private String firstName, lastName;

    public FriendsData( ){
        this.imageProfile = null;
        this.firstName = " ";
        this.lastName = " ";
    }

    public FriendsData(Bitmap imageProfile, String firstName, String lastName){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Bitmap getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(Bitmap imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
