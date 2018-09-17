package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)

public class VerificationStatus {
    @SerializedName("phone_number")
    private boolean phoneNumber;
    private boolean email;
    private int nric;
    private int selfie;

//    const PENDING = 0;
//    const VERIFIED = 1;
//    const DECLINED = 2;

    public boolean isPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(boolean phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public int getNric() {
        return nric;
    }

    public void setNric(int nric) {
        this.nric = nric;
    }

    public int getSelfie() {
        return selfie;
    }

    public void setSelfie(int selfie) {
        this.selfie = selfie;
    }
}