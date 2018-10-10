package com.richonpay.model;

import java.util.List;

/**
 * Created by Winardi on 9/27/2017.
 */

public class VendorList {

    private int key;
    private String name;
    private int type;
    private List<User> data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getData() {
        return data;
    }

    public void setData(List<User> data) {
        this.data = data;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
