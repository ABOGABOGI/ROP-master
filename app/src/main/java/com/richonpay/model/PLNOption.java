package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

public class PLNOption {

    @SerializedName("ref_num")
    private String refNum;
    @SerializedName("id_pelanggan")
    private String idPelanggan;
    @SerializedName("nama_pelanggan")
    private String namaPelanggan;
    private String biaya;
    private double total;
    @SerializedName("biaya_agen")
    private double biayaAgen;
    private String receipt;
    @SerializedName("id_inquiry")
    private int idInquiry;

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getNamaPelanggan() {
        return namaPelanggan;
    }

    public void setNamaPelanggan(String namaPelanggan) {
        this.namaPelanggan = namaPelanggan;
    }

    public String getBiaya() {
        return biaya;
    }

    public void setBiaya(String biaya) {
        this.biaya = biaya;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getBiayaAgen() {
        return biayaAgen;
    }

    public void setBiayaAgen(double biayaAgen) {
        this.biayaAgen = biayaAgen;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public int getIdInquiry() {
        return idInquiry;
    }

    public void setIdInquiry(int idInquiry) {
        this.idInquiry = idInquiry;
    }
}
