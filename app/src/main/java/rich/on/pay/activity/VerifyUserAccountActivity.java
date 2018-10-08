package rich.on.pay.activity;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.api.API;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.model.VerificationStatus;

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
                break;

            case VerificationStatus.PENDING:
                btnVerifyAccount.setEnabled(false);
                btnVerifyAccount.setText(R.string.ongoing);
                break;

            case VerificationStatus.VERIFIED:
                btnVerifyAccount.setEnabled(false);
                btnVerifyAccount.setText(R.string.successful);
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
