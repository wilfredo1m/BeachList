package com.example.beachlist;

public class FriendsData {
    String imageProfile;
    String firstName, lastName, userId;

    public FriendsData(String imageProfile, String firstName, String lastName){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public FriendsData(String imageProfile, String firstName, String lastName, String userId){
        this.imageProfile = imageProfile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
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
