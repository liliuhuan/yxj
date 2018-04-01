package com.bolooo.studyhomeparents.activty.adver;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.ad.ComboEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.WxUtil;
import com.bolooo.studyhomeparents.views.MyWebView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 套餐详情页面
 */
public class AdDetailActivity extends BaseActivity implements IRequestCallBack {
    @Bind(R.id.webView)
    MyWebView webView;
    @Bind(R.id.share_image)
    ImageView shareImage;
    @Bind(R.id.combo_buy_tv)
    TextView comboBuyTv;
    @Bind(R.id.title)
    TextView titleTv;
    @Bind(R.id.bar_right_image)
    ImageView barRightImage;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.ll_buy_info)
    LinearLayout llBuyInfo;
    @Bind(R.id.activity_preview)
    RelativeLayout activityPreview;
    private String tcId;
    private IWXAPI api;
    private String introduceUrl;
    private String packageName;
    private String packageImg;
    private String packagedescription;


    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            //  getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        tcId = getIntent().getStringExtra("adId");
        init();
        back.setOnClickListener(v->finish());
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_ad_detail;
    }

    public static void openHtmlActivity(Activity activity, String adId) {
        Intent intent = new Intent(activity, AdDetailActivity.class);
        intent.putExtra("adId", adId);
        activity.startActivity(intent);
    }

    private void init() {
        barRightImage.setVisibility(View.GONE);
    }

    private void loadWebview(String url) {
        webView.loadUrl(url);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
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
        });
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (!TextUtils.isEmpty(tcId)) MineUtils.getInstance().getComboInfo(tcId, this);
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        ComboEntity comboEntity = JSONObject.parseObject(result, ComboEntity.class);
        if (comboEntity != null) {
            titleTv.setText(comboEntity.getPackageName());
            comboBuyTv.setText(comboEntity.getDiscountPrice() + "游票 购买套餐");
            introduceUrl = comboEntity.getIntroduceUrl();
            packageName = comboEntity.getPackageName();
            packageImg = comboEntity.getPackageImg();
            packagedescription = comboEntity.getDescription();
            loadWebview(comboEntity.getIntroduceUrl());
        }
    }

    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }

    @OnClick({R.id.share_image, R.id.combo_buy_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.share_image:
                if (OnClickUtils.isFastDoubleClick()) return;
                showShareDialog();
                break;
            case R.id.combo_buy_tv:
                if (OnClickUtils.isFastDoubleClick()) return;
                if (checkLogin()) {
                    if (TextUtils.isEmpty(tcId)) return;
                    Bundle bundle = new Bundle();
                    bundle.putString("tcId", tcId);
                    IntentUtils.startNewIntentBundle(this, bundle, ComboBuyActivity.class);
                }
                break;
        }
    }

    private void showShareDialog() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.show_share_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        dialog.setCancelable(true);
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = Constants.screenWidth * 4 / 5;
        dialog.getWindow().setAttributes(attributes);

        LinearLayout llWeixin = (LinearLayout) conentView.findViewById(R.id.weixin);
        LinearLayout llFriends = (LinearLayout) conentView.findViewById(R.id.friends);
        LinearLayout saveAlbum = (LinearLayout) conentView.findViewById(R.id.save_album);
        saveAlbum.setVisibility(View.GONE);
        llWeixin.setOnClickListener(view -> {
            shareType(SendMessageToWX.Req.WXSceneSession);
            dialog.dismiss();
        });
        llFriends.setOnClickListener(view -> {
            shareType(SendMessageToWX.Req.WXSceneTimeline);
            dialog.dismiss();
        });
    }

    //分享网页
    private void shareType(int mTargetScene) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = introduceUrl;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = "游学家 " + packageName;
        msg.description = packagedescription;
        Glide.with(this) // could be an issue!
                .load(Constants.imageUrl + packageImg + "?w=300&h=300")
                .asBitmap() //强制转换Bitmap
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                        //这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                        msg.thumbData = WxUtil.bmpToByteArray(thumbBmp, true);
                        if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
                            SendMessageToWX.Req req = new SendMessageToWX.Req();
                            req.transaction = buildTransaction("webpage");
                            req.message = msg;
                            req.scene = mTargetScene;
                            api.sendReq(req);
                        } else {
                            ToastUtils.showToast("您还没有安装微信或微信版本过低");
                        }

                    }
                });
        if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
            SendMessageToWX.Req req = new SendMessageToWX.Req();
            req.transaction = buildTransaction("webpage");
            req.message = msg;
            req.scene = mTargetScene;
            api.sendReq(req);
        } else {
            ToastUtils.showToast("您还没有安装微信或微信版本过低");
        }
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
