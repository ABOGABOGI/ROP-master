package rich.on.pay.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Winardi on 8/21/2017.
 */

public class DeviceToken {
    private String brand;
    private String model;
    private String version;
    private String os;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("device_type")
    private String deviceType;


    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getToken() {
        return deviceToken;
    }

    public void setToken(String token) {
        this.deviceToken = token;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
