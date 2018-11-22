package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

public class PaymentProductBody {

    @SerializedName("payment_product_id")
    private Integer paymentProductId;
    @SerializedName("customer_phone_number")
    private String customerPhoneNumber;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("biller_code")
    private String billerCode;
    @SerializedName("customer_id")
    private String customerId;
    private double amount;
    @SerializedName("payment_inquiry_id")
    private Integer paymentInquiryId;
    private String password;
    @SerializedName("wallet_type")
    private int walletType;
    @SerializedName("order_id")
    private String orderId;
    @SerializedName("inquiry_type")
    private Integer inquiryType;

    public Integer getPaymentProductId() {
        return paymentProductId;
    }

    public void setPaymentProductId(Integer paymentProductId) {
        this.paymentProductId = paymentProductId;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public String getBillerCode() {
        return billerCode;
    }

    public void setBillerCode(String billerCode) {
        this.billerCode = billerCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getPaymentInquiryId() {
        return paymentInquiryId;
    }

    public void setPaymentInquiryId(Integer paymentInquiryId) {
        this.paymentInquiryId = paymentInquiryId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getInquiryType() {
        return inquiryType;
    }

    public void setInquiryType(Integer inquiryType) {
        this.inquiryType = inquiryType;
    }
}
