package com.richonpay.activity.transaction_detail;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richonpay.R;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.OptionModel;
import com.richonpay.model.TransactionOrderDetail;
import com.richonpay.utils.Extension;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.OnClick;

import static com.richonpay.utils.Extension.setTextToView;

public class PaymentProductActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivStore)
    ImageView ivStore;
    @BindView(R.id.tvType)
    TextView tvType;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvAmount)
    TextView tvAmount;
    @BindView(R.id.tvMerchantCategory)
    TextView tvMerchantCategory;
    @BindView(R.id.ivMerchantCategory)
    ImageView ivMerchantCategory;
    @BindView(R.id.tvTransactionDetail)
    TextView tvTransactionDetail;
    @BindView(R.id.llTransactionDetail)
    LinearLayout llTransactionDetail;
    @BindView(R.id.tvTransactionID)
    TextView tvTransactionID;
    @BindView(R.id.tvPaymentMethod)
    TextView tvPaymentMethod;
    @BindView(R.id.tvDateTime)
    TextView tvDateTime;
    @BindView(R.id.tvVoucherCode)
    TextView tvVoucherCode;
    @BindView(R.id.tvVoucherType)
    TextView tvVoucherType;
    @BindView(R.id.tvSplitDetail)
    TextView tvSplitDetail;
    @BindView(R.id.llSplitDetail)
    LinearLayout llSplitDetail;
    @BindView(R.id.rcSplitBIll)
    RecyclerView rcSplitBIll;
    @BindView(R.id.tvBillDetail)
    TextView tvBillDetail;
    @BindView(R.id.llBillDetail)
    LinearLayout llBillDetail;
    @BindView(R.id.llDiscount)
    LinearLayout llDiscount;
    @BindView(R.id.tvSplitTotalBill)
    TextView tvSplitTotalBill;
    @BindView(R.id.tvSplitPayment)
    TextView tvSplitPayment;
    @BindView(R.id.llVoucher)
    LinearLayout llVoucher;
    @BindView(R.id.tvAdminFee)
    TextView tvAdminFee;
    @BindView(R.id.tvCode)
    TextView tvCode;
    @BindView(R.id.llCode)
    LinearLayout llCode;
    @BindView(R.id.tvCodeLabel)
    TextView tvCodeLabel;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.tvAdminBank)
    TextView tvAdminBank;
    @BindView(R.id.tvStamp)
    TextView tvStamp;
    @BindView(R.id.tvPpn)
    TextView tvPpn;
    @BindView(R.id.tvPpj)
    TextView tvPpj;
    @BindView(R.id.tvInstallment)
    TextView tvInstallment;
    @BindView(R.id.tvTokenPrice)
    TextView tvTokenPrice;
    @BindView(R.id.llPriceDetail)
    LinearLayout llPriceDetail;
    @BindView(R.id.tvDetail)
    TextView tvDetail;

    @BindView(R.id.tvProductDetail)
    TextView tvProductDetail;
    @BindView(R.id.llProductDetail)
    LinearLayout llProductDetail;
    @BindView(R.id.tvNoMeter)
    TextView tvNoMeter;
    @BindView(R.id.tvRatePower)
    TextView tvRatePower;
    @BindView(R.id.tvFullname)
    TextView tvFullname;
    @BindView(R.id.tvKWHValue)
    TextView tvKWHValue;
    @BindView(R.id.tvStandMeter)
    TextView tvStandMeter;
    @BindView(R.id.llPeriod)
    LinearLayout llPeriod;
    @BindView(R.id.tvPeriod)
    TextView tvPeriod;
    @BindView(R.id.llKWH)
    LinearLayout llKWH;
    @BindView(R.id.tvReferenceNumber)
    TextView tvReferenceNumber;
    @BindView(R.id.tvCustomerId)
    TextView tvCustomerId;
    @BindView(R.id.llRatePower)
    LinearLayout llRatePower;
    @BindView(R.id.tvMeterTitle)
    TextView tvMeterTitle;
    @BindView(R.id.tvReferenceTitle)
    TextView tvReferenceTitle;
    @BindView(R.id.tvBankAdminTitle)
    TextView tvBankAdminTitle;
    @BindView(R.id.tvStampTitle)
    TextView tvStampTitle;
    @BindView(R.id.llTokenPrice)
    LinearLayout llTokenPrice;
    @BindView(R.id.tvPpnTitle)
    TextView tvPpnTitle;
    @BindView(R.id.tvPpjTitle)
    TextView tvPpjTitle;
    @BindView(R.id.tvInstallmentTitle)
    TextView tvInstallmentTitle;

    private TransactionOrderDetail transaction;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_payment_product;
    }

    @Override
    protected void onViewCreated() {

        tvTitle.setText(R.string.transaction_detail);
        transaction = Parcels.unwrap(getIntent().getParcelableExtra("TRANSACTION_DETAIL"));

//        ivStore.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int size = ivStore.getHeight();
//                Log.e("KAM",size+"")
//                if (size > 0) {
//                    ivStore.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(size, size);
//                    layoutParams.setMargins(4, 4, 4, 4);
//                    ivStore.setLayoutParams(layoutParams);
//                    layoutParams.setMargins(8, 8, 8, 8);
//                    ivMerchantCategory.setLayoutParams(layoutParams);
//                }
//            }
//        });

        if (transaction != null) {

            tvDateTime.setText(Extension.receiptDetailDateFormat.format(transaction.getCreatedAt()));

            switch (transaction.getType()) {
                case 8:
                    setTextToView(tvType, getString(R.string.phone));
                    break;
                case 9:
                    setTextToView(tvType, getString(R.string.meter_number));
                    break;
                case 10:
                    setTextToView(tvType, getString(R.string.game_voucher));
                    break;
                case 17:
                    setTextToView(tvType, getString(R.string.customer_number));
                    break;
                default:
                    break;
            }
            setTextToView(tvAmount, Extension.priceFormat(transaction.getTotalAmount()));
//            setTextToView(tvMerchantCategory, transaction.getOrder().getVendor().getPrimaryCategory().getName());
            setTextToView(tvTransactionID, transaction.getReference_number());
            setTextToView(tvSplitTotalBill, Extension.priceFormat(transaction.getPaymentProductAmount()));
            setTextToView(tvSplitPayment, Extension.priceFormat(transaction.getTotalAmount()));
            setTextToView(tvPaymentMethod, transaction.getWalletType() == 0 ? getString(R.string.balance) : getString(R.string.point));
            setTextToView(tvAdminFee, Extension.priceFormat(transaction.getPaymentProductFee()));

            if (transaction.getOrder() != null) {
                if (transaction.getOrder().getVendor() != null) {
                    if (transaction.getOrder().getVendor().getPictureUrl() != null) {
                        Extension.setImage(PaymentProductActivity.this, ivStore, transaction.getOrder().getVendor().getPictureUrl());
                    } else {
                        Extension.setImage(PaymentProductActivity.this, ivStore, R.drawable.app_icon_rop);
                    }

                    if (transaction.getOrder().getVendor().getPrimaryCategory() != null) {
                        if (transaction.getOrder().getVendor().getPrimaryCategory().getPictureUrl() != null) {
                            Extension.setImage(PaymentProductActivity.this, ivMerchantCategory, transaction.getOrder().getVendor().getPrimaryCategory().getCoverUrl());
                        }
                    }
                }

                if (transaction.getOrder().getOrderDetails().size() > 0) {
                    if (transaction.getOrder().getOrderDetails().get(0).getProduct() != null) {
                        setTextToView(tvMerchantCategory, transaction.getOrder().getOrderDetails().get(0).getProduct().getName());
                        if (transaction.getType() == 8) {
                            tvTotal.setText(R.string.total_bill);
                            setTextToView(tvPhone, transaction.getOrder().getOptions().getPhoneNumber());
                            llDiscount.setVisibility(View.VISIBLE);
                            llPriceDetail.setVisibility(View.GONE);
                            tvDetail.setVisibility(View.GONE);

                            setTextToView(tvSplitTotalBill, Extension.priceFormat(transaction.getOrder().getOrderDetails().get(0).getProduct().getPrice()));

                        } else if (transaction.getType() == 9) {
                            tvTotal.setText(R.string.product_price);
                            tvProductDetail.setVisibility(View.VISIBLE);
                            llProductDetail.setVisibility(View.VISIBLE);

                            if (transaction.getOrder().getType() == 2) { // PLN_PREPAID = 2
                                tvDetail.setVisibility(View.VISIBLE);
                                llCode.setVisibility(View.VISIBLE);
                                llDiscount.setVisibility(View.GONE);

                                tvCode.setText(transaction.getOrder().getOptions().getSn());
                                tvCodeLabel.setText(R.string.token_code);

                                llKWH.setVisibility(View.VISIBLE);
                                setTextToView(tvNoMeter, transaction.getOrder().getOptions().getMeterNo());
                                setTextToView(tvKWHValue, String.valueOf(transaction.getOrder().getOptions().getKwh()));
                                setTextToView(tvCustomerId, transaction.getOrder().getOptions().getCustomerId());

                            } else if (transaction.getOrder().getType() == 3) { // PLN_POSTPAID = 3
                                tvDetail.setVisibility(View.GONE);
                                llCode.setVisibility(View.GONE);
                                llKWH.setVisibility(View.GONE);

                                llPeriod.setVisibility(View.VISIBLE);
                                tvStandMeter.setText(R.string.stand_meter);

                                setTextToView(tvNoMeter, transaction.getOrder().getOptions().getCustomerId());
                                setTextToView(tvPeriod, transaction.getOrder().getOptions().getPeriod());
                                setTextToView(tvCustomerId, transaction.getOrder().getOptions().getStandMeter());
                            }

                            setTextToView(tvRatePower, transaction.getOrder().getOptions().getPowerRate());
                            setTextToView(tvFullname, transaction.getOrder().getOptions().getCustomerName());
                            setTextToView(tvReferenceNumber, transaction.getOrder().getOptions().getRefNo());
                            setTextToView(tvPhone, transaction.getOrder().getOptions().getCustomerId());

                            llPriceDetail.setVisibility(View.GONE);
                            OptionModel option = transaction.getOrder().getOptions();
                            tvAdminBank.setText(Extension.priceFormat(option.getAdminCharge()));
                            tvStamp.setText(Extension.priceFormat(option.getStampDuty()));
                            tvPpn.setText(Extension.priceFormat(option.getPpn()));
                            tvPpj.setText(Extension.priceFormat(option.getPpj()));
                            tvInstallment.setText(Extension.priceFormat(option.getInstallment()));
                            tvTokenPrice.setText(Extension.priceFormat(option.getPp()));

                        } else {
                            tvTotal.setText(R.string.total_bill);
                            if (!transaction.getOrder().getOptions().getSn().isEmpty()) {
                                llCode.setVisibility(View.VISIBLE);
                                tvCode.setText(transaction.getOrder().getOptions().getSn());
                            }
                            setTextToView(tvPhone, transaction.getOrder().getOrderDetails().get(0).getProduct().getCategoryName());
                            llDiscount.setVisibility(View.VISIBLE);
                            llPriceDetail.setVisibility(View.GONE);
                            tvDetail.setVisibility(View.GONE);
                        }

                        if (transaction.getOrder().getOrderDetails().get(0).getProduct().getCoverUrl() != null) {
                            Extension.setImageFitCenter(this, ivStore, transaction.getOrder().getOrderDetails().get(0).getProduct().getCoverUrl());
                        }
                        if (transaction.getOrder().getOrderDetails().get(0).getProduct().getPictureUrl() != null) {
                            Extension.setImageFitCenter(this, ivMerchantCategory, transaction.getOrder().getOrderDetails().get(0).getProduct().getPictureUrl());
                        }
                    }

                    if (transaction.getType() == 17) {
                        Log.e("asdf", "WATER BILL");
                        Extension.setImage(PaymentProductActivity.this, ivStore, R.drawable.ic_pdam_transaction);
                        llRatePower.setVisibility(View.GONE);
                        llDiscount.setVisibility(View.GONE);
                        llPriceDetail.setVisibility(View.GONE);
                        llTokenPrice.setVisibility(View.GONE);
                        llPeriod.setVisibility(View.VISIBLE);
                        tvProductDetail.setVisibility(View.VISIBLE);

                        // TRANSACTION DETAIL
                        setTextToView(tvMerchantCategory, "Air PDAM");
                        setTextToView(tvMeterTitle, getString(R.string.customer_number));
                        setTextToView(tvPhone, transaction.getOrder().getOptions().getRef2());

                        // PRODUCT DETAIL
                        setTextToView(tvNoMeter, transaction.getOrder().getOptions().getCustomerId());
                        setTextToView(tvFullname, transaction.getOrder().getOptions().getCustomerName());
                        setTextToView(tvStandMeter, getString(R.string.pam_name));

                        StringBuilder monthsPaid = new StringBuilder();
                        for (int i = 0; i < transaction.getOrder().getOptions().getMonths().size(); i++) {
                            monthsPaid.append(transaction.getOrder().getOptions().getMonths().get(i)).append(" ");
                        }

                        setTextToView(tvPeriod, monthsPaid.toString());

                        setTextToView(tvCustomerId, transaction.getOrder().getOptions().getPdamName());
                        setTextToView(tvReferenceTitle, getString(R.string.receipt_number));
                        setTextToView(tvReferenceNumber, transaction.getOrder().getOptions().getRef2());

                        // BILL DETAIL
                        setTextToView(tvSplitTotalBill, Extension.priceFormat(transaction.getTotalAmount()));

                        setTextToView(tvBankAdminTitle, getString(R.string.total_fine));
                        setTextToView(tvAdminBank, Extension.priceFormat(transaction.getOrder().getOptions().getPenalty()));
                        setTextToView(tvStampTitle, getString(R.string.non_water_bill));
                        setTextToView(tvStamp, Extension.priceFormat(0));
                        setTextToView(tvPpnTitle, getString(R.string.stamp_duty_fee));
                        setTextToView(tvPpn, Extension.priceFormat(0));
                        setTextToView(tvPpjTitle, getString(R.string.admin_fee));
                        setTextToView(tvPpj, Extension.priceFormat(transaction.getOrder().getOptions().getFee()));
                        setTextToView(tvInstallmentTitle, getString(R.string.total_bill));
                        setTextToView(tvInstallment, Extension.priceFormat(transaction.getOrder().getOptions().getAmount()));
                    }
                }
//                if (transaction.getOrder().getPromo() != null) {
//                    llVoucher.setVisibility(View.VISIBLE);
//                    llDiscount.setVisibility(View.VISIBLE);
//
//                    // 0 CASHBACK    1 DISCOUNT
//                    setTextToView(tvVoucherCode, transaction.getOrder().getPromo().getCode());
//                    setTextToView(tvVoucherType, transaction.getOrder().getPromo().getType() == 0 ? getString(R.string.cashback) : getString(R.string.discount));
//                    setTextToView(tvDiscount, transaction.getOrder().getPromo().getType() == 0 ? Extension.priceFormat(transaction.getOrder().getPromo().getAmount()) : String.valueOf(transaction.getOrder().getPromo().getAmount() + "%"));
//
//                } else {
//                    llDiscount.setVisibility(View.GONE);
//                    llVoucher.setVisibility(View.GONE);
//                }
            }
        }
    }

    @OnClick({R.id.tvTransactionDetail, R.id.tvSplitDetail, R.id.tvBillDetail, R.id.tvProductDetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvTransactionDetail: {
                llTransactionDetail.setVisibility((llTransactionDetail.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
                tvTransactionDetail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, (llTransactionDetail.getVisibility() == View.GONE ? R.drawable.arrowdown_white : R.drawable.arrowup_white)), null);
                break;
            }
            case R.id.tvSplitDetail: {
                llSplitDetail.setVisibility((llSplitDetail.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
                tvSplitDetail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, (llSplitDetail.getVisibility() == View.GONE ? R.drawable.arrowdown_white : R.drawable.arrowup_white)), null);
                break;
            }
            case R.id.tvBillDetail: {
                llBillDetail.setVisibility((llBillDetail.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
                tvBillDetail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, (llBillDetail.getVisibility() == View.GONE ? R.drawable.arrowdown_white : R.drawable.arrowup_white)), null);
                break;
            }
            case R.id.tvProductDetail: {
                llProductDetail.setVisibility((llProductDetail.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
                tvProductDetail.setCompoundDrawablesWithIntrinsicBounds(null, null, ContextCompat.getDrawable(this, (llBillDetail.getVisibility() == View.GONE ? R.drawable.arrowdown_white : R.drawable.arrowup_white)), null);
                break;
            }
        }
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

    @OnClick(R.id.tvDetail)
    void flipDetail() {
        llPriceDetail.setVisibility((llPriceDetail.getVisibility() == View.GONE ? View.VISIBLE : View.GONE));
        tvDetail.setText((llPriceDetail.getVisibility() == View.GONE ? R.string.more_detail : R.string.less_detail));
    }
}
