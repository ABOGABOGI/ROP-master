package com.richonpay.activity.payment_product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.richonpay.R;
import com.richonpay.activity.SuccessTransactionActivity;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.PaymentProductBody;
import com.richonpay.utils.Extension;
import com.richonpay.utils.InsertPinDialog;

import org.parceler.Parcels;

import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

import static com.richonpay.utils.Extension.setTextToView;

public class WaterbillActivity extends ToolbarActivity {

    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.etLocation)
    TextInputEditText etLocation;
    @BindView(R.id.btnDropdown)
    ImageButton btnDropdown;
    @BindView(R.id.llDropdown)
    LinearLayout llDropdown;
    @BindView(R.id.etCustomerNumber)
    TextInputEditText etCustomerNumber;
    @BindView(R.id.llBillInformation)
    LinearLayout llBillInformation;
    @BindView(R.id.tvCustomerNumber)
    TextView tvCustomerNumber;
    @BindView(R.id.tvReceipt)
    TextView tvReceipt;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvBillAmount)
    TextView tvBillAmount;
    @BindView(R.id.tvPamName)
    TextView tvPamName;
    @BindView(R.id.llBillDetail)
    LinearLayout llBillDetail;
    @BindView(R.id.tvPeriod)
    TextView tvPeriod;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.llSeeMore)
    LinearLayout llSeeMore;
    @BindView(R.id.tvMoreDetail)
    TextView tvMoreDetail;
    @BindView(R.id.tvTotalFine)
    TextView tvTotalFine;
    @BindView(R.id.tvNonWaterBill)
    TextView tvNonWaterBill;
    @BindView(R.id.tvStampFee)
    TextView tvStampFee;
    @BindView(R.id.tvAdminFee)
    TextView tvAdminFee;
    @BindView(R.id.tvTotalBill)
    TextView tvTotalBill;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private PaymentProductBody paymentProductRequest = new PaymentProductBody();
    public static int WATERBILL_RESULTCODE = 9821;
    private InsertPinDialog enterPinDialog;
    private Integer selectedID = null;
    private double totalPrice = 0d;
    private String customerID = "";
    private int ppobID = 5;
    private Timer timer;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_waterbill;
    }

    @Override
    protected void onViewCreated() {
        try {
            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                ppobID = extra.getInt("CATEGORY", 5);
            }

            tvBalance.setText(Extension.numberPriceFormat(API.currentUser().getWallets().get(0).getBalance()));

            etCustomerNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (timer != null) {
                        timer.cancel();
                    }
                }

                @Override
                public void afterTextChanged(final Editable s) {
                    customerID = s.toString();
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            // do your actual work here
                            if (TextUtils.isEmpty(s.toString().trim())) {
                                requestInquiry();
                            } else {
                                requestInquiry();
                            }
                        }
                    }, 600);
                }
            });

        } catch (Exception exception) {
            Log.e("INIT", "" + exception);
        }
    }

    @OnClick({R.id.llDropdown, R.id.btnDropdown, R.id.etLocation})
    public void onLocationClicked(View view) {
        try {
            Intent intent = new Intent(this, SearchWaterBillLocationActivity.class);
            intent.putExtra("CATEGORY", ppobID);
            startActivityForResult(intent, WATERBILL_RESULTCODE);
        } catch (Exception exception) {
            Log.e("onLocationClicked", "" + exception);
        }
    }

    @OnClick(R.id.tvMoreDetail)
    public void onSeeMore(View view) {
        try {
            llSeeMore.setVisibility((llSeeMore.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
            tvMoreDetail.setText((llSeeMore.getVisibility() == View.GONE ? R.string.more_detail : R.string.less_detail));
        } catch (Exception exception) {
            Log.e("onSeeMore", "" + exception);
        }
    }

    @OnClick(R.id.btnNext)
    public void requestPayment(View view) {
        try {
            PaymentProductBody product = new PaymentProductBody();
            product.setPaymentProductId(selectedID);
            product.setPassword(enterPinDialog.getPassword());
            product.setAmount(totalPrice);
            product.setWalletType(0);

            enterPinDialog = new InsertPinDialog(this, R.style.darkPopupAnimation) {
                @Override
                protected void submitPassword() {
                    requestPayment();
                }

                @Override
                protected void forgotPassword() {
                    requestForgotPassword();
                }
            };
            enterPinDialog.show();
        } catch (Exception exception) {
            Log.e("enterPIN", "" + exception);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == WATERBILL_RESULTCODE) {
                selectedID = data.getIntExtra("ID", 0);
                etLocation.setText(data.getStringExtra("LOCATION"));
                requestInquiry();
            }
        } catch (Exception exception) {
            Log.e("onActivityResult", "" + exception);
        }
    }

    private void requestInquiry() {
        try {
            if (selectedID != 0 && customerID.length() > 3) {

                WaterbillActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                });

                PaymentProductBody inquiryBody = new PaymentProductBody();
                inquiryBody.setPaymentProductId(selectedID);
                inquiryBody.setCustomerId(customerID);

                API.service().inquiryPLN(ppobID, inquiryBody).enqueue(new APICallback<APIResponse>(WaterbillActivity.this) {
                    @Override
                    protected void onSuccess(APIResponse apiResponse) {

                        setTextToView(tvCustomerNumber, apiResponse.getData().getOrder().getOptions().getCustomerId());
                        setTextToView(tvReceipt, apiResponse.getData().getOrder().getOptions().getRef2());
                        setTextToView(tvName, apiResponse.getData().getOrder().getOptions().getCustomerName());
                        setTextToView(tvBillAmount, String.valueOf(apiResponse.getData().getOrder().getOptions().getMonths().size()));
                        setTextToView(tvPamName, apiResponse.getData().getOrder().getOptions().getPdamName());

                        for (int i = 0; i < apiResponse.getData().getOrder().getOptions().getMonths().size(); i++) {
                            setTextToView(tvPeriod, tvPeriod.getText().toString() + apiResponse.getData().getOrder().getOptions().getMonths().get(i) + " ");
                        }

                        setTextToView(tvTotalPrice, Extension.priceFormat(apiResponse.getData().getOrder().getOptions().getTotalAmount()));
                        setTextToView(tvTotalFine, Extension.priceFormat(apiResponse.getData().getOrder().getOptions().getPenalty()));
                        setTextToView(tvNonWaterBill, Extension.priceFormat(0));
                        setTextToView(tvStampFee, Extension.priceFormat(0));
                        setTextToView(tvAdminFee, Extension.priceFormat(apiResponse.getData().getOrder().getOptions().getFee()));
                        setTextToView(tvTotalBill, Extension.priceFormat(apiResponse.getData().getOrder().getOptions().getAmount()));

                        WaterbillActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                llBillInformation.setVisibility(View.VISIBLE);
                                llBillDetail.setVisibility(View.VISIBLE);
                                llSeeMore.setVisibility(View.GONE);
                            }
                        });

                        totalPrice = apiResponse.getData().getOrder().getOptions().getTotalAmount();
                        paymentProductRequest.setPaymentProductId(selectedID);
                        paymentProductRequest.setCustomerId(customerID);
                        paymentProductRequest.setOrderId(String.valueOf(apiResponse.getData().getOrder().getId()));
                    }

                    @Override
                    protected void onError(final BadRequest error) {
                        WaterbillActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                                llBillDetail.setVisibility(View.GONE);
                                llBillInformation.setVisibility(View.GONE);
                                llSeeMore.setVisibility(View.GONE);
                                Toast.makeText(WaterbillActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        } catch (Exception exception) {
            Log.e("requestInquiry", "" + exception);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (timer != null) {
                timer.cancel();
            }
        } catch (Exception exception) {
            Log.e("CANCEL TIMER", "" + exception);
        }
    }

    private void requestPayment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enterPinDialog.startLoading();
            }
        });

        paymentProductRequest.setWalletType(0);
        paymentProductRequest.setPassword(enterPinDialog.getPassword());

        try {
            API.service().paymentPLN(ppobID, paymentProductRequest).enqueue(new APICallback<APIResponse>(WaterbillActivity.this) {
                @Override
                protected void onSuccess(APIResponse apiResponse) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterPinDialog.stopLoading();
                            enterPinDialog.dismiss();
                        }
                    });

                    Intent intent = new Intent(WaterbillActivity.this, SuccessTransactionActivity.class);
                    intent.putExtra("RECEIPT", Parcels.wrap(apiResponse.getData().getReceipt()));
                    intent.putExtra("IS_PRODUCT", true);
                    intent.putExtra("PRODUCT_TYPE", "LISTRIK");
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    Toast.makeText(WaterbillActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterPinDialog.stopLoading();
                            enterPinDialog.dismiss();
                        }
                    });
                }
            });
        } catch (Exception exception) {
            Log.e("PAY PPOB", "" + exception);
        }
    }

    private void requestForgotPassword() {
        enterPinDialog.enableForgotPassword(false);
        runOnUiThread(new Runnable() {
            public void run() {
                Extension.showLoading(WaterbillActivity.this);
            }
        });

        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("email", API.currentUser().getEmail());
        MultipartBody requestBody = buildernew.build();

        API.service().forgotPassword(requestBody).enqueue(new APICallback<APIResponse>(WaterbillActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                enterPinDialog.enableForgotPassword(true);

                AlertDialog alertDialog = new AlertDialog.Builder(WaterbillActivity.this).create();
                alertDialog.setTitle(getString(R.string.successful));
                alertDialog.setMessage(getString(R.string.forgot_password_successful));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }

            @Override
            protected void onError(BadRequest error) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                enterPinDialog.enableForgotPassword(true);

                if (error.code == 400) {
                    AlertDialog alertDialog = new AlertDialog.Builder(WaterbillActivity.this).create();
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

                        AlertDialog alertDialog = new AlertDialog.Builder(WaterbillActivity.this).create();
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
                        Log.e("forgotPassword", "" + exception);
                        AlertDialog alertDialog = new AlertDialog.Builder(WaterbillActivity.this).create();
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
