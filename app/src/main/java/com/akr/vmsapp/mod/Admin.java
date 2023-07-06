package com.akr.vmsapp.mod;

public class Admin {
    private String admId, username, firstname, lastname, surname, countryCode, phone, email, idNumber,
            gender, dob, photoUrl, status, regDate, password;

    public Admin() {
    }

    public Admin(String admId, String username, String firstname, String lastname,
                 String surname, String countryCode, String phone, String email, String idNumber,
                 String gender, String dob, String photoUrl,
                 String status, String regDate, String password) {
        this.admId = admId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.surname = surname;
        this.countryCode = countryCode;
        this.phone = phone;
        this.email = email;
        this.idNumber = idNumber;
        this.gender = gender;
        this.dob = dob;
        this.photoUrl = photoUrl;
        this.status = status;
        this.regDate = regDate;
        this.password = password;
    }

    public String getAdmId() {
        return admId;
    }

    public void setAdmId(String admId) {
        this.admId = admId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }
}
