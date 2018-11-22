package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.utils.Extension;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class RegisterActivity extends ToolbarActivity {

    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPhone)
    TextInputEditText etPhone;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etConfirmPassword)
    TextInputEditText etConfirmPassword;
    @BindView(R.id.etLeaderCode)
    TextInputEditText etLeaderCode;
    @BindView(R.id.etSponsorCode)
    TextInputEditText etSponsorCode;
    @BindView(R.id.cbTerms)
    CheckBox cbTerms;
    @BindView(R.id.btnRegister)
    Button btnRegister;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewCreated() {

    }

    @OnClick(R.id.btnRegister)
    void register() {
        try {
            btnRegister.setEnabled(false);
            if (etName.getText().toString().length() < 3) {
                showError(getString(R.string.please_insert_valid_name));
                btnRegister.setEnabled(true);
                return;
            }

            if (!Extension.isValidEmail(etEmail.getText().toString())) {
                showError(getString(R.string.please_insert_valid_email));
                btnRegister.setEnabled(true);
                return;
            }

            if (etPhone.getText().toString().length() < 10) {
                showError(getString(R.string.please_insert_valid_phone));
                btnRegister.setEnabled(true);
                return;
            }

            if (etPassword.getText().toString().length() < 6) {
                Toast.makeText(this, R.string.please_insert_valid_password, Toast.LENGTH_SHORT).show();
                btnRegister.setEnabled(true);
                return;
            } else if (etConfirmPassword.getText().toString().length() < 6) {
                Toast.makeText(this, R.string.please_insert_valid_password, Toast.LENGTH_SHORT).show();
                btnRegister.setEnabled(true);
                return;
            } else if (!etConfirmPassword.getText().toString().matches(etPassword.getText().toString())) {
                Toast.makeText(this, R.string.passwords_dont_match, Toast.LENGTH_SHORT).show();
                btnRegister.setEnabled(true);
                return;
            }

            if (!cbTerms.isChecked()) {
                Toast.makeText(this, R.string.you_must_agree_to_the_terms_and_condition, Toast.LENGTH_SHORT).show();
                btnRegister.setEnabled(true);
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.showLoading(RegisterActivity.this);
                    }
                });
                MultipartBody.Builder buildernew = new MultipartBody.Builder();
                buildernew.setType(MultipartBody.FORM);

                String nameArray[] = etName.getText().toString().split(" ", 2);
                buildernew.addFormDataPart("first_name", ((nameArray.length == 2) ? nameArray[0] : etName.getText().toString()));
                buildernew.addFormDataPart("last_name", ((nameArray.length == 2) ? nameArray[1] : ""));
                buildernew.addFormDataPart("phone_number", Extension.validatePhoneNumber(etPhone.getText().toString()));
                buildernew.addFormDataPart("email", etEmail.getText().toString());
                buildernew.addFormDataPart("password", etPassword.getText().toString());
                buildernew.addFormDataPart("password_confirmation", etConfirmPassword.getText().toString());

                buildernew.addFormDataPart("has_referral", (etLeaderCode.getText().length() > 0 || etSponsorCode.getText().toString().length() > 0
                        ? "1" : "0"));
                buildernew.addFormDataPart("sponsor_code", (etSponsorCode.getText().length() > 0 ? etSponsorCode.getText().toString() : ""));
                buildernew.addFormDataPart("leader_code", (etLeaderCode.getText().length() > 0 ? etLeaderCode.getText().toString() : ""));
                MultipartBody requestBody = buildernew.build();

                API.service().register(requestBody).enqueue(new APICallback<APIResponse>(RegisterActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        API.setToken(response.getData().getToken());
                        API.setUser(response.getData().getUser());
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        btnRegister.setEnabled(true);

                        Intent intent = new Intent(RegisterActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[0]);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        btnRegister.setEnabled(true);

                        if (error.code == 400) {
                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(error.errorDetails);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            btnRegister.setEnabled(true);
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        } else {
                            try {
                                Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                                for (Map.Entry<String, JsonElement> entry : entries) {
                                    if (entry.getKey().matches("first_name") || entry.getKey().matches("last_name")) {
                                        etName.setError(etName.getError().toString() + " " + entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("phone_number")) {
                                        etPhone.setError(entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("email")) {
                                        etEmail.setError(entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("password")) {
                                        etPassword.setError(entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("password_confirmation")) {
                                        etConfirmPassword.setError(entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("sponsor_code")) {
                                        etSponsorCode.setError(entry.getValue().getAsString());
                                    }
                                    if (entry.getKey().matches("leader_code")) {
                                        etLeaderCode.setError(entry.getValue().getAsString());
                                    }
                                }

                            } catch (Exception exception) {
                                Log.e("loginAPI", "" + exception);
                                AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                                alertDialog.setTitle(getString(R.string.sorry));
                                alertDialog.setMessage(error.errorDetails);
                                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                btnRegister.setEnabled(true);
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    }
                });
            }
        } catch (Exception exception) {
            Log.e("register", "" + exception);
            btnRegister.setEnabled(true);
        }
    }

    private void showError(String text) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
