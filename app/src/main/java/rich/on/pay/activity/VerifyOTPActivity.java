package rich.on.pay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rich.on.pay.R;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.model.APIResponse;
import rich.on.pay.model.User;
import rich.on.pay.utils.Extension;
import rich.on.pay.utils.PinView;

public class VerifyOTPActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.etPinView)
    PinView etPinView;
    @BindView(R.id.tvHeader)
    TextView tvHeader;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvDestination)
    TextView tvDestination;
    @BindView(R.id.tvResend)
    TextView tvResend;

    public static final int[] OTP_TYPE = {
            0, // REGISTER
            1, // CHANGE PHONE
            2, // CHANGE EMAIL
            3  // LOGIN
    };
    private int retryCount = 0;
    private int otpType = 0;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getAction() != null) {
                    if (intent.getAction().matches("otp")) {
                        final String message = intent.getStringExtra("message");
                        Pattern pattern = Pattern.compile("(\\d{4})");
                        Matcher matcher = pattern.matcher(message);
                        String val = "";
                        if (matcher.find()) {
                            val = matcher.group(1);  // 4 digit number
                        }

                        etPinView.setText(val);
                    }
                }
                Log.e("SMS OTP", "" + intent.getAction());
            } catch (Exception exception) {
                Log.e("SMS OTP", "" + exception);
            }
        }
    };

    private CountDownTimer resendCountdown = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tvResend.setEnabled(false);
            tvResend.setText(String.valueOf("(" + (millisUntilFinished / 1000) + " Detik)"));
        }

        @Override
        public void onFinish() {
            tvResend.setEnabled(true);
            tvResend.setText(R.string.resend);
        }
    };

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_verify_otp;
    }

    @Override
    protected void onViewCreated() {
        resendCountdown.start();
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            otpType = extra.getInt("TYPE", OTP_TYPE[0]);
            tvDestination.setText(String.valueOf(extra.getString("DESTINATION", (otpType == 2 ? API.currentUser().getEmail() : API.currentUser().getPhoneNumber()))));

            if (otpType == OTP_TYPE[3]) {
                resendOTP();
            }
        }

        etPinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int count = s.toString().length();
                    if (count == 0) {
                        line1.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line2.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line3.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line4.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                    }

                    if (count == 1) {
                        line1.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line2.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line3.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line4.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                    }

                    if (count == 2) {
                        line1.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line2.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line3.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                        line4.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                    }

                    if (count == 3) {
                        line1.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line2.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line3.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line4.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.black));
                    }

                    if (count == 4) {
                        line1.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line2.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line3.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        line4.setBackground(ContextCompat.getDrawable(VerifyOTPActivity.this, R.color.colorPrimary));
                        submitOTP();
                    }
                } catch (Exception exception) {
                    Log.e("UPDATE VIEW", "" + exception);
                }
            }
        });

    }

    private void submitOTP() {
        etPinView.setEnabled(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(VerifyOTPActivity.this);
            }
        });
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart((otpType == 2 ? "email" : "phone_number"), tvDestination.getText().toString());
        buildernew.addFormDataPart("pin", etPinView.getText().toString());
        MultipartBody requestBody = buildernew.build();

        switch (otpType) {
            case 0:
                verifyPhoneNumber(requestBody);
                break;
            case 1:
                API.service().updatePhone(requestBody).enqueue(new APICallback<APIResponse>(VerifyOTPActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        User user = API.currentUser();
                        user.setPhoneNumber(tvDestination.getText().toString());
                        API.setUser(user);

                        setResult(ChangePhoneEmailActivity.EMAIL, new Intent(VerifyOTPActivity.this, ChangePhoneEmailActivity.class));
                        finish();
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        etPinView.setEnabled(true);
                        if (error.code == 400) {
                            AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                                    errorMessage.append(entry.getValue().getAsString());
                                }

                                AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                                Log.e("updatePhone", "" + exception);
                                AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                break;
            case 2:
                API.service().updateEmail(requestBody).enqueue(new APICallback<APIResponse>(VerifyOTPActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse response) {
                        User user = API.currentUser();
                        user.setEmail(tvDestination.getText().toString());
                        API.setUser(user);

                        setResult(ChangePhoneEmailActivity.EMAIL, new Intent(VerifyOTPActivity.this, ChangePhoneEmailActivity.class));
                        finish();
                    }

                    @Override
                    protected void onError(BadRequest error) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Extension.dismissLoading();
                            }
                        });
                        etPinView.setEnabled(true);
                        if (error.code == 400) {
                            AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                                    errorMessage.append(entry.getValue().getAsString());
                                }

                                AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                                Log.e("updateEmail", "" + exception);
                                AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                break;
            case 3:
                verifyPhoneNumber(requestBody);
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, new IntentFilter("otp"));
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @OnClick(R.id.tvResend)
    void resendOTP() {
        try {
            tvResend.setEnabled(false);
            resendCountdown.start();

            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);
            if (otpType == OTP_TYPE[3]) {
                buildernew.addFormDataPart("usage", "verify_phone_number");
            }
            final MultipartBody requestBody = buildernew.build();

            API.service().requestOTP(requestBody).enqueue(new APICallback<APIResponse>(VerifyOTPActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {

                }

                @Override
                protected void onError(BadRequest error) {
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                            String errorMessage = "";
                            Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                errorMessage = errorMessage + entry.getValue().getAsString();
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(errorMessage);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        } catch (Exception exception) {
                            Log.e("requestOTP", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
        } catch (Exception exception) {
            Log.e("requestSMS", "" + exception);
        }
    }

    private void verifyPhoneNumber(RequestBody requestBody) {
        API.service().verifyPhone(requestBody).enqueue(new APICallback<APIResponse>(VerifyOTPActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                Intent intent = new Intent(VerifyOTPActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }

            @Override
            protected void onError(BadRequest error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                etPinView.setEnabled(true);

                if (error.code == 400) {
                    AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
                        String errorMessage = "";
                        Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                        for (Map.Entry<String, JsonElement> entry : entries) {
                            errorMessage = errorMessage + entry.getValue().getAsString();
                        }

                        AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(errorMessage);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    } catch (Exception exception) {
                        Log.e("requestOTP", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(VerifyOTPActivity.this).create();
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
