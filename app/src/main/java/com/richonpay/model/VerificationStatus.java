package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)

public class VerificationStatus {
    @SerializedName("phone_number")
    private boolean phoneNumber;
    private boolean email;
    private int nric;
    private int selfie;

    public static final int PENDING = 0;
    public static final int VERIFIED = 1;
    public static final int DEFAULT = 2; // NOT UPLOADED YET / REJECT

    public boolean isPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(boolean phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public int getNric() {
        return nric;
    }

    public void setNric(int nric) {
        this.nric = nric;
    }

    public int getSelfie() {
        return selfie;
    }

    public void setSelfie(int selfie) {
        this.selfie = selfie;
    }
}