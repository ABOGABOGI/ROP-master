package com.richonpay.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.VerificationStatus;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

public class VerifyUserAccountActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.btnVerifyAccount)
    Button btnVerifyAccount;

    public static final int VERIFY_ACCOUNT = 22211;
    public static File selectedNRIC = null;
    public static File selectedSelfie = null;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_verify_user;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.verify_user_data);
        setVerificationStatus();
    }

    @OnClick(R.id.btnVerifyAccount)
    void takeICPhoto() {
        Intent intent = new Intent(VerifyUserAccountActivity.this, CameraPreviewActivity.class);
        intent.putExtra("TYPE", CameraResultActivity.IDENTITY);
        startActivityForResult(intent, VERIFY_ACCOUNT);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        VerifyUserAccountActivity.selectedNRIC = null;
    }

    private void setVerificationStatus() {
        switch (API.currentUser().getVerificationStatus().getNric()) {
            case VerificationStatus.DEFAULT:
                btnVerifyAccount.setEnabled(true);
                btnVerifyAccount.setText(R.string.verify_now);
                btnVerifyAccount.setBackgroundResource(R.drawable.rounded_bottom_primary_button);
                break;

            case VerificationStatus.PENDING:
                btnVerifyAccount.setEnabled(false);
                btnVerifyAccount.setText(R.string.ongoing);
                btnVerifyAccount.setBackgroundResource(R.drawable.rounded_bottom_ongoing);
                break;

            case VerificationStatus.VERIFIED:
                btnVerifyAccount.setEnabled(false);
                btnVerifyAccount.setText(R.string.successful);
                btnVerifyAccount.setBackgroundResource(R.drawable.rounded_bottom_successful);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setVerificationStatus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setVerificationStatus();
    }
}
