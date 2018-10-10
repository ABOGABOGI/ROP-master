package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class BusinessOperationHour {
    @SerializedName("operation_day")
    private int operationDay;
    @SerializedName("operation_open")
    private String operationOpen;
    @SerializedName("operation_close")
    private String operationClose;
    private String filteredDayName;
    private List<Integer> filteredDay = new ArrayList<>();

    public BusinessOperationHour() {

    }

    public BusinessOperationHour(String operationOpen, String operationClose) {
        this.operationOpen = operationOpen;
        this.operationClose = operationClose;
    }

    public BusinessOperationHour(String operationOpen, String operationClose, int operationDay) {
        this.operationOpen = operationOpen;
        this.operationClose = operationClose;
        this.operationDay = operationDay;
    }

    public String getOperationOpen() {
        return operationOpen;
    }

    public void setOperationOpen(String operationOpen) {
        this.operationOpen = operationOpen;
    }

    public String getOperationClose() {
        return operationClose;
    }

    public void setOperationClose(String operationClose) {
        this.operationClose = operationClose;
    }

    public int getOperationDay() {
        return operationDay;
    }

    public void setOperationDay(int operationDay) {
        this.operationDay = operationDay;
    }

    public void addFilteredDay(int day) {
        if (this.filteredDay == null) {
            this.filteredDay = new ArrayList<>();
        }
        this.filteredDay.add(day);
    }

    public String getFilteredDayName() {
        return filteredDayName;
    }

    public void setFilteredDayName(String filteredDayName) {
        this.filteredDayName = filteredDayName;
    }

    public List<Integer> getFilteredDay() {
        return filteredDay;
    }

    public void setFilteredDay(List<Integer> filteredDay) {
        this.filteredDay = filteredDay;
    }

    public String getTimeID() {
        return this.operationOpen + operationClose;
    }
}
