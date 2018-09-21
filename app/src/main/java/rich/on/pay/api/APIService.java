package rich.on.pay.api;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rich.on.pay.model.APIResponse;

public interface APIService {

    @GET("v1/user/me")
    Call<APIResponse> getProfile();

    //////////////////////////  POST  //////////////////////////
    @POST("v1/login")
    Call<APIResponse> login(@Body RequestBody requestBody);

    @POST("v1/register")
    Call<APIResponse> register(@Body RequestBody requestBody);
}
