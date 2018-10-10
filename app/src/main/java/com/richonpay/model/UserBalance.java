package com.richonpay.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class UserBalance {

    private double primary;
    private double secondary;
    private double point;
    private double frozen;

    public double getPrimary() {
        return primary;
    }

    public void setPrimary(double primary) {
        this.primary = primary;
    }

    public double getSecondary() {
        return secondary;
    }

    public void setSecondary(double secondary) {
        this.secondary = secondary;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public double getFrozen() {
        return frozen;
    }

    public void setFrozen(double frozen) {
        this.frozen = frozen;
    }
}
