package com.bignerdranch.android.finalproject;

public class Product {

    private String _id;
    private String productName;
    private double price;
    private String description;

    public Product(String _id, String productName, double price, String description) {
        this._id = _id;
        this.productName = productName;
        this.price = price;
        this.description = description;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}