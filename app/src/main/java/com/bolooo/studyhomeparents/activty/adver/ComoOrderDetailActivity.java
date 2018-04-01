package com.bolooo.studyhomeparents.activty.adver;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherActivity;
import com.bolooo.studyhomeparents.activty.mine.SelectPayTypeActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.order.ComboDetailEntity;
import com.bolooo.studyhomeparents.event.ComboOrderEvent;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.PayOrderEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DateUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.CustomerComboCourseRelayLayout;

import org.json.JSONException;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ComoOrderDetailActivity extends BaseActivity implements IRequestCallBack {

    @Bind(R.id.bar_right_image)
    ImageView barRightImage;
    @Bind(R.id.order_number)
    TextView orderNumber;
    @Bind(R.id.combo_image)
    ImageView comboImage;
    @Bind(R.id.combo_name)
    TextView comboName;
    @Bind(R.id.rl_combo)
    RelativeLayout rlCombo;
    @Bind(R.id.ll_contait_course)
    LinearLayout llContaitCourse;
    @Bind(R.id.sign_up_student)
    TextView signUpStudent;
    @Bind(R.id.tv_all_couse_count)
    TextView tvAllCouseCount;
    @Bind(R.id.sign_up_number)
    TextView signUpNumber;
    @Bind(R.id.tv_orgin_price)
    TextView tvOrginPrice;
    @Bind(R.id.tv_discount_price)
    TextView tvDiscountPrice;
    @Bind(R.id.tv_palce_order_time)
    TextView tvPalceOrderTime;
    @Bind(R.id.tv_pay_time)
    TextView tvPayTime;
    @Bind(R.id.tv_comfrim_time)
    TextView tvComfrimTime;
    @Bind(R.id.tv_order_status)
    TextView tvOrderStatus;
    @Bind(R.id.uncert_layout)
    LinearLayout uncertLayout;
    @Bind(R.id.order_course_allnumber)
    TextView orderCourseAllnumber;
    @Bind(R.id.order_course_signup_number)
    TextView orderCourseSignupNumber;
    @Bind(R.id.order_original_price)
    TextView orderOriginalPrice;
    @Bind(R.id.order_combo_discount)
    TextView orderComboDiscount;
    @Bind(R.id.order_receivable)
    TextView orderReceivable;
    @Bind(R.id.course_sign_up)
    TextView courseSignUp;
    @Bind(R.id.ll_buy_info)
    LinearLayout llBuyInfo;
    @Bind(R.id.tv_cance_time)
    TextView tvCanceTime;
    @Bind(R.id.rl_pay_time)
    RelativeLayout rlPayTime;
    @Bind(R.id.rl_comfrim_time)
    RelativeLayout rlComfrimTime;
    @Bind(R.id.rl_cance_time)
    RelativeLayout rlCanceTime;
    private String tcOrderId;
    private PopupWindow clearLessonPopWindow;
    private String untiketId;
    private String needUntiket;
    private String untiketPrice;
    private int buyUntiketnumber;
    private double buyUntiketMoney;
    private ComboDetailEntity comboDetailEntity;

    @Override
    protected void initView() {
        initBar();
        insureBar.setTitle(getString(R.string.lessons_order_detail_title));
        barRightImage.setVisibility(View.GONE);
        barRightImage.setImageResource(R.drawable.ticon_more);
        barRightImage.setOnClickListener(V -> {
//            if (clearLessonPopWindow != null) {
//                clearLessonPopWindow.showAtLocation(barRightImage, Gravity.TOP | Gravity.RIGHT, DensityUtil.dip2px(this, 40), DensityUtil.dip2px(this, 60));
//                setBackgroundAlpha(0.5f);
//            }
            showDailog();
        });
       // showClearPop();
        courseSignUp.setOnClickListener(V -> {
            if (!TextUtils.isEmpty(needUntiket) && !TextUtils.isEmpty(untiketPrice))
                checkUntiketNumer();
        });
        rlCombo.setOnClickListener(v->{
            if (comboDetailEntity != null)AdDetailActivity.openHtmlActivity(this,comboDetailEntity.PackageId);
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
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    int allUntikets = object.optInt("UTickets");
                    if (Integer.valueOf(needUntiket) > allUntikets) {
                        showDialogInsufficient(needUntiket, allUntikets, untiketPrice, false);
                    } else {
                        showDialogInsufficient(needUntiket, allUntikets, untiketPrice, true);
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
           // buyUntiketMoney = Math.abs(utickets - Integer.valueOf(needUtickets)) * Double.valueOf(price);
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
            bug_untiket.setVisibility(View.GONE);
            preferential_tv.setVisibility(View.GONE);
            tvup = "您的游票余额游票 " + "<font color ='#B90000'>" + utickets + " </font>张";
            tvdown = "需要支付游票 " + "<font color ='#B90000'>" + needUtickets + " </font>张";
            btnOk.setText("支付");
            tv_title.setText("支付");
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
        }
        cofirm_unticket.setText(Html.fromHtml(tvup));
        my_untiket.setTextSize(14);
        my_untiket.setText(Html.fromHtml(tvdown));
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        preferential_tv.setOnClickListener(v -> {
            dialog.dismiss();
            IntentUtils.startIntent(ComoOrderDetailActivity.this, UTicketActivity.class);
        });
        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            if (flags) {
                //去支付
                confirmOrder();
            } else {
                //去购买
//                if (!TextUtils.isEmpty(untiketId)) {
//                    Intent intent = new Intent(ComoOrderDetailActivity.this, SelectPayTypeActivity.class);
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
        preferential_tv.setOnClickListener(v -> {
            dialog.dismiss();
            if (!TextUtils.isEmpty(tcOrderId)) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                bundle.putString("orderId", tcOrderId);
                IntentUtils.startNewIntentBundle(ComoOrderDetailActivity.this, bundle, UTicketActivity.class);
                finish();
            }
        });
        untiketRemainBuy.setOnClickListener(v->{
            dialog.dismiss();
            //去支付
            if (TextUtils.isEmpty(tcOrderId))confirmOrder();
        });
        originBuy.setOnClickListener(V->{
            dialog.dismiss();
            //去购买
            Intent intent = new Intent(ComoOrderDetailActivity.this, SelectPayTypeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("flag", 2);
            bundle.putInt("from", 1);
            bundle.putDouble("buyNum", buyUntiketMoney);
            bundle.putInt("number", buyUntiketnumber);
            bundle.putString("untiketId", untiketId);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        tvNonpayment.setOnClickListener(v->{
            dialog.dismiss();
        });
    }

    private void confirmOrder() {
        MineUtils.getInstance().signUpPayComboOrder(tcOrderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                ToastUtils.showToast("支付成功");
                EventBus.getDefault().post(new ComboOrderEvent());
                prepareData();
                Bundle bundle = new Bundle();
                bundle.putString("tcId",tcOrderId);
                IntentUtils.startNewIntentBundle(ComoOrderDetailActivity.this, bundle, ComboSignUpSucessActivity.class);
                ComoOrderDetailActivity.this.finish();
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
    private void showDailog() {
        android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setMessage("确定取消此订单？")
                .setPositiveButton("确定", (dialog12, which) -> {
                    cancelComboOrder();
                    dialog12.dismiss();
                }).setNegativeButton("返回",((dialog1, which) -> {
            dialog1.dismiss();
        }));
        dialog.show() ;
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
            if (!TextUtils.isEmpty(tcOrderId)) cancelComboOrder();
        });
        clearLessonPopWindow.setOnDismissListener(() -> setBackgroundAlpha(1.0f));
    }

    private void cancelComboOrder() {
        MineUtils.getInstance().cancelComboOrderList(tcOrderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new ComboOrderEvent());
                EventBus.getDefault().post(new MineListEvent());
                ComoOrderDetailActivity.this.finish();
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
        return R.layout.activity_como_order_detail;
    }

    @Override
    protected void initDate() {
        super.initDate();
        Bundle extras = getIntent().getExtras();
        if (extras != null) tcOrderId = extras.getString("orderId");
    }

    @Override
    protected void prepareData() {
        super.prepareData();
        if (!TextUtils.isEmpty(tcOrderId)) {
            MineUtils.getInstance().getComboOrderDetail(tcOrderId, this);
        }
    }

    @Override
    public void onStartLoading() {

    }

    @Override
    public void onSuccess(String result) {
        comboDetailEntity = JSONObject.parseObject(result, ComboDetailEntity.class);
        if (comboDetailEntity != null) fillData(comboDetailEntity);
    }


    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }


    private void fillData(ComboDetailEntity comboDetailEntity) {
        comboDetailEntity.getId();
        orderNumber.setText("订单号：" + comboDetailEntity.getOrderNumber());
        imageLoaderUtils.loadFileImage(comboDetailEntity.getPackageImg(), comboImage);
        comboName.setText(comboDetailEntity.getPackageName());
        List<ComboDetailEntity.FrequencysEntity> frequencys = comboDetailEntity.getFrequencys();
        if (frequencys != null && !frequencys.isEmpty()) {
            for (int i = 0; i < frequencys.size(); i++) {
                ComboDetailEntity.FrequencysEntity frequencysEntity = frequencys.get(i);
                CustomerComboCourseRelayLayout customerRelayLayout = new CustomerComboCourseRelayLayout(this);
                customerRelayLayout.setteacherCourseName(frequencysEntity.getCourseName());
                customerRelayLayout.setteacherPhoto(frequencysEntity.getHeadPhoto());
                customerRelayLayout.setteacherDes(frequencysEntity.getFrequencyName());
                customerRelayLayout.setteacherName(frequencysEntity.getTeacherName());
                llContaitCourse.addView(customerRelayLayout);
                customerRelayLayout.setOnClickListener(v->intentTeacherDetail(frequencysEntity));
            }
        }
        signUpStudent.setText(comboDetailEntity.getChildrenNames());
        tvAllCouseCount.setText(comboDetailEntity.getCourseCount() + " 课时");
        signUpNumber.setText(comboDetailEntity.getBuyCount() + " 人");
        tvOrginPrice.setText(comboDetailEntity.getOriginalPrice() + " 游票");
        tvDiscountPrice.setText(comboDetailEntity.getDiscountPrice() + " 游票");
        orderCourseAllnumber.setText(comboDetailEntity.getCourseCount() + " 课时");
        orderCourseSignupNumber.setText(comboDetailEntity.getBuyCount() + " 人");
        String recvieStr = "<font color='#EF5F5F'>" + comboDetailEntity.getDiscountPrice() + "</font> 游票（ 或 <font color='#EF5F5F'>"
                + comboDetailEntity.getDiscountPrice() * comboDetailEntity.getUTicketPrice() + "</font>元）";
        orderOriginalPrice.setText(comboDetailEntity.getOriginalPrice() + " 游票（ 或 " +
                comboDetailEntity.getOriginalPrice() * comboDetailEntity.getUTicketPrice() + "元）");
        orderComboDiscount.setText((comboDetailEntity.getOriginalPrice() - comboDetailEntity.getDiscountPrice())
                + " 游票（ 或 " +
                (comboDetailEntity.getOriginalPrice() - comboDetailEntity.getDiscountPrice()) * comboDetailEntity.getUTicketPrice() + "元）");
        orderReceivable.setText(Html.fromHtml(recvieStr));
        int orderStatus = comboDetailEntity.getOrderStatus();
        tvPalceOrderTime.setText(DateUtils.getYmdhm2(comboDetailEntity.getOrderTime()));
        tvPayTime.setText(DateUtils.getYmdhm2(comboDetailEntity.getPayTime()));
        tvComfrimTime.setText(DateUtils.getYmdhm2(comboDetailEntity.getCancelTime()));
        tvCanceTime.setText(DateUtils.getYmdhm2(comboDetailEntity.getCancelTime()));
        uncertLayout.setVisibility(View.VISIBLE);
        String orderStatusStr = "";
        if (orderStatus == 1) {
            llBuyInfo.setVisibility(View.VISIBLE);
            uncertLayout.setVisibility(View.GONE);
            orderStatusStr = "待支付";
            rlPayTime.setVisibility(View.GONE);
            rlComfrimTime.setVisibility(View.GONE);
            rlCanceTime.setVisibility(View.GONE);
            tvOrderStatus.setTextColor(UIUtil.getColor(R.color.setting_reply));
            barRightImage.setVisibility(View.VISIBLE);
//        } else if (orderStatus == 2) {
//            llBuyInfo.setVisibility(View.GONE);
//            uncertLayout.setVisibility(View.VISIBLE);
//            orderStatusStr = "已支付，等待老师确认";
//            tvOrderStatus.setTextColor(UIUtil.getColor(R.color.setting_reply));
//            rlPayTime.setVisibility(View.VISIBLE);
//            barRightImage.setVisibility(View.GONE);
//        } else if (orderStatus == 3) {
//            orderStatusStr = "支付失败，订单已过期";
        } else if (orderStatus == 2) {
            orderStatusStr = "报名成功";
            rlPayTime.setVisibility(View.VISIBLE);
            rlCanceTime.setVisibility(View.GONE);
            rlComfrimTime.setVisibility(View.GONE);
            tvOrderStatus.setTextColor(UIUtil.getColor(R.color.theme_blue));
//        } else if (orderStatus == 5) {
//            orderStatusStr = "审核未通过";
        } else if (orderStatus == 3) {
            orderStatusStr = "已取消";
            tvOrderStatus.setTextColor(UIUtil.getColor(R.color.text_color_66));
            rlPayTime.setVisibility(View.GONE);
            rlComfrimTime.setVisibility(View.GONE);
            rlCanceTime.setVisibility(View.VISIBLE);
            tvOrderStatus.setVisibility(View.VISIBLE);
        } else if (orderStatus == 7) {
            orderStatusStr = "申请退款";
        } else if (orderStatus == 8) {
            orderStatusStr = "已退款";
        } else if (orderStatus == 9) {
            orderStatusStr = "家长未上课，已过期";
        } else if (orderStatus == 10) {
            orderStatusStr = "超时未审核";
        } else if (orderStatus == 16) {
            orderStatusStr = "已完成";
        }
        tvOrderStatus.setText("  " + orderStatusStr);
        untiketId = comboDetailEntity.getUTicketId();
        needUntiket = String.valueOf(comboDetailEntity.getDiscountPrice());
        untiketPrice = String.valueOf(comboDetailEntity.getUTicketPrice());
    }

    private void intentTeacherDetail(ComboDetailEntity.FrequencysEntity frequencysEntity) {
        Bundle bundle = new Bundle();
        bundle.putString("teacherId", frequencysEntity.getTeacherId());
        bundle.putString("courseId", frequencysEntity.CourseId);
        bundle.putString("teacherName", frequencysEntity.getTeacherName());
        IntentUtils.startIntentBundle(this, bundle, TeacherActivity.class);
    }

    @OnClick(R.id.call_phone_bt)
    public void onClick() {
        showServicePhoneDialog();
    }

    /**
     * 显示拨打电话窗口
     */
    public void showServicePhoneDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ComoOrderDetailActivity.this);
        final String phone = "4000-722-788";
        builder.setMessage(phone)
                .setCancelable(false)
                .setTitle(getString(R.string.lessons_phone_bt_txt))
                .setPositiveButton("呼叫", (dialog, id) -> {
                    //			        	   dialog.cancel();
                    ComoOrderDetailActivityPermissionsDispatcher
                            .makeServicePhoneWithCheck(ComoOrderDetailActivity.this, phone);
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
        ComoOrderDetailActivityPermissionsDispatcher
                .onRequestPermissionsResult(ComoOrderDetailActivity.this, requestCode,
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
