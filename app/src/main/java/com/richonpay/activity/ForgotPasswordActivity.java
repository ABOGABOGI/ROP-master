package com.richonpay.activity;

import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
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

public class ForgotPasswordActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etInput)
    TextInputEditText etInput;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_forgot_password;
    }

    @Override
    protected void onViewCreated() {

    }

    @OnClick(R.id.btnSubmit)
    void submitRequest() {
        btnSubmit.setEnabled(false);

        if (!Extension.isValidEmail(etInput.getText().toString())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage(getString(R.string.please_insert_valid_email));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    btnSubmit.setEnabled(true);
                }
            });

        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(ForgotPasswordActivity.this);
                }
            });

            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);
            buildernew.addFormDataPart("email", etInput.getText().toString());
            MultipartBody requestBody = buildernew.build();

            API.service().forgotPassword(requestBody).enqueue(new APICallback<APIResponse>(ForgotPasswordActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnSubmit.setEnabled(true);

                    AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
                    alertDialog.setTitle(getString(R.string.successful));
                    alertDialog.setMessage(getString(R.string.forgot_password_successful));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

                @Override
                protected void onError(BadRequest error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnSubmit.setEnabled(true);

                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
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
                                ;
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
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
                            Log.e("forgotPassword", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(ForgotPasswordActivity.this).create();
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
    }
}
