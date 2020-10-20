package com.example.beachlist;

public class OtherUser {
    String firstName;
    String lastName;
    String imageUrl;
    String userId;

    public OtherUser() {
        //default initialzer
        //required by firebase for converting the object to json
    }

    public OtherUser(String firstName, String lastName, String imageUrl) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
    }

    public OtherUser(String firstName, String lastName, String imageUrl, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.imageUrl = imageUrl;
        this.userId = userId;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() { return userId; }

    public void setUserId(String lastName) { this.userId = userId; }
}
