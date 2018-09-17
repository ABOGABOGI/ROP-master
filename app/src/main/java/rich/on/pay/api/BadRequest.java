package rich.on.pay.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tommy on 7/11/2016.
 */

public class BadRequest {
    @SerializedName("message")
    public final String errorDetails;
    @SerializedName("code")
    public int code;

    public BadRequest(String message) {
        this.errorDetails = message;
    }
}
