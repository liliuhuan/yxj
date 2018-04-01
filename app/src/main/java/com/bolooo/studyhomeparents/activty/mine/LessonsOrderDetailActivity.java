package com.bolooo.studyhomeparents.activty.mine;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.SignUpSucessActivity;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.adapter.CheckTimeAdapter;
import com.bolooo.studyhomeparents.adapter.MineListLessonsAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.event.CourseOrderEvnet;
import com.bolooo.studyhomeparents.event.LessonsOrderEvent;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.PayOrderEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.MyListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

/**
 * 购课详情
 * nanfeifei
 * 2017/3/13 14:47
 *
 * @version 3.7.0
 */
@RuntimePermissions
public class LessonsOrderDetailActivity extends BaseActivity
        implements MineUtils.OnLessonsOrderDetailListener {
    @Bind(R.id.lessons_headimg_iv)
    ImageView lessonsHeadimgIv;
    @Bind(R.id.lessons_phone_iv)
    ImageView lessonsPhoneIv;
    @Bind(R.id.lessons_name_tv)
    TextView lessonsNameTv;
    @Bind(R.id.lessons_coursename_tv)
    TextView lessonsCoursenameTv;
    @Bind(R.id.mine_lessons_list_lv)
    MyListView mineLessonsListLv;
    @Bind(R.id.lessons_classname_tv)
    TextView lessonsClassnameTv;
    @Bind(R.id.lessons_address_tv)
    TextView lessonsAddressTv;
    @Bind(R.id.lessons_duration_tv)
    TextView lessonsDurationTv;
    @Bind(R.id.lessons_attention_tv)
    TextView lessonsAttentionTv;
    @Bind(R.id.lessons_expandle_tb)
    CheckBox lessonsExpandleTb;
    @Bind(R.id.lessons_student_tv)
    TextView lessonsStudentTv;
    @Bind(R.id.lessons_place_order_time_tv)
    TextView lessonsPlaceOrderTimeTv;
    @Bind(R.id.lessons_affirm_order_time_tv)
    TextView lessonsAffirmOrderTimeTv;
    @Bind(R.id.lessons_pay_uticket_tv)
    TextView lessonsPayUticketTv;
    @Bind(R.id.lessons_status_title_tv)
    TextView lessonsStatusTitleTv;
    @Bind(R.id.lessons_status_tv)
    TextView lessonsStatusTv;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.call_phone_bt)
    Button callPhoneBt;
    List<MineLessonsEntity.DetailShowResponsesBean> allList;
    List<MineLessonsEntity.DetailShowResponsesBean> list;
    MineListLessonsAdapter mineListLessonsAdapter;
    String orderId;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.course_name)
    TextView courseName;
    @Bind(R.id.see_course_time)
    TextView seeCourseTime;
    @Bind(R.id.course_price)
    TextView coursePrice;
    @Bind(R.id.course_allnumber)
    TextView courseAllnumber;
    @Bind(R.id.course_signup_number)
    TextView courseSignupNumber;
    @Bind(R.id.all_count_uticket)
    TextView allCountUticket;
    @Bind(R.id.receivable)
    TextView receivable;
    @Bind(R.id.course_sign_up)
    TextView courseSignUp;
    @Bind(R.id.pay_info)
    LinearLayout payInfo;
    @Bind(R.id.uncertain_pay_info)
    LinearLayout uncertainPayInfo;
    @Bind(R.id.uncertain_course_price)
    TextView uncertainCoursePrice;
    @Bind(R.id.uncertain_course_allnumber)
    TextView uncertainCourseAllnumber;
    @Bind(R.id.uncertain_course_signup_number)
    TextView uncertainCourseSignupNumber;
    @Bind(R.id.uncertain_all_count_uticket)
    TextView uncertainAllCountUticket;
    @Bind(R.id.bar_right_image)
    ImageView barRightImage;
    @Bind(R.id.lessons_cancel_order_time_tv)
    TextView lessonsCancelOrderTimeTv;
    @Bind(R.id.rl_goin_teacherinfo)
    RelativeLayout rlGoinTeacherInfo;
    private boolean isExapan;
    private CheckTimeAdapter checkTimeAdapter;
    private String frequencyId;
    private PopupWindow clearLessonPopWindow;
    private String needUntiket;
    private String oneprice;
    private int buyUntiketnumber;
    private double buyUntiketMoney;
    private String untiketId;
    private String uTicketPrice;
    private String courseId;
    private String teacherId;
    private String teacherName;
    private int frequencyType;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.lessons_order_detail_title));

        barRightImage.setImageResource(R.drawable.ticon_more);
        barRightImage.setOnClickListener(V -> {
//            if (clearLessonPopWindow != null) {
//                clearLessonPopWindow.showAtLocation(barRightImage, Gravity.TOP | Gravity.RIGHT, DensityUtil.dip2px(this, 40), DensityUtil.dip2px(this, 60));
//                setBackgroundAlpha(0.5f);
//            }
            showDailog();
        });
       // showClearPop();
        rlGoinTeacherInfo.setOnClickListener(v->{
            if (!TextUtils.isEmpty(courseId)){
                Bundle bundle = new Bundle();
                bundle.putString("teacherId", teacherId);
                bundle.putString("courseId", courseId);
                bundle.putString("teacherName", teacherName);
                IntentUtils.startIntentBundle(this, bundle, TeacherActivity.class);
            }

        });
        courseSignUp.setOnClickListener(V -> {
            if (!TextUtils.isEmpty(needUntiket) && !TextUtils.isEmpty(uTicketPrice))
                checkUntiketNumer();
        });

    }

    private void checkUntiketNumer() {
        MineUtils.getInstance().getParent(new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    int allUntikets = object.optInt("UTickets");
                    if (Integer.valueOf(needUntiket) > allUntikets) {
                        showDialogInsufficient(needUntiket, allUntikets, uTicketPrice, false);
                    } else {
                        showDialogInsufficient(needUntiket, allUntikets, uTicketPrice, true);
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

    private void showDialogInsufficient(String needUtickets, int utickets, final String price, final boolean flags) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.new_dialog_layout, null);
        //对话框
        final Dialog dialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        dialog.setCancelable(false);
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        WindowManager.LayoutParams attributes = dialog.getWindow().getAttributes();
        attributes.width = Constants.screenWidth * 4 / 5;
        dialog.getWindow().setAttributes(attributes);

        TextView btnOk = (TextView) conentView.findViewById(R.id.go_buy_untickte);
        TextView tv_title = (TextView) conentView.findViewById(R.id.tv_title);
        TextView cancel = (TextView) conentView.findViewById(R.id.cancel);
        TextView cofirm_unticket = (TextView) conentView.findViewById(R.id.cofirm_unticket);
        TextView my_untiket = (TextView) conentView.findViewById(R.id.my_untiket);
        TextView bug_untiket = (TextView) conentView.findViewById(R.id.bug_untiket);
        TextView preferential_tv = (TextView) conentView.findViewById(R.id.preferential_tv);
        LinearLayout llAction = (LinearLayout) conentView.findViewById(R.id.ll_action);
        TextView originBuy = (TextView) conentView.findViewById(R.id.origin_buy);
        TextView tvLine = (TextView) conentView.findViewById(R.id.tv_line);
        TextView tvNonpayment = (TextView) conentView.findViewById(R.id.tv_nonpayment);
        TextView untiketRemainBuy = (TextView) conentView.findViewById(R.id.untiket_remain_buy);
        String tvup;
        String tvdown;
        String buyNeedsMoney;

        if (!flags) {//游票不足
            buyUntiketnumber = Integer.valueOf(needUtickets);
            buyUntiketMoney = Integer.valueOf(needUtickets) * Double.valueOf(price);

            bug_untiket.setVisibility(View.VISIBLE);
            preferential_tv.setVisibility(View.VISIBLE);
            tvdown = "需要支付游票 " + "<font color ='#1EADEC'>" + needUtickets + " </font> 张 或 "+"<font color ='#B90000'>" + buyUntiketMoney + " </font> 元";
            tvup = "您当前游票余额 " + "<font color ='#1EADEC'>" + utickets + " </font> 张 ";
            buyNeedsMoney = "是否支付 " + "<font color ='#B90000'> " + buyUntiketMoney + " </font>元购买" + "<font color ='#1EADEC'> " + Math.abs(buyUntiketnumber) + " </font>游票";
            // btnOk.setText("购买并报名");
            bug_untiket.setTextSize(14);
            // bug_untiket.setText(Html.fromHtml(buyNeedsMoney));
            // tv_title.setText("游票不足");
            tvLine.setVisibility(View.GONE);
            bug_untiket.setVisibility(View.GONE);
            tv_title.setVisibility(View.INVISIBLE);
            llAction.setVisibility(View.GONE);
            originBuy.setVisibility(View.VISIBLE);
            originBuy.setText("原价支付 " + buyUntiketMoney + "元");
            tvNonpayment.setVisibility(View.VISIBLE);
        } else {//去支付
//            bug_untiket.setVisibility(View.GONE);
//            preferential_tv.setVisibility(View.GONE);
//            tvup = "您的游票余额游票 " + "<font color ='#B90000'>" + utickets + " </font>张";
//            tvdown = "需要支付游票 " + "<font color ='#B90000'>" + needUtickets + " </font>张";
//            btnOk.setText("支付");
//            tv_title.setText("支付");
            buyUntiketnumber = Integer.valueOf(needUtickets);
            buyUntiketMoney = Integer.valueOf(needUtickets) * Double.valueOf(price);

            preferential_tv.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
            bug_untiket.setVisibility(View.GONE);
            tv_title.setVisibility(View.INVISIBLE);
            llAction.setVisibility(View.GONE);

            untiketRemainBuy.setVisibility(View.VISIBLE);
            if (buyUntiketMoney == 0){
                originBuy.setText("原价支付 " + buyUntiketMoney + "元");
                originBuy.setVisibility(View.GONE);
            }else {
                originBuy.setText("原价支付 " + buyUntiketMoney + "元");
                originBuy.setVisibility(View.VISIBLE);
            }

            tvNonpayment.setVisibility(View.VISIBLE);

            tvdown = "报名需要使用 " + "<font color ='#1EADEC'>" + needUtickets + " </font> 游票 或 "+"<font color ='#B90000'>" + buyUntiketMoney + " </font> 元";
            tvup = "您当前游票余额 " + "<font color ='#1EADEC'>" + utickets + " </font> 张 ";

            btnOk.setText("确认报名");
            tv_title.setText("确认报名");
        }
        cofirm_unticket.setText(Html.fromHtml(tvup));
        my_untiket.setTextSize(14);
        my_untiket.setText(Html.fromHtml(tvdown));
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        preferential_tv.setOnClickListener(v -> {
            dialog.dismiss();
            IntentUtils.startIntent(LessonsOrderDetailActivity.this, UTicketActivity.class);
        });
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (flags) {
                //去支付
                confirmOrder();
            } else {
                //去购买
//                if (!TextUtils.isEmpty(untiketId)) {
//                    Intent intent = new Intent(LessonsOrderDetailActivity.this, SelectPayTypeActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("flag", 2);
//                    bundle.putDouble("buyNum", buyUntiketMoney);
//                    bundle.putInt("number", buyUntiketnumber);
//                    bundle.putString("untiketId", untiketId);
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
            }
        });
        preferential_tv.setOnClickListener(v -> {//游票商城
            dialog.dismiss();
            if (!TextUtils.isEmpty(orderId)) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                bundle.putString("orderId", orderId);
                bundle.putString("frequencyId",frequencyId);
                IntentUtils.startNewIntentBundle(LessonsOrderDetailActivity.this, bundle, UTicketActivity.class);
                finish();
            }
        });
        originBuy.setOnClickListener(V->{
            dialog.dismiss();
            //去购买
            if (!TextUtils.isEmpty(untiketId)) {
                Intent intent = new Intent(LessonsOrderDetailActivity.this, SelectPayTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                bundle.putInt("from", 1);
                bundle.putDouble("buyNum", buyUntiketMoney);
                bundle.putInt("number", buyUntiketnumber);
                bundle.putString("untiketId", untiketId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        untiketRemainBuy.setOnClickListener(V->{//游票充足用游票支付
            dialog.dismiss();
            //去支付
            if (!TextUtils.isEmpty(orderId))  confirmOrder();
        });
        tvNonpayment.setOnClickListener(v->{
            dialog.dismiss();
        });
    }

    private void confirmOrder() {
        MineUtils.getInstance().payMyOrder(orderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                ToastUtils.showToast("支付成功");
                EventBus.getDefault().post(new CourseOrderEvnet());
                prepareData();
                Bundle bundle = new Bundle();
                bundle.putString("frequencyId", frequencyId);
                bundle.putString("orderId", orderId);
                bundle.putInt("flag", 1);//课程名称
                IntentUtils.startIntentBundle(LessonsOrderDetailActivity.this, bundle, SignUpSucessActivity.class);
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    public void onEventMainThread(PayOrderEvent event) {
        confirmOrder();
    }

    private void showClearPop() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_clear_lessons, null);
        clearLessonPopWindow = new PopupWindow(contentView, FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        TextView tvClearReset = (TextView) contentView.findViewById(R.id.tv_clear_reset);

        clearLessonPopWindow.setFocusable(true);
        clearLessonPopWindow.setTouchable(true);
        clearLessonPopWindow.setBackgroundDrawable(new BitmapDrawable());
        clearLessonPopWindow.setOutsideTouchable(true);// 设置允许在外点击消失

        tvClearReset.setOnClickListener(V -> {
            if (!TextUtils.isEmpty(orderId)) cancelOrder();
        });
        clearLessonPopWindow.setOnDismissListener(() -> setBackgroundAlpha(1.0f));
    }
    private void showDailog() {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage("确定取消此订单？")
                .setPositiveButton("确定", (dialog12, which) -> {
                    cancelOrder();
                    dialog12.dismiss();
                }).setNegativeButton("返回",((dialog1, which) -> {
            dialog1.dismiss();
        }));
        dialog.show() ;
    }

    private void cancelOrder() {
        MineUtils.getInstance().cancelOrder(orderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new CourseOrderEvnet());
                EventBus.getDefault().post(new MineListEvent());
                IntentUtils.startIntent(LessonsOrderDetailActivity.this,MyOrderListActivity.class);
                LessonsOrderDetailActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_lessons_order_detail;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            orderId = bundle.getString("orderId");
            frequencyId = bundle.getString("frequencyId");
        }
        mineListLessonsAdapter = new MineListLessonsAdapter(this, 1, 0, orderId, lessonsExpandleTb);
        mineLessonsListLv.setAdapter(mineListLessonsAdapter);
        lessonsExpandleTb.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (allList != null) {
                    mineListLessonsAdapter.setItems(allList);
                    mineLessonsListLv.setBackgroundColor(
                            ContextCompat.getColor(LessonsOrderDetailActivity.this, R.color.list_bg));
                }
            } else {
                if (list != null) {
                    mineListLessonsAdapter.setItems(list);
                    mineLessonsListLv.setBackgroundColor(
                            ContextCompat.getColor(LessonsOrderDetailActivity.this, R.color.white));
                }
            }
        });
        seeCourseTime.setOnClickListener(V -> {if(frequencyType > 0)showCheckTime();});
    }

    private void showCheckTime() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.check_time_layout, null);
        //对话框
        Dialog dialog = new AlertDialog.Builder(this, R.style.CustomDialog).create();
        dialog.show();
        dialog.getWindow().setContentView(conentView);

        ListView lvList = (ListView) conentView.findViewById(R.id.time_list);
        TextView tvEnsure = (TextView) conentView.findViewById(R.id.ensure);

        tvEnsure.setOnClickListener(view -> dialog.dismiss());
        checkTimeAdapter = new CheckTimeAdapter(this,frequencyType);
        lvList.setAdapter(checkTimeAdapter);
        SignUpUtils.getInstance().getCourseFrequencyDetail(frequencyId,orderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }

            @Override
            public void onSuccess(String result) {
                Log.d("check==", result);
                try {
                    JSONArray array = new JSONArray(result);
                    List<JSONObject> listTime;
                    if (array != null && array.length() > 0) {
                        listTime = new ArrayList<>();
                        for (int i = 0; i < array.length(); i++) {
                            listTime.add(array.getJSONObject(i));
                        }
                        checkTimeAdapter.setItems(listTime);
                        checkTimeAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        progressBar.show();
        MineUtils.getInstance().getLessonsOrderDetail(orderId, frequencyId, this);
    }

    /**
     * 设置购课信息
     */
    private void setLessonsOrderData(MineLessonsEntity mineLessonsEntity) {
        if (mineLessonsEntity == null) {
            return;
        }
        courseId = mineLessonsEntity.CourseId;
        teacherId = mineLessonsEntity.TeacherId;
        teacherName = mineLessonsEntity.TeacherName;
        if (mineLessonsEntity.HeadPhoto != null) {
            imageLoaderUtils.loadRoundImage(
                    Constants.imageUrl + mineLessonsEntity.HeadPhoto + "?w=400&h=400",
                    lessonsHeadimgIv, R.drawable.noavatar, DensityUtil.dip2px(this, 60));
        }
        lessonsNameTv.setText(mineLessonsEntity.TeacherName);
        lessonsCoursenameTv.setText("《" + mineLessonsEntity.CourseName + "》");
        //lessonsClassnameTv.setText("班级名称：" + mineLessonsEntity.FrequencyName);
        lessonsClassnameTv.setVisibility(View.GONE);
        lessonsAttentionTv.setVisibility(View.VISIBLE);
        lessonsAttentionTv.setText(mineLessonsEntity.Attention);
        String dropInAddress = mineLessonsEntity.DropInAddress;
        if (mineLessonsEntity.FrequencyType == 2){
            String strAddress = "<font color='#999999'>上课地址：</font><font color='#555555'>" + dropInAddress + " </font>";
            lessonsAddressTv.setText(Html.fromHtml(strAddress));
        }else {
            StringBuffer address = new StringBuffer();
            if (!TextUtils.isEmpty(mineLessonsEntity.AreaName)) {
                address.append(mineLessonsEntity.AreaName + " ");
                if (!TextUtils.isEmpty(mineLessonsEntity.Street)) {
                    address.append(mineLessonsEntity.Street + " ");
                    if (!TextUtils.isEmpty(mineLessonsEntity.District)) {
                        address.append(mineLessonsEntity.District + " ");
                        if (!TextUtils.isEmpty(mineLessonsEntity.HouseNum)) {
                            address.append(mineLessonsEntity.HouseNum + " ");
                            if (!TextUtils.isEmpty(mineLessonsEntity.AddressDetail)) {
                                address.append(mineLessonsEntity.AddressDetail + " ");
                            }
                        }
                    }
                }
            }
            String strAddress = "<font color='#999999'>上课地址：</font><font color='#555555'>" + address + " </font>";
            lessonsAddressTv.setText(Html.fromHtml(strAddress));
        }

        if (isExapan) {
            lessonsExpandleTb.setChecked(true);
        } else {
            lessonsExpandleTb.setChecked(false);
        }

        String strDuration = "<font color='#999999'>课程节数：</font><font color='#555555'>" +"  共" + mineLessonsEntity.ClassCount + "课" + " </font>";
        lessonsDurationTv.setText(Html.fromHtml(strDuration));
        allList = mineLessonsEntity.FrequencyDetails;
        list = new ArrayList<>();
        if (allList != null && allList.size() > 0) {
            for (int i = 0; i < allList.size(); i++) {
                MineLessonsEntity.DetailShowResponsesBean detailShowResponsesBean = allList.get(i);
                if (detailShowResponsesBean.Status == 2) {
                    list.add(detailShowResponsesBean);
                } else if (detailShowResponsesBean.Status == 1) {
                    detailShowResponsesBean.isShowCode = true;
                    list.add(detailShowResponsesBean);
                    break;
                }
            }
            if (list.size() == 0) {
                list.add(allList.get(0));
            }
        }

        mineListLessonsAdapter.setItems(list);
        List<BabyEntity> babyList = mineLessonsEntity.Children;
        if (babyList != null) {
            StringBuffer learningIntent = new StringBuffer();
            for (int i = 0; i < babyList.size(); i++) {
                if (i == 0) {
                    learningIntent.append(babyList.get(i).Name);
                } else {
                    learningIntent.append("，" + babyList.get(i).Name);
                }
            }
            String strStudent = "<font color='#999999'>上课学生：</font><font color='#555555'>"
                    + learningIntent + " </font>";
            lessonsStudentTv.setText(Html.fromHtml(strStudent));
        }
        String attentionText = mineLessonsEntity.Attention;
        if (TextUtils.isEmpty(attentionText)) attentionText = "";
       // String strAttention = "<font color='#555555'>" + attentionText + " </font>";
        lessonsAttentionTv.setText(attentionText);
        String strOrderTime = "<font color='#999999'>下单时间：</font><font color='#555555'>" + DateUtils.getYmdhm2(mineLessonsEntity.OrderTime) + " </font>";
        String strAffirmOrderTime = "<font color='#999999'>确认时间：</font><font color='#555555'>" + DateUtils.getYmdhm2(mineLessonsEntity.CertifyTime) + " </font>";
        lessonsPlaceOrderTimeTv.setText(Html.fromHtml(strOrderTime));
        lessonsAffirmOrderTimeTv.setText(Html.fromHtml(strAffirmOrderTime));
        String strPayUticket = "<font color='#999999'>支付时间：</font><font color='#555555'>" + DateUtils.getYmdhm2(mineLessonsEntity.PayTime) + " </font>";
        lessonsPayUticketTv.setText(Html.fromHtml(strPayUticket));
        String strCancel = "<font color='#999999'>取消时间：</font><font color='#555555'>" + DateUtils.getYmdhm2(mineLessonsEntity.CancelTime) + " </font>";
        lessonsCancelOrderTimeTv.setText(Html.fromHtml(strCancel));
        String orderStatusStr = "";
        if (mineLessonsEntity.OrderStatus == 1) {
            payInfo.setVisibility(View.VISIBLE);
            uncertainPayInfo.setVisibility(View.GONE);
            lessonsAffirmOrderTimeTv.setVisibility(View.GONE);
            lessonsPayUticketTv.setVisibility(View.GONE);
            orderStatusStr = "待支付";
            lessonsStatusTv.setTextColor(UIUtil.getColor(R.color.setting_reply));
            barRightImage.setVisibility(View.VISIBLE);
        } else if (mineLessonsEntity.OrderStatus == 2) {
            payInfo.setVisibility(View.GONE);
            uncertainPayInfo.setVisibility(View.VISIBLE);
            lessonsAffirmOrderTimeTv.setVisibility(View.GONE);
            lessonsPayUticketTv.setVisibility(View.VISIBLE);
            orderStatusStr = "已支付，等待老师确认";
            lessonsStatusTv.setTextColor(UIUtil.getColor(R.color.setting_reply));
            barRightImage.setVisibility(View.GONE);
        } else if (mineLessonsEntity.OrderStatus == 3) {
            orderStatusStr = "支付失败，订单已过期";
            lessonsStatusTv.setTextColor(UIUtil.getColor(R.color.text_color_66));
        } else if (mineLessonsEntity.OrderStatus == 4) {
            orderStatusStr = "报名成功";
            lessonsAffirmOrderTimeTv.setVisibility(View.VISIBLE);
            lessonsPayUticketTv.setVisibility(View.VISIBLE);
            lessonsStatusTv.setTextColor(UIUtil.getColor(R.color.theme_blue));
        } else if (mineLessonsEntity.OrderStatus == 5) {
            orderStatusStr = "审核未通过";
            lessonsAffirmOrderTimeTv.setVisibility(View.VISIBLE);
        } else if (mineLessonsEntity.OrderStatus == 6) {
            orderStatusStr = "已取消";
            lessonsCancelOrderTimeTv.setVisibility(View.VISIBLE);
        } else if (mineLessonsEntity.OrderStatus == 7) {
            orderStatusStr = "申请退款";
        } else if (mineLessonsEntity.OrderStatus == 8) {
            orderStatusStr = "已退款";
        } else if (mineLessonsEntity.OrderStatus == 9) {
            orderStatusStr = "家长未上课，已过期";
        } else if (mineLessonsEntity.OrderStatus == 10) {
            orderStatusStr = "超时未审核";
        } else if (mineLessonsEntity.OrderStatus == 16) {
            orderStatusStr = "已完成";
        }
        lessonsStatusTv.setText("  " + orderStatusStr);

        orderNumber.setText("订单号： " + mineLessonsEntity.OrderNum);
        String str = "<font color='#999999'>班级：</font><font color='#555555'>" + mineLessonsEntity.FrequencyName + " </font>";
        courseName.setText(Html.fromHtml(str));
        oneprice = mineLessonsEntity.Price;
        uTicketPrice = mineLessonsEntity.UTicketPrice;
        needUntiket = mineLessonsEntity.OrderPrice;

        if (!TextUtils.isEmpty(uTicketPrice)) {
            if (!TextUtils.isEmpty(mineLessonsEntity.TotalPrice)) {//总计的
                allCountUticket.setText(mineLessonsEntity.TotalPrice + " 游票 （或 " + Integer.valueOf(mineLessonsEntity.TotalPrice) * Double.valueOf(uTicketPrice) + "元）");

            }
            if (!TextUtils.isEmpty(mineLessonsEntity.OrderPrice)) {//实付的
                String str1 = "<font color='#ee5f5f'>" + mineLessonsEntity.OrderPrice + "</font> 游票（或 <font color='#ee5f5f'>" + Integer.valueOf(mineLessonsEntity.OrderPrice) * Double.valueOf(uTicketPrice) + "</font>元）";
                receivable.setText(Html.fromHtml(str1));
                uncertainAllCountUticket.setText(mineLessonsEntity.OrderPrice + " 游票");
            }

            if (!TextUtils.isEmpty(oneprice)) {
                coursePrice.setText(oneprice + "游票(价值" + Integer.valueOf(oneprice) * Double.valueOf(uTicketPrice) + "元)/课时");
                uncertainCoursePrice.setText(oneprice + " 游票/课时");
            }
        }
        courseAllnumber.setText(mineLessonsEntity.ClassCount + " 课时");
        uncertainCourseAllnumber.setText(mineLessonsEntity.ClassCount + " 课时");
        if (babyList != null) {
            courseSignupNumber.setText(babyList.size() + " 人");
            uncertainCourseSignupNumber.setText(babyList.size() + " 人");
        }

        untiketId = mineLessonsEntity.UTicketId;
    }

    @Override
    public void onLessonsOrderDetailSucess(MineLessonsEntity mineLessonsEntity) {
        progressBar.hide();
        frequencyType = mineLessonsEntity.FrequencyType;
        setLessonsOrderData(mineLessonsEntity);
    }

    @Override
    public void onLessonsOrderDetailFailure(String error) {
        progressBar.hide();
        ToastUtils.showToast(error);
    }

    public void onEventMainThread(LessonsOrderEvent event) {
        isExapan = false;
        prepareData();
    }


    @OnClick(R.id.call_phone_bt)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.call_phone_bt: {
                showServicePhoneDialog();
                break;
            }
        }
    }

    /**
     * 显示拨打电话窗口
     */
    public void showServicePhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LessonsOrderDetailActivity.this);
        final String phone = "4000-722-788";
        builder.setMessage(phone)
                .setCancelable(false)
                .setTitle(getString(R.string.lessons_phone_bt_txt))
                .setPositiveButton("呼叫", (dialog, id) -> {
                    //			        	   dialog.cancel();
                    LessonsOrderDetailActivityPermissionsDispatcher
                            .makeServicePhoneWithCheck(LessonsOrderDetailActivity.this, phone);
                })
                .setNegativeButton("取消", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * 拨打服务电话
     */
    @NeedsPermission({Manifest.permission.CALL_PHONE})
    void makeServicePhone(String phone) {

        Intent phoneIntent = new Intent(
                "android.intent.action.CALL", Uri.parse("tel:" + phone));
        startActivity(phoneIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        LessonsOrderDetailActivityPermissionsDispatcher
                .onRequestPermissionsResult(LessonsOrderDetailActivity.this, requestCode,
                        grantResults);
    }

    /**
     * 一旦用户拒绝了
     */
    @OnPermissionDenied({
            Manifest.permission.CALL_PHONE
    })
    void onMakePhoneDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        ToastUtils.showToast("您拒绝了拨打电话权限，无法联系老师");
    }

    /**
     * 用户选择的不再询问
     */
    @OnNeverAskAgain({
            Manifest.permission.CALL_PHONE
    })
    void onMakePhoneNeverAskAgain() {
        ToastUtils.showToast("您拒绝了拨打电话权限，无法联系老师");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
