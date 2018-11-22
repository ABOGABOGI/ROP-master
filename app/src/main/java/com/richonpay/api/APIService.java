package com.richonpay.api;

import com.richonpay.model.APIResponse;
import com.richonpay.model.BankAccount;
import com.richonpay.model.PaymentProductBody;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @GET("v1/user/me")
    Call<APIResponse> getProfile();

    @GET("v1/bank")
    Call<APIResponse> getSupportedBank();

    @GET("v1/bank-account")
    Call<APIResponse> getUserBankAccount();

    @GET("v1/balance/topup-list")
    Call<APIResponse> getTopupAmountList();

    @GET("v1/bank-account/payment")
    Call<APIResponse> getPaymentBankAccount();

    @GET("v1/home")
    Call<APIResponse> getHomeBanner();

    @GET("v1/package/history")
    Call<APIResponse> getPackageHistory();

    //////////////////////////  POST  //////////////////////////
    @POST("v1/login")
    Call<APIResponse> login(@Body RequestBody requestBody);

    @POST("v1/logout")
    Call<APIResponse> logout();

    @POST("v1/register")
    Call<APIResponse> register(@Body RequestBody requestBody);

    @POST("v1/refresh")
    Call<APIResponse> refreshToken();

    @POST("v1/user/resend-otp")
    Call<APIResponse> requestOTP(@Body RequestBody requestBody);

    @POST("v1/user/verify-phone-number")
    Call<APIResponse> verifyPhone(@Body RequestBody requestBody);

    @POST("v1/forgot-password")
    Call<APIResponse> forgotPassword(@Body RequestBody requestBody);

    @POST("v1/user/check-password")
    Call<APIResponse> checkPassword(@Body RequestBody requestBody);

    @POST("v1/user/change-password")
    Call<APIResponse> changePassword(@Body RequestBody requestBody);

    @POST("v1/package/upgrade")
    Call<APIResponse> requestPackage(@Body RequestBody requestBody);

    @POST("v1/package/upgrade/{orderID}/pay")
    Call<APIResponse> buyPackage(@Path("orderID") int bankID, @Body RequestBody requestBody);

    @POST("v1/package/upgrade/{orderID}/confirm-payment")
    Call<APIResponse> confirmPackagePayment(@Path("orderID") int orderID);

    @POST("v1/package/upgrade/{orderID}/cancel")
    Call<APIResponse> cancelPackagePayment(@Path("orderID") int orderID);

    @POST("v1/bank-account/create")
    Call<APIResponse> addBankAccount(@Body BankAccount requestBody);

    @POST("v1/user/change-phone-number")
    Call<APIResponse> changePhone(@Body RequestBody requestBody);

    @POST("v1/user/update-phone-number")
    Call<APIResponse> updatePhone(@Body RequestBody requestBody);

    @POST("v1/user/change-email")
    Call<APIResponse> changeEmail(@Body RequestBody requestBody);

    @POST("v1/user/update-email")
    Call<APIResponse> updateEmail(@Body RequestBody requestBody);

    @POST("v1/bank-account/{bankID}/set-primary")
    Call<APIResponse> setPrimaryBank(@Path("bankID") int bankID);

    @POST("v1/bank-account/{bankID}/remove")
    Call<APIResponse> removeBankAccount(@Path("bankID") int bankID);

    @POST("v1/user/upload-identity")
    Call<APIResponse> uploadUserVerification(@Body RequestBody requestBody);

    @GET("v1/payment/{id}")
    Call<APIResponse> getPaymentProductByType(@Path("id") int typeId);

    @POST("v1/payment/{id}/payment")
    Call<APIResponse> payProduct(@Path("id") int category_id, @Body PaymentProductBody paymentProductBody);

    @GET("v1/payment/{type_id}/{cat_id}")
    Call<APIResponse> getPaymentProductByCategory(@Path("type_id") int typeId, @Path("cat_id") int catId);

    @POST("v1/payment/{id}/inquiry")
    Call<APIResponse> inquiryPLN(@Path("id") int id, @Body PaymentProductBody paymentProductBody);

    @POST("v1/payment/{id}/payment")
    Call<APIResponse> paymentPLN(@Path("id") int id, @Body PaymentProductBody paymentProductBody);

    @POST("v1/balance/topup")
    Call<APIResponse> requestTopup(@Body RequestBody requestBody);

    @POST("v1/balance/topup/{orderID}/paid")
    Call<APIResponse> confirmTopup(@Path("orderID") int orderID);

    @POST("v1/balance/topup/{orderID}/cancel")
    Call<APIResponse> cancelTopup(@Path("orderID") int orderID);
}
