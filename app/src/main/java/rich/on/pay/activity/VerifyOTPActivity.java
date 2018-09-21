package rich.on.pay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;
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

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_verify_otp;
    }

    @Override
    protected void onViewCreated() {
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
}
