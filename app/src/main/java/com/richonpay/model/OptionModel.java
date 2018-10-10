package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class OptionModel {
    @SerializedName("dest_bank_account_id")
    private int destBankAccountID;
    @SerializedName("dest_phone_numer")
    private String destPhoneNumer;
    @SerializedName("bank_id")
    private int bankID;
    private String note;
    private String id1;
    @SerializedName("bill_status")
    private String billStatus;
    @SerializedName("total_os_bill")
    private String totalOsBill;
    @SerializedName("sw_reference_no")
    private String swReferenceNo;
    @SerializedName("subscriberName")
    private String subscriberName;
    @SerializedName("subscriber_segmentation")
    private String subscriberSegmentation;
    @SerializedName("power_consuming_category")
    private String powerConsumingCategory;
    @SerializedName("total_admin_charge")
    private String totalAdminCharge;
    private String sahlwbp1;
    private String sahlwbp2;
    private String sahlwbp3;
    private String sahlwbp4;
    @SerializedName("total_rp_tag")
    private List<String> totalRpTag;
    @SerializedName("customer_id")
    private String customerId;
    @SerializedName("customer_name")
    private String customerName;
    @SerializedName("meter_no")
    private String meterNo;
    @SerializedName("power_rate")
    private String powerRate;
    @SerializedName("ref_no")
    private String refNo;
    private String ref2;
    @SerializedName("admin_charge")
    private double adminCharge;
    @SerializedName("stamp_duty")
    private double stampDuty;
    private double ppn;
    private double ppj;
    private double installment;
    private double pp;
    private double kwh;
    @SerializedName("total_amount")
    private double totalAmount;
    @SerializedName("bill_count")
    private int billCount;
    private String period;
    private double penalty;
    private double amount;
    @SerializedName("phone_number")
    private String phoneNumber;
    private String sn;
    @SerializedName("hide_name")
    private int hideName;
    @SerializedName("stand_meter")
    private String standMeter;
    @SerializedName("pdam_name")
    private String pdamName;
    private List<String> months;
    private double fee;

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public int getDestBankAccountID() {
        return destBankAccountID;
    }

    public void setDestBankAccountID(int destBankAccountID) {
        this.destBankAccountID = destBankAccountID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDestPhoneNumer() {
        return destPhoneNumer;
    }

    public void setDestPhoneNumer(String destPhoneNumer) {
        this.destPhoneNumer = destPhoneNumer;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getBillStatus() {
        return billStatus;
    }

    public void setBillStatus(String billStatus) {
        this.billStatus = billStatus;
    }

    public String getTotalOsBill() {
        return totalOsBill;
    }

    public void setTotalOsBill(String totalOsBill) {
        this.totalOsBill = totalOsBill;
    }

    public String getSwReferenceNo() {
        return swReferenceNo;
    }

    public void setSwReferenceNo(String swReferenceNo) {
        this.swReferenceNo = swReferenceNo;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getSubscriberSegmentation() {
        return subscriberSegmentation;
    }

    public void setSubscriberSegmentation(String subscriberSegmentation) {
        this.subscriberSegmentation = subscriberSegmentation;
    }

    public String getPowerConsumingCategory() {
        return powerConsumingCategory;
    }

    public void setPowerConsumingCategory(String powerConsumingCategory) {
        this.powerConsumingCategory = powerConsumingCategory;
    }

    public String getTotalAdminCharge() {
        return totalAdminCharge;
    }

    public void setTotalAdminCharge(String totalAdminCharge) {
        this.totalAdminCharge = totalAdminCharge;
    }

    public String getSahlwbp1() {
        return sahlwbp1;
    }

    public void setSahlwbp1(String sahlwbp1) {
        this.sahlwbp1 = sahlwbp1;
    }

    public String getSahlwbp2() {
        return sahlwbp2;
    }

    public void setSahlwbp2(String sahlwbp2) {
        this.sahlwbp2 = sahlwbp2;
    }

    public String getSahlwbp3() {
        return sahlwbp3;
    }

    public void setSahlwbp3(String sahlwbp3) {
        this.sahlwbp3 = sahlwbp3;
    }

    public String getSahlwbp4() {
        return sahlwbp4;
    }

    public void setSahlwbp4(String sahlwbp4) {
        this.sahlwbp4 = sahlwbp4;
    }

    public List<String> getTotalRpTag() {
        return totalRpTag;
    }

    public void setTotalRpTag(List<String> totalRpTag) {
        this.totalRpTag = totalRpTag;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMeterNo() {
        return meterNo;
    }

    public void setMeterNo(String meterNo) {
        this.meterNo = meterNo;
    }

    public String getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(String powerRate) {
        this.powerRate = powerRate;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public double getAdminCharge() {
        return adminCharge;
    }

    public void setAdminCharge(double adminCharge) {
        this.adminCharge = adminCharge;
    }

    public double getStampDuty() {
        return stampDuty;
    }

    public void setStampDuty(double stampDuty) {
        this.stampDuty = stampDuty;
    }

    public double getPpn() {
        return ppn;
    }

    public void setPpn(double ppn) {
        this.ppn = ppn;
    }

    public double getPpj() {
        return ppj;
    }

    public void setPpj(double ppj) {
        this.ppj = ppj;
    }

    public double getInstallment() {
        return installment;
    }

    public void setInstallment(double installment) {
        this.installment = installment;
    }

    public double getPp() {
        return pp;
    }

    public void setPp(double pp) {
        this.pp = pp;
    }

    public double getKwh() {
        return kwh;
    }

    public void setKwh(double kwh) {
        this.kwh = kwh;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getBillCount() {
        return billCount;
    }

    public void setBillCount(int billCount) {
        this.billCount = billCount;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public double getPenalty() {
        return penalty;
    }

    public void setPenalty(double penalty) {
        this.penalty = penalty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getHideName() {
        return hideName;
    }

    public void setHideName(int hideName) {
        this.hideName = hideName;
    }

    public String getStandMeter() {
        return standMeter;
    }

    public void setStandMeter(String standMeter) {
        this.standMeter = standMeter;
    }

    public String getRef2() {
        return ref2;
    }

    public void setRef2(String ref2) {
        this.ref2 = ref2;
    }

    public String getPdamName() {
        return pdamName;
    }

    public void setPdamName(String pdamName) {
        this.pdamName = pdamName;
    }

    public List<String> getMonths() {
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }
}
