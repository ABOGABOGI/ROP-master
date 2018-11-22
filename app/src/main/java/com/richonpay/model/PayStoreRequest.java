package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

public class PayStoreRequest {
    @SerializedName("user_id")
    private String userID;
    private double amount;
    @SerializedName("amount_after_discount")
    private Double amountAfterDiscount;
    @SerializedName("voucher_id")
    private Integer voucherId;
    @SerializedName("wallet_type")
    private int walletType;
    private String pin;
    private String note;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Double getAmountAfterDiscount() {
        return amountAfterDiscount;
    }

    public void setAmountAfterDiscount(Double amountAfterDiscount) {
        this.amountAfterDiscount = amountAfterDiscount;
    }

    public Integer getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(Integer voucherId) {
        this.voucherId = voucherId;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
