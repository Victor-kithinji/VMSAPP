package com.akr.vmsapp.mod;

public class VehicleType {
    private String typId, name, description, addDate;

    public VehicleType() {
    }

    public VehicleType(String typId, String name, String description, String addDate) {
        this.typId = typId;
        this.name = name;
        this.description = description;
        this.addDate = addDate;
    }

    public String getTypId() {
        return typId;
    }

    public void setTypId(String typId) {
        this.typId = typId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
