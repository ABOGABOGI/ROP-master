package rich.on.pay.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.adapter.BankLogoAdapter;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.model.PaymentProduct;

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

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_topup_bank_selection;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.isi_saldo);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        BankLogoAdapter mAdapter = new BankLogoAdapter(TopupBankSelectionActivity.this, new BankLogoAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int id, int category) {

            }
        });
        recyclerView.setAdapter(mAdapter);

        List<PaymentProduct> paymentProducts = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            PaymentProduct ppob = new PaymentProduct();

            if (i == 0) {
                ppob.setCoverUrl("https://i.pinimg.com/originals/24/f6/00/24f600fd0dff7001eb97394381e43c04.jpg");
            } else if (i == 1) {
                ppob.setCoverUrl("https://i.pinimg.com/originals/78/e1/2f/78e12fcea229b3e3a9989bd6ef66a095.jpg");
                ppob.setProduct("Listrik");
            } else {
                ppob.setCoverUrl("https://i.pinimg.com/originals/df/79/1f/df791fca4780b213abd4087290f31134.jpg");
                ppob.setProduct("Voucher Game");
            }
            paymentProducts.add(ppob);
        }

        mAdapter.setItems(paymentProducts);
    }

    @OnClick(R.id.btnPay)
    void payOrder(){
        startActivity(new Intent(this, OrderDetailActivity.class));
    }
}
