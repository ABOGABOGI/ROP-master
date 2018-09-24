package rich.on.pay.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.TableLayout;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.base.BaseFragment;

public class OrderParentFragment extends BaseFragment {

    @BindView(R.id.tabLayout)
    TableLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_order_parent;
    }

    @Override
    protected void onViewCreated() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getFragmentManager());
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(5);

        Bundle AllOrderBundle = new Bundle();
        AllOrderBundle.putString("TYPE", "ALL");
        OrderFragment allOrder = new OrderFragment();
        allOrder.setArguments(AllOrderBundle);
        viewPagerAdapter.addPage(allOrder, "");

        Bundle waitingBundle = new Bundle();
        waitingBundle.putString("TYPE", "WAITING");
        OrderFragment waitingOrder = new OrderFragment();
        waitingOrder.setArguments(waitingBundle);
        viewPagerAdapter.addPage(waitingOrder, "");

        Bundle canceledBundle = new Bundle();
        canceledBundle.putString("TYPE", "WAITING");
        OrderFragment canceledOrder = new OrderFragment();
        canceledOrder.setArguments(canceledBundle);
        viewPagerAdapter.addPage(canceledOrder, "");

        Bundle ongoingBundle = new Bundle();
        ongoingBundle.putString("TYPE", "WAITING");
        OrderFragment ongoingOrder = new OrderFragment();
        ongoingOrder.setArguments(ongoingBundle);
        viewPagerAdapter.addPage(ongoingOrder, "");

        viewPager.setAdapter(viewPagerAdapter);
    }
}
