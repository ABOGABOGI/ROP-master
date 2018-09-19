package rich.on.pay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import rich.on.pay.R;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.BaseActivity;
import rich.on.pay.model.APIResponse;
import rich.on.pay.utils.Extension;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etEmail)
    TextInputEditText etEmail;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.tvRegister)
    TextView tvRegister;
    @BindView(R.id.btnLogin)
    Button btnLogin;

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
        btnLogin.setEnabled(false);

        if (!Extension.isValidEmail(etEmail.getText().toString())) {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage(getString(R.string.please_insert_valid_email));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    btnLogin.setEnabled(true);
                }
            });
            alertDialog.show();
            return;
        }

        Extension.showLoading(LoginActivity.this);
        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("email", etEmail.getText().toString());
        buildernew.addFormDataPart("password", etPassword.getText().toString());
        MultipartBody requestBody = buildernew.build();

        API.service().login(requestBody).enqueue(new APICallback<APIResponse>(LoginActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                API.setUser(response.getData().getUser());
                Extension.dismissLoading();
                btnLogin.setEnabled(true);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            protected void onError(BadRequest error) {
                Extension.dismissLoading();
                btnLogin.setEnabled(true);
                Toast.makeText(LoginActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.tvRegister)
    void register() {
        startActivity(new Intent(this, RegisterActivity.class));
    }
}
