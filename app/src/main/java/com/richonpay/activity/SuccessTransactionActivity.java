package com.richonpay.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richonpay.R;
import com.richonpay.base.BaseActivity;
import com.richonpay.model.Receipt;
import com.richonpay.utils.Extension;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.OnClick;

public class SuccessTransactionActivity extends BaseActivity {

    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.tvTransactionID)
    TextView tvTransactionID;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvRecepient)
    TextView tvRecepient;
    @BindView(R.id.btnTransactionList)
    Button btnTransactionList;

    private boolean isCashier = false;
    @BindView(R.id.tvRecepientLabel)
    TextView tvRecepientLabel;
    @BindView(R.id.tvExtraLabel)
    TextView tvExtraLabel;
    @BindView(R.id.tvExtraValue)
    TextView tvExtraValue;
    @BindView(R.id.llExtra)
    LinearLayout llExtra;
    @BindView(R.id.llCode)
    LinearLayout llCode;
    @BindView(R.id.tvCodeLabel)
    TextView tvCodeLabel;

    private boolean isProduct = false;
    private String productType = "";

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_success_transaction;
    }

    @Override
    protected void onViewCreated() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            isCashier = extras.getBoolean("IS_CASHIER", false);
            if (isCashier) {
                btnTransactionList.setText(R.string.view_live_transaction);
            }
        }

        Receipt receipt = Parcels.unwrap(getIntent().getParcelableExtra("RECEIPT"));
        isProduct = getIntent().getBooleanExtra("IS_PRODUCT", false);
        productType = getIntent().getStringExtra("PRODUCT_TYPE");
        if (receipt != null) {
            if (receipt.getWalletType().equals("Primary")) {
                tvAmount.setText(Extension.priceFormat(receipt.getAmount()));
            } else {
                tvAmount.setText(Extension.numberPriceFormat(receipt.getAmount()));
            }
            tvTransactionID.setText(receipt.getReferenceNumber());
            tvDateTime.setText(Extension.receiptDetailDateFormat.format(receipt.getTime()));

            if (isProduct) {
                switch (productType) {
                    case "GAME":
                        llExtra.setVisibility(View.GONE);
                        llCode.setVisibility(View.VISIBLE);
                        tvCodeLabel.setText(R.string.redeem_code);
                        tvCode.setText(receipt.getSn());
                        break;
                    case "PULSA":
                        llCode.setVisibility(View.GONE);
                        llExtra.setVisibility(View.VISIBLE);
                        tvExtraLabel.setText(R.string.phone_number_w_colon);
                        tvExtraValue.setText(receipt.getCustomerId());
                        break;
                    case "LISTRIK":
                        llExtra.setVisibility(View.VISIBLE);
                        tvExtraLabel.setText(R.string.meter_number_w_colon);
                        tvExtraValue.setText(receipt.getCustomerId());
                        if (receipt.getSn().equals("")) {
                            llCode.setVisibility(View.GONE);
                        } else {
                            llCode.setVisibility(View.VISIBLE);
                            tvCodeLabel.setText(R.string.redeem_code);
                            tvCode.setText(receipt.getSn());
                        }
                        break;
                    default:
                        llCode.setVisibility(View.GONE);
                        llExtra.setVisibility(View.VISIBLE);
                        break;
                }
                tvRecepient.setText(receipt.getProduct());
                tvRecepientLabel.setText(R.string.product_w_colon);

            } else {
                llCode.setVisibility(View.GONE);
                llExtra.setVisibility(View.GONE);
                tvRecepient.setText(Extension.sensorNumber(receipt.getRecipientPhone()));
            }
        }
    }

    @OnClick(R.id.btnBackHome)
    void btnBackHome() {
        Intent intent = new Intent(SuccessTransactionActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @OnClick(R.id.btnTransactionList)
    void btnTransactionList() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("SELECT_TAB", 1);
        startActivity(intent);
    }

    @OnClick(R.id.llCode)
    void copyCode() {
        ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        ClipData myClip = ClipData.newPlainText("Receive Code", tvCode.getText().toString());
        if (myClipboard != null) {
            myClipboard.setPrimaryClip(myClip);
            Toast.makeText(getApplicationContext(), "Code Copied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

    }
}
