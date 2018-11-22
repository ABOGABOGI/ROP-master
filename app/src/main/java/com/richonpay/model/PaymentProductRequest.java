package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

public class PaymentProductRequest {
    private String pin;
    @SerializedName("wallet_type")
    private int walletType;
    @SerializedName("payment_product_id")
    private int paymentProductID;
    @SerializedName("phone_number")
    private String phoneNumber;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public int getPaymentProductID() {
        return paymentProductID;
    }

    public void setPaymentProductID(int paymentProductID) {
        this.paymentProductID = paymentProductID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
