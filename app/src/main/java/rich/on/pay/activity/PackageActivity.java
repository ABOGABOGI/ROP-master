package rich.on.pay.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
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
    @BindView(R.id.btnUpgradePackage)
    Button btnUpgradePackage;

    private int selectedPackage = 0;

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

        // UPDATE WITH API USING LOOPING IF API PROVIDES INFO
        // SILVER
        Bundle bundle = new Bundle();
        bundle.putInt("PACKAGE", 0);
        bundle.putDouble("FEE", 1000000);
        bundle.putInt("CASHBACK", 2);
        bundle.putDouble("INCOME", 300000);
        bundle.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment silverPackage = new PackageFragment();
        silverPackage.setArguments(bundle);
        viewPagerAdapter.addPage(silverPackage, "");

        // GOLD
        Bundle bundle1 = new Bundle();
        bundle1.putInt("PACKAGE", 1);
        bundle1.putDouble("FEE", 3000000);
        bundle1.putInt("CASHBACK", 3);
        bundle1.putDouble("INCOME", 1200000);
        bundle1.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment goldPackage = new PackageFragment();
        goldPackage.setArguments(bundle1);
        viewPagerAdapter.addPage(goldPackage, "");


        Bundle bundle2 = new Bundle();
        bundle2.putInt("PACKAGE", 2);
        bundle2.putDouble("FEE", 5000000);
        bundle2.putInt("CASHBACK", 5);
        bundle2.putDouble("INCOME", 3600000);
        bundle2.putString("BONUS", "Bonus Tour dalam dan luar negeri hingga Honda HR-V Prestige.");
        PackageFragment platinumPackage = new PackageFragment();
        platinumPackage.setArguments(bundle2);
        viewPagerAdapter.addPage(platinumPackage, "");

        viewPager.setAdapter(viewPagerAdapter);
        indicator.setViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedPackage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick(R.id.btnUpgradePackage)
    void upgradePackage() {
//        if (){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.sorry));
        alertDialog.setMessage(getString(R.string.only_verified_member_can_upgrade));
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.later),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.verification),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(PackageActivity.this, R.color.textGrey));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(PackageActivity.this, R.color.colorPrimary));
//        } else {
//
//        }
    }
}
