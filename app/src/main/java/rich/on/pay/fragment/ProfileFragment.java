package rich.on.pay.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.activity.MainActivity;
import rich.on.pay.activity.PackageActivity;
import rich.on.pay.api.API;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.BaseFragment;
import rich.on.pay.model.APIModels;

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
        } catch (Exception exception) {
            Log.e("onViewCreated", "" + exception);
        }
    }

    @OnClick(R.id.btnUpgradePackage)
    void upgradePackage() {
        startActivity(new Intent(getActivity(), PackageActivity.class));
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
                case 4:
                    tvPackage.setText(R.string.platinum_member);
                    break;
            }
        } catch (Exception exception) {
            Log.e("setProfile PROFILE", "" + exception);
        }
    }
}
