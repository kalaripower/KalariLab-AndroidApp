package com.example.kalarilab;

public class User {
    public String fullName, email,  username,  age, bio, gender, profileImageLink;


    public User(String fullName, String email, String username, String age, String bio,String gender) {
        this.fullName = fullName;
        this.email = email;
        this.username = '@'+username;
        this.age = age;
        this.bio = bio;
        this.gender = gender;

    }

    public void setProfileImageLink(String profileImageLink) {
        this.profileImageLink = profileImageLink;
    }

    public User() {

    }


}
