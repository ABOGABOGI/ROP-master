package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class BankAccount {
    private int id;
    @SerializedName("account_name")
    private String accountName;
    @SerializedName("account_no")
    private String accountNo;
    @SerializedName("ownable_type")
    private String ownableType;
    @SerializedName("bank_id")
    private int bankID;
    private String branch;
    @SerializedName("is_primary")
    private int isPrimary;
    private BankInfo bank;

    public BankAccount() {
    }

    public BankAccount(int bankID, String accountName, String accountNo, int isPrimary) {
        this.bankID = bankID;
        this.accountName = accountName;
        this.accountNo = accountNo;
        this.isPrimary = isPrimary;
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

    public String getOwnableType() {
        return ownableType;
    }

    public void setOwnableType(String ownableType) {
        this.ownableType = ownableType;
    }

    public int getBankID() {
        return bankID;
    }

    public void setBankID(int bankID) {
        this.bankID = bankID;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
