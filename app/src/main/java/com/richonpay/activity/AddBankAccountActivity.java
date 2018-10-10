package com.richonpay.activity;

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
import android.widget.Toast;

import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import com.richonpay.R;
import com.richonpay.adapter.StringBankListAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.APIResponse;
import com.richonpay.model.BankAccount;
import com.richonpay.model.BankInfo;
import com.richonpay.utils.Extension;

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
            API.service().getSupportedBank().enqueue(new APICallback<APIResponse>(AddBankAccountActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    bankResponseList = response.getData().getBanks();
                }

                @Override
                protected void onError(BadRequest error) {

                }
            });
//
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_language_selector);
            final RecyclerView listString = dialog.findViewById(R.id.listString);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            listString.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listString.setLayoutManager(layoutManager);
            StringBankListAdapter mAdapter = new StringBankListAdapter(new StringBankListAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, BankInfo bankResponse) {
                    selectedBank = bankResponse;
                    tvBank.setTextColor(ContextCompat.getColor(AddBankAccountActivity.this, R.color.textColorMain));
                    tvBank.setText(String.valueOf(selectedBank.getName() + " (" + selectedBank.getAbbr() + ")"));
                    dialog.dismiss();
                }
            });
            listString.setAdapter(mAdapter);
            mAdapter.setItems(bankResponseList);
            dialog.show();
            dialog.getWindow().setAttributes(lp);
        } catch (Exception exception) {
            Log.e("ERROR", "CHANGE LANGUAGE " + exception);
        }
    }

    @OnClick(R.id.btnSave)
    void addBankAccount() {
        btnSave.setEnabled(false);

        String owner = etOwnerName.getText().toString();
        String accountNumber = etAccountNumber.getText().toString();

        if (selectedBank == null) {
            Toast.makeText(this, R.string.please_select_bank_first, Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(true);
        } else if (owner.length() < 2) {
            Toast.makeText(this, getString(R.string.please_insert_valid_name), Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(true);
            ;
        } else if (accountNumber.length() < 8) {
            Toast.makeText(this, R.string.please_insert_valid_account_number, Toast.LENGTH_SHORT).show();
            btnSave.setEnabled(true);
        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(AddBankAccountActivity.this);
                }
            });
            API.service().addBankAccount(new BankAccount(selectedBank.getId(), owner, accountNumber, 0)).enqueue(new APICallback<APIResponse>(AddBankAccountActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    btnSave.setEnabled(true);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    AlertDialog alertDialog = new AlertDialog.Builder(AddBankAccountActivity.this).create();
                    alertDialog.setCanceledOnTouchOutside(false);
                    alertDialog.setTitle(getString(R.string.success));
                    alertDialog.setMessage(getString(R.string.your_account_has_been_saved));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            onBackPressed();
                        }
                    });
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AddBankAccountActivity.this, R.color.colorPrimary));
                }

                @Override
                protected void onError(BadRequest error) {
                    btnSave.setEnabled(true);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });

                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(AddBankAccountActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    } else {
                        try {
                            StringBuilder errorMessage = new StringBuilder();
                            Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                errorMessage.append(entry.getValue().getAsString());
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(AddBankAccountActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(errorMessage.toString());
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        } catch (Exception exception) {
                            Log.e("loginAPI", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(AddBankAccountActivity.this).create();
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
                    }
                }
            });
        }
    }
}
