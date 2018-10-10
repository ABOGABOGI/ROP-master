package com.richonpay.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.orhanobut.hawk.Hawk;

import java.util.List;
import java.util.Locale;

import com.richonpay.App;
import com.richonpay.R;
import com.richonpay.api.API;
import com.richonpay.base.BaseActivity;
import com.richonpay.utils.LocaleUtils;

public class SplashActivity extends BaseActivity {

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onViewCreated() {
//        startActivity(new Intent(this, VerifyUserAccountActivity.class));

        if (String.valueOf(Hawk.get(App.APP_LANGUAGE)).matches("in")) {
            Hawk.put(App.APP_LANGUAGE, LocaleUtils.INDO);
            LocaleUtils.setLocale(this, LocaleUtils.INDO);

        } else if (String.valueOf(Hawk.get(App.APP_LANGUAGE)).matches("en")) {
            Hawk.put(App.APP_LANGUAGE, LocaleUtils.ENGLISH);
            LocaleUtils.setLocale(this, LocaleUtils.ENGLISH);

        } else if (String.valueOf(Hawk.get(App.APP_LANGUAGE)).matches("zh")) {
            Hawk.put(App.APP_LANGUAGE, LocaleUtils.CHINESE);
            LocaleUtils.setLocale(this, LocaleUtils.CHINESE);

        } else if (Locale.getDefault().getLanguage().matches("in")) {
            Hawk.put(App.APP_LANGUAGE, LocaleUtils.INDO);
            LocaleUtils.setLocale(this, LocaleUtils.INDO);
        } else {
            Hawk.put(App.APP_LANGUAGE, LocaleUtils.ENGLISH);
            LocaleUtils.setLocale(this, LocaleUtils.ENGLISH);
        }

        Hawk.put(App.UPDATE_DIALOG_SHOWN, false);

        new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    sleep(1000);
                } catch (Exception e) {
                    Log.e("ERROR", e.getMessage());
                } finally {
                    if (Build.VERSION.SDK_INT > 22) {
                        Dexter.withActivity(SplashActivity.this)
                                .withPermissions(
                                        Manifest.permission.CAMERA,
                                        Manifest.permission.READ_CONTACTS,
                                        Manifest.permission.READ_SMS,
                                        Manifest.permission.RECEIVE_SMS,
                                        Manifest.permission.READ_EXTERNAL_STORAGE,
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION

                                ).withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                next();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();

                    } else {
                        next();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        App.mFirebaseAnalytics.setCurrentScreen(this, "Splash Screen", null);
    }

    private void next() {
        if (API.isLoggedIn()) {
            if (API.currentUser() != null) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
