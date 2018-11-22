package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PaymentProductResponse {

    @SerializedName("expired_at")
    private Date expiredAt;
    private int type;
    @SerializedName("referable_id")
    private int referableId;
    @SerializedName("referable_type")
    private String referableType;
    @SerializedName("reference_number")
    private String referenceNumber;
    @SerializedName("ownable_id")
    private int ownableId;
    @SerializedName("ownable_type")
    private String ownableType;
    private PLNOption options;
    private int status;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("created_at")
    private Date createdAt;
    private PLNOption virtual;


    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReferableId() {
        return referableId;
    }

    public void setReferableId(int referableId) {
        this.referableId = referableId;
    }

    public String getReferableType() {
        return referableType;
    }

    public void setReferableType(String referableType) {
        this.referableType = referableType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getOwnableId() {
        return ownableId;
    }

    public void setOwnableId(int ownableId) {
        this.ownableId = ownableId;
    }

    public String getOwnableType() {
        return ownableType;
    }

    public void setOwnableType(String ownableType) {
        this.ownableType = ownableType;
    }

    public PLNOption getOptions() {
        return options;
    }

    public void setOptions(PLNOption options) {
        this.options = options;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public PLNOption getVirtual() {
        return virtual;
    }

    public void setVirtual(PLNOption virtual) {
        this.virtual = virtual;
    }
}
