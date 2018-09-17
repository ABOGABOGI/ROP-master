package rich.on.pay.model;

/**
 * Created by teddysantya on 22/03/18.
 */

public class APIResponse {
    private String message;
    private APIModels data;
    private String type;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public APIModels getData() {
        return data;
    }

    public void setData(APIModels data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
