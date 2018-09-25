package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class VirtualModel {
    @SerializedName("dest_bank_account_id")
    private int destBankAccountID;
    @SerializedName("dest_bank_account")
    private DestinationBankAccount destBankAccount;
    @SerializedName("dest_phone_numer")
    private String destPhoneNumer;
    @SerializedName("bank_account_id")
    private int bankAccountID;
    @SerializedName("bank_account")
    private DestinationBankAccount bankAccount;
    private String note;

    public int getDestBankAccountID() {
        return destBankAccountID;
    }

    public void setDestBankAccountID(int destBankAccountID) {
        this.destBankAccountID = destBankAccountID;
    }

    public DestinationBankAccount getDestBankAccount() {
        return destBankAccount;
    }

    public void setDestBankAccount(DestinationBankAccount destBankAccount) {
        this.destBankAccount = destBankAccount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getBankAccountID() {
        return bankAccountID;
    }

    public void setBankAccountID(int bankAccountID) {
        this.bankAccountID = bankAccountID;
    }

    public DestinationBankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(DestinationBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getDestPhoneNumer() {
        return destPhoneNumer;
    }

    public void setDestPhoneNumer(String destPhoneNumer) {
        this.destPhoneNumer = destPhoneNumer;
    }
}
