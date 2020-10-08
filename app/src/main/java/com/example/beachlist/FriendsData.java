package com.example.beachlist;

public class FriendsData {
    private int imageProfile;
    private String firstName, lastName;

    public FriendsData(int imageProfile, String firstName, String lastName){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(int imageProfile) {
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
