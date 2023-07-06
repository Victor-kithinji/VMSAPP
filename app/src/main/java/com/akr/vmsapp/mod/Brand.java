package com.akr.vmsapp.mod;

public class Brand {
    private String brnId, name, company, description, addDate;

    public Brand() {
    }

    public Brand(String brnId, String name, String company, String description, String addDate) {
        this.brnId = brnId;
        this.name = name;
        this.company = company;
        this.description = description;
        this.addDate = addDate;
    }

    public String getBrnId() {
        return brnId;
    }

    public void setBrnId(String brnId) {
        this.brnId = brnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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
