package com.example.gotcha.Models;

public class User {

    private String uid;
    private String Name;
    private String image;
    private boolean isRegistered;

    public User(){

    }
    public User(String uid, String name, String image) {
        this.uid = uid;
        Name = name;
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return Name;
    }

    public User setName(String name) {
        Name = name;
        return this;
    }

    public String getImage() {
        return image;
    }

    public User setImage(String image) {
        this.image = image;
        return this;
    }

    public boolean isRegistered() {
        return isRegistered;
    }

    public User setRegistered(boolean registered) {
        isRegistered = registered;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", Name='" + Name + '\'' +
                ", image='" + image + '\'' +
                ", isRegistered=" + isRegistered +
                '}';
    }
}
