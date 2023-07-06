package com.akr.vmsapp.mod;

public class Country {
    private String countryId, name, isoCode2, isoCode3, phonePrefix, capitalCity, currency, addDate;

    public Country() {
    }

    public Country(String countryId, String name, String isoCode2, String isoCode3,
                   String phonePrefix, String capitalCity, String currency, String addDate) {
        this.countryId = countryId;
        this.name = name;
        this.isoCode2 = isoCode2;
        this.isoCode3 = isoCode3;
        this.phonePrefix = phonePrefix;
        this.capitalCity = capitalCity;
        this.currency = currency;
        this.addDate = addDate;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsoCode2() {
        return isoCode2;
    }

    public void setIsoCode2(String isoCode2) {
        this.isoCode2 = isoCode2;
    }

    public String getIsoCode3() {
        return isoCode3;
    }

    public void setIsoCode3(String isoCode3) {
        this.isoCode3 = isoCode3;
    }

    public String getPhonePrefix() {
        return phonePrefix;
    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public void setCapitalCity(String capitalCity) {
        this.capitalCity = capitalCity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
