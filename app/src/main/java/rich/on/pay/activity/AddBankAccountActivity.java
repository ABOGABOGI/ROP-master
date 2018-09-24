package rich.on.pay.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.adapter.StringBankListAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.model.APIResponse;
import rich.on.pay.model.BankInfo;

public class AddBankAccountActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvBank)
    TextView tvBank;
    @BindView(R.id.etOwnerName)
    EditText etOwnerName;
    @BindView(R.id.etAccountNumber)
    EditText etAccountNumber;
    @BindView(R.id.btnSave)
    Button btnSave;

    private List<BankInfo> bankResponseList = new ArrayList<>();
    private BankInfo selectedBank = null;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_add_bank_account;
    }

    @Override
    protected void onViewCreated() {
        tvTitle.setText(R.string.add_account);
    }

    @OnClick(R.id.llBankList)
    void selectBank() {
        try {
//            API.service().getBanks().enqueue(new APICallback<APIResponse>(AddBankAccountActivity.this) {
//                @Override
//                protected void onSuccess(APIResponse response) {
//                    bankResponseList = response.getData().getBankList();
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//
//                }
//            });
//
//            final Dialog dialog = new Dialog(this);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dialog_language_selector);
//            final RecyclerView listString = dialog.findViewById(R.id.listString);
//            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
//            lp.copyFrom(dialog.getWindow().getAttributes());
//            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
//            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
//            listString.setHasFixedSize(true);
//            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//            listString.setLayoutManager(layoutManager);
//            StringBankListAdapter mAdapter = new StringBankListAdapter(new StringBankListAdapter.OnItemClickListener() {
//                @Override
//                public void onClick(View view, int position, BankInfo bankResponse) {
//                    selectedBank = bankResponse;
//                    tvBank.setTextColor(ContextCompat.getColor(AddBankAccountActivity.this, R.color.textColorMain));
//                    tvBank.setText(String.valueOf(selectedBank.getName() + " (" + selectedBank.getAbbr() + ")"));
//                    dialog.dismiss();
//                }
//            });
//            listString.setAdapter(mAdapter);
//            mAdapter.setItems(bankResponseList);
//            dialog.show();
//            dialog.getWindow().setAttributes(lp);
        } catch (Exception exception) {
            Log.e("ERROR", "CHANGE LANGUAGE " + exception);
        }
    }

    private void addBankAccount() {
        //  API
        AlertDialog cameraDialog = new AlertDialog.Builder(AddBankAccountActivity.this).create();
        cameraDialog.setCanceledOnTouchOutside(false);
        cameraDialog.setTitle(getString(R.string.success));
        cameraDialog.setMessage(getString(R.string.your_account_has_been_saved));
        cameraDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (!cameraDialog.isShowing()) {
            cameraDialog.show();
            cameraDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AddBankAccountActivity.this, R.color.colorPrimary));
        }
    }
}
