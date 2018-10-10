package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by teddysantya on 21/03/18.
 */

public class TopUpRequest {

    private double amount;
    @SerializedName("dest_bank_account_id")
    private int bankID;

    public TopUpRequest(int amount) {
        this.amount = amount;
    }

    public TopUpRequest(double amount, int bankID) {
        this.amount = amount;
        this.bankID = bankID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }
}
