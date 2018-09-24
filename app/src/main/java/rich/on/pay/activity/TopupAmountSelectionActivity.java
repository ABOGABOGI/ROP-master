package rich.on.pay.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.base.ToolbarActivity;

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

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_topup_amount_selection;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.isi_saldo);
    }

    @OnClick(R.id.btnProceed)
    void submitAmount() {
        startActivity(new Intent(this, TopupBankSelectionActivity.class));
    }
}
