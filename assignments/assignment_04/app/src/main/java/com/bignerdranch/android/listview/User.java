package com.bignerdranch.android.listview;

public class User {
    private final String name;
    private final String phone;
    private final String imageUrl;

    public User(String name, String phone, String imageUrl) {
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getImageUrl() {return imageUrl;}
}