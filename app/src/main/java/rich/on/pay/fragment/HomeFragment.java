package rich.on.pay.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rich.on.pay.R;
import rich.on.pay.activity.MainActivity;
import rich.on.pay.activity.TopupAmountSelectionActivity;
import rich.on.pay.adapter.ProductPaymentAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.BaseFragment;
import rich.on.pay.model.APIModels;
import rich.on.pay.model.PaymentProduct;
import rich.on.pay.utils.BannerImageLoader;
import rich.on.pay.utils.Extension;

public class HomeFragment extends BaseFragment implements MainActivity.OnHomeTabListener{

    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.banner)
    Banner banner;
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
    private MainActivity mActivity;

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
                }
            });

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
                    Extension.setImage(getActivity(), ivBalance, (tab.getPosition() == 0 ? R.drawable.wallet : R.drawable.point));
                    tvBalance.setText((tab.getPosition() == 0 ? String.valueOf("Rp 1.000.000") : String.valueOf("10.000")));
                    btnBalanceAction.setText((tab.getPosition() == 0 ? getString(R.string.topup) : getString(R.string.point)));
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
                    switch (category) {
                        case 0:

                            break;
                        case 1:

                            break;
                        case 2:

                            break;
                        case 3:

                            break;
                        case 4:

                            break;
                        default:
                            break;
                    }
                }
            });
            recyclerView.setAdapter(mAdapter);

        } catch (Exception exception) {
            Log.e("onCreate Home", "" + exception);
        }
    }

    @OnClick(R.id.btnBalanceAction)
    void balanceAction(){
        if (btnBalanceAction.getText().toString().matches(getString(R.string.topup))){
            startActivity(new Intent(getActivity(), TopupAmountSelectionActivity.class));
        } else {

        }
    }

    @Override
    public void refreshProfile() {
        swipeRefresh.setRefreshing(false);
        setProfile();
    }

    @Override
    public void onDataReceived(APIModels response) {
        List<String> images = new ArrayList<>();
        images.add("https://mareco.s3.ap-southeast-1.amazonaws.com/images/1536749855_KkNZJepOF75RDa3.jpeg");
        images.add("http://s3.amazonaws.com/newlife-images/photos/_1300x600_crop_top-center_75/Reaching-Gods-Goal-in-2015.jpg");
        images.add("https://betterandfree.co/wp-content/uploads/2018/01/2018_New_Year.jpeg");
        images.add("https://mareco.s3.ap-southeast-1.amazonaws.com/images/1531194516_DX4jIfqN4D3XTPi.jpeg");

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

            }
        });

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
    }

    @Override
    public void onFailure(BadRequest error) {

    }

    private void setProfile(){
        try {
//            tvName.setText(API.currentUser().getFullname());
            tvName.setText("ADE KAM KAM");

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
            Log.e("setProfile HOME", "" + exception);
        }
    }
}
