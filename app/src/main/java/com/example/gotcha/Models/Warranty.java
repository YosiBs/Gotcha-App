package com.example.gotcha.Models;

import android.util.Log;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Warranty {

    private String warrantyProvider = "";   // Name of the warranty provider (e.g., manufacturer, third-party service).
    private String startDate = null;   // Start date of the warranty coverage.
    private String endDate = null;   // End date of the warranty coverage.
    private long warrantyLength = 0; //In Days
    private String coverageDetails = "";   // Details of the warranty coverage (e.g., what's covered, exclusions).private String warrantyType;   // Type of warranty (e.g., manufacturer's warranty, extended warranty).
    private String warrantyNumber = ""; // Warranty identification number (if applicable).
    private String warrantyContact = "";   // Contact information for warranty inquiries or claims.

    @Override
    public String toString() {
        return "\n    Warranty{" +
                "\n     warrantyProvider='" + warrantyProvider + '\'' +
                ", \n       startDate=" + startDate +
                ", \n       endDate=" + endDate +
                ", \n       warrantyLength=" + warrantyLength +
                ", \n       coverageDetails='" + coverageDetails + '\'' +
                ", \n       warrantyNumber='" + warrantyNumber + '\'' +
                ", \n       warrantyContact='" + warrantyContact + '\'' +
                '}';
    }

    public Warranty(){

    }

    public long getWarrantyLength() {
        return warrantyLength;
    }

    public Warranty setWarrantyLength(long warrantyLength) {
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

    public String getStartDate() {
        return startDate;
    }

    public Warranty setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public Warranty setEndDate(String endDate) {
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
    public long calcWarrantyReminder(LocalDate fromDate){
        LocalDate endDateLocal = LocalDate.parse(endDate);
        long remainingWarranty = ChronoUnit.DAYS.between(fromDate, endDateLocal);
        return remainingWarranty;
    }
}
