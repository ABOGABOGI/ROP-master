package com.richonpay.fragment;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.adapter.MutationAdapter;
import com.richonpay.base.BaseFragment;
import com.richonpay.model.TransactionOrderDetail;
import com.richonpay.utils.Extension;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.richonpay.activity.MutationActivity.TYPE_BALANCE;
import static com.richonpay.activity.MutationActivity.TYPE_BONUS_PENDING;
import static com.richonpay.activity.MutationActivity.TYPE_BONUS_SUCCESS;
import static com.richonpay.activity.MutationActivity.TYPE_POINT;

public class MutationFragment extends BaseFragment {

    @BindView(R.id.tvContentTitle)
    TextView tvContentTitle;
    @BindView(R.id.tvBonusAmount)
    TextView tvBonusAmount;
    @BindView(R.id.tvBonusDisbursement)
    TextView tvBonusDisbursement;
    @BindView(R.id.llPeriod)
    LinearLayout llPeriod;
    @BindView(R.id.tvStartDate)
    TextView tvStartDate;
    @BindView(R.id.tvEndDate)
    TextView tvEndDate;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private int pastVisiblesItems, visibleItemCount, totalItemCount;
    private Date[] selectedDate = {new Date(), new Date()};
    private Calendar daylimit = Calendar.getInstance();
    private BottomSheetBehavior bottomSheetBehavior;
    private BottomSheetDialog bottomSheetDialog;
    private int mutationType = TYPE_BALANCE;
    private MutationAdapter mAdapter;
    private int pagination = 0;
    private int limit = 10;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_mutation;
    }

    @Override
    protected void onViewCreated() {
        try {
            mutationType = getArguments().getInt("TYPE", TYPE_BALANCE);

            daylimit.add(Calendar.DATE, -1);
            selectedDate[0] = daylimit.getTime();
            selectedDate[1] = daylimit.getTime();

            bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.darkPopupAnimation);

            switch (mutationType) {
                case TYPE_BONUS_PENDING:
                    tvContentTitle.setText(R.string.your_total_pending_bonus);
                    tvBonusDisbursement.setVisibility(View.VISIBLE);
                    break;

                case TYPE_BONUS_SUCCESS:
                    tvContentTitle.setText(R.string.total_successful_bonus);
                    break;

                case TYPE_POINT:
                    tvContentTitle.setText(R.string.your_total_point);
                    break;

                case TYPE_BALANCE:
                    tvContentTitle.setText(R.string.your_total_balance);
                    llPeriod.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

            final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            mAdapter = new MutationAdapter(getActivity(), (mutationType == TYPE_BALANCE), new MutationAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, TransactionOrderDetail transaction) {
                    try {
//                        Intent intent = new Intent(TransactionHistoryActivity.this, TransactionDetailActivity.class);
//                        intent.putExtra("TRANSACTION_DETAIL", Parcels.wrap(transaction));
//                        startActivity(intent);
                    } catch (Exception exception) {
                        Log.e("TRANSACTION DETAIL", "" + exception);
                    }

                }
            });

            recyclerView.setAdapter(mAdapter);
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (dy > 0) { //check for scroll down
                        visibleItemCount = layoutManager.getChildCount();
                        totalItemCount = layoutManager.getItemCount();
                        pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            getTransactions();
                        }
                    }
                }
            });

        } catch (Exception exception) {
            Log.e("onViewCreated", "" + exception);
        }
    }

    @OnClick(R.id.tvStartDate)
    void setStartDate() {
        showDatePicker(true);
    }

    @OnClick(R.id.tvEndDate)
    void setEmdDate() {
        showDatePicker(false);
    }

    private void showDatePicker(final boolean isStartDate) {
        if (!bottomSheetDialog.isShowing()) {
            View bottomSheetView = getLayoutInflater().inflate(R.layout.dialog_datepicker_spinner, null);
            bottomSheetDialog = new BottomSheetDialog(getActivity(), R.style.darkPopupAnimation);
            bottomSheetDialog.setContentView(bottomSheetView);
            bottomSheetBehavior = BottomSheetBehavior.from((View) bottomSheetView.getParent());
            bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback);

            Button btnDone = bottomSheetDialog.findViewById(R.id.btnDone);
            final DatePicker datePicker = bottomSheetDialog.findViewById(R.id.datePicker);

            if (datePicker != null) {
                datePicker.setMaxDate(daylimit.getTime().getTime());

                if (isStartDate) {
                    datePicker.setMaxDate(selectedDate[1].getTime());
                } else {
                    datePicker.setMinDate(selectedDate[0].getTime());
                }

                Calendar calendar = Calendar.getInstance();
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);

                        if (isStartDate) {
                            selectedDate[0] = calendar.getTime();
                        } else {
                            selectedDate[1] = calendar.getTime();
                        }
                    }
                });
            }

            if (btnDone != null && datePicker != null) {
                btnDone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        bottomSheetDialog.dismiss();
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
                    pagination = 0;
                    getTransactions();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            });

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            bottomSheetDialog.show();
        }
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

    private void getTransactions() {
        try {
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            });

            tvStartDate.setText(Extension.cashierDateFormat.format(selectedDate[0]));
            tvEndDate.setText(Extension.cashierDateFormat.format(selectedDate[1]));

            switch (mutationType) {
                case TYPE_BONUS_PENDING:

                    break;

                case TYPE_BONUS_SUCCESS:

                    break;

                case TYPE_POINT:

                    break;

                case TYPE_BALANCE:

                    break;
                default:
                    break;
            }

//            API.service().getCashierTransction(limit, pagination * limit, Extension.apiDateFormat.format(selectedDate[0]), Extension.apiDateFormat.format(selectedDate[1])).enqueue(new APICallbackCashier<APIResponse>(this) {
//                @Override
//                protected void onSuccess(APIResponse response) {
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    });
//
//                    if (pagination == 0) {
//                        mAdapter.setItems(response.getData().getTransactions());
//                    } else {
//                        mAdapter.addItems(response.getData().getTransactions());
//                    }
//                    pagination++;
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//                    Toast.makeText(TransactionHistoryActivity.this, error.errorDetails, Toast.LENGTH_SHORT).show();
//                    getActivity().runOnUiThread(new Runnable() {
//                        public void run() {
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    });
//                }
//            });
        } catch (Exception exception) {
            Log.e("API TRANSACTION ALL", "" + exception);
            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    progressBar.setVisibility(View.GONE);
                }

            });
        }
    }
}
