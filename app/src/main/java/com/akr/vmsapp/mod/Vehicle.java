package com.akr.vmsapp.mod;

public class Vehicle {
    private String carId, ownId, numberPlate, modelId, typeId, weight, color, sector, photoUrl, addDate;

    public Vehicle() {
    }

    public Vehicle(String carId, String ownId, String numberPlate, String modelId, String typeId,
                   String weight, String color, String sector, String photoUrl, String addDate) {
        this.carId = carId;
        this.ownId = ownId;
        this.numberPlate = numberPlate;
        this.modelId = modelId;
        this.typeId = typeId;
        this.weight = weight;
        this.color = color;
        this.sector = sector;
        this.photoUrl = photoUrl;
        this.addDate = addDate;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getOwnId() {
        return ownId;
    }

    public void setOwnId(String ownId) {
        this.ownId = ownId;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
