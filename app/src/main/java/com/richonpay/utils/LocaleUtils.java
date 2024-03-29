package com.richonpay.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.support.annotation.StringDef;
import android.util.Log;

import com.orhanobut.hawk.Hawk;
import com.richonpay.App;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;

public class LocaleUtils {

    public static final String ENGLISH = "en";
    public static final String INDO = "id";
    public static final String CHINESE = "zh";

    @Retention(RetentionPolicy.SOURCE)
    @StringDef({INDO, ENGLISH, CHINESE})
    public @interface LocaleDef {
        String[] SUPPORTED_LOCALES = {INDO, ENGLISH, CHINESE};
    }

    public static void initialize(Context context) {
//        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        if (Locale.getDefault().getLanguage().matches("en")) {
            setLocale(context, ENGLISH);
        } else {
            setLocale(context, INDO);
        }
    }

    public static void initialize(Context context, @LocaleDef String defaultLanguage) {
//        String lang = getPersistedData(context, defaultLanguage);
        setLocale(context, defaultLanguage);
    }

//    public static String getLanguage(Context context) {
//        return getPersistedData(context, Locale.getDefault().getLanguage());
//    }

    public static boolean setLocale(Context context, @LocaleDef String language) {
//        persist(context, language);
        return updateResources(context, language);
    }

    public static boolean setLocale(Context context, int languageIndex) {
//        persist(context, language);
        if (languageIndex >= LocaleDef.SUPPORTED_LOCALES.length) {
            return false;
        }

        return updateResources(context, LocaleDef.SUPPORTED_LOCALES[languageIndex]);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(Hawk.get(App.APP_LANGUAGE, INDO), defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        String defaultLocale = "";
        if (Locale.getDefault().getLanguage().matches("en")) {
            defaultLocale = ENGLISH;
        } else {
            defaultLocale = INDO;
        }
        Log.e("CHECKCHECK", Hawk.get(App.APP_LANGUAGE) + "");
        editor.putString(Hawk.get(App.APP_LANGUAGE, defaultLocale), language);
        editor.apply();
    }

    private static boolean updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return true;
    }
}