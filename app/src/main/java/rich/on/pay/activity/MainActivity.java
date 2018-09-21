package rich.on.pay.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.firebase.MyFirebaseMessagingService;
import rich.on.pay.fragment.HomeFragment;
import rich.on.pay.fragment.NotificationFragment;
import rich.on.pay.fragment.OrderFragment;
import rich.on.pay.fragment.ProfileFragment;
import rich.on.pay.fragment.ScanQRFragment;
import rich.on.pay.model.APIModels;
import rich.on.pay.utils.LockableViewPager;

public class MainActivity extends ToolbarActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivLogo)
    ImageView ivLogo;
    @BindView(R.id.viewPager)
    LockableViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    public static final String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    private boolean doubleBackToExitPressedOnce = false;
    private OnAccountTabListener mAccountTabListener;
    private OnHomeTabListener mHomeTabListener;
    private int selectedTab = 0;


    @Override
    protected int getContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onViewCreated() {
        toolbar.setNavigationIcon(null);

        this.registerReceiver(mMessageReceiver, new IntentFilter("refresh_profile"));
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
        viewPager.setSwipeable(false);
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

        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        for (int i = 0; i < tabStrip.getChildCount(); i++) {
            tabStrip.getChildAt(2).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    int action = event.getAction();
                    if (action == MotionEvent.ACTION_DOWN) {
                        showScanQRDialog();
                        return true;
                    }
                    return true;
                }
            });
        }

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
                        tab3.getCustomView().findViewById(R.id.icon).setBackgroundResource(R.drawable.notif_active);
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

    private void showAddBankDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.important));
        alertDialog.setMessage(getString(R.string.popup_add_bank_account_message));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.later),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.add_masukkan),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
    }

    private void showScanQRDialog() {
        AlertDialog cameraDialog = new AlertDialog.Builder(MainActivity.this).create();
        cameraDialog.setCanceledOnTouchOutside(false);
        cameraDialog.setTitle(getString(R.string.sorry));
        cameraDialog.setMessage(getString(R.string.this_feature_is_still_on_development));
        cameraDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        if (!cameraDialog.isShowing()) {
            cameraDialog.show();
            cameraDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary));
        }
    }

    public interface OnAccountTabListener {

        void refreshProfile();

        void onDataReceived(APIModels response);

        void onFailure(BadRequest error);

    }

    public void setAccountTabListener(OnAccountTabListener mAccountTabListener) {
        this.mAccountTabListener = mAccountTabListener;
    }

    public interface OnHomeTabListener {
        void refreshProfile();

        void onDataReceived(APIModels response);

        void onFailure(BadRequest error);
    }

    public void setHomeTabListener(OnHomeTabListener mHomeTabListener) {
        this.mHomeTabListener = mHomeTabListener;
    }

    private void updateProfile() {
        Log.e("CALLED", "updateProfile");
        //REFRESH PROFILE FOR HOME AND PROFILE PAGE
        if (mHomeTabListener != null) {
            mHomeTabListener.refreshProfile();
        }

        if (mAccountTabListener != null) {
            mAccountTabListener.refreshProfile();
        }
    }

    public void fetchProfile() {
        try {
//            API.service().getProfile().enqueue(new APICallback<APIResponse>(this) {
//                @Override
//                protected void onSuccess(APIResponse response) {
//                    if (response.getData() != null) {
//                        API.setUser(response.getData().getUser());
            Intent intent = new Intent("refresh_profile");
            //send broadcast
            MainActivity.this.sendBroadcast(intent);
//                    }
//                }
//
//                @Override
//                protected void onError(BadRequest error) {
//                    if (mAccountTabListener != null) {
//                        mAccountTabListener.onFailure(error);
//                    }
//                }
//            });
        } catch (Exception exception) {
            Log.e("fetchProfile", "" + exception);
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            Log.e("CALLED", "BROADCAST");
            updateProfile();
            //do other stuff here
        }
    };
}
