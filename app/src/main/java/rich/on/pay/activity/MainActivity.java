package rich.on.pay.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.firebase.MyFirebaseMessagingService;
import rich.on.pay.fragment.HomeFragment;
import rich.on.pay.fragment.NotificationFragment;
import rich.on.pay.fragment.OrderFragment;
import rich.on.pay.fragment.ProfileFragment;
import rich.on.pay.fragment.ScanQRFragment;

public class MainActivity extends ToolbarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    public static final String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    private boolean doubleBackToExitPressedOnce = false;
    private int selectedTab = 0;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {
        toolbar.setNavigationIcon(null);
        initView();
    }

    private void initView() {
        revertStatusBar();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(5);
        viewPagerAdapter.addPage(new HomeFragment(), getString(R.string.home));
        viewPagerAdapter.addPage(new OrderFragment(), getString(R.string.order));
        viewPagerAdapter.addPage(new ScanQRFragment(), getString(R.string.scan_qr));
        viewPagerAdapter.addPage(new NotificationFragment(), getString(R.string.notification));

        String notificationCommand = "";
        Bundle extra = getIntent().getExtras();
        if (extra != null) {
            notificationCommand = extra.getString(MyFirebaseMessagingService.NOTIFICATION_INTENT, "");
        }

        Bundle bundle = new Bundle();
        bundle.putString(MyFirebaseMessagingService.NOTIFICATION_INTENT, notificationCommand);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        viewPagerAdapter.addPage(profileFragment, getString(R.string.account));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicatorHeight(0);
        tabLayout.setPadding(0, 0, 0, 0);
        tabLayout.setTabTextColors(ContextCompat.getColor(this, R.color.divider_color_dark), ContextCompat.getColor(this, R.color.colorPrimary));
        tabLayout.removeAllTabs();

        View view1 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab1 = view1.findViewById(R.id.title);
        tvTab1.setText(R.string.home);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view1));

        View view2 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab2 = view2.findViewById(R.id.title);
        tvTab2.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textColorMain));
        tvTab2.setText(R.string.order);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view2));

        View view3 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab3 = view3.findViewById(R.id.title);
        tvTab3.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textColorMain));
        tvTab3.setText(R.string.scan_qr);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view3));

        View view4 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab4 = view4.findViewById(R.id.title);
        tvTab4.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textColorMain));
        tvTab4.setText(R.string.notification);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view4));

        View view5 = getLayoutInflater().inflate(R.layout.custom_tab, null);
        TextView tvTab5 = view5.findViewById(R.id.title);
        tvTab5.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textColorMain));
        tvTab5.setText(R.string.account);
        tabLayout.addTab(tabLayout.newTab().setCustomView(view5));

        changeTabSelection(0);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeTabSelection(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                TextView tvTab = tabLayout.getTabAt(position).getCustomView().findViewById(R.id.title);
                tvTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.textColorMain));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                changeTabSelection(position);
            }
        });

        try {
            if (selectedTab != 0) {
                if (tabLayout.getTabAt(selectedTab) != null) {
                    tabLayout.getTabAt(selectedTab).select();
                }
                selectedTab = 0;
            }

//            if (!notificationCommand.matches(""))
//                startActivity(new Intent(this, NotificationsActivity.class));
//            if (notificationCommand.matches("identitas")) {
//                startActivity(new Intent(MainActivity.this, NotificationsActivity.class));
//            } else if (notificationCommand.matches("topup")) {
//                Intent intent = new Intent(new Intent(MainActivity.this, TopupSelectionActivity.class));
//                intent.putExtra("SELECT_TAB", 2);
//                startActivity(intent);
//            } else if (notificationCommand.matches("withdraw")) {
//                Intent intent = new Intent(new Intent(MainActivity.this, WithdrawActivity.class));
//                intent.putExtra("SELECT_TAB", 1);
//                startActivity(intent);
//            }
        } catch (Exception exception) {
            Log.e("ERROR", "" + exception);
        }
    }

    private void changeTabSelection(int position) {
        try {
            TabLayout.Tab tab0 = tabLayout.getTabAt(0);
            TabLayout.Tab tab1 = tabLayout.getTabAt(1);
            TabLayout.Tab tab2 = tabLayout.getTabAt(2);
            TabLayout.Tab tab3 = tabLayout.getTabAt(3);
            TabLayout.Tab tab4 = tabLayout.getTabAt(4);

            if (tab0 != null) {
                tab0.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.home_inactive);
            }

            if (tab1 != null) {
                tab1.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.order_inactive);
            }

            if (tab2 != null) {
                tab2.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.scan_inactive);
            }

            if (tab3 != null) {
                tab3.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.notif_inactive);
            }

            if (tab4 != null) {
                tab4.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.account_inactive);
            }

            switch (position) {
                case 0:
                    if (tab0 != null) {
                        tab0.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.home_active);
                        revertStatusBar();
                    }
                    break;
                case 1:
                    if (tab1 != null) {
                        tab1.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.order_active);
                        addToolbarBackground();
                        tvTitle.setText(getString(R.string.order));
                    }
                    break;
                case 2:
                    if (tab2 != null) {
                        tab2.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.scan_inactive);
                        addToolbarBackground();
                        tvTitle.setText(getString(R.string.scan_qr));
                    }
                    break;
                case 3:
                    if (tab3 != null) {
                        tab3.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.notif_inactive);
                        addToolbarBackground();
                        tvTitle.setText(getString(R.string.notification));
                    }
                    break;
                case 4:
                    if (tab3 != null) {
                        tab3.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.account_inactive);
                        addToolbarBackground();
                        tvTitle.setText(getString(R.string.account));
                    }
                    break;
                default:
                    break;
            }
            TextView tvTab = tabLayout.getTabAt(position).getCustomView().findViewById(R.id.title);
            tvTab.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        } catch (Exception exception) {
            Log.e("ERROR", "CHANGE BOTTOM BAR ICON " + exception);
        }
    }

    private void addToolbarBackground() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        ivLogo.setVisibility(View.GONE);
    }

    private void revertStatusBar() {
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.background));
        tvTitle.setText("");
        ivLogo.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.press_again_to_exit, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
