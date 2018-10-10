package com.richonpay.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import com.richonpay.R;
import com.richonpay.activity.MainActivity;
import com.richonpay.activity.WebViewActivity;
import com.richonpay.adapter.ProductPaymentAdapter;
import com.richonpay.api.API;
import com.richonpay.api.BadRequest;
import com.richonpay.base.BaseFragment;
import com.richonpay.model.APIModels;
import com.richonpay.model.Banner;
import com.richonpay.model.PaymentProduct;
import com.richonpay.utils.BannerImageLoader;
import com.richonpay.utils.Extension;

public class HomeFragment extends BaseFragment implements MainActivity.OnHomeTabListener {

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPackage)
    TextView tvPackage;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.ivBalance)
    ImageView ivBalance;
    @BindView(R.id.tvBalance)
    TextView tvBalance;
    @BindView(R.id.btnBalanceAction)
    Button btnBalanceAction;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ProductPaymentAdapter mAdapter;
    private int selectedBalanceTab = 0;
    private MainActivity mActivity;
    private List<Banner> bannerList = new ArrayList<>();

    @Override
    protected int getContentViewResource() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onViewCreated() {
        try {
            mActivity = (MainActivity) getActivity();
            mActivity.setHomeTabListener(this);
            swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
            swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    mActivity.fetchProfile();
                    mActivity.getHomeBanner();
                }
            });
            mActivity.getHomeBanner();

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int width = displayMetrics.widthPixels;

            float calculatedSize = width / 16f;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Integer.parseInt(String.valueOf(Math.round(calculatedSize))), Integer.parseInt(String.valueOf(Math.round(calculatedSize))));
            layoutParams.gravity = Gravity.CENTER;
            ivBalance.setLayoutParams(layoutParams);

            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.balance)));
            tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.point)));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    selectedBalanceTab = tab.getPosition();
                    Extension.setImage(getActivity(), ivBalance, (tab.getPosition() == 0 ? R.drawable.wallet : R.drawable.point));
                    tvBalance.setText((selectedBalanceTab == 0 ? Extension.priceFormat(API.currentUser().getWallets().get(0).getBalance()) : Extension.numberPriceFormat(API.currentUser().getWallets().get(2).getBalance())));
                    btnBalanceAction.setText((tab.getPosition() == 0 ? getString(R.string.topup) : getString(R.string.exchange)));
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
            tabLayout.getTabAt(0).select();

            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            mAdapter = new ProductPaymentAdapter(getActivity(), new ProductPaymentAdapter.OnItemClickListener() {
                @Override
                public void onClick(View view, int id, int category) {
//                    switch (category) {
//                        case 0:
//
//                            break;
//                        case 1:
//
//                            break;
//                        case 2:
//
//                            break;
//                        case 3:
//
//                            break;
//                        case 4:
//
//                            break;
//                        default:
//                            break;
//                    }
                    comingSoonDialog();

                }
            });
            recyclerView.setAdapter(mAdapter);

        } catch (Exception exception) {
            Log.e("onCreate Home", "" + exception);
        }
    }

    @OnClick(R.id.btnBalanceAction)
    void balanceAction() {
//        if (btnBalanceAction.getText().toString().matches(getString(R.string.topup))) {
//            startActivity(new Intent(getActivity(), TopupAmountSelectionActivity.class));
//        } else {
        comingSoonDialog();
//        }
    }

    @Override
    public void refreshProfile() {
        swipeRefresh.setRefreshing(false);
        setProfile();
    }

    @Override
    public void onDataReceived(APIModels response) {
        try {
            bannerList = response.getBanners();
            List<String> images = new ArrayList<>();
            for (Banner banner : bannerList) {
                images.add(banner.getCoverUrl());
            }

            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            banner.setIndicatorGravity(BannerConfig.CENTER);
            // set the image loader
            banner.setImageLoader(new BannerImageLoader());
            //Set the picture collection
            banner.setImages(images);
            //Set banner animation
            banner.setBannerAnimation(Transformer.Default);
            // set the automatic rotation, the default is to true
            banner.isAutoPlay(true);
            // Set rotation time
            banner.setDelayTime(10000);
            // set the pointer position (when there banner mode indicator)
            banner.start();
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("TYPE", WebViewActivity.WEBVIEW_TYPE[1]);
                    intent.putExtra("TITLE", String.valueOf(bannerList.get(position).getTitle()));
                    intent.putExtra("WEBVIEW_URL", String.valueOf(bannerList.get(position).getShareUrl()));
                    startActivity(intent);
                }
            });
        } catch (Exception exception) {
            Log.e("ONRECEIVE_BANNER", "" + exception);
        }
    }

    @Override
    public void onFailure(BadRequest error) {

    }

    private void setProfile() {
        try {
            tvName.setText(API.currentUser().getFullname());

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

            Extension.setImage(getActivity(), ivBalance, (selectedBalanceTab == 0 ? R.drawable.wallet : R.drawable.point));
            tvBalance.setText((selectedBalanceTab == 0 ? Extension.priceFormat(API.currentUser().getWallets().get(0).getBalance()) : Extension.numberPriceFormat(API.currentUser().getWallets().get(2).getBalance())));
            btnBalanceAction.setText((selectedBalanceTab == 0 ? getString(R.string.topup) : getString(R.string.exchange)));

            List<PaymentProduct> paymentProducts = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                PaymentProduct ppob = new PaymentProduct();

                if (i == 0) {
                    ppob.setProduct("Pulsa");
                } else if (i == 1) {
                    ppob.setProduct("Listrik");
                } else {
                    ppob.setProduct("Voucher Game");
                }
                paymentProducts.add(ppob);
            }

            mAdapter.setItems(paymentProducts);

        } catch (Exception exception) {
            Log.e("setProfile HOME", "" + exception);
        }
    }

    private void comingSoonDialog() {
        AlertDialog cameraDialog = new AlertDialog.Builder(getActivity()).create();
        cameraDialog.setCanceledOnTouchOutside(false);
        cameraDialog.setTitle(getString(R.string.coming_soon));
        cameraDialog.setMessage(getString(R.string.this_feature_is_still_on_development));
        cameraDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.oke),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        cameraDialog.show();
        cameraDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
    }
}
