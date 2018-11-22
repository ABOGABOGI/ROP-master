package com.richonpay.activity.payment_product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.adapter.WaterBIllParentAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.PaymentProduct;

import java.util.Timer;

import butterknife.BindView;

public class SearchWaterBillLocationActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.tvSearchResult)
    TextView tvSearchResult;
    @BindView(R.id.llemptyResult)
    LinearLayout llemptyResult;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    private WaterBIllParentAdapter mAdapter;
    private String keyword = "";
    private int ppobID = 5;
    private Timer timer;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_search_water_bill_location;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            ppobID = extra.getInt("CATEGORY", 5);
        }

        llemptyResult.setVisibility(View.GONE);
        tvTitle.setText(R.string.select_region);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new WaterBIllParentAdapter(this, new WaterBIllParentAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, PaymentProduct paymentProduct) {

            }

            @Override
            public void onItemSelected(PaymentProduct paymentProduct) {
                Log.e("asdf", "selected ITEM " + paymentProduct.getId() + ", name= " + paymentProduct.getName());
                Intent intent = new Intent();
                intent.putExtra("ID", paymentProduct.getId());
                intent.putExtra("LOCATION", paymentProduct.getName());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        recyclerView.setAdapter(mAdapter);
        searchLocation();
    }

    private void searchLocation() {
        SearchWaterBillLocationActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        API.service().getPaymentProductByType(ppobID).enqueue(new APICallback<APIResponse>(this) {
            @Override
            protected void onSuccess(APIResponse apiResponse) {

                SearchWaterBillLocationActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                    }
                });

                if (apiResponse.getData().getCategories().size() == 0) {
                    tvSearchResult.setText(getString(R.string.no_results));
                    llemptyResult.setVisibility(View.VISIBLE);
                } else {
                    llemptyResult.setVisibility(View.GONE);
                    mAdapter.setItems(apiResponse.getData().getCategories());
                }
            }

            @Override
            protected void onError(BadRequest error) {

                SearchWaterBillLocationActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        tvSearchResult.setText((keyword.isEmpty() ? String.valueOf(getString(R.string.no_results)) : String.valueOf(getString(R.string.no_results_for) + " " + keyword)));
                        llemptyResult.setVisibility(View.VISIBLE);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception exception) {
            Log.e("DISMISS KEYBOARD", "" + exception);
        }
        super.onBackPressed();
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
}
