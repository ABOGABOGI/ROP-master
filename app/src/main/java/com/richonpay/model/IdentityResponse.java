package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class IdentityResponse {
    private int id;
    private int type;
    @SerializedName("ownable_id")
    private int ownableID;
    private String description;
    @SerializedName("identification_number")
    private String identificationNumber;
    @SerializedName("attachment_id")
    private int attachmentID;
    @SerializedName("attachment_url")
    private String attachmentUrl;
    @SerializedName("is_verified")
    private boolean isVerified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getOwnableID() {
        return ownableID;
    }

    public void setOwnableID(int ownableID) {
        this.ownableID = ownableID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public int getAttachmentID() {
        return attachmentID;
    }

    public void setAttachmentID(int attachmentID) {
        this.attachmentID = attachmentID;
    }

    public String getAttachmentUrl() {
        return attachmentUrl;
    }

    public void setAttachmentUrl(String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}
