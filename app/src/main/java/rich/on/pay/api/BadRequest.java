package rich.on.pay.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tommy on 7/11/2016.
 */

public class BadRequest<M> {
    @SerializedName("message")
    public final String errorDetails;
    @SerializedName("code")
    public int code;
    public JsonObject errors;

    public BadRequest(int code, String message) {
        this.code = code;
        this.errorDetails = message;
    }

    public BadRequest(String message) {
        this.errorDetails = message;
    }

    public JsonObject getErrors() {
        return errors;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setErrors(JsonObject errors) {
        this.errors = errors;
    }
}
