package rich.on.pay.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.fragment.MutationFragment;

public class MutationActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    final public static int TYPE_BONUS = 1;
    final public static int TYPE_BONUS_PENDING = 2;
    final public static int TYPE_BONUS_SUCCESS = 3;
    final public static int TYPE_POINT = 4;
    final public static int TYPE_BALANCE = 5;

    private int type = TYPE_BONUS;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_mutation_bonus;
    }

    @Override
    protected void onViewCreated() {
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            type = extra.getInt("TYPE", TYPE_BONUS);
        }

        initView();
    }

    private void initView() {
        try {
            tabLayout.setVisibility(((type != TYPE_BONUS) ? View.GONE : View.VISIBLE));
            tabLayout.setupWithViewPager(viewPager);
            ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setClipToPadding(false);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setPageMargin(0);

            switch (type) {
                case MutationActivity.TYPE_BONUS:
                    tvTitle.setText(R.string.bonus_mutation);
                    viewPager.setOffscreenPageLimit(3);

                    Bundle bundlePending = new Bundle();
                    bundlePending.putInt("TYPE", TYPE_BONUS_PENDING);
                    MutationFragment pendingFragment = new MutationFragment();
                    pendingFragment.setArguments(bundlePending);
                    viewPagerAdapter.addPage(pendingFragment, getString(R.string.pending));

                    Bundle bundleSuccess = new Bundle();
                    bundleSuccess.putInt("TYPE", TYPE_BONUS_SUCCESS);
                    MutationFragment successFragment = new MutationFragment();
                    successFragment.setArguments(bundleSuccess);
                    viewPagerAdapter.addPage(successFragment, getString(R.string.successful));

                    break;
                case MutationActivity.TYPE_POINT:
                    tvTitle.setText(R.string.point_mutation);
                    Bundle bundlePoint = new Bundle();
                    bundlePoint.putInt("TYPE", type);
                    MutationFragment pointFragment = new MutationFragment();
                    pointFragment.setArguments(bundlePoint);
                    viewPagerAdapter.addPage(pointFragment, "");

                    break;
                case MutationActivity.TYPE_BALANCE:
                    tvTitle.setText(R.string.balance_mutation);
                    Bundle bundleBalance = new Bundle();
                    bundleBalance.putInt("TYPE", type);
                    MutationFragment balanceFragment = new MutationFragment();
                    balanceFragment.setArguments(bundleBalance);
                    viewPagerAdapter.addPage(balanceFragment, "");

                    break;
                default:
                    break;
            }

            viewPager.setAdapter(viewPagerAdapter);

        } catch (Exception exception) {
            Log.e("INITVIEW", "" + exception);
        }
    }
}
