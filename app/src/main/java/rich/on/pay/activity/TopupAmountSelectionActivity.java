package rich.on.pay.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.adapter.TopUpAmountAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.model.APIResponse;
import rich.on.pay.model.TopUpRequest;
import rich.on.pay.utils.Extension;
import rich.on.pay.utils.NumberTextWatcher;

public class TopupAmountSelectionActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.etAmount)
    EditText etAmount;
    @BindView(R.id.btnProceed)
    Button btnProceed;

    private List<Double> topupList = new ArrayList<>();
    private TopUpAmountAdapter topUpAmountAdapter;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_topup_amount_selection;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.isi_saldo);
        tvBalance.setText(Extension.numberPriceFormat(API.currentUser().getWallets().get(0).getBalance()));

        try {
            etAmount.addTextChangedListener(new NumberTextWatcher(etAmount));
            etAmount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable input) {
                    if (input.length() > 5) {
                        btnProceed.setBackground(ContextCompat.getDrawable(TopupAmountSelectionActivity.this, R.drawable.rounded_primary_button));
                        btnProceed.setEnabled(true);

                        double amount = Double.parseDouble(etAmount.getText().toString().replaceAll(",", ""));
                        if (topupList.contains(amount)) {
                            topUpAmountAdapter.selectItem(topupList.indexOf(amount));
                        } else {
                            topUpAmountAdapter.selectItem(-1);
                        }

                    } else {
                        btnProceed.setBackground(ContextCompat.getDrawable(TopupAmountSelectionActivity.this, R.drawable.button_disabled));
                        topUpAmountAdapter.selectItem(-1);
                        btnProceed.setEnabled(false);
                    }
                }
            });

            topUpAmountAdapter = new TopUpAmountAdapter(this, new TopUpAmountAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, TopUpRequest selectedItem) {
                    etAmount.setText(String.valueOf(selectedItem.getAmount()));
                }
            });

            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(topUpAmountAdapter);

            API.service().getTopupAmountList().enqueue(new APICallback<APIResponse>(this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    try {
                        if (response.getData().getTopup() != null) {
                            for (int i = 0; i < response.getData().getTopup().size(); i++) {
                                topupList.add(response.getData().getTopup().get(i).getAmount());
                            }

                            topUpAmountAdapter.setItems(response.getData().getTopup());
                        }
                    } catch (Exception exception) {
                        Log.e("getTopupAmountList", "" + exception);
                    }
                }

                @Override
                protected void onError(BadRequest error) {

                }
            });
        } catch (Exception exception) {
            Log.e("TOPUP_AMOUNT", "" + exception);
        }
    }

    @OnClick(R.id.btnProceed)
    void submitAmount() {
        startActivity(new Intent(this, TopupBankSelectionActivity.class));
    }
}
