package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.adapter.BankLogoAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.UpgradeRequest;
import com.richonpay.utils.Extension;

import org.parceler.Parcels;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class TopupBankSelectionActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTransactionCode)
    TextView tvTransactionCode;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvStatus)
    TextView tvStatus;
    @BindView(R.id.tvDate)
    TextView tvDate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnPay)
    Button btnPay;

    private boolean isPackageUpgrade = false;
    private Integer selectedBank = null;
    private UpgradeRequest upgradeRequest;
    private double topupAmount = 0d;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_topup_bank_selection;
    }

    @Override
    protected void onViewCreated() {
        try {
            tvTitle.setText(R.string.isi_saldo);

            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                isPackageUpgrade = extra.getBoolean("PACKAGE", false);
                upgradeRequest = Parcels.unwrap(getIntent().getParcelableExtra("UPGRADE_REQUEST"));
                topupAmount = extra.getDouble("AMOUNT", 0d);

                if (upgradeRequest != null) {
                    tvTransactionCode.setText(String.valueOf(getString(R.string.transaction_code) + " " + upgradeRequest.getCode()));
                    if (isPackageUpgrade) {
                        tvAmount.setText(Extension.priceFormat(upgradeRequest.getPrice()));
                    } else {
                        tvAmount.setText(String.valueOf(getString(R.string.topup) + " " + Extension.priceFormat(upgradeRequest.getPrice())));
                    }
                    tvStatus.setText(getString(R.string.waiting));
                    tvDate.setText(Extension.receiptDetailDateFormat.format(upgradeRequest.getCreatedAt()));
                } else {
//                    tvTransactionCode.setText(String.valueOf(getString(R.string.transaction_code) + " "));
                    tvTransactionCode.setVisibility(View.GONE);
                    tvAmount.setText(String.valueOf(getString(R.string.topup) + " " + Extension.priceFormat(topupAmount)));
                    tvStatus.setText(getString(R.string.waiting));
                    tvDate.setText(Extension.receiptDetailDateFormat.format(new Date()));
                }
            }

            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            final BankLogoAdapter mAdapter = new BankLogoAdapter(TopupBankSelectionActivity.this, new BankLogoAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int id, int selectedBankID) {
                    selectedBank = selectedBankID;
                }
            });
            recyclerView.setAdapter(mAdapter);

            API.service().getPaymentBankAccount().enqueue(new APICallback<APIResponse>(TopupBankSelectionActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    mAdapter.setItems(response.getData().getBankAccounts());
                    if (response.getData().getBankAccounts().size() > 0) {
                        selectedBank = response.getData().getBankAccounts().get(0).getId();
                    }
                }

                @Override
                protected void onError(BadRequest error) {
                    Toast.makeText(TopupBankSelectionActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception exception) {
            Log.e("BANKSELECTION", "" + exception);
        }
    }

    @OnClick(R.id.btnPay)
    void payOrder() {
        btnPay.setEnabled(false);
        if (selectedBank == null) {
            AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
            alertDialog.setTitle(getString(R.string.sorry));
            alertDialog.setMessage(getString(R.string.please_select_bank_first));
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    btnPay.setEnabled(true);
                }
            });
            return;
        }

        if (isPackageUpgrade) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(TopupBankSelectionActivity.this);
                }
            });
            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);
            buildernew.addFormDataPart("bank_account_id", String.valueOf((selectedBank)));
            MultipartBody requestBody = buildernew.build();

            API.service().buyPackage(upgradeRequest.getId(), requestBody).enqueue(new APICallback<APIResponse>(TopupBankSelectionActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnPay.setEnabled(true);

                    Intent intent = new Intent(TopupBankSelectionActivity.this, OrderDetailActivity.class);
                    intent.putExtra("UPGRADE_REQUEST", Parcels.wrap(response.getData().getUpgradeRequest()));
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnPay.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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
                                errorMessage.append(entry.getValue().getAsString()).append("\n");
                                ;
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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
                            Log.e("loginAPI", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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
        } else {

            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(TopupBankSelectionActivity.this);
                }
            });
            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);
            buildernew.addFormDataPart("dest_bank_account_id", String.valueOf((selectedBank)));
            buildernew.addFormDataPart("amount", String.valueOf((topupAmount)));
            MultipartBody requestBody = buildernew.build();

            API.service().requestTopup(requestBody).enqueue(new APICallback<APIResponse>(TopupBankSelectionActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnPay.setEnabled(true);

                    Intent intent = new Intent(TopupBankSelectionActivity.this, OrderDetailActivity.class);
                    intent.putExtra("ORDER_DETAIL", Parcels.wrap(response.getData().getOrder()));
                    intent.putExtra("IS_ORDER",true);
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnPay.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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
                                errorMessage.append(entry.getValue().getAsString()).append("\n");
                                ;
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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
                            Log.e("loginAPI", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(TopupBankSelectionActivity.this).create();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("refresh_transaction");
        //send broadcast
        TopupBankSelectionActivity.this.sendBroadcast(intent);
    }
}
