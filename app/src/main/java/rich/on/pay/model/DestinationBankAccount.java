package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class DestinationBankAccount {
    private int id;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account_no")
    private String accountNo;
    @SerializedName("ownable_type")
    private String ownable_type;
    @SerializedName("bank_id")
    private int bankID;
    @SerializedName("is_primary")
    private int isPrimary;
    private BankInfo bank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getOwnable_type() {
        return ownable_type;
    }

    public void setOwnable_type(String ownable_type) {
        this.ownable_type = ownable_type;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public int getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(int isPrimary) {
        this.isPrimary = isPrimary;
    }

    public BankInfo getBank() {
        return bank;
    }

    public void setBank(BankInfo bank) {
        this.bank = bank;
    }
}
