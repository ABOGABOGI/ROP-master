package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by Winardi on 12/9/2017.
 */
@Parcel(Parcel.Serialization.BEAN)
public class BankInfo {
    private int id;
    private String name;
    //    private String logo;
    private int code;
    private String abbr;
    @SerializedName("logo_url")
    private String logoUrl;

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

//    public String getLogo() {
//        return logo;
//    }
//
//    public void setLogo(String logo) {
//        this.logo = logo;
//    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}
