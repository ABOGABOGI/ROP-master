package rich.on.pay.model;

import org.parceler.Parcel;

@Parcel(Parcel.Serialization.BEAN)
public class BusinessSocialLink {
    private String facebook;
    private String instagram;

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }
}
