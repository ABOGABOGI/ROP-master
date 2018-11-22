package com.richonpay.model;

import org.parceler.Parcel;

import java.io.File;

/**
 * Created by Winardi on 12/2/2017.
 */
@Parcel(Parcel.Serialization.BEAN)
public class PayStore {
    private String channel;
    private String username;
    private int pin;
    private double amount;
    private String note;
    private File image;
    private Integer promoID;
    private User vendor;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Integer getPromoID() {
        return promoID;
    }

    public void setPromoID(Integer promoID) {
        this.promoID = promoID;
    }

    public User getVendor() {
        return vendor;
    }

    public void setVendor(User vendor) {
        this.vendor = vendor;
    }
}
