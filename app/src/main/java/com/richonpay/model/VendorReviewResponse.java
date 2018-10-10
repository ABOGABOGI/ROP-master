package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by winardisusanto on 30/12/17.
 */

public class VendorReviewResponse {
    private int id;
    @SerializedName("review_by")
    private User reviewBy;
    private float rating;
    private String content;
    private Date gendt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(User reviewBy) {
        this.reviewBy = reviewBy;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getGendt() {
        return gendt;
    }

    public void setGendt(Date gendt) {
        this.gendt = gendt;
    }
}
