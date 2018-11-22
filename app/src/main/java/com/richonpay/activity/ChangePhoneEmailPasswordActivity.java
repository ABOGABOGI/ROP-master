package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

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

public class ChangePhoneEmailPasswordActivity extends ToolbarActivity {

    public static final int PHONE = 10020;
    public static final int EMAIL = 10021;
    public static final int PASSWORD = 10022;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etPreviousHint)
    TextInputLayout etPreviousHint;
    @BindView(R.id.etPrevious)
    TextInputEditText etPrevious;
    @BindView(R.id.etUpdatedHint)
    TextInputLayout etUpdatedHint;
    @BindView(R.id.etUpdated)
    TextInputEditText etUpdated;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    private String prevPassword = "";
    private int type = 0;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_change_phone_email;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("TYPE", 0);
            prevPassword = extra.getString("PREV_PASSWORD", "");
        }

        switch (type) {
            case ChangePhoneEmailPasswordActivity.PHONE:
                tvTitle.setText(getString(R.string.phone_number));
                etUpdated.setInputType(InputType.TYPE_CLASS_PHONE);
                etPreviousHint.setHint(getString(R.string.previous_phone_number));
                etPrevious.setText(API.currentUser().getPhoneNumber());
                etUpdatedHint.setHint(getString(R.string.new_phone_number));
                break;
            case ChangePhoneEmailPasswordActivity.EMAIL:
                tvTitle.setText(getString(R.string.email));
                etUpdated.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                etPreviousHint.setHint(getString(R.string.previous_email));
                etPrevious.setText(API.currentUser().getEmail());
                etUpdatedHint.setHint(getString(R.string.new_email));
                break;
            case ChangePhoneEmailPasswordActivity.PASSWORD:
                tvTitle.setText(getString(R.string.password));
                etPrevious.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etUpdated.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                etPrevious.setEnabled(true);
                etPreviousHint.setHint(getString(R.string.new_password));
                etUpdatedHint.setHint(getString(R.string.confirm_new_password));
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.btnSubmit)
    void submitChanges() {
        btnSubmit.setEnabled(false);

        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(ChangePhoneEmailPasswordActivity.this);
            }
        });
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);

        switch (type) {
            case ChangePhoneEmailPasswordActivity.PHONE:
                buildernew.addFormDataPart("new_phone_number", Extension.validatePhoneNumber(etUpdated.getText().toString()));
                break;
            case ChangePhoneEmailPasswordActivity.EMAIL:
                buildernew.addFormDataPart("new_email", etUpdated.getText().toString());
                break;
            case ChangePhoneEmailPasswordActivity.PASSWORD:
                buildernew.addFormDataPart("password", prevPassword);
                buildernew.addFormDataPart("new_password", etPrevious.getText().toString());
                buildernew.addFormDataPart("new_password_confirmation", etUpdated.getText().toString());
                break;
        }

        MultipartBody requestBody = buildernew.build();

        switch (type) {
            case ChangePhoneEmailPasswordActivity.PHONE:
                API.service().changePhone(requestBody).enqueue(new APICallback<APIResponse>(ChangePhoneEmailPasswordActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });

                        Intent intent = new Intent(ChangePhoneEmailPasswordActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[1]);
                        intent.putExtra("DESTINATION", Extension.validatePhoneNumber(etUpdated.getText().toString()));
                        startActivityForResult(intent, ChangePhoneEmailPasswordActivity.PHONE);
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        showErrorMessage(error);
                    }
                });
                break;

            case ChangePhoneEmailPasswordActivity.EMAIL:
                API.service().changeEmail(requestBody).enqueue(new APICallback<APIResponse>(ChangePhoneEmailPasswordActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });

                        Intent intent = new Intent(ChangePhoneEmailPasswordActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[2]);
                        intent.putExtra("DESTINATION", etUpdated.getText().toString());
                        startActivityForResult(intent, ChangePhoneEmailPasswordActivity.EMAIL);
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        showErrorMessage(error);
                    }
                });
                break;

            case ChangePhoneEmailPasswordActivity.PASSWORD:
                API.service().changePassword(requestBody).enqueue(new APICallback<APIResponse>(ChangePhoneEmailPasswordActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });

                        Intent previousScreen = new Intent(ChangePhoneEmailPasswordActivity.this, ChangePasswordActivity.class);
                        setResult(ChangePhoneEmailPasswordActivity.PASSWORD, previousScreen);
                        finish();
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        showErrorMessage(error);
                    }
                });
                break;
            default:
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                btnSubmit.setEnabled(true);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ChangePhoneEmailPasswordActivity.PHONE || resultCode == ChangePhoneEmailPasswordActivity.EMAIL) {
                Intent previousScreen = new Intent(ChangePhoneEmailPasswordActivity.this, AccountSettingActivity.class);
                previousScreen.putExtra("UPDATE", (type == 0 ? Extension.validatePhoneNumber(etUpdated.getText().toString()) : etUpdated.getText().toString()));
                setResult((type == 0 ? PHONE : EMAIL), previousScreen);
                finish();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }

    private void showErrorMessage(BadRequest error) {
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.dismissLoading();
            }
        });
        btnSubmit.setEnabled(true);

        if (error.code == 400) {
            AlertDialog alertDialog = new AlertDialog.Builder(ChangePhoneEmailPasswordActivity.this).create();
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
                StringBuilder errorMessage = new StringBuilder();
                Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                for (Map.Entry<String, JsonElement> entry : entries) {
                    errorMessage.append(entry.getValue().getAsString()).append("\n");
                }

                AlertDialog alertDialog = new AlertDialog.Builder(ChangePhoneEmailPasswordActivity.this).create();
                alertDialog.setTitle(getString(R.string.sorry));
                alertDialog.setMessage(errorMessage.toString());
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

            } catch (Exception exception) {
                Log.e("requestPackage", "" + exception);
                AlertDialog alertDialog = new AlertDialog.Builder(ChangePhoneEmailPasswordActivity.this).create();
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
}