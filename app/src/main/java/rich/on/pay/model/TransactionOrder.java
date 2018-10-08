package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class TransactionOrder {
    private int id;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("expired_at")
    private Date expiredAt;
    @SerializedName("referable_id")
    private int referableID;
    @SerializedName("referable_type")
    private String referableType;
    @SerializedName("reference_number")
    private String referenceNumber;
    @SerializedName("ownable_id")
    private int ownableID;
    @SerializedName("ownable_type")
    private String ownableType;
    private OptionModel options;
    private VirtualModel virtual;
    @SerializedName("order_details")
    private List<TransactionOrderDetail> orderDetails;
    private Vendor vendor;
    private Vendor referable;
    private User user;
    private User admin;
    private Banner promo;
    @SerializedName("total_before_discount")
    private double totalBeforeDiscount;
    private double total;

    //    0: Topup, 1: Withdraw
    private int type;
    //    Status: PENDING = 0; WAITING_FOR_PAYMENT = 1; ONGOING = 2; COMPLETED = 3; EXPIRED = 4; CANCELLED = 5; DECLINED = 6;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getReferableID() {
        return referableID;
    }

    public void setReferableID(int referableID) {
        this.referableID = referableID;
    }

    public String getReferableType() {
        return referableType;
    }

    public void setReferableType(String referableType) {
        this.referableType = referableType;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public int getOwnableID() {
        return ownableID;
    }

    public void setOwnableID(int ownableID) {
        this.ownableID = ownableID;
    }

    public String getOwnableType() {
        return ownableType;
    }

    public void setOwnableType(String ownableType) {
        this.ownableType = ownableType;
    }

    public OptionModel getOptions() {
        return options;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<TransactionOrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<TransactionOrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public VirtualModel getVirtual() {
        return virtual;
    }

    public void setVirtual(VirtualModel virtual) {
        this.virtual = virtual;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Vendor getReferable() {
        return referable;
    }

    public void setReferable(Vendor referable) {
        this.referable = referable;
    }

    public Banner getPromo() {
        return promo;
    }

    public void setPromo(Banner promo) {
        this.promo = promo;
    }

    public void setOptions(OptionModel options) {
        this.options = options;
    }

    public double getTotalBeforeDiscount() {
        return totalBeforeDiscount;
    }

    public void setTotalBeforeDiscount(double totalBeforeDiscount) {
        this.totalBeforeDiscount = totalBeforeDiscount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
