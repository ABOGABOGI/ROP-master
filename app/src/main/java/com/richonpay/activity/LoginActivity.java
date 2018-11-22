package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.BaseActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.utils.Extension;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        SpannableString spannableString = new SpannableString(tvRegister.getText());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 18, tvRegister.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegister.setText(spannableString);
    }

    @OnClick(R.id.tvForgotPassword)
    void forgotPassword() {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }

    @OnClick(R.id.btnLogin)
    void login() {
        btnLogin.setEnabled(false);

        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(LoginActivity.this);
            }
        });
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("email", (etEmail.getText().toString().contains("@") ? etEmail.getText().toString() : Extension.validatePhoneNumber(etEmail.getText().toString())));
        buildernew.addFormDataPart("password", etPassword.getText().toString());
        final MultipartBody requestBody = buildernew.build();

        API.service().login(requestBody).enqueue(new APICallback<APIResponse>(LoginActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                API.setToken(response.getData().getToken());
                API.setUser(response.getData().getUser());
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                btnLogin.setEnabled(true);

                if (API.currentUser().getVerificationStatus().isPhoneNumber()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Intent intent = new Intent(LoginActivity.this, VerifyOTPActivity.class);
                    intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[3]);
                    startActivity(intent);
                }
            }

            @Override
            protected void onError(BadRequest error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                btnLogin.setEnabled(true);

                if (error.code == 400) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                    alertDialog.setTitle(getString(R.string.sorry));
                    alertDialog.setMessage(error.errorDetails);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                } else {
                    try {
                        Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry : entries) {
                            if (entry.getKey().matches("email")) {
                                etEmail.setError(entry.getValue().getAsString());
                            }
                            if (entry.getKey().matches("password")) {
                                etPassword.setError(entry.getValue().getAsString());
                            }
                        }

                    } catch (Exception exception) {
                        Log.e("loginAPI", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                }
            }
        });
    }

    @OnClick(R.id.tvRegister)
    void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        API.logOut();
    }
}
