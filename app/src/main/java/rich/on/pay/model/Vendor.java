package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.Date;
import java.util.List;

@Parcel(Parcel.Serialization.BEAN)
public class Vendor {
    private int id;
    @SerializedName("authorized_by")
    private int authorizedBy;
    @SerializedName("picture_id")
    private int pictureId;
    @SerializedName("cover_id")
    private int coverId;
    private String name;
    private String city;
    private String tagline;
    @SerializedName("phone_number")
    private String phoneNumber;
    @SerializedName("slug_url")
    private String slugUrl;
    @SerializedName("force_close")
    private int forceClose;
    private int type;
    @SerializedName("head_branch_id")
    private int headBranchId;
    private double lat;
    private double lng;
    @SerializedName("delete_reason")
    private String deleteReason;
    @SerializedName("deleted_at")
    private Date deletedAt;
    @SerializedName("created_at")
    private Date createdAt;
    @SerializedName("updated_at")
    private Date updatedAt;
    @SerializedName("cover_url")
    private String coverUrl;
    @SerializedName("picture_url")
    private String pictureUrl;
    private Cover cover;
    private Cover picture;
    private String address;
    private String distance;
    private UserBalance balances;
    @SerializedName("business_reviews")
    private List<BusinessReview> businessReviews;
    @SerializedName("business_operations")
    private List<BusinessOperationHour> businessOperationHours;
    private List<Voucher> vouchers;
    private BusinessSocialLink socmed;
    private List<VendorGallery> galleries;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorizedBy() {
        return authorizedBy;
    }

    public void setAuthorizedBy(int authorizedBy) {
        this.authorizedBy = authorizedBy;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public int getCoverId() {
        return coverId;
    }

    public void setCoverId(int coverId) {
        this.coverId = coverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSlugUrl() {
        return slugUrl;
    }

    public void setSlugUrl(String slugUrl) {
        this.slugUrl = slugUrl;
    }

    public int getForceClose() {
        return forceClose;
    }

    public void setForceClose(int forceClose) {
        this.forceClose = forceClose;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getHeadBranchId() {
        return headBranchId;
    }

    public void setHeadBranchId(int headBranchId) {
        this.headBranchId = headBranchId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getDeleteReason() {
        return deleteReason;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Cover getCover() {
        return cover;
    }

    public void setCover(Cover cover) {
        this.cover = cover;
    }

    public Cover getPicture() {
        return picture;
    }

    public void setPicture(Cover picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BusinessReview> getBusinessReviews() {
        return businessReviews;
    }

    public void setBusinessReviews(List<BusinessReview> businessReviews) {
        this.businessReviews = businessReviews;
    }

    public List<BusinessOperationHour> getBusinessOperationHours() {
        return businessOperationHours;
    }

    public void setBusinessOperationHours(List<BusinessOperationHour> businessOperationHours) {
        this.businessOperationHours = businessOperationHours;
    }

    public List<Voucher> getVouchers() {
        return vouchers;
    }

    public void setVouchers(List<Voucher> vouchers) {
        this.vouchers = vouchers;
    }

    public BusinessSocialLink getSocmed() {
        return socmed;
    }

    public void setSocmed(BusinessSocialLink socmed) {
        this.socmed = socmed;
    }

    public UserBalance getBalances() {
        return balances;
    }

    public void setBalances(UserBalance balances) {
        this.balances = balances;
    }

    public List<VendorGallery> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<VendorGallery> galleries) {
        this.galleries = galleries;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
