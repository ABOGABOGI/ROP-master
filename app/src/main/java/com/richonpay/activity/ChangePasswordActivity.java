package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.utils.Extension;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class ChangePasswordActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etInput)
    TextInputEditText etInput;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_change_password;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(getString(R.string.password));

    }

    @OnClick(R.id.btnSubmit)
    void submitInput() {
        btnSubmit.setEnabled(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(ChangePasswordActivity.this);
            }
        });
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("password", String.valueOf(etInput.getText()));
        MultipartBody requestBody = buildernew.build();

        API.service().checkPassword(requestBody).enqueue(new APICallback<APIResponse>(ChangePasswordActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                btnSubmit.setEnabled(true);

                Intent intent = new Intent(ChangePasswordActivity.this, ChangePhoneEmailPasswordActivity.class);
                intent.putExtra("TYPE", ChangePhoneEmailPasswordActivity.PASSWORD);
                intent.putExtra("PREV_PASSWORD", etInput.getText().toString());
                startActivityForResult(intent, ChangePhoneEmailPasswordActivity.PASSWORD);
            }

            @Override
            protected void onError(BadRequest error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                btnSubmit.setEnabled(true);
                AlertDialog alertDialog = new AlertDialog.Builder(ChangePasswordActivity.this).create();
                alertDialog.setTitle(getString(R.string.wrong_password));
                alertDialog.setMessage(getString(R.string.wrong_password_please_try_again));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        btnSubmit.setEnabled(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ChangePhoneEmailPasswordActivity.PASSWORD) {
                Intent previousScreen = new Intent(ChangePasswordActivity.this, AccountSettingActivity.class);
                previousScreen.putExtra("UPDATE", "PASSWORD");
                setResult(ChangePhoneEmailPasswordActivity.PASSWORD, previousScreen);
                finish();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }
}
