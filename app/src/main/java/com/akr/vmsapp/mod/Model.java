package com.akr.vmsapp.mod;

public class Model {
    private String modId, brnId, name, typId, description, addDate;

    public Model() {
    }

    public Model(String modId, String brnId, String name, String type, String description, String addDate) {
        this.modId = modId;
        this.brnId = brnId;
        this.name = name;
        this.typId = type;
        this.description = description;
        this.addDate = addDate;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
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

    public String getTypId() {
        return typId;
    }

    public void setTypId(String typId) {
        this.typId = typId;
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
