package com.richonpay.model;

import org.parceler.Parcel;

/**
 * Created by Winardi on 2/3/2018.
 */

@Parcel(Parcel.Serialization.BEAN)
public class VendorPicture {
    private int id;
    private String url;
    private Integer urut;

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

    public Integer getUrut() {
        return urut;
    }

    public void setUrut(Integer urut) {
        this.urut = urut;
    }
}
