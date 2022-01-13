package com.example.kalarilab;

import android.graphics.Bitmap;

public class User {
    public String fullName;
    public String email;
    public String username;
    public int age;
    public String bio;
    public String gender;
    public String profileImageLink;
    public Bitmap avatar;
    public ProgressTrackingSystem progressTrackingSystem;


    public User(String fullName, String email, int age, String bio, String gender, Bitmap avatar) {
        this.fullName = fullName;
        this.email = email;
        this.age = age;
        this.bio = bio;
        this.gender = gender;
        this.avatar = avatar;
        progressTrackingSystem = new ProgressTrackingSystem();
    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    public User() {

    }

    public Bitmap getAvatar() {
        return avatar;
    }
}
