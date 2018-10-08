package rich.on.pay.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.base.BaseFragment;

import static rich.on.pay.fragment.OrderFragment.TRANSACTION_TYPE;

public class OrderParentFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_order_parent;
    }

    @Override
    protected void onViewCreated() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setPadding(0, 0, 0, 0);
        tabLayout.setTabTextColors(ContextCompat.getColor(getActivity(), R.color.divider_color_dark), ContextCompat.getColor(getActivity(), R.color.colorPrimary));
        tabLayout.removeAllTabs();

        Bundle AllOrderBundle = new Bundle();
        AllOrderBundle.putInt("TRANSACTION_TYPE", TRANSACTION_TYPE[0]);
        OrderFragment allOrder = new OrderFragment();
        allOrder.setArguments(AllOrderBundle);
        viewPagerAdapter.addPage(allOrder, getString(R.string.all));

        Bundle waitingBundle = new Bundle();
        waitingBundle.putInt("TRANSACTION_TYPE", TRANSACTION_TYPE[1]);
        OrderFragment waitingOrder = new OrderFragment();
        waitingOrder.setArguments(waitingBundle);
        viewPagerAdapter.addPage(waitingOrder, getString(R.string.waiting_allcaps));

        Bundle canceledBundle = new Bundle();
        canceledBundle.putInt("TRANSACTION_TYPE", TRANSACTION_TYPE[2]);
        OrderFragment canceledOrder = new OrderFragment();
        canceledOrder.setArguments(canceledBundle);
        viewPagerAdapter.addPage(canceledOrder, getString(R.string.cancel_allcaps));

        Bundle ongoingBundle = new Bundle();
        ongoingBundle.putInt("TRANSACTION_TYPE", TRANSACTION_TYPE[3]);
        OrderFragment ongoingOrder = new OrderFragment();
        ongoingOrder.setArguments(ongoingBundle);
        viewPagerAdapter.addPage(ongoingOrder, getString(R.string.processing_allcaps));

        Bundle refundBundle = new Bundle();
        refundBundle.putInt("TRANSACTION_TYPE", TRANSACTION_TYPE[4]);
        OrderFragment refundedOrder = new OrderFragment();
        refundedOrder.setArguments(refundBundle);
        viewPagerAdapter.addPage(refundedOrder, getString(R.string.refund_allcaps));

        viewPager.setAdapter(viewPagerAdapter);
    }
}
