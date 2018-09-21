package rich.on.pay.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import butterknife.BindView;
import me.relex.circleindicator.CircleIndicator;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.fragment.PackageFragment;

public class PackageActivity extends ToolbarActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_package;
    }

    @Override
    protected void onViewCreated() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setClipToPadding(false);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setPageMargin(0);
        viewPager.setOffscreenPageLimit(5);

        // UPDATE WITH API USING LOOPING
        // SILVER
        Bundle bundle = new Bundle();
        bundle.putString("PACKAGE", "0");
        bundle.putFloat("FEE", 1000000);
        bundle.putFloat("CASHBACK", 2);
        bundle.putFloat("INCOME", 300000);
        bundle.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment silverPackage = new PackageFragment();
        silverPackage.setArguments(bundle);
        viewPagerAdapter.addPage(silverPackage, "");

        // GOLD
        Bundle bundle1 = new Bundle();
        bundle1.putString("PACKAGE", "0");
        bundle1.putFloat("FEE", 3000000);
        bundle1.putFloat("CASHBACK", 3);
        bundle1.putFloat("INCOME", 1200000);
        bundle1.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment goldPackage = new PackageFragment();
        silverPackage.setArguments(bundle1);
        viewPagerAdapter.addPage(goldPackage, "");


        Bundle bundle2 = new Bundle();
        bundle2.putString("PACKAGE", "0");
        bundle2.putFloat("FEE", 5000000);
        bundle2.putFloat("CASHBACK", 5);
        bundle2.putFloat("INCOME", 3600000);
        bundle2.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment platinumPackage = new PackageFragment();
        silverPackage.setArguments(bundle2);
        viewPagerAdapter.addPage(platinumPackage, "");

        viewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(viewPager);
    }
}
