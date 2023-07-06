package com.akr.vmsapp.mod;

public class Insurance {
    private String insId, carId, acquiryDate, expiryDate, company, amount, photoUrl, status, addDate;

    public Insurance() {
    }

    public Insurance(String insId, String carId, String acquiryDate, String expiryDate,
                     String company, String amount, String photoUrl, String status, String addDate) {
        this.insId = insId;
        this.carId = carId;
        this.acquiryDate = acquiryDate;
        this.expiryDate = expiryDate;
        this.company = company;
        this.amount = amount;
        this.photoUrl = photoUrl;
        this.status = status;
        this.addDate = addDate;
    }

    public String getInsId() {
        return insId;
    }

    public void setInsId(String insId) {
        this.insId = insId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getAcquiryDate() {
        return acquiryDate;
    }

    public void setAcquiryDate(String acquiryDate) {
        this.acquiryDate = acquiryDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
