package com.richonpay.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.richonpay.R;
import com.richonpay.activity.OrderDetailActivity;
import com.richonpay.adapter.OrderAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.BaseFragment;
import com.richonpay.model.APIResponse;
import com.richonpay.model.UpgradeRequest;

import org.parceler.Parcels;

import butterknife.BindView;

public class OrderFragment extends BaseFragment {

    @BindView(R.id.llEmptyList)
    LinearLayout llEmptyList;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final int[] TRANSACTION_TYPE = {
            0, // ALL
            1, // WAITING
            2, // CANCELED
            3, // PROCESSING
            4, // SUCCESSFUL
            5  // REFUNDED
    };
    private OrderAdapter mAdapter;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onViewCreated() {
        try {
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    getTransactionList();
                }
            });

            int transactionType = getArguments().getInt("TRANSACTION_TYPE", TRANSACTION_TYPE[0]);
            mAdapter = new OrderAdapter(getActivity(), new OrderAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int id, UpgradeRequest upgradeRequest) {
                    Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                    intent.putExtra("UPGRADE_REQUEST", Parcels.wrap(upgradeRequest));
                    startActivity(intent);
                }
            });

            getActivity().registerReceiver(mMessageReceiver, new IntentFilter("refresh_transaction"));

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(mAdapter);

            if (transactionType == TRANSACTION_TYPE[0]) {
                getTransactionList();
            }
        } catch (Exception exception) {
            Log.e("fetchProfile", "" + exception);
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Extract data included in the Intent
            Log.e("CALLED", "BROADCAST TRANSACTION");
            getTransactionList();
            //do other stuff here
        }
    };

    private void getTransactionList() {
        try {
            if (swipeRefresh != null) {
                swipeRefresh.setRefreshing(true);
            }
            API.service().getPackageHistory().enqueue(new APICallback<APIResponse>(getActivity()) {
                @Override
                protected void onSuccess(APIResponse response) {
                    if (swipeRefresh != null) {
                        swipeRefresh.setRefreshing(false);
                    }

                    if (response.getData() != null) {
                        if (response.getData().getUpgradeRequests() != null) {
                            if (response.getData().getUpgradeRequests().size() > 0) {
                                llEmptyList.setVisibility(View.GONE);
                                mAdapter.setItems(response.getData().getUpgradeRequests());
                            } else {
                                llEmptyList.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                protected void onError(BadRequest error) {
                    if (swipeRefresh != null) {
                        swipeRefresh.setRefreshing(false);
                    }
                }
            });
        } catch (Exception exception) {
            Log.e("getTransactionList", "" + exception);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(mMessageReceiver);
    }
}
