package com.richonpay.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.youth.banner.loader.ImageLoader;

/**
 * Created by Winardi on 11/21/2017.
 */

public class BannerImageLoader extends ImageLoader {


    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Extension.setImage(context, imageView, path.toString());
        DisplayMetrics displayMetrics = new DisplayMetrics();
    }
}