package com.example.beachlist;

import android.graphics.Bitmap;

public class UserData {

    String firstName, lastName, idNumber, email, gradDate, phoneNum;
    String userId, imageUrl;
    String[] friends, pending, sent = {};

    public UserData() { }

    public UserData(String fname, String lname, String idNumber, String email, String gradDate, String phoneNum, String userId, String url) {
        this.userId = userId;
        this.firstName = fname;
        this.lastName = lname;
        this.idNumber = idNumber;
        this.email = email;
        this.gradDate = gradDate;
        this.phoneNum = phoneNum;
        this.imageUrl = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        this.firstName = fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lname) {
        this.lastName = lname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGradDate() {
        return gradDate;
    }

    public void setGradDate(String gradDate) {
        this.gradDate = gradDate;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
