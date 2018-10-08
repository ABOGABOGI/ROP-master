package rich.on.pay.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import butterknife.BindView;
import rich.on.pay.BuildConfig;
import rich.on.pay.R;
import rich.on.pay.api.API;
import rich.on.pay.base.ToolbarActivity;

public class WebViewActivity extends ToolbarActivity {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    public static final int[] WEBVIEW_TYPE = {
            0, // OTHER
            1, // BANNER
            2  // NETWORK
    };
    private String webViewTitle = "";
    private String webViewUrl = "";
    private int webViewType = 0;

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void onViewCreated() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        try {
            webView.clearCache(true);
            webView.getSettings().setAppCacheEnabled(false);
            webView.getSettings().setJavaScriptEnabled(true);

            Bundle extra = getIntent().getExtras();
            if (extra != null) {
                webViewType = extra.getInt("TYPE", WEBVIEW_TYPE[0]);
                String screenTitle = extra.getString("TITLE", getString(R.string.app_name));
                webViewUrl = extra.getString("WEBVIEW_URL", String.valueOf(BuildConfig.ROOT_URL));

                webViewTitle = (webViewType == 2 ? getString(R.string.network) : screenTitle);
            }

            tvTitle.setText(webViewTitle);
            switch (webViewType) {
                case 0:
                    webView.loadUrl(webViewUrl);
                    break;
                case 1:
                    tvTitle.setText("");
                    webView.loadUrl(webViewUrl);
                    break;
                case 2:
                    if (API.isLoggedIn()) {
                        if (API.currentUser() != null) {
                            webView.loadUrl(String.valueOf(BuildConfig.ROOT_URL + "/webview/binary?token=" + API.getToken()));
                            Log.e("asdf", "URL " + String.valueOf(BuildConfig.ROOT_URL + "/webview/binary?token=" + API.getToken()));
                        } else {
                            webView.loadUrl(String.valueOf(BuildConfig.ROOT_URL));
                        }
                    } else {
                        webView.loadUrl(String.valueOf(BuildConfig.ROOT_URL));
                    }
                    break;
                default:
                    webView.loadUrl(String.valueOf(BuildConfig.ROOT_URL));
                    break;
            }

            webView.setWebViewClient(new WebViewClient() {
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String request) {
                    return true;
                }
            });

        } catch (Exception exception) {
            Log.e("WEBVIEW", "" + exception);
            progressBar.setVisibility(View.GONE);
            webView.loadUrl(String.valueOf(BuildConfig.ROOT_URL));
        }
    }
}
