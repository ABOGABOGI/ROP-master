package com.richonpay;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.hawk.Hawk;
import com.richonpay.api.API;
import com.richonpay.services.GPSTracker;
import com.richonpay.utils.LocaleUtils;

import java.util.Date;
import java.util.UUID;

public class App extends Application {

    public static final String FIREBASE_TOKEN = "FIREBASE_TOKEN";
    public static final String SERVER_TIME = "SERVER_TIME";
    public static final String PING_TIME = "PING_TIME";
    public static final String APP_LANGUAGE = "APP_LANGUAGE";
    public static final String DEVICE_SESSION = "DEVICE_SESSION";
    public static final String IPADDRESS = "202.154.187.3";
    public static final String UPDATE_DIALOG_SHOWN = "UPDATE_DIALOG";
    public static final String SKIPPED_VERSION = "SKIPPED_VERSION";
    public static boolean isAuthorized = false;
    public static App.AuthorizedChangeListener variableChangeListener;
    private static final String SHOW_TRANSACTION = "IS_SHOW_TRANSACTION";
    private static final String SHOW_BALANCE = "IS_SHOW_BALANCE";
    public static final String SEEN_INTRO = "SEEN_INTRO";
    public static final String SEEN_TUTORIAL = "SEEN_TUTORIAL";
    public static final String WAS_BACKGROUND = "WAS_BACKGROUND";
    public static final String AUTOLOCK_DURATION = "AUTOLOCK_DURATION";
    public static boolean wasBackground = false;
    public static boolean onLockScreen = false;
    public static CountDownTimer countDownTimer;
    public static FirebaseAnalytics mFirebaseAnalytics;

    public static void setVariableChangeListener(App.AuthorizedChangeListener variableChangeListener) {
        App.variableChangeListener = variableChangeListener;
    }

    public interface AuthorizedChangeListener {
        void onVariableChanged(boolean isAuthorized);
    }

    public static void signalChanged() {
        if (variableChangeListener != null) {
            App.variableChangeListener.onVariableChanged(isAuthorized);
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        if (!BuildConfig.DEBUG) {
//        Fabric.with(this, new Crashlytics());
//        }
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Hawk.init(this).build();
        LocaleUtils.initialize(this);

//        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
//                .setProgressiveJpegConfig(new SimpleProgressiveJpegConfig())
//                .setResizeAndRotateEnabledForNetwork(true)
//                .setDownsampleEnabled(true)
//                .build();
//        Fresco.initialize(this, config);

        isAuthorized = false;
        new GPSTracker(this);

        Hawk.put(SERVER_TIME, null);
        String deviceSession = Hawk.get(DEVICE_SESSION, "");
        if (deviceSession.length() == 0) {
            Hawk.put(DEVICE_SESSION, String.valueOf(new Date().getTime() + UUID.randomUUID().toString()));
        }

        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Asia/Jakarta"));
        setVariableChangeListener(new App.AuthorizedChangeListener() {
            @Override
            public void onVariableChanged(final boolean isAuthorized) {
                if (API.isLoggedIn()) {
                    initAutolockDuration();
                }
            }
        });
    }

    public static void initAutolockDuration() {
        try {
            countDownTimer = new CountDownTimer(getAutolockDuration(), getAutolockDuration()) {
                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    App.isAuthorized = false;
                }
            }.start();
        } catch (Exception exception) {
            Log.e("INIT AUTOLOCK", "" + exception);
        }
    }

    public static Boolean getIsShowBalance() {
        return Hawk.get(SHOW_BALANCE, true);
    }

    public static Boolean getIsShowTransaction() {
        return Hawk.get(SHOW_TRANSACTION, true);
    }

    public static Boolean setIsShowBalance(Boolean isShow) {
        return Hawk.put(SHOW_BALANCE, isShow);
    }

    public static Boolean setIsShowTransaction(Boolean isShow) {
        return Hawk.put(SHOW_TRANSACTION, isShow);
    }

    public static boolean didntSkipIntro() {
        return Hawk.get(SEEN_INTRO, true);
    }

    public static void setdidntSkipIntro(boolean isShow) {
        Hawk.put(SEEN_INTRO, isShow);
    }

    public static boolean didSkipTutorial() {
        return Hawk.get(SEEN_TUTORIAL, true);
    }

    public static void setdidSkipTutorial(boolean seen) {
        Hawk.put(SEEN_TUTORIAL, seen);
    }

    public static int getAutolockDuration() {
        return Hawk.get(AUTOLOCK_DURATION, 300000);
    }

    public static void setAutolockDuration(int duration) {
        Hawk.put(AUTOLOCK_DURATION, duration);
        initAutolockDuration();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}