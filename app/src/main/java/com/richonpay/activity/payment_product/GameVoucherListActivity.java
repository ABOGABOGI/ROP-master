package com.richonpay.activity.payment_product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.adapter.VoucherGameAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.ExploreCategory;

import java.util.List;

import butterknife.BindView;

public class GameVoucherListActivity extends ToolbarActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private VoucherGameAdapter mAdapter;
    private String productId;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_game_voucher_list;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            productId = String.valueOf(extra.getInt("PRODUCT_ID"));
        } else {
            productId = null;
        }
        tvTitle.setText(R.string.game_voucher);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new VoucherGameAdapter(this, new VoucherGameAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, ExploreCategory exploreCategory) {
                Intent intent = new Intent(GameVoucherListActivity.this, GameVoucherPhonePrepaidActivity.class);
                intent.putExtra("IS_GAME", true);
                intent.putExtra("VOUCHER_NAME", exploreCategory.getCategory());
                intent.putExtra("PRODUCT_ID", exploreCategory.getId());
                intent.putExtra("CATEGORY_ID", Integer.parseInt(productId));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);
        getVouchers();
    }

    private void getVouchers() {
        API.service().getPaymentProductByType(Integer.parseInt(productId)).enqueue(new APICallback<APIResponse>(this) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {
                List<ExploreCategory> categories = apiResponse.getData().getCategories();
                if (categories.size() > 0) {
                    mAdapter.setItems(apiResponse.getData().getCategories());
                }
            }

            @Override
            protected void onError(BadRequest error) {

            }
        });
    }
}
