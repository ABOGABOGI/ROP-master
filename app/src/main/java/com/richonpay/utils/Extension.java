package com.richonpay.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.richonpay.R;

public class Extension {
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public static SimpleDateFormat sdfMonthName = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
    public static SimpleDateFormat vendorReviewFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a", Locale.getDefault());
    public static SimpleDateFormat sdfTransactionHistory = new SimpleDateFormat("HH:mm:ss dd MMM yyyy", Locale.getDefault());
    public static SimpleDateFormat sdfPayResponse = new SimpleDateFormat("hh:mm a / dd MMM yyyy", Locale.getDefault());
    public static SimpleDateFormat sdfMonth = new SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault());
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
    public static SimpleDateFormat mutationDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
    public static SimpleDateFormat voucherDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    public static SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    public static SimpleDateFormat vendorDateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
    public static SimpleDateFormat cashierDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
    public static SimpleDateFormat updateProfileDateFormat = new SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault());
    public static SimpleDateFormat dateMonthFormat = new SimpleDateFormat("dd MMMM", Locale.getDefault());
    public static SimpleDateFormat sdfAMPM = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    public static SimpleDateFormat sdfAMPMwithSecond = new SimpleDateFormat("hh:mm a", Locale.getDefault());
    public static SimpleDateFormat receiptDateFormat = new SimpleDateFormat("dd MM yyyy - hh:mm a", Locale.getDefault());
    public static SimpleDateFormat receiptDetailDateFormat = new SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault());
    public static SimpleDateFormat notificationDateFormat = new SimpleDateFormat("hh:mm a - dd MMM yyyy", Locale.getDefault());
    public static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private static Dialog dialog;
    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private Location mLastKnownLocation;

    public static void setImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .load(url)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImage(Context context, ImageView view, int drawable) {
        try {
            Glide.with(context)
                    .load(drawable)
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .load(file)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .into(view);

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, Integer drawable) {
        try {
            if (drawable != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .into(view);

                Glide.with(context)
                        .load(drawable)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageFitCenter(Context context, ImageView view, File url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .into(view);

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.fitCenterTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setImageCenterCrop(Context context, ImageView view, int drawable) {
        try {

            Glide.with(context)
                    .load(R.drawable.logo_splash)
                    .into(view);

            Glide.with(context)
                    .load(drawable)
                    .apply(RequestOptions.centerCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                    .into(view);

        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.logo_splash)
                    .into(view);

            if (url != null) {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, int image) {
        try {
            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .into(view);

                Glide.with(context)
                        .load(file)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleStoreImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.logo_splash)
                    .into(view);

            if (url != null && url != "") {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, String url) {
        try {

            Glide.with(context)
                    .load(R.drawable.logo_splash)
                    .into(view);

            if (url != null && url != "") {

                Glide.with(context)
                        .load(url)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, int image) {
        try {
            Glide.with(context)
                    .load(image)
                    .apply(RequestOptions.circleCropTransform())
                    .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                    .into(view);
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setCircleProfileImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .into(view);

                Glide.with(context)
                        .load(file)
                        .apply(RequestOptions.circleCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImageFitCenter(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new RoundedCorners(20), new FitCenter()))
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerImageCenterCrop(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setRoundedCornerStoreBannerImage(Context context, ImageView view, String url) {
        try {
            Glide.with(context)
                    .load(R.drawable.logo_splash)
                    .apply(new RequestOptions().transforms(new FitCenter(), new RoundedCorners(20)))
                    .into(view);

            if (url != null && !url.equals("")) {
                Glide.with(context)
                        .load(url)
                        .apply(new RequestOptions().transforms(new CenterCrop(), new RoundedCorners(20)))
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setProfileImage(Context context, ImageView view, String url) {
        try {
            if (url != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .load(url)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static void setProfileImage(Context context, ImageView view, File file) {
        try {
            if (file != null) {
                Glide.with(context)
                        .load(R.drawable.logo_splash)
                        .load(file)
                        .apply(RequestOptions.centerCropTransform())
                        .apply(RequestOptions.errorOf(R.drawable.logo_splash))
                        .into(view);
            }
        } catch (Exception ex) {
            Log.e("ERROR", "LOAD IMAGE: " + ex);
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", "getIPAddress: " + ex);
        }
        return "";
    }

    public static String priceFormat(double price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        return "Rp " + formatter.format(Math.floor(price));
    }

    public static String numberPriceFormat(double price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(Math.floor(price));
    }

    public static String priceFormatHtml(double price) {
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        formatter.setDecimalFormatSymbols(symbols);
        return "<font color='#000000'>Rp</font> <font color='#00A0C3'>" + formatter.format(price) + "</font>";
    }

    public static void showLoading(final Activity context) {
        try {
            dialog = new Dialog(context, R.style.darkPopupAnimation);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.loading_progressbar);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);

            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setAttributes(lp);

            final TextView tvLoading = dialog.findViewById(R.id.tvLoading);
            ProgressBar indeterminateBar = dialog.findViewById(R.id.indeterminateBar);
            indeterminateBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            tvLoading.setText(R.string.loading);

            if (!dialog.isShowing()) {
                dialog.show();
            }
        } catch (Exception exception) {
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
        }
    }

    public static void dismissLoading() {
        try {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception exception) {
            Log.e("DISMISS LOADING", "" + exception);
        }
    }

    public static void setSeoeuiRegular(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/segoeui-Regular.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setSeoeuiRegular " + exception);
        }
    }

    public static void setSegoeuiBold(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/segoeui-Bold.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setSegoeuiBold " + exception);
        }
    }

    public static void setSogoeuiSemibold(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/segoeui-Semibold.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setSegoeuiBold " + exception);
        }
    }

    public static void setOpenSansRegular(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-regular.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansRegular " + exception);
        }
    }

    public static void setOpenSansItalic(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-italic.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansItalic " + exception);
        }
    }

    public static void setOpenSansSemiBold(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-semibold.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansSemiBold " + exception);
        }
    }

    public static void setOpenSansSemiBoldItalic(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-semibold-italic.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansSemiBoldItalic " + exception);
        }
    }

    public static void setOpenSansBold(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-bold.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansBold " + exception);
        }
    }

    public static void setOpenSansBoldItalic(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-bold-italic.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansBoldItalic " + exception);
        }
    }

    public static void setOpenSansLight(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-light.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansLight " + exception);
        }
    }

    public static void setOpenSansLightItalic(AssetManager getAssets, TextView textview) {
        try {
            Typeface custom_font = Typeface.createFromAsset(getAssets, "fonts/open-sans-light-italic.ttf");
            textview.setTypeface(custom_font);
        } catch (Exception exception) {
            Log.e("ERROR", "setOpenSansLightItalic " + exception);
        }
    }

    public static String countPercentage(int price, int discountedPrice) {
        try {
            float count = 100 - Math.round((Float.valueOf(String.valueOf(discountedPrice)) / Float.valueOf(String.valueOf(price))) * 100);
            return decimalFormat.format(count) + "%";
        } catch (Exception exception) {
            Log.e("ERROR", "countPercentage " + exception);
        }
        return "";
    }

    public static String getRemainingTime(Context context, Date endDate) {
        try {
            Date now = new Date();
            long different = endDate.getTime() - now.getTime();

            if (different <= 0) {
                return context.getString(R.string.transaction_time_has_expired);
            }

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            String minute = String.valueOf(elapsedMinutes);
            String second = String.valueOf(elapsedSeconds);

            if (minute.length() == 1) {
                minute = String.valueOf("0" + minute);
            } else {
                minute = String.valueOf(minute);
            }

            Log.e("asdf", "hour:" + elapsedHours + " min: " + elapsedMinutes + " second: " + second);

            if (elapsedHours != 0) {
                return elapsedHours + " " + context.getString(R.string.hour) + " " + context.getString(R.string.and) + " " + minute + " " + context.getString(R.string.minute) + " " + context.getString(R.string.remain);

            } else if (elapsedMinutes != 0) {
                return minute + " " + context.getString(R.string.minute) + " " + context.getString(R.string.remain);

            } else if (elapsedSeconds != 0) {
                return "1 " + context.getString(R.string.minute) + " " + context.getString(R.string.remain);

            } else {
                return context.getString(R.string.transaction_time_has_expired);
            }
        } catch (Exception exception) {
            Log.e("Remaining Time", "" + exception);
        }
        return "";
    }

//    static LottieAnimationView animationView;
//    static FrameLayout view;
//    static LottieAnimationView secondAnimationView;

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static boolean isToday(Date date1, Date date2) {
        return vendorDateFormat.format(date1).matches(vendorDateFormat.format(date2));
    }

    public static float pixelsToSp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    public static String sensorNumber(String number) {
        try {
            int offset = Math.round(number.length() / 3);
            int sensorNumber = number.length() - (offset * 2);
            StringBuilder stars = new StringBuilder("");
            for (int i = 0; i <= sensorNumber; i++) {
                stars.append("*");
            }
            return number.substring(0, offset) + stars + number.substring(offset * 2 + 1);
        } catch (Exception exception) {
            Log.e("sensorNumber", "" + exception);
            return "";
        }
    }

    public static String sensorName(String name) {
        return name.replaceAll("\\B\\w", "*");
    }

    public static String validatePhoneNumber(String input) {
        if (input.startsWith("0")) {
            StringBuilder sb = new StringBuilder(input);
            sb.deleteCharAt(0);
            return "+62" + sb.toString();
        } else {
            return input;
        }
    }

    public static void setTextToView(TextView view, String string) {
        try {
            view.setText(string);
        } catch (Exception exception) {
            Log.e("SETTEXT", "" + exception);
        }
    }
}