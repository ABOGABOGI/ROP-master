package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.TransactionOrder;
import com.richonpay.model.UpgradeRequest;
import com.richonpay.utils.Extension;

import org.parceler.Parcels;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends ToolbarActivity {

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
    @BindView(R.id.tvTransferBefore)
    TextView tvTransferBefore;
    @BindView(R.id.tvRemainingTime)
    TextView tvRemainingTime;
    @BindView(R.id.tvTotalPayment)
    TextView tvTotalPayment;
    @BindView(R.id.ivBankLogo)
    ImageView ivBankLogo;
    @BindView(R.id.tvBankAccount)
    TextView tvBankAccount;
    @BindView(R.id.tvAccountOwner)
    TextView tvAccountOwner;
    @BindView(R.id.tvActionDescription)
    TextView tvActionDescription;
    @BindView(R.id.btnConfirmation)
    Button btnConfirmation;
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    private UpgradeRequest upgradeRequest;
    private boolean isOrderDetail = false;
    private TransactionOrder order;
    public static Timer timer;
    private TimerTask timerTask;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.payment);

        try {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                upgradeRequest = Parcels.unwrap(getIntent().getParcelableExtra("UPGRADE_REQUEST"));
                isOrderDetail = extra.getBoolean("IS_ORDER", false);
                order = Parcels.unwrap(getIntent().getParcelableExtra("ORDER_DETAIL"));

                initTransactionDetail();
            }
        } catch (Exception exception) {
            Log.e("OrderDetail", "" + exception);
        }
    }

    @OnClick(R.id.btnConfirmation)
    void confirmPayment() {
        btnConfirmation.setEnabled(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(OrderDetailActivity.this);
            }
        });

        if (isOrderDetail) {
            API.service().confirmTopup(order.getId()).enqueue(new APICallback<APIResponse>(OrderDetailActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnConfirmation.setEnabled(true);

                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                    intent.putExtra("SELECT_TAB", 1);
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
                    btnConfirmation.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            Log.e("confirmPayment", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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

        } else if (upgradeRequest != null) {  // PACKAGE
            API.service().confirmPackagePayment(upgradeRequest.getId()).enqueue(new APICallback<APIResponse>(OrderDetailActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnConfirmation.setEnabled(true);

                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                    intent.putExtra("SELECT_TAB", 1);
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
                    btnConfirmation.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            Log.e("confirmPayment", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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

    @OnClick(R.id.tvCancel)
    void tvCancel() {
        tvCancel.setEnabled(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(OrderDetailActivity.this);
            }
        });

        if (isOrderDetail) {
            API.service().cancelTopup(order.getId()).enqueue(new APICallback<APIResponse>(OrderDetailActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    tvCancel.setEnabled(true);

                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                    intent.putExtra("SELECT_TAB", 1);
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
                    tvCancel.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            Log.e("cancelPayment", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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

        } else if (upgradeRequest != null) {  // PACKAGE
            API.service().cancelPackagePayment(upgradeRequest.getId()).enqueue(new APICallback<APIResponse>(OrderDetailActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    tvCancel.setEnabled(true);

                    Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
                    intent.putExtra("SELECT_TAB", 1);
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
                    tvCancel.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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
                            Log.e("cancelPayment", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(OrderDetailActivity.this).create();
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

    private void initTransactionDetail() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float calculatedWidth = width / 8f;
        float calculatedHeight = width / 12f;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedWidth))), Integer.parseInt(String.valueOf(Math.round(calculatedHeight))));
        layoutParams.gravity = Gravity.CENTER;
        ivBankLogo.setLayoutParams(layoutParams);

        if (isOrderDetail) {
            tvTransactionCode.setText(String.valueOf(getString(R.string.transaction_code) + " " + order.getReferenceNumber()));
            tvAmount.setText(String.valueOf(getString(R.string.topup) + " " + Extension.priceFormat(order.getOrderDetails().get(0).getTotal())));

            switch (order.getStatus()) {
                case UpgradeRequest.PENDING:
                    tvActionDescription.setVisibility(View.VISIBLE);
                    btnConfirmation.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.VISIBLE);
                    tvStatus.setText(getString(R.string.waiting));
                    tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.WAITING_PAYMENT:
                    tvActionDescription.setVisibility(View.VISIBLE);
                    btnConfirmation.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.VISIBLE);
                    tvStatus.setText(getString(R.string.waiting));
                    tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.ON_PROCESS:
                    tvStatus.setText(getString(R.string.processing_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_process);
                    break;

                case UpgradeRequest.ACCEPTED:
                    tvStatus.setText(getString(R.string.successful));
                    tvStatus.setBackgroundResource(R.drawable.status_success);
                    break;

                case UpgradeRequest.DECLINED:
                    tvStatus.setText(getString(R.string.declined_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.CANCELLED:
                    tvStatus.setText(getString(R.string.cancel_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.REFUNDED:
                    tvStatus.setText(getString(R.string.refund_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_refund);
                    break;
                default:
                    break;
            }

            tvDate.setText(Extension.receiptDetailDateFormat.format(order.getCreatedAt()));
            tvTransferBefore.setText(String.valueOf(getString(R.string.transfer_before) + " " + Extension.sdfAMPMwithSecond.format(order.getExpiredAt())));
            tvRemainingTime.setText(Extension.getRemainingTime(OrderDetailActivity.this, order.getExpiredAt()));
            tvTotalPayment.setText(Extension.numberPriceFormat(order.getOrderDetails().get(0).getSubTotal()));
            tvBankAccount.setText(order.getVirtual().getDestBankAccount().getAccountNo());
            tvAccountOwner.setText(order.getVirtual().getDestBankAccount().getAccountName());
            Extension.setImageFitCenter(OrderDetailActivity.this, ivBankLogo, order.getVirtual().getDestBankAccount().getBank().getCoverUrl());

        } else if (upgradeRequest != null) {  // PACKAGE
            tvTransactionCode.setText(String.valueOf(getString(R.string.transaction_code) + " " + upgradeRequest.getCode()));
            tvAmount.setText(Extension.priceFormat(upgradeRequest.getPrice()));

            switch (upgradeRequest.getStatus()) {
                case UpgradeRequest.PENDING:
                    tvActionDescription.setVisibility(View.VISIBLE);
                    btnConfirmation.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.VISIBLE);
                    tvStatus.setText(getString(R.string.waiting));
                    tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.WAITING_PAYMENT:
                    tvActionDescription.setVisibility(View.VISIBLE);
                    btnConfirmation.setVisibility(View.VISIBLE);
                    tvCancel.setVisibility(View.VISIBLE);
                    tvStatus.setText(getString(R.string.waiting));
                    tvStatus.setBackgroundResource(R.drawable.status_waiting);
                    break;

                case UpgradeRequest.ON_PROCESS:
                    tvStatus.setText(getString(R.string.processing_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_process);
                    break;

                case UpgradeRequest.ACCEPTED:
                    tvStatus.setText(getString(R.string.successful));
                    tvStatus.setBackgroundResource(R.drawable.status_success);
                    break;

                case UpgradeRequest.DECLINED:
                    tvStatus.setText(getString(R.string.declined_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.CANCELLED:
                    tvStatus.setText(getString(R.string.cancel_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_cancelled);
                    break;

                case UpgradeRequest.REFUNDED:
                    tvStatus.setText(getString(R.string.refund_allcaps));
                    tvStatus.setBackgroundResource(R.drawable.status_refund);
                    break;
                default:
                    break;
            }

            tvDate.setText(Extension.receiptDetailDateFormat.format(upgradeRequest.getCreatedAt()));
            tvTransferBefore.setText(String.valueOf(getString(R.string.transfer_before) + " " + Extension.sdfAMPMwithSecond.format(upgradeRequest.getExpiredAt())));
            tvRemainingTime.setText(Extension.getRemainingTime(OrderDetailActivity.this, upgradeRequest.getExpiredAt()));
            tvTotalPayment.setText(Extension.numberPriceFormat(upgradeRequest.getPrice()));
            tvBankAccount.setText(upgradeRequest.getVirtual().getBankAccount().getAccountNo());
            tvAccountOwner.setText(upgradeRequest.getVirtual().getBankAccount().getAccountName());
            Extension.setImageFitCenter(OrderDetailActivity.this, ivBankLogo, upgradeRequest.getVirtual().getBankAccount().getBank().getCoverUrl());
        }

        try {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                }

                @Override
                protected void onPostExecute(Void aVoid) {

                }

                @Override
                protected Void doInBackground(Void... voids) {
                    if (timer != null) {
                        timer.cancel();
                    }
                    if (timerTask != null) {
                        timerTask.cancel();
                    }
                    timer = new Timer();
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected void onPreExecute() {
                                    super.onPreExecute();
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    if (isOrderDetail) {
                                        tvRemainingTime.setText(Extension.getRemainingTime(OrderDetailActivity.this, order.getExpiredAt()));

                                    } else if (upgradeRequest != null) {  // PACKAGE
                                        tvRemainingTime.setText(Extension.getRemainingTime(OrderDetailActivity.this, upgradeRequest.getExpiredAt()));
                                    }
                                }

                                @Override
                                protected Void doInBackground(Void... voids) {
                                    return null;
                                }
                            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        }
                    };
                    timer.schedule(timerTask, 1000, 500);
                    return null;
                }
            }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception exception) {
            Log.e("TIME_REMAINING", "" + exception);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            timerTask.cancel();
            timerTask = new TimerTask() {
                @Override
                public void run() {

                }
            };
            timer.cancel();
            timer = null;
        } catch (Exception e) {
            Log.e("HOMEFRAGMENT", "ON STOP " + e);
        }
    }
}
