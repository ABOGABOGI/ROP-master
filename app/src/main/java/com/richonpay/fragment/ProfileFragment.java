package com.richonpay.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.richonpay.R;
import com.richonpay.activity.AccountSettingActivity;
import com.richonpay.activity.LoginActivity;
import com.richonpay.activity.MainActivity;
import com.richonpay.activity.PackageActivity;
import com.richonpay.activity.VerifyUserAccountActivity;
import com.richonpay.activity.WebViewActivity;
import com.richonpay.adapter.ItemListAdapter;
import com.richonpay.api.API;
import com.richonpay.api.APICallback;
import com.richonpay.api.BadRequest;
import com.richonpay.base.BaseFragment;
import com.richonpay.model.APIModels;
import com.richonpay.model.APIResponse;
import com.richonpay.model.ItemList;
import com.richonpay.model.VerificationStatus;
import com.richonpay.utils.Extension;

public class ProfileFragment extends BaseFragment implements MainActivity.OnAccountTabListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPhone)
    TextView tvPhone;
    @BindView(R.id.tvPackage)
    TextView tvPackage;
    @BindView(R.id.llReferral)
    LinearLayout llReferral;
    @BindView(R.id.tvReferralCode)
    TextView tvReferralCode;
    @BindView(R.id.btnShareReferral)
    Button btnShareReferral;
    @BindView(R.id.llUpgrade)
    LinearLayout llUpgrade;
    @BindView(R.id.btnUpgradePackage)
    Button btnUpgradePackage;
    @BindView(R.id.llBonusMutation)
    LinearLayout llBonusMutation;
    @BindView(R.id.ivBonusMutation)
    ImageView ivBonusMutation;
    @BindView(R.id.llPointMutation)
    LinearLayout llPointMutation;
    @BindView(R.id.ivPointMutation)
    ImageView ivPointMutation;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnLogout)
    Button btnLogout;

    private MainActivity mActivity;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void onViewCreated() {
        try {
            recyclerView.setFocusable(false);
            tvName.requestFocus();
            mActivity = (MainActivity) getActivity();
            mActivity.setAccountTabListener(this);
            swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mActivity.fetchProfile();
                }
            });

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            float calculatedSize = width / 14f;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedSize))), Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
            layoutParams.gravity = Gravity.CENTER;
            ivBonusMutation.setLayoutParams(layoutParams);
            ivPointMutation.setLayoutParams(layoutParams);

            Extension.setImage(getActivity(), ivBonusMutation, R.drawable.bonus_mutation);
            Extension.setImage(getActivity(), ivPointMutation, R.drawable.point);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            ItemListAdapter mAdapter = new ItemListAdapter(getActivity(), new ItemListAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int position, ItemList exploreCategory) {
                    switch (position) {
                        case 0:  //  BALANCE MUTATION
//                            Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
//                            balanceIntent.putExtra("TYPE", TYPE_BALANCE);
//                            startActivity(balanceIntent);
                            comingSoonDialog();
                            break;
                        case 1:  //  ACCOUNT VERIFICATION
                            startActivity(new Intent(getActivity(), VerifyUserAccountActivity.class));
                            break;
                        case 2:  //  NETWORK
                            Intent networkIntent = new Intent(getActivity(), WebViewActivity.class);
                            networkIntent.putExtra("TYPE", WebViewActivity.WEBVIEW_TYPE[2]);
                            startActivity(networkIntent);
                            break;
                        case 3:  //  ACCOUNT SETTING
                            startActivity(new Intent(getActivity(), AccountSettingActivity.class));
                            break;
                        case 4:  //  INFORMATION
                            comingSoonDialog();
//                            startActivity(new Intent(getActivity(), MutationBalanceActivity.class));
                            break;
                        default:
                            break;
                    }
                }
            });
            recyclerView.setAdapter(mAdapter);

            List<ItemList> accountMenu = new ArrayList<>();
            accountMenu.add(new ItemList(R.drawable.balance_mutation, getString(R.string.balance_mutation)));
            accountMenu.add(new ItemList(R.drawable.verify_account, getString(R.string.verify_user_data)));
            accountMenu.add(new ItemList(R.drawable.network, getString(R.string.network)));
            accountMenu.add(new ItemList(R.drawable.account_setting, getString(R.string.account_setting)));
            accountMenu.add(new ItemList(R.drawable.information, getString(R.string.information)));
            mAdapter.setItems(accountMenu);

        } catch (Exception exception) {
            Log.e("onViewCreated", "" + exception);
        }
    }

    @OnClick(R.id.btnUpgradePackage)
    void upgradePackage() {
        startActivity(new Intent(getActivity(), PackageActivity.class));
    }

    @OnClick(R.id.llBonusMutation)
    void redirectBonusMutation() {
//        Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
//        balanceIntent.putExtra("TYPE", TYPE_BONUS);
//        startActivity(balanceIntent);
        comingSoonDialog();
    }


    @OnClick(R.id.llPointMutation)
    void redirectPointMutation() {
//        Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
//        balanceIntent.putExtra("TYPE", TYPE_POINT);
//        startActivity(balanceIntent);
        comingSoonDialog();
    }

    @OnClick(R.id.btnShareReferral)
    void shareReferralCode() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.referral_message) + " " + API.currentUser().getReferralCode());
        sendIntent.setType("text/nbnbnplain");
        startActivity(Intent.createChooser(sendIntent, getResources().getString(R.string.send_to)));
    }

    @OnClick(R.id.btnLogout)
    void logoutAccount() {
        btnLogout.setEnabled(false);
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(Html.fromHtml(getString(R.string.logout_confirmation)));
        builder.setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.showLoading(getActivity());
                        }
                    });

                    API.service().logout().enqueue(new APICallback<APIResponse>(getActivity()) {
                        @Override
                        protected void onSuccess(APIResponse response) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Extension.dismissLoading();
                                }
                            });
                            API.logOut();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        @Override
                        protected void onError(BadRequest error) {
                            getActivity().runOnUiThread(new Runnable() {
                                public void run() {
                                    Extension.dismissLoading();
                                }
                            });
                            API.logOut();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    });
                } catch (Exception exception) {
                    Log.e("ERROR", "" + exception);
                }
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        AlertDialog dialog = builder.show();
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                btnLogout.setEnabled(true);
            }
        });
    }

    @Override
    public void refreshProfile() {
        swipeRefresh.setRefreshing(false);
        setProfile();
    }

    @Override
    public void onDataReceived(APIModels response) {

    }

    @Override
    public void onFailure(BadRequest error) {

    }

    private void setProfile() {
        try {
            tvName.setText(API.currentUser().getFullname());
            tvPhone.setText(API.currentUser().getPhoneNumber());

            switch (API.currentUser().getPackages()) {
                case 0:
                    tvPackage.setText(R.string.free_member);
                    tvPackage.setBackgroundResource(R.drawable.package_free);
                    llBonusMutation.setVisibility(View.GONE);
                    break;
                case 1:
                    tvPackage.setText(R.string.silver_member);
                    tvPackage.setBackgroundResource(R.drawable.package_silver);
                    llBonusMutation.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    tvPackage.setText(R.string.gold_member);
                    tvPackage.setBackgroundResource(R.drawable.package_gold);
                    llBonusMutation.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tvPackage.setText(R.string.platinum_member);
                    tvPackage.setBackgroundResource(R.drawable.package_platinum);
                    llBonusMutation.setVisibility(View.VISIBLE);
                    llUpgrade.setVisibility(View.GONE);
                    break;
            }

            if (API.currentUser().getVerificationStatus().getNric() == VerificationStatus.VERIFIED) {
                llReferral.setVisibility(View.VISIBLE);
                tvReferralCode.setText(API.currentUser().getReferralCode());
            } else {
                llReferral.setVisibility(View.GONE);
            }

        } catch (Exception exception) {
            Log.e("setProfile PROFILE", "" + exception);
        }
    }

    private void comingSoonDialog() {
        android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(getActivity()).create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setTitle(getString(R.string.coming_soon));
        alertDialog.setMessage(getString(R.string.this_feature_is_still_on_development));
        alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }
}
