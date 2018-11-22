package com.richonpay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Winardi on 3/26/2018.
 */
@Parcel(Parcel.Serialization.BEAN)
public class ExploreCategory {

    // EXPLORE
    private int id;
    private String name;
    @SerializedName("cover_url")
    private String coverUrl;

    // PAYMENT PRODUCT
    private List<String> data = new ArrayList<>();
    private String category;
    @SerializedName("picture_url")
    private String pictureUrl;
    private List<PaymentProduct> paymentProducts = new ArrayList<>();
    private int view;

    //WATERBILL
    @SerializedName("payment_products")
    private List<PaymentProduct> paymentProduct;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public List<PaymentProduct> getPaymentProducts() {
        return paymentProducts;
    }

    public void setPaymentProducts(List<PaymentProduct> paymentProducts) {
        this.paymentProducts = paymentProducts;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public List<PaymentProduct> getPaymentProduct() {
        return paymentProduct;
    }

    public void setPaymentProduct(List<PaymentProduct> paymentProduct) {
        this.paymentProduct = paymentProduct;
    }
}
