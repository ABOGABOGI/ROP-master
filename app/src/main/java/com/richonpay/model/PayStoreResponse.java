package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Winardi on 12/2/2017.
 */

public class PayStoreResponse {
    private int owner;
    private Date createdt;
    @SerializedName("total_amt")
    private Double totalAmt;
    @SerializedName("received_by")
    private String receivedBy;
    @SerializedName("payment_method")
    private String paymentMethod;
    private int walletlog;
    private int paidstat;
    private String uniqueid;
    private String picture;
    private String note;
    private int id;

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getWalletlog() {
        return walletlog;
    }

    public void setWalletlog(int walletlog) {
        this.walletlog = walletlog;
    }

    public int getPaidstat() {
        return paidstat;
    }

    public void setPaidstat(int paidstat) {
        this.paidstat = paidstat;
    }

    public String getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid) {
        this.uniqueid = uniqueid;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreatedt() {
        return createdt;
    }

    public void setCreatedt(Date createdt) {
        this.createdt = createdt;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
}
