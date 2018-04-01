package com.bolooo.studyhomeparents.activty.dynamic;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.activty.home.PicturesActivity;
import com.bolooo.studyhomeparents.adapter.dynamic.DynamicDetailAdapter;
import com.bolooo.studyhomeparents.adapter.dynamic.DynamiclistImageAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicDetailEntity;
import com.bolooo.studyhomeparents.event.RefreshCommentListEvent;
import com.bolooo.studyhomeparents.event.RefreshDynamicDetailEvent;
import com.bolooo.studyhomeparents.event.RefreshDynamicListEvent;
import com.bolooo.studyhomeparents.event.RefreshZanListEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DynamicUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.WxUtil;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bolooo.studyhomeparents.views.MySwipeRefreshlayout;
import com.bolooo.studyhomeparents.views.NoScollViewPager;
import com.bolooo.studyhomeparents.views.recyclerview.interfaces.AppBarStateChangeListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;


//老师动态
public class DynamicDetailActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, DynamicUtils.OnDynamicDetailInfoLisenter {
    private static final int THUMB_SIZE = 150;
    @Bind(R.id.ll_right_action)
    LinearLayout llRightAction;
    @Bind(R.id.tv_line)
    TextView tvLine;

    @Bind(R.id.viewpager)
    NoScollViewPager viewpager;
    @Bind(R.id.rb_like)
    RadioButton rbLike;
    @Bind(R.id.rb_comment)
    RadioButton rbComment;
    @Bind(R.id.rg_choose)
    RadioGroup rgChoose;
    @Bind(R.id.view0)
    View view0;
    @Bind(R.id.view1)
    View view1;

