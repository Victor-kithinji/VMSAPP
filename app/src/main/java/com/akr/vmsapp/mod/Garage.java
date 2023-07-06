package com.akr.vmsapp.mod;

public class Garage {
    private String grgId, name, countryCode, POBox, box, zipCode, locDesc, town, tel1, tel2, tel3,
            email1, email2, faxNumber, photoUrl, lat, lng, locName, status, addDate;

    public Garage() {
    }

    public Garage(String grgId, String name, String countryCode, String POBox, String box, String zipCode,
                  String locDesc, String town, String tel1, String tel2, String tel3, String email1, String email2,
                  String faxNumber, String photoUrl, String lat, String lng, String locName, String status, String addDate) {
        this.grgId = grgId;
        this.name = name;
        this.countryCode = countryCode;
        this.POBox = POBox;
        this.box = box;
        this.zipCode = zipCode;
        this.locDesc = locDesc;
        this.town = town;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
        this.email1 = email1;
        this.email2 = email2;
        this.faxNumber = faxNumber;
        this.photoUrl = photoUrl;
        this.lat = lat;
        this.lng = lng;
        this.locName = locName;
        this.status = status;
        this.addDate = addDate;
    }

    public String getGrgId() {
        return grgId;
    }

    public void setGrgId(String grgId) {
        this.grgId = grgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPOBox() {
        return POBox;
    }

    public void setPOBox(String POBox) {
        this.POBox = POBox;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLocDesc() {
        return locDesc;
    }

    public void setLocDesc(String locDesc) {
        this.locDesc = locDesc;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getTel3() {
        return tel3;
    }

    public void setTel3(String tel3) {
        this.tel3 = tel3;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
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
