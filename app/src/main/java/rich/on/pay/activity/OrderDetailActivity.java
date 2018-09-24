package rich.on.pay.activity;

import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.utils.Extension;

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
    @BindView(R.id.btnConfirmation)
    Button btnConfirmation;
    @BindView(R.id.tvCancel)
    TextView tvCancel;

    @Override

    protected int getContentViewResource() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.payment);

        tvTransactionCode.setText(String.valueOf("Kode Transaksi: TOP123123"));
        tvAmount.setText(String.valueOf("Isi Saldo Rp 50.000"));
        tvStatus.setText(R.string.waiting);
        tvDate.setText(String.valueOf("21 Sept 2018 - 02:00 PM"));
        tvTransferBefore.setText(String.valueOf("Transfer sebelum 04:00 AM"));
        tvRemainingTime.setText(String.valueOf("1 Jam dan 57 Menit lagi"));
        tvTotalPayment.setText(Extension.numberPriceFormat(50123));
        tvBankAccount.setText(String.valueOf("0615 867 999"));
        tvAccountOwner.setText(String.valueOf("PT. MARECO PRIMA MANDIRI"));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

        float calculatedSize = width / 16f;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedSize))), Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
        layoutParams.gravity = Gravity.CENTER;
        ivBankLogo.setLayoutParams(layoutParams);
    }

    @OnClick(R.id.btnConfirmation)
    void confirmPayment() {

    }

    @OnClick(R.id.tvCancel)
    void tvCancel() {
        onBackPressed();
    }
}
