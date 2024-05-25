package com.example.gotcha.Models;

import java.util.ArrayList;

public class Category {

    private Product.CategoryType type;
    private int productCount;
    private ArrayList<Product> productList;

    public Category() {
    }

    public Category(Product.CategoryType type) {
        this.type = type;
        this.productCount = 0;
        this.productList = new ArrayList<Product>();
    }

    public Product.CategoryType getType() {
        return type;
    }

    public Category setType(Product.CategoryType type) {
        this.type = type;
        return this;
    }


    public int getProductCount() {
        return productCount;
    }

    public Category setProductCount(int productCount) {
        this.productCount = productCount;
        return this;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public Category setProductList(ArrayList<Product> productList) {
        this.productList = productList;
        return this;
    }

    @Override
    public String toString() {
        return "\n\nCategory{" +
                "type=" + type +
                ", productCount=" + productCount +
                ", productList=" + productList +
                "}";
    }
}
