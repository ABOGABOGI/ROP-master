package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.richonpay.R;
import com.richonpay.adapter.ItemListAdapter;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.ItemList;

public class AccountSettingActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_account_setting;
    }

    @Override
    protected void onViewCreated() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemListAdapter mAdapter = new ItemListAdapter(AccountSettingActivity.this, new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, ItemList exploreCategory) {
                switch (position) {
                    case 0:  //  CHANGE PHONE NUMBER
                        Intent changePhoneIntent = new Intent(AccountSettingActivity.this, ChangePhoneEmailActivity.class);
                        changePhoneIntent.putExtra("TYPE", ChangePhoneEmailActivity.PHONE);
                        startActivityForResult(changePhoneIntent, ChangePhoneEmailActivity.PHONE);
                        break;
                    case 1:  //  CHANGE EMAIL
                        Intent changeEmailIntent = new Intent(AccountSettingActivity.this, ChangePhoneEmailActivity.class);
                        changeEmailIntent.putExtra("TYPE", ChangePhoneEmailActivity.EMAIL);
                        startActivityForResult(changeEmailIntent, ChangePhoneEmailActivity.EMAIL);
                        break;
                    case 2:  //  USER BANK ACCOUNT
                        startActivity(new Intent(AccountSettingActivity.this, UserBankAccountActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        List<ItemList> accountMenu = new ArrayList<>();
        accountMenu.add(new ItemList(R.drawable.account_phone, getString(R.string.change_phone_number)));
        accountMenu.add(new ItemList(R.drawable.account_email, getString(R.string.change_email)));
        accountMenu.add(new ItemList(R.drawable.account_bank, getString(R.string.bank_account)));
        mAdapter.setItems(accountMenu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ChangePhoneEmailActivity.PHONE || resultCode == ChangePhoneEmailActivity.EMAIL) {
                String updatedContent = data.getStringExtra("UPDATE");

                AlertDialog alertDialog = new AlertDialog.Builder(AccountSettingActivity.this).create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle(getString(R.string.success));
                alertDialog.setMessage((resultCode == ChangePhoneEmailActivity.PHONE ?
                        String.valueOf(getString(R.string.your_phone_number_has_been_successfully_changed_to) + " " + updatedContent) :
                        String.valueOf(getString(R.string.your_email_was_successfully_changed_to) + " " + updatedContent)));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                if (!alertDialog.isShowing()) {
                    alertDialog.show();
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(AccountSettingActivity.this, R.color.colorPrimary));
                }
            }

        } catch (Exception exception) {
            Log.e("onActivityResult ", "" + exception);
        }
    }
}
