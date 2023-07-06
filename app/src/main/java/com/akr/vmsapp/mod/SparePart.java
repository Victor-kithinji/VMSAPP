package com.akr.vmsapp.mod;

public class SparePart {
    private String sppId, spsId, name, price, rosCost, modelId, photoUrl, addDate;

    public SparePart() {
    }

    public SparePart(String sppId, String spsId, String name, String price, String rosCost, String modelId, String photoUrl, String addDate) {
        this.sppId = sppId;
        this.spsId = spsId;
        this.name = name;
        this.price = price;
        this.rosCost = rosCost;
        this.modelId = modelId;
        this.photoUrl = photoUrl;
        this.addDate = addDate;
    }

    public String getSppId() {
        return sppId;
    }

    public void setSppId(String sppId) {
        this.sppId = sppId;
    }

    public String getSpsId() {
        return spsId;
    }

    public void setSpsId(String spsId) {
        this.spsId = spsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRosCost() {
        return rosCost;
    }

    public void setRosCost(String rosCost) {
        this.rosCost = rosCost;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
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
