package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.User;
import com.richonpay.model.VerificationStatus;
import com.richonpay.utils.Extension;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CameraResultActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivResult)
    ImageView ivResult;
    @BindView(R.id.tvCategoryTitle)
    TextView tvCategoryTitle;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.btnUsePhoto)
    Button btnUsePhoto;

    public static final int IDENTITY = 201;
    public static final int SELFIE = 202;
    private int type = IDENTITY;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_camera_result;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("TYPE", IDENTITY);
        }

        tvTitle.setText((type == IDENTITY) ? getString(R.string.verify_nric) : getString(R.string.selfie_with_nric));
        tvCategoryTitle.setText((type == IDENTITY) ? getString(R.string.foto_ktp) : getString(R.string.selfie_with_nric));
        tvDescription.setText((type == IDENTITY) ? getString(R.string.verify_nric_description) : getString(R.string.verify_selfie_description));

        btnUsePhoto.setText((type == IDENTITY) ? getString(R.string.next) : getString(R.string.send));
        Extension.setImage(this, ivResult, ((type == IDENTITY) ? VerifyUserAccountActivity.selectedNRIC : VerifyUserAccountActivity.selectedSelfie));
    }

    @OnClick(R.id.btnRetakePhoto)
    void retakePhoto() {
        onBackPressed();
    }

    @OnClick(R.id.btnUsePhoto)
    void usePhoto() {
        btnUsePhoto.setEnabled(false);
        if (type == IDENTITY) {
            Intent intent = new Intent(CameraResultActivity.this, CameraPreviewActivity.class);
            intent.putExtra("TYPE", CameraResultActivity.SELFIE);
            startActivityForResult(intent, VerifyUserAccountActivity.VERIFY_ACCOUNT);
        } else {
            btnUsePhoto.setEnabled(true);
            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(CameraResultActivity.this);
                }
            });
            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);

            if (VerifyUserAccountActivity.selectedNRIC != null) {
                buildernew.addFormDataPart("nric_picture", VerifyUserAccountActivity.selectedNRIC.getName(), RequestBody.create(MediaType.parse("image/jpeg"), VerifyUserAccountActivity.selectedNRIC));
            } else {
                return;
            }

            if (VerifyUserAccountActivity.selectedSelfie != null) {
                buildernew.addFormDataPart("selfie_picture", VerifyUserAccountActivity.selectedSelfie.getName(), RequestBody.create(MediaType.parse("image/jpeg"), VerifyUserAccountActivity.selectedSelfie));
            } else {
                return;
            }

            MultipartBody requestBody = buildernew.build();

            API.service().uploadUserVerification(requestBody).enqueue(new APICallback<APIResponse>(CameraResultActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnUsePhoto.setEnabled(true);

                    VerifyUserAccountActivity.selectedNRIC = null;
                    VerifyUserAccountActivity.selectedSelfie = null;

                    User user = API.currentUser();
                    user.getVerificationStatus().setNric(VerificationStatus.PENDING);
                    user.getVerificationStatus().setSelfie(VerificationStatus.PENDING);
                    API.setUser(user);

                    setResult(VerifyUserAccountActivity.VERIFY_ACCOUNT, new Intent(CameraResultActivity.this, CameraPreviewActivity.class));
                    finish();
                }

                @Override
                protected void onError(BadRequest error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });

                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(CameraResultActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        btnUsePhoto.setEnabled(true);
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                btnUsePhoto.setEnabled(true);
                                dialog.dismiss();
                            }
                        });

                    } else {
                        try {
                            String errorMessage = "";
                            Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                if (entry.getKey().matches("email")) {
                                    errorMessage = String.valueOf(errorMessage + "\n" + entry.getValue().getAsString());
                                }
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(CameraResultActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(errorMessage);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            btnUsePhoto.setEnabled(true);
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    btnUsePhoto.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });
                        } catch (Exception exception) {
                            Log.e("uploadUserVerification", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(CameraResultActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(error.errorDetails);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            btnUsePhoto.setEnabled(true);
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                    btnUsePhoto.setEnabled(true);
                                    dialog.dismiss();
                                }
                            });
                        }
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == VerifyUserAccountActivity.VERIFY_ACCOUNT) {
                setResult(VerifyUserAccountActivity.VERIFY_ACCOUNT, new Intent(CameraResultActivity.this, CameraPreviewActivity.class));
                finish();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }
}
