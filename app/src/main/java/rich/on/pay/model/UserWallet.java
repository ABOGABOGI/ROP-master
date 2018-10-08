package rich.on.pay.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class UserWallet {
    private int id;
    private double balance;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    // 0 PRIMARY
    // 1 SECONDARY
    // 2 POINT
    // 3 FROZEN
    // 4 CREDIT
}