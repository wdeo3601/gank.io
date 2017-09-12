package com.wdeo3601.gankio.base.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.wdeo3601.gankio.R;
import com.wdeo3601.gankio.utils.AndroidUtil;
import com.wdeo3601.gankio.utils.DensityHelper;
import com.wdeo3601.gankio.utils.ToastUtil;
import com.wdeo3601.gankio.utils.UIUtil;

import java.util.HashMap;
import java.util.Map;

public class WebActivity extends ToolbarActivity {
    private static final String TAG = "WebActivity";
    private static final String EXTRA_URL = TAG + "extra_url";
    private static final String EXTRA_HEADER = TAG + "extra_header";
    private static final String EXTRA_TITLE = TAG + "extra_title";

    private String mUrl;
    private String mHeader;
    private String mTitle;
    private WebView mWebView;
    private TextSwitcher mTextSwitcher;
    private NumberProgressBar mProgressbar;

    public static void startMe(Context context, String mUrl, String mTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, mUrl);
        intent.putExtra(EXTRA_TITLE, mTitle);
        context.startActivity(intent);
    }

    public static void startMe(Context context, String mUrl, String header, String mTitle) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra(EXTRA_URL, mUrl);
        intent.putExtra(EXTRA_HEADER, header);
        intent.putExtra(EXTRA_TITLE, mTitle);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initView() {
        DensityHelper.Companion.resetDensity(this, 375);
        super.initView();
        clearExtendToStatusBar();
        mWebView = (WebView) findViewById(R.id.web_webView);
        mProgressbar = (NumberProgressBar) findViewById(R.id.web_progressbar);
        mTextSwitcher = (TextSwitcher) findViewById(R.id.textSwitcher_title);

        mUrl = getIntent().getStringExtra(EXTRA_URL);
        mHeader = getIntent().getStringExtra(EXTRA_HEADER);
        mTitle = getIntent().getStringExtra(EXTRA_TITLE);

        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setWebViewClient(new LoveClient());
//        mWebView.addJavascriptInterface(new Javascript(), "WebViewJavascriptBridge");
        if (TextUtils.isEmpty(mHeader)) {
            mWebView.loadUrl(mUrl);
        } else {
            Map<String, String> extraHeaders = new HashMap<>();
            extraHeaders.put("Authorization", mHeader);
            mWebView.loadUrl(mUrl, extraHeaders);
        }

        mTextSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                final TextView textView = new TextView(WebActivity.this);
                TextSwitcher.LayoutParams layoutParams =
                        new TextSwitcher.LayoutParams(TextSwitcher.LayoutParams.WRAP_CONTENT,
                                TextSwitcher.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.CENTER;
                textView.setLayoutParams(layoutParams);
                textView.setSingleLine(true);
                textView.setTextSize(18);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(UIUtil.INSTANCE.getColor(R.color.global_title_text_51));
                textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                textView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textView.setSelected(true);
                    }
                }, 1738);
                return textView;
            }
        });
        mTextSwitcher.setInAnimation(this, android.R.anim.fade_in);
        mTextSwitcher.setOutAnimation(this, android.R.anim.fade_out);
        if (mTitle != null) setTitle(mTitle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void refresh() {
        mWebView.reload();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        mTextSwitcher.setText(title);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (mWebView.canGoBack()) {
                        mWebView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean canBack() {
        return true;
    }

    @Override
    protected int getMenuId() {
        return 0;
        //return R.menu.menu_web;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.webview_action_refresh:
                refresh();
                return true;
            case R.id.webview_action_copy_url:
                String copyDone = getString(R.string.tip_copy_done);
                AndroidUtil.INSTANCE.copyToClipBoard(this, mWebView.getUrl(), copyDone);
                return true;
            case R.id.webview_action_open_url:
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.parse(mUrl);
                intent.setData(uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    ToastUtil.INSTANCE.showLong(R.string.tip_open_fail);
                }
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mWebView != null) mWebView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mWebView != null) mWebView.onResume();
    }

    private class ChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mProgressbar.setProgress(newProgress);
            if (newProgress == 100) {
                mProgressbar.setVisibility(View.GONE);
            } else {
                mProgressbar.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            setTitle(title);
        }
    }

    private class LoveClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url != null) view.loadUrl(url);
            return true;
        }
    }
//
//    private class Javascript {
//        @JavascriptInterface
//        public void closeWindow() {
//            EventBus.getDefault().post(new UpdateData());//更新速配页数据
//            EventBus.getDefault().post(new UpdateUserInfoData());//更新用户主页数据
//            EventBus.getDefault().post(new UpdateProfileEvent(UpdateProfileEvent.PROFILE_DETAIL));
//            finish();
//        }
//    }
}
