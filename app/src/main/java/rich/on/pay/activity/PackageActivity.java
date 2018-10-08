package rich.on.pay.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import org.parceler.Parcels;

import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import me.relex.circleindicator.CircleIndicator;
import okhttp3.MultipartBody;
import rich.on.pay.R;
import rich.on.pay.adapter.ViewPagerAdapter;
import rich.on.pay.api.API;
import rich.on.pay.api.APICallback;
import rich.on.pay.api.BadRequest;
import rich.on.pay.base.ToolbarActivity;
import rich.on.pay.fragment.PackageFragment;
import rich.on.pay.model.APIResponse;
import rich.on.pay.utils.Extension;

public class PackageActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
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
        tvTitle.setText(getString(R.string.upgrade_package));
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
                selectedPackage = position;
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
        btnUpgradePackage.setEnabled(false);
        if (API.currentUser().getVerificationStatus().getNric() == 0) {
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
                            startActivity(new Intent(PackageActivity.this, VerifyUserAccountActivity.class));
                        }
                    });
            alertDialog.show();
            alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    btnUpgradePackage.setEnabled(true);
                }
            });
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(PackageActivity.this, R.color.textGrey));
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(PackageActivity.this, R.color.colorPrimary));

        } else {
            runOnUiThread(new Runnable() {
                public void run() {
                    Extension.showLoading(PackageActivity.this);
                }
            });
            MultipartBody.Builder buildernew = new MultipartBody.Builder();
            buildernew.setType(MultipartBody.FORM);
            buildernew.addFormDataPart("package", String.valueOf((selectedPackage + 1)));
            // 0 FREE
            // 1 SILVER
            // 2 GOLD
            // 3 PLATINUM

            MultipartBody requestBody = buildernew.build();

            API.service().requestPackage(requestBody).enqueue(new APICallback<APIResponse>(PackageActivity.this) {
                @Override
                protected void onSuccess(APIResponse response) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnUpgradePackage.setEnabled(true);

                    Intent intent = new Intent(PackageActivity.this, TopupBankSelectionActivity.class);
                    intent.putExtra("PACKAGE", true);
                    intent.putExtra("UPGRADE_REQUEST", Parcels.wrap(response.getData().getUpgradeRequest()));
                    startActivity(intent);
                }

                @Override
                protected void onError(BadRequest error) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Extension.dismissLoading();
                        }
                    });
                    btnUpgradePackage.setEnabled(true);
                    if (error.code == 400) {
                        AlertDialog alertDialog = new AlertDialog.Builder(PackageActivity.this).create();
                        alertDialog.setTitle(getString(R.string.sorry));
                        alertDialog.setMessage(error.errorDetails);
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                    } else {
                        try {
                            StringBuilder errorMessage = new StringBuilder();
                            Set<Map.Entry<String, JsonElement>> entries = error.errors.entrySet();//will return members of your object
                            for (Map.Entry<String, JsonElement> entry : entries) {
                                errorMessage.append(entry.getValue().getAsString());
                            }

                            AlertDialog alertDialog = new AlertDialog.Builder(PackageActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(errorMessage.toString());
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                        } catch (Exception exception) {
                            Log.e("requestPackage", "" + exception);
                            AlertDialog alertDialog = new AlertDialog.Builder(PackageActivity.this).create();
                            alertDialog.setTitle(getString(R.string.sorry));
                            alertDialog.setMessage(error.errorDetails);
                            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.dialog_ok),
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                    }
                }
            });
        }
    }
}
