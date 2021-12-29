package com.example.kalarilab;

import android.graphics.Bitmap;

public class User {
    public String fullName, email,  username,  age, bio, gender, profileImageLink;
    public Bitmap avatar;
    public ProgressTrackingSystem progressTrackingSystem;


    public User(String fullName, String email, String username, String age, String bio,String gender, Bitmap avatar) {
        this.fullName = fullName;
        this.email = email;
        this.username = '@'+username;
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
        progressTrackingSystem = new ProgressTrackingSystem();
    }

    public Bitmap getAvatar() {
        return avatar;
    }
}
