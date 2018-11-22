package com.richonpay.activity.payment_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.adapter.PaymentProductSelectionAdapter;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.ExploreCategory;

import org.parceler.Parcels;

import butterknife.BindView;

public class PaymentProductSelectionActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final int PRODUCT_RESULT = 1022;
    private ExploreCategory productList = null;
    private int type = 0;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_payment_product_selection;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("CATEGORY", 0);
            Log.e("AAAAAAA", type + "");
            switch (type) {
                case 1:  //  1 : Phone_Prepaid
                    tvTitle.setText(R.string.nominal);
                    break;
                case 2:  //  2 : Phone_Postpaid

                    break;
                case 3:  //  3 : PLN_PREPAID
                    tvTitle.setText(R.string.nominal);
                    break;
                case 5:  //  4 : PLN_POSTPAID

                    break;
                case 4:  //  4 : GAME
                    tvTitle.setText(R.string.game_voucher);
                    break;
            }

            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            final PaymentProductSelectionAdapter mAdapter = new PaymentProductSelectionAdapter(type, new PaymentProductSelectionAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, String category) {
                    Intent intent = new Intent();
                    intent.putExtra("SELECTED_INDEX", position);
                    setResult(PRODUCT_RESULT, intent);
                    finish();
                }
            });
            recyclerView.setAdapter(mAdapter);

            productList = Parcels.unwrap(getIntent().getParcelableExtra("PRODUCT_LIST"));
            if (productList != null) {
                mAdapter.setItems(productList.getPaymentProducts());
            }
        }
    }
}
