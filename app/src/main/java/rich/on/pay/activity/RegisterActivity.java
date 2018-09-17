package rich.on.pay.activity;

import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;

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
    @BindView(R.id.llReferral)
    LinearLayout llReferral;
    @BindView(R.id.cbReferral)
    CheckBox cbReferral;
    @BindView(R.id.cbTerms)
    CheckBox cbTerms;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onViewCreated() {
        cbReferral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                llReferral.setVisibility((llReferral.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
            }
        });
    }

    @OnClick(R.id.btnRegister)
    void register() {
        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "You must agree to the terms to continue", Toast.LENGTH_SHORT).show();
        } else {
            //REGISTER
        }
    }
}
