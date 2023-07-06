package com.akr.vmsapp.mod;

public class Rating {
    private String ratId, cliId, gomId, comments, addDate;
    private float stars;

    public Rating() {
    }

    public Rating(String ratId, String cliId, String gomId, float stars, String comments, String addDate) {
        this.ratId = ratId;
        this.cliId = cliId;
        this.gomId = gomId;
        this.stars = stars;
        this.comments = comments;
        this.addDate = addDate;
    }

    public String getRatId() {
        return ratId;
    }

    public void setRatId(String ratId) {
        this.ratId = ratId;
    }

    public String getCliId() {
        return cliId;
    }

    public void setCliId(String cliId) {
        this.cliId = cliId;
    }

    public String getGomId() {
        return gomId;
    }

    public void setGomId(String gomId) {
        this.gomId = gomId;
    }

    public float getStars() {
        return stars;
    }

    public void setStars(float stars) {
        this.stars = stars;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }
}
