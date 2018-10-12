package com.richonpay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.richonpay.R;
import com.richonpay.adapter.ItemListAdapter;
import com.richonpay.base.ToolbarActivity;
import com.richonpay.model.ItemList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

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
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemListAdapter mAdapter = new ItemListAdapter(AccountSettingActivity.this, new ItemListAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position, ItemList exploreCategory) {
                switch (position) {
                    case 0:  //  CHANGE PASSWORD
                        Intent changePasswordIntent = new Intent(AccountSettingActivity.this, ChangePasswordActivity.class);
                        changePasswordIntent.putExtra("TYPE", ChangePhoneEmailPasswordActivity.PASSWORD);
                        startActivityForResult(changePasswordIntent, ChangePhoneEmailPasswordActivity.PASSWORD);
                        break;
                    case 1:  //  CHANGE PHONE NUMBER
                        Intent changePhoneIntent = new Intent(AccountSettingActivity.this, ChangePhoneEmailPasswordActivity.class);
                        changePhoneIntent.putExtra("TYPE", ChangePhoneEmailPasswordActivity.PHONE);
                        startActivityForResult(changePhoneIntent, ChangePhoneEmailPasswordActivity.PHONE);
                        break;
                    case 2:  //  CHANGE EMAIL
                        Intent changeEmailIntent = new Intent(AccountSettingActivity.this, ChangePhoneEmailPasswordActivity.class);
                        changeEmailIntent.putExtra("TYPE", ChangePhoneEmailPasswordActivity.EMAIL);
                        startActivityForResult(changeEmailIntent, ChangePhoneEmailPasswordActivity.EMAIL);
                        break;
                    case 3:  //  USER BANK ACCOUNT
                        startActivity(new Intent(AccountSettingActivity.this, UserBankAccountActivity.class));
                        break;
                    default:
                        break;
                }
            }
        });
        recyclerView.setAdapter(mAdapter);

        List<ItemList> accountMenu = new ArrayList<>();
        accountMenu.add(new ItemList(R.drawable.lock, getString(R.string.change_password)));
        accountMenu.add(new ItemList(R.drawable.account_phone, getString(R.string.change_phone_number)));
        accountMenu.add(new ItemList(R.drawable.account_email, getString(R.string.change_email)));
        accountMenu.add(new ItemList(R.drawable.account_bank, getString(R.string.bank_account)));
        mAdapter.setItems(accountMenu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == ChangePhoneEmailPasswordActivity.PHONE || resultCode == ChangePhoneEmailPasswordActivity.EMAIL || resultCode == ChangePhoneEmailPasswordActivity.PASSWORD) {
                String updatedContent = data.getStringExtra("UPDATE");

                AlertDialog alertDialog = new AlertDialog.Builder(AccountSettingActivity.this).create();
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setTitle(getString(R.string.success));

                switch (resultCode) {
                    case ChangePhoneEmailPasswordActivity.PHONE:
                        alertDialog.setMessage(String.valueOf(getString(R.string.your_phone_number_has_been_successfully_changed_to) + " " + updatedContent));
                        break;
                    case ChangePhoneEmailPasswordActivity.EMAIL:
                        alertDialog.setMessage(String.valueOf(getString(R.string.your_email_was_successfully_changed_to) + " " + updatedContent));
                        break;
                    case ChangePhoneEmailPasswordActivity.PASSWORD:
                        alertDialog.setMessage(getString(R.string.password_has_been_changed));
                        break;
                    default:
                        break;
                }

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
