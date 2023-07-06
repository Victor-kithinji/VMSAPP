package com.akr.vmsapp.mod;

public class Expense {
    private String expId, carId, grgId, mecId, sppId, amount, description, addDate;

    public Expense() {
    }

    public Expense(String expId, String carId, String grgId, String mecId, String sppId, String amount, String description, String addDate) {
        this.expId = expId;
        this.carId = carId;
        this.grgId = grgId;
        this.mecId = mecId;
        this.sppId = sppId;
        this.amount = amount;
        this.description = description;
        this.addDate = addDate;
    }

    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getGrgId() {
        return grgId;
    }

    public void setGrgId(String grgId) {
        this.grgId = grgId;
    }

    public String getMecId() {
        return mecId;
    }

    public void setMecId(String mecId) {
        this.mecId = mecId;
    }

    public String getSppId() {
        return sppId;
    }

    public void setSppId(String sppId) {
        this.sppId = sppId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
