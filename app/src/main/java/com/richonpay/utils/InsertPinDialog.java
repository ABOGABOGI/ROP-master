package com.richonpay.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richonpay.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class InsertPinDialog extends Dialog {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tvDescription)
    TextView tvDescription;
    @BindView(R.id.tvForgot)
    TextView tvForgot;
    @BindView(R.id.textinput)
    TextInputLayout textinput;
    @BindView(R.id.etPassword)
    TextInputEditText etPassword;

    private String password = "";

    public InsertPinDialog(@NonNull Context context) {
        super(context);
    }

    public InsertPinDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected InsertPinDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    protected void onBindView() {
        ButterKnife.bind(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        password = "";
        setContentView(R.layout.dialog_enter_password);

        Window window = this.getWindow();
        if (window != null) {
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            window.setGravity(Gravity.CENTER);
        }
        onBindView();
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    submitPassword();
                    return true;
                }
                return false;
            }
        });
    }

    protected abstract void submitPassword();

    protected abstract void forgotPassword();

    public String getPassword() {
        return etPassword.getText().toString();
    }

    public void editProfile() {
        if (tvDescription != null) {
            tvDescription.setVisibility(View.GONE);
        }
    }

    public void enableForgotPassword(boolean status) {
        tvForgot.setEnabled(status);
    }

    public void startLoading() {
        textinput.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void stopLoading() {
        textinput.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }

    @OnClick(R.id.tvForgot)
    void forgotPin() {
        forgotPassword();
    }
}
