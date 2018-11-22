package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(Parcel.Serialization.BEAN)
public class TransactionOrderDetail {
    private int id;
    @SerializedName("order_id")
    private int orderID;
    private int qty;
    private double price;
    private double total;
    private double discount;
    @SerializedName("total_amount")
    private double totalAmount;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("expired_at")
    private Date expiredAt;
    @SerializedName("transaction_type")
    private int transactionType; //0=TERIMA  1=KELUAR
    private String reference;
    private String reference_number;
    @SerializedName("reference_phone_number")
    private String referencePhoneNumber;
    private int type;
    private TransactionOrder order;
    @SerializedName("wallet_type")  //  0 Primary  2 Point
    private int walletType;
    @SerializedName("related_reference_number")
    private String relatedReferenceNumber;
    @SerializedName("referable_type")
    private String referableType;
    private int status;
    @SerializedName("total_before_discount")
    private double totalBeforeDiscount;
    @SerializedName("combined_serial_number")
    private String combinedSerialNumber;
    @SerializedName("payment_product_amount")
    private double paymentProductAmount;
    @SerializedName("payment_product_fee")
    private double paymentProductFee;
    private ProductPay product;
    @SerializedName("sub_total")
    private double subTotal;

//     PAY = 0;
//     SPLIT_PAY = 1;
//     TRANSFER = 2;
//     TOPUP = 3;
//     TOPUP_CREDIT = 4;
//     WITHDRAW = 5;
//     CASHBACK = 6;
//     REFUND = 7;
//     PULSA = 8;
//     PLN = 9;
//     GAME = 10;
//     FREEZE = 11;
//     UNFREEZE = 12;
//     BONUS = 13;
//     WITHDRAW_CREDIT = 14;
//     BONUS_REFERRAL = 15;
//     REVERSAL = 16;
//     PDAM = 17;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public TransactionOrder getOrder() {
        return order;
    }

    public void setOrder(TransactionOrder order) {
        this.order = order;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getReference_number() {
        return reference_number;
    }

    public void setReference_number(String reference_number) {
        this.reference_number = reference_number;
    }

    public int getWalletType() {
        return walletType;
    }

    public void setWalletType(int walletType) {
        this.walletType = walletType;
    }

    public String getRelatedReferenceNumber() {
        return relatedReferenceNumber;
    }

    public void setRelatedReferenceNumber(String relatedReferenceNumber) {
        this.relatedReferenceNumber = relatedReferenceNumber;
    }

    public String getReferableType() {
        return referableType;
    }

    public void setReferableType(String referableType) {
        this.referableType = referableType;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public void setTotalBeforeDiscount(double totalBeforeDiscount) {
        this.totalBeforeDiscount = totalBeforeDiscount;
    }

    public String getReferencePhoneNumber() {
        return referencePhoneNumber;
    }

    public void setReferencePhoneNumber(String referencePhoneNumber) {
        this.referencePhoneNumber = referencePhoneNumber;
    }

    public String getCombinedSerialNumber() {
        return combinedSerialNumber;
    }

    public void setCombinedSerialNumber(String combinedSerialNumber) {
        this.combinedSerialNumber = combinedSerialNumber;
    }

    public double getPaymentProductAmount() {
        return paymentProductAmount;
    }

    public void setPaymentProductAmount(double paymentProductAmount) {
        this.paymentProductAmount = paymentProductAmount;
    }

    public double getPaymentProductFee() {
        return paymentProductFee;
    }

    public void setPaymentProductFee(double paymentProductFee) {
        this.paymentProductFee = paymentProductFee;
    }

    public ProductPay getProduct() {
        return product;
    }

    public void setProduct(ProductPay product) {
        this.product = product;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getSubTotal() {
        return subTotal;
    }
}
