package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.utils.Extension;

public class ChangePhoneEmailActivity extends ToolbarActivity {

    public static final int PHONE = 10020;
    public static final int EMAIL = 10021;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etPrevious)
    TextInputEditText etPrevious;
    @BindView(R.id.etUpdated)
    TextInputEditText etUpdated;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

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
        }
        etUpdated.setInputType((type == PHONE ? InputType.TYPE_CLASS_PHONE : InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS));
        tvTitle.setText((type == PHONE ? getString(R.string.phone_number) : getString(R.string.email)));
        etPrevious.setHint((type == PHONE ? getString(R.string.previous_phone_number) : getString(R.string.previous_email)));
        etPrevious.setText((type == PHONE ? API.currentUser().getPhoneNumber() : API.currentUser().getEmail()));
        etUpdated.setHint((type == PHONE ? getString(R.string.new_phone_number) : getString(R.string.new_email)));
    }

    @OnClick(R.id.btnSubmit)
    void submitChanges() {
        btnSubmit.setEnabled(false);

        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(ChangePhoneEmailActivity.this);
            }
        });
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart((type == ChangePhoneEmailActivity.PHONE ? "new_phone_number" : "new_email"),
                (type == ChangePhoneEmailActivity.PHONE ? Extension.validatePhoneNumber(etUpdated.getText().toString()) : etUpdated.getText().toString()));
        MultipartBody requestBody = buildernew.build();

        switch (type) {
            case ChangePhoneEmailActivity.PHONE:
                API.service().changePhone(requestBody).enqueue(new APICallback<APIResponse>(ChangePhoneEmailActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        Intent intent = new Intent(ChangePhoneEmailActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[1]);
                        intent.putExtra("DESTINATION", Extension.validatePhoneNumber(etUpdated.getText().toString()));
                        startActivityForResult(intent, ChangePhoneEmailActivity.PHONE);
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        btnSubmit.setEnabled(true);
                        AlertDialog alertDialog = new AlertDialog.Builder(ChangePhoneEmailActivity.this).create();
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
                });
                break;
            case ChangePhoneEmailActivity.EMAIL:
                API.service().changeEmail(requestBody).enqueue(new APICallback<APIResponse>(ChangePhoneEmailActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        Intent intent = new Intent(ChangePhoneEmailActivity.this, VerifyOTPActivity.class);
                        intent.putExtra("TYPE", VerifyOTPActivity.OTP_TYPE[2]);
                        intent.putExtra("DESTINATION", etUpdated.getText().toString());
                        startActivityForResult(intent, ChangePhoneEmailActivity.EMAIL);
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        btnSubmit.setEnabled(true);
                        AlertDialog alertDialog = new AlertDialog.Builder(ChangePhoneEmailActivity.this).create();
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
                });
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ChangePhoneEmailActivity.PHONE || resultCode == ChangePhoneEmailActivity.EMAIL) {
                Intent previousScreen = new Intent(ChangePhoneEmailActivity.this, AccountSettingActivity.class);
                previousScreen.putExtra("UPDATE", (type == 0 ? Extension.validatePhoneNumber(etUpdated.getText().toString()) : etUpdated.getText().toString()));
                setResult((type == 0 ? PHONE : EMAIL), previousScreen);
                finish();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }
}
