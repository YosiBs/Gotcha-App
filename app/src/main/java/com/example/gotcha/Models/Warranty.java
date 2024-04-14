package com.example.gotcha.Models;

import java.util.Date;

public class Warranty {

    private String warrantyProvider;   // Name of the warranty provider (e.g., manufacturer, third-party service).
    private Date startDate;   // Start date of the warranty coverage.
    private Date endDate;   // End date of the warranty coverage.
    private int warrantyLength; //In Days
    private String coverageDetails;   // Details of the warranty coverage (e.g., what's covered, exclusions).private String warrantyType;   // Type of warranty (e.g., manufacturer's warranty, extended warranty).
    private String warrantyNumber; // Warranty identification number (if applicable).
    private String warrantyContact;   // Contact information for warranty inquiries or claims.

    public Warranty(){

    }

    public int getWarrantyLength() {
        return warrantyLength;
    }

    public Warranty setWarrantyLength(int warrantyLength) {
        this.warrantyLength = warrantyLength;
        return this;
    }

    public String getWarrantyProvider() {
        return warrantyProvider;
    }

    public Warranty setWarrantyProvider(String warrantyProvider) {
        this.warrantyProvider = warrantyProvider;
        return this;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Warranty setStartDate(Date startDate) {
        this.startDate = startDate;
        return this;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Warranty setEndDate(Date endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public Warranty setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
        return this;
    }

    public String getWarrantyNumber() {
        return warrantyNumber;
    }

    public Warranty setWarrantyNumber(String warrantyNumber) {
        this.warrantyNumber = warrantyNumber;
        return this;
    }

    public String getWarrantyContact() {
        return warrantyContact;
    }

    public Warranty setWarrantyContact(String warrantyContact) {
        this.warrantyContact = warrantyContact;
        return this;
    }
}
