package com.bolooo.studyhomeparents.activty.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.views.MyWebView;

import butterknife.Bind;

/*
* 预览课程*/
public class HtmlActivity extends BaseActivity{
    @Bind(R.id.webView)
    MyWebView webView;
    private String url;
    private String title;

    @Override
    public int initLoadResId() {
        return R.layout.activity_preview;
    }
    @Override
    protected void initView() {
        url = getIntent().getStringExtra("url");
        title = getIntent().getStringExtra("title");
        init();
    }
    public static void openHtmlActivityNew(Context activity, String url, String title) {
        Intent intent = new Intent(activity, HtmlActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }
    public static void openHtmlActivity(Activity activity, String url, String title) {
        Intent intent = new Intent(activity, HtmlActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("title", title);
        activity.startActivity(intent);
    }

    private void init() {
        initBar();
        insureBar.getRightImage().setVisibility(View.GONE);
        insureBar.setTitle(title);
        webView.loadUrl(url);
        webView.getSettings ().setJavaScriptEnabled(true);
        Log.d("about==",webView.getSettings().getUserAgentString());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading
                    (WebView view, String url) {

                Log.i("用户单击超连接", url);
                //判断用户单击的是那个超连接
                String tag = "tel:";
                if (url.contains(tag)) {
                    String mobile = url.substring(url.lastIndexOf(":") + 1);
                    Uri uri = Uri.parse("tel:" + mobile);
                    Intent intent = new Intent(Intent.ACTION_CALL, uri);
                    startActivity(intent);
                    //这个超连接,java已经处理了，webview不要处理了
                    return true;
                }

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                insureBar.setTitle(view.getTitle());
            }
        });
    }

}