    DynamicDetailAdapter adapter;
    @Bind(R.id.dynamic_time)
    TextView dynamicTime;
    @Bind(R.id.dynamic_content)
    TextView dynamicContent;
    @Bind(R.id.dynamic_pictures)
    MyGridView dynamicPictures;
    @Bind(R.id.tv_add_zan)
    TextView tvAddZan;
    @Bind(R.id.refresh)
    MySwipeRefreshlayout swipeRefreshLayout;
    @Bind(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    private String title0;
    private String title1;
    private String title00;
    private String title11;
    private String uid;
    private String dynamicId;
    private boolean isZan = false;
    private IWXAPI api;
    private int refreshFlag = 1;

    String dynamicDetailUrl ;
    private DynamicDetailEntity dynamicDetail;

    private String headPhotoUrl;
    private String teacherName;

    @Override
    protected void initView() {
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        initBar();
        insureBar.setTitle(getString(R.string.dynamic_detail));
        insureBar.getCheckBox().setVisibility(View.GONE);
        tvLine.setVisibility(View.GONE);
        llRightAction.setVisibility(View.GONE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            uid = extras.getString("Id");
            headPhotoUrl = extras.getString("headPhotoUrl");
            teacherName = extras.getString("teacherName");
            dynamicDetailUrl = Constants.dynamicDetailUrl+uid;
        }
        title0 = "点赞";
        title00 = "<font color='#AAAAAA'>点赞</font>";
        title1 = "评论";
        title11 = "<font color='#AAAAAA'>评论</font>";

        //刷新
        swipeRefreshLayout.setProgressViewOffset(false, 0, DensityUtil.dip2px(this, 48));
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light
                , android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            EventBus.getDefault().post(new RefreshCommentListEvent());
            EventBus.getDefault().post(new RefreshZanListEvent(1));
            prepareData();
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    swipeRefreshLayout.setEnabled(true);
                }else{
                    swipeRefreshLayout.setEnabled(false);
                }
            }
        });

        adapter = new DynamicDetailAdapter(getSupportFragmentManager(), uid);
        viewpager.setAdapter(adapter);
        rgChoose.setOnCheckedChangeListener(this);
        rgChoose.check(R.id.rb_like);
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        DynamicUtils.getInstance().getDynamcDetail(this, uid, this);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_detail_nestscollview_layout;
    }


    public void onEventMainThread(RefreshDynamicDetailEvent event) {
        refreshFlag = event.i;
        prepareData();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.rb_like:
                viewpager.setCurrentItem(0);
                rbLike.setText(Html.fromHtml(title0));
                rbComment.setText(Html.fromHtml(title11));
                view0.setVisibility(View.VISIBLE);
                view1.setVisibility(View.INVISIBLE);
                break;
            case R.id.rb_comment:
                viewpager.setCurrentItem(1);
                rbLike.setText(Html.fromHtml(title00));
                rbComment.setText(Html.fromHtml(title1));
                view1.setVisibility(View.VISIBLE);
                view0.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public static void startIntent(Context mContext, String fId, String headPhotoUrl, String teacherName) {
        Bundle bundle = new Bundle();
        bundle.putString("Id", fId);
        bundle.putString("headPhotoUrl", headPhotoUrl);
        bundle.putString("teacherName", teacherName);
        IntentUtils.startNewIntentBundle(mContext, bundle, DynamicDetailActivity.class);
    }

    @Override
    public void OnGetDynamicDetailinfoSucessful(DynamicDetailEntity dynamicDetailEntity) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        dynamicDetail = dynamicDetailEntity;
        fillData(dynamicDetailEntity);
    }

    @Override
    public void OnGetDynamicDetailinfoFail(String error) {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void fillData(DynamicDetailEntity dynamicDetailEntity) {
        int zanCount = dynamicDetailEntity.getZanCount();
        if (zanCount != 0) { //@ 选中 @@ 未选中
            title0 = "点赞(<font color='red'>" + zanCount + "</font>)";//选中
            title00 = "<font color='#AAAAAA'>点赞(" + zanCount + ")</font>";
        } else {
            title0 = "点赞";
            title00 = "<font color='#AAAAAA'>点赞</font>";
        }
        int commentCount = dynamicDetailEntity.getCommentCount();
        if (commentCount != 0) {//选中
            title1 = "评论(<font color='red'>" + dynamicDetailEntity.getCommentCount() + "</font>)";
            title11 = "<font color='#AAAAAA'>评论(" + dynamicDetailEntity.getCommentCount() + ")</font>";
        } else {
            title1 = "评论";
            title11 = "<font color='#AAAAAA'>评论</font>";
        }

        if (refreshFlag == 2) {//评论
            rbLike.setText(Html.fromHtml(title00));
            rbComment.setText(Html.fromHtml(title1));
            rgChoose.check(R.id.rb_comment);
        } else {
            rbLike.setText(Html.fromHtml(title0));
            rbComment.setText(Html.fromHtml(title11));
            rgChoose.check(R.id.rb_like);
        }

        dynamicContent.setText(dynamicDetailEntity.getInfo());
        //时间
        String time = dynamicDetailEntity.getCreateTime();
        if (TimeUtils.isToday(time)) {
            dynamicTime.setText("今天");
        } else if (TimeUtils.isYesterday(time)) {
            dynamicTime.setText("昨天");
        } else {
            String timeStr = TimeUtils.getNeedDay(time) + TimeUtils.getNeedMouth(time);
            dynamicTime.setText(setTextSize(timeStr));
        }

        isZan = dynamicDetailEntity.isIsZan();
        if (isZan) {
            setSelectZanImage();
        } else {
            setNomalZanImage();
        }
        dynamicId = dynamicDetailEntity.getId();
        //图片显示
        displayDynamicImages(dynamicDetailEntity);
    }

    private void displayDynamicImages(DynamicDetailEntity dynamicDetailEntity) {
        final List<DynamicDetailEntity.UZoneImagesEntity> uZoneImages = dynamicDetailEntity.getUZoneImages();
        DynamiclistImageAdapter adapter = new DynamiclistImageAdapter(this);
        dynamicPictures.setAdapter(adapter);
        dynamicPictures.setOnItemClickListener((adapterView, view, pos, l) -> {
            List<String> imageList = new ArrayList<String>();
            for (int j = 0; j < uZoneImages.size(); j++) {
                imageList.add(uZoneImages.get(j).getId());
            }
            Intent intent = new Intent(DynamicDetailActivity.this, PicturesActivity.class);
            intent.putExtra("images", (ArrayList<String>) imageList);//非必须
            intent.putExtra("position", pos);
            startActivity(intent);
        });
        List<String> imageId = new ArrayList<>();
        for (int i = 0; i < uZoneImages.size(); i++) {
            imageId.add(uZoneImages.get(i).getId());
        }
        adapter.setItems(imageId);
    }

    private void setNomalZanImage() {
        Drawable img = getResources().getDrawable(R.drawable.img_zan_nomal);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        //img1.setColorFilter(getResources().getColor(R.color.light_gray_color), PorterDuff.Mode.MULTIPLY);
        tvAddZan.setCompoundDrawables(img, null, null, null); //设置左图标
        tvAddZan.setText("点赞");
        tvAddZan.setTextColor(getResources().getColor(R.color.text_name));
    }


    private void setSelectZanImage() {
        Drawable img1 = getResources().getDrawable(R.drawable.img_zan_select);
        // 调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        img1.setBounds(0, 0, img1.getMinimumWidth(), img1.getMinimumHeight());
        tvAddZan.setText("已赞");
        tvAddZan.setTextColor(getResources().getColor(R.color.bar_bg));
        tvAddZan.setCompoundDrawables(img1, null, null, null); //设置左图标
    }

    private SpannableString setTextSize(String timeStr) {
        //根据要显示的String得到SpannableString
        SpannableString span = new SpannableString(timeStr);
        //设置需要的字体大小，已经需要设置文本起始位置
        span.setSpan(new AbsoluteSizeSpan(40), 2, timeStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return span;
    }


    @OnClick({R.id.tv_share, R.id.tv_add_zan, R.id.tv_comment_publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_share:
                if (checkLogin()) showShareDialog();
                break;
            case R.id.tv_add_zan:
                if(checkLogin()){
                    if (dynamicId == null) return;
                    addZan();//点赞
                }

                break;
            case R.id.tv_comment_publish://评论
                if (checkLogin()){
                    Bundle bundle = new Bundle();
                    if (dynamicId == null) return;
                    bundle.putString("dynamicId", dynamicId);
                    DynamicCommentActivity.startIntent(this, bundle);
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

    private void addZan() {
        DynamicUtils.getInstance().AddDynamcLike(dynamicId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                refreshFlag = 1 ;
                prepareData();
                EventBus.getDefault().post(new RefreshZanListEvent(1));
                EventBus.getDefault().post(new RefreshDynamicListEvent());
            }
            @Override
            public void onError(String error) {
            }
        });
    }

    //分享网页
    private void shareType(int mTargetScene) {
            WXWebpageObject webpage = new WXWebpageObject();
            webpage.webpageUrl = dynamicDetailUrl;
            WXMediaMessage msg = new WXMediaMessage(webpage);
            if (dynamicDetail !=null) {
                String info = dynamicDetail.getInfo();
                msg.title =teacherName+"老师发布的新动态" ;
                if (info.length() > 16)
                    msg.description = info.substring(0,15)+"...";
                else
                    msg.description = info;
            }else{
                ToastUtils.showToast("");
            }
        if (StringUtil.isEmpty(headPhotoUrl)){
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.noavatar);
            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
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
        }else {
            Glide.with(this) // could be an issue!
                    .load(Constants.imageUrl + headPhotoUrl + "?w=100&h=100")
                    .asBitmap() //强制转换Bitmap
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
                            //这里我们拿到回掉回来的bitmap，可以加载到我们想使用到的地方
                            Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, THUMB_SIZE, THUMB_SIZE, true);
                            msg.thumbData = WxUtil.bmpToByteArray(thumbBmp, true);

                            if (api.isWXAppInstalled() && api.isWXAppSupportAPI()) {
                                SendMessageToWX.Req req = new SendMessageToWX.Req();
                                StudyApplication.getInstance().weichatLoginType = 3;
                                req.transaction = buildTransaction("webpage");
                                req.message = msg;
                                req.scene = mTargetScene;
                                api.sendReq(req);
                            } else {
                                ToastUtils.showToast("您还没有安装微信或微信版本过低");
                            }
                        }
                    });
        }
    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
