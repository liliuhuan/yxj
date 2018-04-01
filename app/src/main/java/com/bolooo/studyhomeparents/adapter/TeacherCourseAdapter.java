package com.bolooo.studyhomeparents.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.LoginActivity;
import com.bolooo.studyhomeparents.activty.doorteaching.ClassSignUpActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.entity.UticketNoticeEntity;
import com.bolooo.studyhomeparents.event.FocusCurriculum;
import com.bolooo.studyhomeparents.event.SignUpEmptyEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.NetworkUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.WxUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Guojunhong on 2017/3/2.
 */

public class TeacherCourseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ImageView tvSignPu;
    private String teachName;
    private IWXAPI api;
    private Context context;
    private JSONArray courseScoll;
    private JSONObject userInfoEntity;
    private String teachId;
    private List<String> mGuides;
    private List<Integer> statusList;
    private boolean favCourseFlag;
    private int pos = 0;
    private String courseId;
    private String firstImgUrl;
    private String courseName;
    private String cityName;
    private boolean countNotify = false ;
    private String courseNewId;

    public TeacherCourseAdapter(Context context, String teachId, String courseScollId, String teachName, ImageView tvSignUp) {
        this.context = context;
        this.teachId = teachId;
        this.teachName = teachName;
        this.tvSignPu = tvSignUp ;
        mGuides = new ArrayList<>();
        statusList = new ArrayList<>();
        api = WXAPIFactory.createWXAPI(context, Constants.APP_ID);
        getTeacherCourse(teachId, courseScollId);//根据教师Id获取课程的滑动图片
    }

    public void getTeacherCourse(String teacherId,  String courseScollId) {

        MainUtils.getInstance().getTeacherCourse(teacherId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                 Log.d("AAA.de", result);
                countNotify = true ;
                try {
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            if (array.getJSONObject(i).optString("CourseId").equals(courseScollId)) {
                                pos = i;
                                break;
                            }
                            if (i == array.length() - 1) break;
                        }
                        JSONObject jsonObject = array.getJSONObject(pos);
                        courseNewId = jsonObject.optString("CourseId");
                        getDetailCouser(jsonObject.optString("CourseId"), false);
                        setCourseScoll(array);
                        if (lisenter != null)lisenter.OnShowBottomLayout(false);
                    } else {
                        if (lisenter != null)lisenter.OnShowBottomLayout(true);
                        getDetailCouser(courseScollId, false);
                        notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void getDetailCouser(String courseId, final boolean flag) {
        MainUtils.getInstance().getDetailCourse(courseId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                Log.d("AAA.de", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    setCourseDetail(jsonObject, flag);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
            }
        });
    }

    public void setCourseDetail(JSONObject userInfoEntity, boolean flag) {
        this.userInfoEntity = userInfoEntity;
     //   if (flag) {//滑动viewpage 刷新下面详情
            notifyItemChanged(1);
//        } else {
//            notifyDataSetChanged();
//        }
    }

    public void setCourseScoll(JSONArray courseScoll) {
        this.courseScoll = courseScoll;
        notifyItemChanged(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == 0) {
            view = View.inflate(context, R.layout.teacher_course_item1, null);
            return new ViewPagerViewHolder(view);
        } else {
            view = View.inflate(context, R.layout.teacher_course_item2, null);
            return new CourseDeatilViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case 0:
                try {
                    final ViewPagerViewHolder holderOne = (ViewPagerViewHolder) holder;
                    //设置ViewPager的布局
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                            Constants.screenWidth * 5 / 10,
                            DensityUtil.dip2px(context, 130));
                    /**** 重要部分  ******/
                    //clipChild用来定义他的子控件是否要在他应有的边界内进行绘制。 默认情况下，clipChild被设置为true。 也就是不允许进行扩展绘制。

                    holderOne.mViewPager.setClipChildren(false);
                    //父容器一定要设置这个，否则看不出效果
                    holderOne.mViewPagerContainer.setClipChildren(false);
                    holderOne.mViewPager.setLayoutParams(params);
                    if (courseScoll != null && courseScoll.length() > 0) {
                        firstImgUrl = courseScoll.getJSONObject(position).optString("FirstImg");
                        mGuides.clear();
                        statusList.clear();
                        for (int i = 0; i < courseScoll.length(); i++) {
                            mGuides.add(courseScoll.getJSONObject(i).optString("FirstImg"));
                            statusList.add(courseScoll.getJSONObject(i).optInt("CourseStatus"));

                        }
                        CoursePagerAdapter myadapter = new CoursePagerAdapter(context, mGuides,statusList);
                        //为ViewPager设置PagerAdapter
                        holderOne.mViewPager.setAdapter(myadapter);

                        holderOne.mViewPagerContainer.setVisibility(View.VISIBLE);
                        holderOne.tvEmpty.setVisibility(View.GONE);
                    } else {
                        if (countNotify){
                            holderOne.mViewPagerContainer.setVisibility(View.GONE);
                            holderOne.tvEmpty.setVisibility(View.VISIBLE);
                       }
                    }

                    //设置ViewPager切换效果，即实现画廊效果
                    holderOne.mViewPager.setPageTransformer(true, new ZoomOutPageTransformer());
                    //设置预加载数量
                    holderOne.mViewPager.setOffscreenPageLimit(20);
                    //设置每页之间的左右间隔
                    holderOne.mViewPager.setPageMargin(10);
                    holderOne.mViewPager.setCurrentItem(pos);
                    holderOne.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                            if (holderOne.mViewPagerContainer != null) {
                                holderOne.mViewPagerContainer.invalidate();
                            }
                        }

                        @Override
                        public void onPageSelected(int position1) {
                            try {
                                pos = position1;
                                getDetailCouser(courseScoll.getJSONObject(position1).optString("CourseId"), true);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {
                        }
                    });
                    //将容器的触摸事件反馈给ViewPager
                    holderOne.mViewPagerContainer.setOnTouchListener((v, event) ->
                            holderOne.mViewPager.dispatchTouchEvent(event));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                try {
                    CourseDeatilViewHolder holderTwo = (CourseDeatilViewHolder) holder;
                    holderTwo.imageEmpty.setVisibility(View.GONE);
                    if (userInfoEntity == null || userInfoEntity.length() == 0){
                        if (countNotify){
                            holderTwo.llContent.setVisibility(View.GONE);
                            holderTwo.imageEmpty.setVisibility(View.VISIBLE);
                        }
                        return;
                    }else {
                        holderTwo.llContent.setVisibility(View.VISIBLE);

                    }
                    if (courseScoll == null || courseScoll.length() == 0) {
                        if (countNotify){
                            holderTwo.llContent.setVisibility(View.GONE);
                            holderTwo.imageEmpty.setVisibility(View.VISIBLE);
                       }
                    } else {
                        holderTwo.llContent.setVisibility(View.VISIBLE);
                        courseId = courseScoll.getJSONObject(pos).optString("CourseId");
                    }
                    int courseStatus = userInfoEntity.optInt("CourseStatus");
                    switch (courseStatus){
                        case  1:
                            tvSignPu.setImageResource(R.drawable.btn_apply_pre);
                            break;
                        case  2:
                            tvSignPu.setImageResource(R.drawable.btn_apply);
                            break;
                        case  3:
                            tvSignPu.setImageResource(R.drawable.btn_apply_full);
                            break;
                    }

                    courseName = userInfoEntity.optString("CourseName");
                    cityName = userInfoEntity.optString("CityName");
                    holderTwo.course.setText("《" + userInfoEntity.optString("CourseName") + "》");
                    favCourseFlag = userInfoEntity.optBoolean("IsWantGo");
                    EventBus.getDefault().post(new SignUpEmptyEvent(favCourseFlag));
                    if (favCourseFlag) {
                        holderTwo.favCourse.setBackgroundResource(R.drawable.ticon_text_favedclass);
                    } else {
                        holderTwo.favCourse.setBackgroundResource(R.drawable.ticon_text_favclass);
                    }
                    holderTwo.shareImage.setOnClickListener(v -> showShareDialog());
                    final String finalCourseId = courseId;
                    //可以使用checkbox实现连环点击切换不同的状态
                    holderTwo.favCourse.setOnClickListener(view -> {
                        if (CommentUtils.isLogin()) {
                            if (favCourseFlag) {//代表已收藏，去取消收藏
                                favCourseFlag = false;
                                addIsWantGo(holderTwo.favCourse, finalCourseId, false);
                            } else {
                                favCourseFlag = true;
                                addIsWantGo(holderTwo.favCourse, finalCourseId, true);
                            }
                        } else {
                            IntentUtils.startIntent(context, LoginActivity.class);
                        }

                    });
                    JSONArray courseTags = userInfoEntity.getJSONArray("CourseTags");
                    if (courseTags == null || courseTags.equals("[]") || courseTags.length() == 0) {
                        holderTwo.studying.setVisibility(View.GONE);
                        holderTwo.childYear.setVisibility(View.GONE);
                        holderTwo.signUp.setVisibility(View.GONE);
                       // return;
                    } else {
                        int length = courseTags.length();
                        switch (length) {
                            case 1:
                                holderTwo.studying.setVisibility(View.VISIBLE);
                                holderTwo.childYear.setVisibility(View.GONE);
                                holderTwo.signUp.setVisibility(View.GONE);
                                holderTwo.studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                                break;
                            case 2:
                                holderTwo.studying.setVisibility(View.VISIBLE);
                                holderTwo.childYear.setVisibility(View.VISIBLE);
                                holderTwo.signUp.setVisibility(View.GONE);
                                holderTwo.studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                                holderTwo.childYear.setText(courseTags.getJSONObject(1).optString("TagName"));
                                break;
                            case 3:
                                holderTwo.studying.setVisibility(View.VISIBLE);
                                holderTwo.childYear.setVisibility(View.VISIBLE);
                                holderTwo.signUp.setVisibility(View.VISIBLE);

                                holderTwo.studying.setText(courseTags.getJSONObject(0).optString("TagName"));
                                holderTwo.childYear.setText(courseTags.getJSONObject(1).optString("TagName"));
                                holderTwo.signUp.setText(courseTags.getJSONObject(2).optString("TagName"));
                                break;
                        }
                    }

                    holderTwo.newStar.setStepSize((float) 0.1);
                    holderTwo.newStar.setRating((float) userInfoEntity.optDouble("AverageScore"));
                    String price = userInfoEntity.optString("Price");
                    holderTwo.uticketNum.setText(price);
                    holderTwo.uticket.setOnClickListener(v->{
                        getUnticktImage();
                       // showDialog();
                    });
                    if (lisenter != null) {
                        lisenter.onTransitNum(Integer.valueOf(price), courseId);
                        lisenter.OnCourseStatus(courseStatus);

                    }
                    holderTwo.newNum.setText(userInfoEntity.optString("AllApplyCount"));
                    holderTwo.position.setText(userInfoEntity.optString("Street") +" "+ userInfoEntity.optString("District") +" "+ userInfoEntity.optString("HouseNum"));
                    holderTwo.duration.setText(userInfoEntity.optString("Duration") + "分钟 X 共" + userInfoEntity.optString("ClassCount") + "节课");
                    holderTwo.personnum.setText("" + userInfoEntity.optString("MinAge") + "-" + userInfoEntity.optString("MaxAge") + "岁");
                    if (StringUtil.isEmpty(userInfoEntity.optString("Attention"))) {
                        holderTwo.notes.setText("无");
                    } else {
                        holderTwo.notes.setText(userInfoEntity.optString("Attention"));
                    }
                    String videoUrl = userInfoEntity.optString("VideoUrl");
                    String videoScreenshot = userInfoEntity.optString("VideoScreenshot");
                    boolean isVideoCertify = userInfoEntity.optBoolean("IsVideoCertify");
                    if (isVideoCertify){
                        holderTwo.videoplayer.setVisibility(View.VISIBLE);
                        holderTwo.videoplayer.ivBack.setVisibility(View.GONE);
                        ((JCVideoPlayer)holderTwo.videoplayer).ivStart.setVisibility(View.GONE);
                       // Bitmap bitmapFromVideoPath = BitmapUtils.createBitmapFromVideoPath(videoUrl, 800, 640);
                        holderTwo.videoplayer.ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        Glide.with(context).load(Constants.imageUrl+videoScreenshot).error(R.drawable.noimage)
                                .into(holderTwo.videoplayer.ivThumb);
                       // holderTwo.videoplayer.ivThumb.setImageBitmap(bitmapFromVideoPath);
                        holderTwo.imageAction.setVisibility(View.VISIBLE);
                    }else {
                        holderTwo.videoplayer.setVisibility(View.GONE);
                        holderTwo.imageAction.setVisibility(View.GONE);
                    }

                    holderTwo.imageAction.setOnClickListener(V->{
                        int netWorkType = NetworkUtils.getNetWorkType(context);
                        if (netWorkType == 4){
                            JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
                        }else {
                            showAdialogIsWifi(videoUrl);
                        }

                    });
                    holderTwo.videoplayer.ivThumb.setOnClickListener(V->{
                        int netWorkType = NetworkUtils.getNetWorkType(context);
                        if (netWorkType == 4){
                            JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
                        }else {
                            showAdialogIsWifi(videoUrl);
                        }
                    });

                    String description = userInfoEntity.optString("Description");
                    if (StringUtil.isEmpty(description)) {
                        holderTwo.course_inTitle.setVisibility(View.GONE);
                        holderTwo.webView.loadData("", "text/html", "utf-8");
                    } else {
                        holderTwo.course_inTitle.setVisibility(View.VISIBLE);
                        holderTwo.webView.getSettings().setJavaScriptEnabled(true);
                        String htmlStr = "<html lang=\"en\">" + "<head><link rel=\"stylesheet\" "
                                + "href=\"http://us2080.com/doc/code/article.css\" type=\"text/css\"/><meta name=\"viewport\""
                                + " id=\"WebViewport\" content=\"width=device-width,user-scalable=0\" />   </head>" + "<body>"
                                + "<article>" + description + "</article>" + "</body>" + "</html>";
                        holderTwo.webView.loadDataWithBaseURL(null, getNewContent(htmlStr), "text/html", "utf-8", "");
                    }
                    //报名
                    tvSignPu.setOnClickListener(view -> {
                        if (StringUtil.isEmpty(SharedPreferencesUtil.getInstance().getToken())) {
                            IntentUtils.startIntent(context, LoginActivity.class);
                        } else {
                            switch (courseStatus){
                                case 1:
                                    if (!favCourseFlag)showDialogDes(context.getString(R.string.pre_course),1);
                                    else ToastUtils.showToast("课程正在预热中,开课后我们会通知您");
                                    break;
                                case 2:
                                    Bundle bundle = new Bundle();
                                    bundle.putString("courseId", finalCourseId);
                                    bundle.putBoolean("favCourseFlag", favCourseFlag);
                                    bundle.putString("tid",teachId);
                                    IntentUtils.startNewIntentBundle(context, bundle, ClassSignUpActivity.class);
                                    break;
                                case 3:
                                    if (!favCourseFlag) showDialogDes(context.getString(R.string.sign_up_course_full),2);
                                    else ToastUtils.showToast("老师的课程已经报满了,开课后我们会通知您");
                                    break;
                            }
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }
        //countNotify = true ;
    }
    private void showAdialogIsWifi(String videoUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您当前还不是wifi环境，确定要播放吗？").setPositiveButton("确定", (dialog, which) -> {
            JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
            dialog.dismiss();
        }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
    }

    private void showDialogDes(String stringStr,int flag) {
        AlertDialog dlg = new AlertDialog.Builder(context, R.style.OldDialogStyle).create();
        dlg.show();
        Window window = dlg.getWindow();
        dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_fav_tip_layout);
        window.setGravity(Gravity.CENTER);
        TextView tvDes = (TextView) window.findViewById(R.id.tv_fav_des);
        TextView tvCancel = (TextView) window.findViewById(R.id.tv_cancel);
        TextView tvFav = (TextView) window.findViewById(R.id.tv_fav);

        WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        lp.width=Constants.screenWidth*3/4;
        String htmlString = null;
        if (flag == 1){
            htmlString = "课程正在"+stringStr+"<br>请先收藏课程<br>开课后我们会通知您";
        }else {
            htmlString = "老师的所有课程都"+stringStr+"<br>请先收藏课程<br>开课后我们会通知您";
        }
        tvDes.setText(Html.fromHtml(htmlString));
        tvFav.setOnClickListener(v->{
            favCourse();
            dlg.cancel();
        });
        tvCancel.setOnClickListener(v->dlg.cancel());
        dlg.getWindow().setAttributes(lp);
    }

    private void favCourse() {
        Log.d("tag====", courseId);
        MainUtils.getInstance().AddTeacherCourse(courseId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                getDetailCouser(courseId, false);
            }
            @Override
            public void onError(String error) {

            }
        });
    }

    private void getUnticktImage() {
        MineUtils.getInstance().getUtickImage(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                UticketNoticeEntity uticketNoticeEntity = com.alibaba.fastjson.JSONObject.parseObject(result, UticketNoticeEntity.class);
                if (uticketNoticeEntity!= null ){
                    String fieldKey = uticketNoticeEntity.getFieldValue();
                    if (!StringUtil.isEmpty(fieldKey)){

                        Glide.with(context).load(fieldKey).asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                showDialog(resource);
                            }
                        });

                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void showDialog(Bitmap bitmap) {
        AlertDialog dlg = new AlertDialog.Builder(context, R.style.OldDialogStyle).create();
        dlg.show();
        Window window = dlg.getWindow();
        dlg.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.dialog_uticket);
        window.setGravity(Gravity.CENTER);
        ImageView goShopping = (ImageView) window.findViewById(R.id.goto_unticket_shop);
        ImageView imageUticket = (ImageView) window.findViewById(R.id.image_uticket);
        ImageView imageCancel = (ImageView) window.findViewById(R.id.image_cancel);
        WindowManager.LayoutParams lp = dlg.getWindow().getAttributes();
        lp.width=Constants.screenWidth;
        imageUticket.setImageBitmap(bitmap);
        goShopping.setOnClickListener(v->{
            if (CommentUtils.isLogin()){
                IntentUtils.startIntent(context, UTicketActivity.class);
            }else {
                IntentUtils.startIntent(context, LoginActivity.class);
            }
            dlg.cancel();
        });
        imageCancel.setOnClickListener(v->dlg.cancel());
        dlg.getWindow().setAttributes(lp);

    }

    private void addIsWantGo(ImageView favCourse, String courseId, boolean isWantGo) {
        Log.d("tag====", courseId);
        MainUtils.getInstance().AddTeacherCourse(courseId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                if (isWantGo) {
                    favCourseFlag = isWantGo;
                    ToastUtils.showToast("收藏成功");
                    favCourse.setBackgroundResource(R.drawable.ticon_text_favedclass);
                } else {
                    ToastUtils.showToast("取消收藏");
                    favCourseFlag = !isWantGo ;
                    favCourse.setBackgroundResource(R.drawable.ticon_text_favclass);
                }
                //EventBus.getDefault().post(new SignUpEmptyEvent(isWantGo));
                EventBus.getDefault().post(new FocusCurriculum());

            }

            @Override
            public void onError(String error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        switch (position) {
            case 0:
                type = 0;//viewpage 滑动部分
                break;
            case 1:
                type = 1;//课程详情
                break;
        }
        return type;
    }


    class ViewPagerViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.viewpager)
        ViewPager mViewPager;
        @Bind(R.id.tv_empty)
        TextView tvEmpty;
        @Bind(R.id.viewPagerContainer)
        RelativeLayout mViewPagerContainer;

        public ViewPagerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private String getNewContent(String htmltext) {
        Document doc = Jsoup.parse(htmltext);
        Elements pngs = doc.select("img[src]");
        for (Element element : pngs) {
            String imgUrl = element.attr("src");
            //element.attr("width", "100%").attr("height", "auto");
            if (imgUrl.trim().startsWith("/")) {
                imgUrl = Constants.baseUrl + imgUrl;
                element.attr("src", imgUrl);
            }
        }
        htmltext = doc.toString();
        return htmltext;
    }

    class CourseDeatilViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.course)
        TextView course;
        @Bind(R.id.fav_course)
        ImageView favCourse;

        @Bind(R.id.share_image)
        ImageView shareImage;
        @Bind(R.id.studying)
        TextView studying;
        @Bind(R.id.child_year)
        TextView childYear;
        @Bind(R.id.signUp)
        TextView signUp;
        @Bind(R.id.new_star)
        RatingBar newStar;
        @Bind(R.id.new_num)
        TextView newNum;
        @Bind(R.id.uticket)
        ImageView uticket;
        @Bind(R.id.uticket_num)
        TextView uticketNum;
        @Bind(R.id.position)
        TextView position;
        @Bind(R.id.duration)
        TextView duration;
        @Bind(R.id.personnum)
        TextView personnum;
        @Bind(R.id.notes)
        TextView notes;
        @Bind(R.id.webView)
        WebView webView;
        @Bind(R.id.course_sign_up)
        ImageView courseSignUp;
        @Bind(R.id.image_action)
        ImageView imageAction;
        @Bind(R.id.course_inTitle)
        TextView course_inTitle;

        @Bind(R.id.ll_content)
        LinearLayout llContent;
        @Bind(R.id.image_empty)
        ImageView imageEmpty;
        @Bind(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;
        public CourseDeatilViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MAX_SCALE = 1.0f;
        private static final float MIN_SCALE = 0.85f;//0.85f

        @Override
        public void transformPage(View view, float position) {
            //setScaleY只支持api11以上
            if (position < -1) {
                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);
            } else if (position <= 1) //a页滑动至b页 ； a页从 0.0 -1 ；b页从1 ~ 0.0
            { // [-1,1]
                float scaleFactor = MIN_SCALE + (1 - Math.abs(position)) * (MAX_SCALE - MIN_SCALE);
                view.setScaleX(scaleFactor);
                //每次滑动后进行微小的移动目的是为了防止在三星的某些手机上出现两边的页面为显示的情况
                if (position > 0) {
                    view.setTranslationX(-scaleFactor * 2);
                } else if (position < 0) {
                    view.setTranslationX(scaleFactor * 2);
                }
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]

                view.setScaleX(MIN_SCALE);
                view.setScaleY(MIN_SCALE);

            }
        }
    }

    ItransmitNumLisenter lisenter;

    public void setLisenter(ItransmitNumLisenter lisenter) {
        this.lisenter = lisenter;
    }

    public interface ItransmitNumLisenter {
        void onTransitNum(int num, String courseId);
        void OnCourseStatus(int courseStatus);
        void OnShowBottomLayout(boolean isshow);
    }

    private void showShareDialog() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.show_share_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(context, R.style.CustomDialog).create();
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
        webpage.webpageUrl = Constants.shareCourseUrl + "tId=" + teachId + "&cId=" + courseId;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = teachName + "老师的精彩课程";
        msg.description = "《" + courseName + "》推荐给大家," + cityName + "的家长们快来看看吧。";
        Glide.with(context) // could be an issue!
                .load(Constants.imageUrl + firstImgUrl + "?w=100&h=100")
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

    }

    private String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
