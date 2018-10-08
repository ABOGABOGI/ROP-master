package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;

@Parcel(Parcel.Serialization.BEAN)
public class UpgradeRequest {
    private int id;
    private int status;
    private String code;
    private double price;
    @SerializedName("package")
    private int packageType;
    private OptionModel options;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("expired_at")
    private Date expiredAt;
    private VirtualModel virtual;

    public static final int PENDING = 0;
    public static final int ON_PROCESS = 1;
    public static final int ACCEPTED = 2;
    public static final int DECLINED = 3;
    public static final int CANCELLED = 4;
    public static final int REFUNDED = 5;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPackageType() {
        return packageType;
    }

    public void setPackageType(int packageType) {
        this.packageType = packageType;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public OptionModel getOptions() {
        return options;
    }

    public void setOptions(OptionModel options) {
        this.options = options;
    }

    public VirtualModel getVirtual() {
        return virtual;
    }

    public void setVirtual(VirtualModel virtual) {
        this.virtual = virtual;
    }
}
