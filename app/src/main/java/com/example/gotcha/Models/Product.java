package com.example.gotcha.Models;

import java.util.Date;

public class Product {

    public enum CategoryType {
        NA,
        ELECTRONICS,
        APPLIANCES,
        CLOTHING,
        FURNITURE,
        SPORTING_GOODS,
        TOYS,
        BEAUTY,
        BOOKS,
        JEWELRY,
        AUTOMOTIVE,
        HOME_AND_GARDEN,
        HEALTH_AND_WELLNESS,
        OFFICE_SUPPLIES,
        FOOD_AND_DRINK,
        OTHER
    }
    private String productName = "";  // Name of the product.
    private CategoryType category = CategoryType.NA ; // Category of the product (e.g., electronics, appliances).
    private double price = 0.0 ; // Price of the product.
    private Date purchaseDate ; // Date when the product was purchased.
    private String purchaseLocation = "" ; // Location where the product was purchased.
    private String serialNumber = "" ; // Serial number of the product (if applicable).
    private String notes = "" ; // Any additional notes about the product.
    private boolean hasWarranty ;
    private Warranty warranty = new Warranty();
    public Product(){

    }

    public boolean isHasWarranty() {
        return hasWarranty;
    }

    public Warranty getWarranty() {
        return warranty;
    }

    public Product setWarranty(Warranty warranty) {
        this.warranty = warranty;
        return this;
    }

    public Product setHasWarranty(boolean hasWarranty) {
        this.hasWarranty = hasWarranty;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public Product setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public CategoryType getCategory() {
        return category;
    }

    public Product setCategory(CategoryType category) {
        this.category = category;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public Product setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public String getPurchaseLocation() {
        return purchaseLocation;
    }

    public Product setPurchaseLocation(String purchaseLocation) {
        this.purchaseLocation = purchaseLocation;
        return this;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public Product setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public Product setNotes(String notes) {
        this.notes = notes;
        return this;
    }
}
