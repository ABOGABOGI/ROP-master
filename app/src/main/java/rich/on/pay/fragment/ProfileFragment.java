package rich.on.pay.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import rich.on.pay.R;
import rich.on.pay.activity.AccountSettingActivity;
import rich.on.pay.activity.MainActivity;
import rich.on.pay.activity.MutationActivity;
import rich.on.pay.activity.PackageActivity;
import rich.on.pay.activity.VerifyUserAccountActivity;
import rich.on.pay.adapter.ItemListAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.BaseFragment;
import rich.on.pay.model.APIModels;
import rich.on.pay.model.ItemList;
import rich.on.pay.utils.Extension;

import static rich.on.pay.activity.MutationActivity.TYPE_BALANCE;
import static rich.on.pay.activity.MutationActivity.TYPE_BONUS;
import static rich.on.pay.activity.MutationActivity.TYPE_POINT;

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

    private MainActivity mActivity;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void onViewCreated() {
        try {
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
                            Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
                            balanceIntent.putExtra("TYPE", TYPE_BALANCE);
                            startActivity(balanceIntent);
                            break;
                        case 1:  //  ACCOUNT VERIFICATION
                            startActivity(new Intent(getActivity(), VerifyUserAccountActivity.class));
                            break;
                        case 2:  //  NETWORK
//                            startActivity(new Intent(getActivity(), MutationBalanceActivity.class));
                            break;
                        case 3:  //  ACCOUNT SETTING
                            startActivity(new Intent(getActivity(), AccountSettingActivity.class));
                            break;
                        case 4:  //  INFORMATION
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
        Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
        balanceIntent.putExtra("TYPE", TYPE_BONUS);
        startActivity(balanceIntent);
    }

    @OnClick(R.id.llPointMutation)
    void redirectPointMutation() {
        Intent balanceIntent = new Intent(getActivity(), MutationActivity.class);
        balanceIntent.putExtra("TYPE", TYPE_POINT);
        startActivity(balanceIntent);
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
//            tvName.setText(API.currentUser().getFullname());
            tvName.setText("ADE KAM");

            switch (API.currentUser().getPackages()) {
                case 0:
                    tvPackage.setText(R.string.free_member);
                    break;
                case 1:
                    tvPackage.setText(R.string.silver_member);
                    break;
                case 2:
                    tvPackage.setText(R.string.gold_member);
                    break;
                case 3:
                    tvPackage.setText(R.string.platinum_member);
                    break;
            }
        } catch (Exception exception) {
            Log.e("setProfile PROFILE", "" + exception);
        }
    }
}
