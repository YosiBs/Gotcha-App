package com.example.gotcha.Models;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class User {

    private String uid;
    private String name;
    private String image;
    private boolean isRegistered;
    private ArrayList<Product> productList;
    public User(){

    }
    public User(String uid, String name, String image) {
        this.uid = uid;
        this.name = name;
        this.image = image;
        this.productList = new ArrayList<Product>();
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public User setProductList(ArrayList<Product> productList) {
        this.productList = productList;
        return this;
    }

    public String getUid() {
        return uid;
    }

    public User setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
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
                ", Name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", isRegistered=" + isRegistered +
                '}';
    }
}
