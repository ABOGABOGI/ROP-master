package rich.on.pay.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.parceler.Parcels;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.activity.OrderDetailActivity;
import rich.on.pay.adapter.OrderAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.BaseFragment;
import rich.on.pay.model.APIResponse;
import rich.on.pay.model.UpgradeRequest;

public class OrderFragment extends BaseFragment {

    @BindView(R.id.llEmptyList)
    LinearLayout llEmptyList;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    public static final int[] TRANSACTION_TYPE = {
            0, // ALL
            1, // WAITING
            2, // CANCELED
            3, // PROCESSING
            4  // REFUNDED
    };
    private OrderAdapter mAdapter;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_order;
    }

    @Override
    protected void onViewCreated() {
        try {
            int transactionType = getArguments().getInt("TRANSACTION_TYPE", TRANSACTION_TYPE[0]);
            Log.e("asdf", "asdf " + transactionType);
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
            API.service().getPackageHistory().enqueue(new APICallback<APIResponse>(getActivity()) {
                @Override
                protected void onSuccess(APIResponse response) {
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
