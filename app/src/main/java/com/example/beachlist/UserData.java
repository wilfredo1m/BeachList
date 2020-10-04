package com.example.beachlist;

import android.graphics.Bitmap;

public class UserData {

    String userId, fname, lname, idNumber, email, gradDate, phoneNum;
    Bitmap profileImage;

    public UserData() { }

    public UserData(String userId, String fname, String lname, String idNumber, String email, String gradDate, String phoneNum, Bitmap profileImage) {
        this.userId = userId;
        this.fname = fname;
        this.lname = lname;
        this.idNumber = idNumber;
        this.email = email;
        this.gradDate = gradDate;
        this.phoneNum = phoneNum;
        this.profileImage = profileImage;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }
}
