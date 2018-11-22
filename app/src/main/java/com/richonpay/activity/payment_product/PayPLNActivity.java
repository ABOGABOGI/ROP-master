package com.richonpay.activity.payment_product;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.richonpay.model.ExploreCategory;
import com.richonpay.model.PaymentProduct;
import com.richonpay.model.PaymentProductBody;
import com.richonpay.model.TransactionOrder;
import com.richonpay.utils.Extension;
import com.richonpay.utils.InsertPinDialog;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

public class PayPLNActivity extends ToolbarActivity {
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.radioTagihan)
    RadioButton radioTagihan;
    @BindView(R.id.radioToken)
    RadioButton radioToken;
    @BindView(R.id.etAccountNumber)
    EditText etAccountNumber;
    @BindView(R.id.llBillInformation)
    LinearLayout llBillInformation;
    @BindView(R.id.llBill)
    LinearLayout llBill;
    @BindView(R.id.llSelectAmount)
    LinearLayout llSelectAmount;
    @BindView(R.id.llTotalAmount)
    LinearLayout llTotalAmount;
    @BindView(R.id.btnNext)
    Button btnNext;
    @BindView(R.id.rgType)
    RadioGroup rgType;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTariff)
    TextView tvTariff;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvBillAmount)
    TextView tvBillAmount;
    @BindView(R.id.llBillAmount)
    LinearLayout llBillAmount;
    @BindView(R.id.tvPeriod)
    TextView tvPeriod;
    @BindView(R.id.tvTotalFine)
    TextView tvTotalFine;
    @BindView(R.id.tvAdminFee)
    TextView tvAdminFee;
    @BindView(R.id.tvTotalBill)
    TextView tvTotalBill;
    @BindView(R.id.tvMeterNum)
    TextView tvMeterNum;
    @BindView(R.id.etAmount)
    TextInputEditText etAmount;
    @BindView(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    @BindView(R.id.tvStandMeter)
    TextView tvStandMeter;
    @BindView(R.id.llStandMeter)
    LinearLayout llStandMeter;
    @BindView(R.id.llCustomerId)
    LinearLayout llCustomerId;
    @BindView(R.id.tvCustomerId)
    TextView tvCustomerId;

    private TransactionOrder order;
    private int categoryId;
    private PaymentProductBody paymentProductBody = new PaymentProductBody();
    private PaymentProduct selectedProduct;
    private List<PaymentProduct> paymentProductList;
    private PaymentProductBody inquiryBody = new PaymentProductBody();
    private ExploreCategory selectedCategory = null;

    private InsertPinDialog enterPinDialog;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_pay_pln;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            categoryId = extra.getInt("CATEGORY");
        }

        etAccountNumber.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                inquiryBody.setCustomerId(etAccountNumber.getText().toString());
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    API.service().inquiryPLN(categoryId, inquiryBody).enqueue(new APICallback<APIResponse>(PayPLNActivity.this) {
                        @Override
                        protected void onSuccess(APIResponse apiResponse) {
                            order = apiResponse.getData().getOrder();
                            llBillInformation.setVisibility(View.VISIBLE);
                            llTotalAmount.setVisibility(View.VISIBLE);
                            tvTariff.setText(order.getOptions().getPowerRate());
                            tvName.setText(order.getOptions().getCustomerName());
                            if (radioTagihan.isChecked()) {
                                tvMeterNum.setText(order.getOptions().getCustomerId());
                                tvBillAmount.setText(String.valueOf(order.getOptions().getBillCount()));
                                llBill.setVisibility(View.VISIBLE);
                                llSelectAmount.setVisibility(View.GONE);
                                llBillAmount.setVisibility(View.VISIBLE);
                                tvAdminFee.setText(Extension.priceFormat(Math.round(order.getOptions().getAdminCharge())));
                                tvPeriod.setText(order.getOptions().getPeriod());
                                tvTotalFine.setText(Extension.priceFormat(order.getOptions().getPenalty()));
                                tvTotalBill.setText(Extension.priceFormat(order.getOptions().getAmount()));
                                tvTotalAmount.setText(Extension.priceFormat(order.getOptions().getTotalAmount()));
                                llStandMeter.setVisibility(View.VISIBLE);
                                tvStandMeter.setText(order.getOptions().getStandMeter());
                                llCustomerId.setVisibility(View.GONE);
//                                tvTotalAmount.setText();
                            } else {
                                tvMeterNum.setText(order.getOptions().getMeterNo());
                                llSelectAmount.setVisibility(View.VISIBLE);
                                llBill.setVisibility(View.GONE);
                                llBillAmount.setVisibility(View.GONE);
                                llStandMeter.setVisibility(View.GONE);
                                paymentProductList = apiResponse.getData().getPaymentProducts();
                                llCustomerId.setVisibility(View.VISIBLE);
                                tvCustomerId.setText(order.getOptions().getCustomerId());
                                if (paymentProductList.size() > 0) {
                                    setSelectedProduct(paymentProductList.get(0));
                                    selectedCategory = new ExploreCategory();
                                    selectedCategory.setId(categoryId);
                                    selectedCategory.setPaymentProducts(paymentProductList);
                                }
                            }
//                                tvBillAmount.setText(order.getOptions().getInstallment());
                        }

                        @Override
                        protected void onError(BadRequest error) {
                            Toast.makeText(PayPLNActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                return false;
            }
        });
        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                llBillInformation.setVisibility(View.GONE);
                llBill.setVisibility(View.GONE);
                llSelectAmount.setVisibility(View.GONE);
                llTotalAmount.setVisibility(View.GONE);
                if (radioTagihan.isChecked()) {
                    inquiryBody.setInquiryType(1);
                } else {
                    inquiryBody.setInquiryType(0);
                }
            }
        });
        radioTagihan.setChecked(true);
        tvBalance.setText(Extension.numberPriceFormat(API.currentUser().getWallets().get(0).getBalance()));
        tvTitle.setText(R.string.electricity_bill);
    }

    @OnClick(R.id.btnNext)
    void btnNext() {
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
    }

    @OnClick({R.id.btnDropdown, R.id.llDropdown})
    void showSelection() {
        try {
            Intent intent = new Intent(this, PaymentProductSelectionActivity.class);
            intent.putExtra("CATEGORY", categoryId);
            intent.putExtra("PRODUCT_LIST", Parcels.wrap(selectedCategory));
            startActivityForResult(intent, PaymentProductSelectionActivity.PRODUCT_RESULT);
        } catch (Exception exception) {
            Log.e("ERROR", "SHOW PLANS " + exception);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == PaymentProductSelectionActivity.PRODUCT_RESULT) {
            int selectedIndex = data.getIntExtra("SELECTED_INDEX", 0);
            setSelectedProduct(paymentProductList.get(selectedIndex));
        }
    }

    public PaymentProduct getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(PaymentProduct selectedProduct) {
        this.selectedProduct = selectedProduct;
        etAmount.setText(Extension.priceFormat(selectedProduct.getAmount()));
        tvTotalAmount.setText(Extension.priceFormat(selectedProduct.getPrice()));
    }

    private void requestPayment() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                enterPinDialog.startLoading();
            }
        });

        if (radioToken.isChecked()) {
            paymentProductBody.setPaymentProductId(selectedProduct.getId());
        }
        paymentProductBody.setWalletType(0);
        paymentProductBody.setOrderId(String.valueOf(order.getId()));
        paymentProductBody.setCustomerId(order.getOptions().getCustomerId());
        paymentProductBody.setPassword(enterPinDialog.getPassword());
        try {
            API.service().paymentPLN(categoryId, paymentProductBody).enqueue(new APICallback<APIResponse>(PayPLNActivity.this) {
                @Override
                protected void onSuccess(APIResponse apiResponse) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            enterPinDialog.stopLoading();
                            enterPinDialog.dismiss();
                        }
                    });

                    Intent intent = new Intent(PayPLNActivity.this, SuccessTransactionActivity.class);
                    intent.putExtra("RECEIPT", Parcels.wrap(apiResponse.getData().getReceipt()));
                    intent.putExtra("IS_PRODUCT", true);
                    intent.putExtra("PRODUCT_TYPE", "LISTRIK");
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    Toast.makeText(PayPLNActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
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
                Extension.showLoading(PayPLNActivity.this);
            }
        });

        MultipartBody.Builder buildernew = new MultipartBody.Builder();
        buildernew.setType(MultipartBody.FORM);
        buildernew.addFormDataPart("email", API.currentUser().getEmail());
        MultipartBody requestBody = buildernew.build();

        API.service().forgotPassword(requestBody).enqueue(new APICallback<APIResponse>(PayPLNActivity.this) {
            @Override
            protected void onSuccess(APIResponse response) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Extension.dismissLoading();
                    }
                });
                enterPinDialog.enableForgotPassword(true);

                AlertDialog alertDialog = new AlertDialog.Builder(PayPLNActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(PayPLNActivity.this).create();
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

                        AlertDialog alertDialog = new AlertDialog.Builder(PayPLNActivity.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(PayPLNActivity.this).create();
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
