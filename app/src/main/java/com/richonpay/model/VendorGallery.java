package com.richonpay.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class VendorGallery {
    private int id;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
