package rich.on.pay.activity;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.base.BaseActivity;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.tvRegister)
    TextView tvRegister;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onViewCreated() {
        SpannableString spannableString = new SpannableString(tvRegister.getText());
        spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)), 18, tvRegister.getText().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRegister.setText(spannableString);
    }

    @OnClick(R.id.tvForgotPassword)
    void forgotPassword() {

    }

    @OnClick(R.id.btnLogin)
    void login() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @OnClick(R.id.tvRegister)
    void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
