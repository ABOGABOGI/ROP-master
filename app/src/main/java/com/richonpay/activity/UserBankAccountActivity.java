package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richonpay.R;
import com.richonpay.adapter.BankListAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.BankAccount;
import com.richonpay.utils.Extension;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class UserBankAccountActivity extends ToolbarActivity {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnAdd)
    Button btnAdd;

    private BankListAdapter mAdapter;
    private BottomSheetDialog bottomSheetDialog;
    private BottomSheetBehavior bottomSheetBehavior;
    private boolean withdrawRequest = false;
    private boolean isCashier = false;
    private List<BankAccount> cashierBankAccounts = new ArrayList<>();

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_user_bank_account;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            withdrawRequest = extra.getBoolean("WITHDRAW", false);
            isCashier = extra.getBoolean("IS_CASHIER", false);
            cashierBankAccounts = Parcels.unwrap(getIntent().getParcelableExtra("BANKLIST"));
        }

        if (isCashier) {
            swipeRefresh.setEnabled(false);
            btnAdd.setVisibility(View.GONE);
        }

        tvTitle.setText(getString(R.string.app_name));
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getBankAccount();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BankListAdapter(this, true, new BankListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, final BankAccount selectedBank) {
                try {
                    if (selectedBank.getIsPrimary() != 1) {
                        View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_bank_primary_delete, null);
                        bottomSheetDialog = new BottomSheetDialog(UserBankAccountActivity.this, R.style.darkPopupAnimation);
                        bottomSheetDialog.setContentView(bottomSheetView);
                        bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
                        bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

                        LinearLayout llSetMain = bottomSheetDialog.findViewById(R.id.llSetMain);
                        LinearLayout llDelete = bottomSheetDialog.findViewById(R.id.llDelete);

                        if (llSetMain != null) {
                            llSetMain.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Extension.showLoading(UserBankAccountActivity.this);
                                        }
                                    });

                                    API.service().setPrimaryBank(selectedBank.getId()).enqueue(new APICallback<APIResponse>(UserBankAccountActivity.this) {
                                        @Override
                                        protected void onSuccess(APIResponse response) {
                                            bottomSheetDialog.dismiss();
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Extension.dismissLoading();
                                                }
                                            });
                                            getBankAccount();
                                        }

                                        @Override
                                        protected void onError(BadRequest error) {
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Extension.dismissLoading();
                                                }
                                            });
                                            AlertDialog alertDialog = new AlertDialog.Builder(UserBankAccountActivity.this).create();
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
                                    });
                                }
                            });
                        }

                        if (llDelete != null) {
                            llDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Extension.showLoading(UserBankAccountActivity.this);
                                        }
                                    });
                                    API.service().removeBankAccount(selectedBank.getId()).enqueue(new APICallback<APIResponse>(UserBankAccountActivity.this) {
                                        @Override
                                        protected void onSuccess(APIResponse response) {
                                            bottomSheetDialog.dismiss();
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Extension.dismissLoading();
                                                }
                                            });
                                            getBankAccount();
                                        }

                                        @Override
                                        protected void onError(BadRequest error) {
                                            runOnUiThread(new Runnable() {
                                                public void run() {
                                                    Extension.dismissLoading();
                                                }
                                            });
                                            AlertDialog alertDialog = new AlertDialog.Builder(UserBankAccountActivity.this).create();
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
                                    });
                                }
                            });
                        }

                        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {

                            }
                        });

                        bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                            }
                        });

                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                        bottomSheetDialog.show();
                    }

                } catch (Exception exception) {
                    Log.e("ERROR", " " + exception);
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        if (isCashier) {
            mAdapter.setItems(cashierBankAccounts);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankAccount();
    }

    @OnClick(R.id.btnAdd)
    void selectBank() {
        startActivity(new Intent(this, AddBankAccountActivity.class));
    }

    private void getBankAccount() {
        swipeRefresh.setRefreshing(true);
        API.service().getUserBankAccount().enqueue(new APICallback<APIResponse>(this) {
            @Override
            protected void onSuccess(APIResponse response) {
                mAdapter.setItems(response.getData().getBankAccounts());
                swipeRefresh.setRefreshing(false);
            }

            @Override
            protected void onError(BadRequest error) {
                Toast.makeText(UserBankAccountActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_COLLAPSED:
                    break;
                case BottomSheetBehavior.STATE_DRAGGING:
                    break;
                case BottomSheetBehavior.STATE_EXPANDED:
                    break;
                case BottomSheetBehavior.STATE_HIDDEN:
                    break;
                case BottomSheetBehavior.STATE_SETTLING:

                    break;
                default:
                    break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {

        }
    };
}
