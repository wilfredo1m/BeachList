package com.example.beachlist;

public class FriendsData {
    private int imageProfile;
    String firstName, lastName, userId;

    public FriendsData(int imageProfile, String firstName, String lastName){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FriendsData(int imageProfile, String firstName, String lastName, String userId){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String lastName) {
        this.userId = userId;
    }
}
