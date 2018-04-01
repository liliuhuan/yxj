package com.bolooo.studyhomeparents.activty.adver;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.SelectPayTypeActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.adapter.order.ComboChildListAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.ad.CombobuyEntity;
import com.bolooo.studyhomeparents.event.CourseOrderDetailEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.MyBabyEvent;
import com.bolooo.studyhomeparents.event.PayOrderEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.CustomerComboCourseRelayLayout;
import com.bolooo.studyhomeparents.views.MyGridView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

import static com.bolooo.studyhomeparents.R.id.course_sign_up;

/**
 * 套餐购买页面
 */
public class ComboBuyActivity extends BaseActivity implements IRequestCallBack {
    CombobuyEntity combobuyEntity;
    @Bind(R.id.combo_image)
    ImageView comboImage;
    @Bind(R.id.combo_name)
    TextView comboName;
    @Bind(R.id.ll_contait_course)
    LinearLayout llContaitCourse;
    @Bind(R.id.child_grid_view)
    MyGridView childGridView;
    @Bind(R.id.course_allnumber)
    TextView courseAllnumber;
    @Bind(R.id.course_signup_number)
    TextView courseSignupNumber;
    @Bind(R.id.original_price)
    TextView originalPrice;
    @Bind(R.id.combo_discount)
    TextView comboDiscount;
    @Bind(R.id.receivable)
    TextView receivable;
    @Bind(course_sign_up)
    TextView courseSignUp;
    @Bind(R.id.ll_buy_info)
    LinearLayout llBuyInfo;
    private String tcId;
    private ArrayList childList;
    private ComboChildListAdapter adapter;
    private List<String> selectChildIds;
    private String untiketId;
    private int buyUntiketnumber;
    private double buyUntiketMoney;
    private int needUntiket;
    private double uTicketPrice;
    private String pOrderId;
    private int personnumberSize;
    private int mPrice;
    private int selecetSize = 1;

    @Override
    protected void initView() {
        Bundle extras = getIntent().getExtras();
        if (extras != null) tcId = extras.getString("tcId");
        initBar();
        insureBar.getCheckBox().setVisibility(View.GONE);
        insureBar.getBack().setOnClickListener(v->finish());
        childList = new ArrayList<>();
        adapter = new ComboChildListAdapter(this,tcId);
        childGridView.setAdapter(adapter);

        courseSignUp.setOnClickListener(V->{
            if (selectChildIds!=null && !selectChildIds.isEmpty())createComboOrder();
        });
    }

    private void createComboOrder() {
        MineUtils.getInstance().createComoboOrder(tcId, selectChildIds, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new MineListEvent());
                pOrderId = result;
                checkUntiketNumer();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }
    public void onEventMainThread(MyBabyEvent event) {
       // prepareData();
        selecetSize +=1;
        getChildData();//获取孩子信息
    }
    @Override
    public int initLoadResId() {
        return R.layout.activity_combo_buy;
    }
    @Override
    protected void prepareData() {
        super.prepareData();
        if (!TextUtils.isEmpty(tcId)) MineUtils.getInstance().getComboBuyInfo(tcId, this);

    }

    @Override
    public void onStartLoading() {}
    @Override
    public void onSuccess(String result) {
        combobuyEntity = JSONObject.parseObject(result, CombobuyEntity.class);
        if (combobuyEntity != null) fillData();
        getChildData();
    }
    @Override
    public void onError(String error) {
        ToastUtils.showToast(error);
    }
    private void getChildData() {
        SignUpUtils.getInstance().getChild(new IRequestCallBack() {
            @Override
            public void onStartLoading() {}
            @Override
            public void onSuccess(String result) {
                Log.d("child==", result);
                try {
                    //if (progressBar != null) progressBar.hide();
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        List<org.json.JSONObject> list = new ArrayList<org.json.JSONObject>();
                        org.json.JSONObject fristChild = null;
                        for (int i = 0; i < array.length(); i++) {
                            org.json.JSONObject obejct = array.getJSONObject(i);
                            fristChild = array.getJSONObject(0);
                            if (i == 0){
                                obejct.put("isSelect", true);
                            }else {
                                obejct.put("isSelect", false);
                            }
                            list.add(obejct);
                        }
                        org.json.JSONObject lastObeject = new org.json.JSONObject();
                        lastObeject.put("NickName", "lastOne");
                        lastObeject.put("Id", "lastOne");
                        lastObeject.put("Name", "lastOne");
                        lastObeject.put("HeadPhoto", "lastOne");
                        lastObeject.put("isSelect", false);
                        list.add(lastObeject);
                        if(list!=null&&childList!=null&&list.size()>childList.size()&&childList.size()>0){
                            childList.add(0,list.get(0));
                        }else {
                            if (childList == null ||childList.size() == 0) childList.addAll(list);
                        }
                        adapter.setItems(childList);

                        if (fristChild != null){
                            initSignUpinfo(fristChild);
                        }
                    } else {
                        org.json.JSONObject lastObeject = new org.json.JSONObject();
                        lastObeject.put("NickName", "lastOne");
                        lastObeject.put("Id", "lastOne");
                        lastObeject.put("Name", "lastOne");
                        lastObeject.put("HeadPhoto", "lastOne");
                        lastObeject.put("isSelect", false);
                        childList.add(lastObeject);
                        adapter.setItems(childList);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String error) {
               // if (progressBar != null) progressBar.hide();
                ToastUtils.showToast(error);
            }
        });
    }
    private void fillData() {
        imageLoaderUtils.loadFileImage(combobuyEntity.getPackageImg(), comboImage);
        comboName.setText(combobuyEntity.getPackageName());
        List<CombobuyEntity.FrequencysEntity> frequencys = combobuyEntity.getFrequencys();
        if (frequencys != null && !frequencys.isEmpty()){
            for (int i = 0; i < frequencys.size(); i++) {
                CombobuyEntity.FrequencysEntity frequencysEntity = frequencys.get(i);
                CustomerComboCourseRelayLayout customerRelayLayout = new CustomerComboCourseRelayLayout(this);
                customerRelayLayout.setteacherCourseName(frequencysEntity.getCourseName());
                customerRelayLayout.setteacherPhoto(frequencysEntity.getHeadPhoto());
                customerRelayLayout.setteacherDes(frequencysEntity.getFrequencyName());
                customerRelayLayout.setteacherName(frequencysEntity.getTeacherName());
                llContaitCourse.addView(customerRelayLayout);

            }
        }

        insureBar.setTitle(combobuyEntity.getPackageName());
        courseAllnumber.setText(combobuyEntity.getCourseCount()+" 课时");
        untiketId  = combobuyEntity.getUTicketId();
        uTicketPrice  = combobuyEntity.getUTicketPrice();
        needUntiket = combobuyEntity.getDiscountPrice();
        adapter.setOnclickLister(new ComboChildListAdapter.OnNumClickLister() {
            @Override
            public void changeNum(int size) {
                selecetSize = size ;
                personnumberSize =  size;
                courseSignupNumber.setText(size+" 人");
                originalPrice.setText(combobuyEntity.getOriginalPrice()*size+" 游票（ 或 "+Double.valueOf(size)*Double.valueOf(combobuyEntity.getOriginalPrice())*combobuyEntity.getUTicketPrice()+" 元）");
                comboDiscount.setText((combobuyEntity.getOriginalPrice()-combobuyEntity.getDiscountPrice())*size+" 游票（ 或 "+Double.valueOf(size)*Double.valueOf((combobuyEntity.getOriginalPrice()-combobuyEntity.getDiscountPrice()))*combobuyEntity.getUTicketPrice()+" 元）");
                String str ="<font color='#EF5F5F'>"+ combobuyEntity.getDiscountPrice() * size + "</font> 游票（ 或 <font color='#EF5F5F'>" + Double.valueOf(size )* Double.valueOf(combobuyEntity.getDiscountPrice()) * combobuyEntity.getUTicketPrice() + "</font> 元）";
                receivable.setText(Html.fromHtml(str));
            }
            @Override
            public void getAllSelect(List<org.json.JSONObject> selectVipChilds) {
                if (selectVipChilds == null || selectVipChilds.size() == 0) {
                    llBuyInfo.setVisibility(View.GONE);
                } else {
                    llBuyInfo.setVisibility(View.VISIBLE);
                    selectChildIds = new ArrayList<String>();
                    for (int i = 0; i < selectVipChilds.size(); i++) {
                        org.json.JSONObject selectChild = selectVipChilds.get(i);
                        selectChildIds.add(selectChild.optString("Id"));
                    }
                }
            }
        });
    }
    private void initSignUpinfo(org.json.JSONObject jsonObject) {
        llBuyInfo.setVisibility(View.VISIBLE);
        if (selectChildIds != null)selectChildIds.clear();
        courseSignupNumber.setText(selecetSize+" 人");
        originalPrice.setText(combobuyEntity.getOriginalPrice()*selecetSize+" 游票（ 或 "
                +Double.valueOf(selecetSize)*Double.valueOf(combobuyEntity.getOriginalPrice())*combobuyEntity.getUTicketPrice()+" 元）");
        comboDiscount.setText((combobuyEntity.getOriginalPrice()-
                combobuyEntity.getDiscountPrice())*selecetSize+" 游票（ 或 "+Double.valueOf(selecetSize)*Double.valueOf((combobuyEntity.getOriginalPrice()-combobuyEntity.getDiscountPrice()))*combobuyEntity.getUTicketPrice()+" 元）");
        String str ="<font color='#EF5F5F'>"+
                combobuyEntity.getDiscountPrice() * selecetSize + "</font> 游票（ 或 <font color='#EF5F5F'>" + Double.valueOf(selecetSize )* Double.valueOf(combobuyEntity.getDiscountPrice()) * combobuyEntity.getUTicketPrice() + "</font> 元）";
        receivable.setText(Html.fromHtml(str));

        selectChildIds.add(jsonObject.optString("Id"));
    }
    private void checkUntiketNumer() {
        MineUtils.getInstance().getParent(new IRequestCallBack() {
            @Override
            public void onStartLoading() {}
            @Override
            public void onSuccess(String result) {
                try {
                    org.json.JSONObject object = new org.json.JSONObject(result);
                    int allUntikets = object.optInt("UTickets");
                    if (Integer.valueOf(needUntiket*selecetSize) > allUntikets){//邮票不足
                        showDialogInsufficient(String.valueOf(needUntiket*selecetSize),allUntikets,String.valueOf(uTicketPrice),false);
                    }else {//直接购买
                        showDialogInsufficient(String.valueOf(needUntiket*selecetSize),allUntikets, String.valueOf(uTicketPrice),true);
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
            buyUntiketnumber =  Integer.valueOf(needUtickets);
           // buyUntiketMoney = Math.abs(utickets - Integer.valueOf(needUtickets)) * Double.valueOf(price);
            buyUntiketMoney = Integer.valueOf(needUtickets) * Double.valueOf(price);

            bug_untiket.setVisibility(View.VISIBLE);
            preferential_tv.setVisibility(View.VISIBLE);
            untiketRemainBuy.setVisibility(View.GONE);
            tvdown = "报名需要使用 " + "<font color ='#1EADEC'>" + needUtickets + " </font> 游票 或 "+"<font color ='#B90000'>" + buyUntiketMoney + " </font> 元";
            tvup = "您当前游票余额 " + "<font color ='#1EADEC'>" + utickets + " </font> 张 ";
            bug_untiket.setTextSize(14);
            tvLine.setVisibility(View.GONE);
            bug_untiket.setVisibility(View.GONE);
            tv_title.setVisibility(View.INVISIBLE);
            llAction.setVisibility(View.GONE);
            originBuy.setVisibility(View.VISIBLE);
            originBuy.setText("原价支付 " + buyUntiketMoney + "元");
            tvNonpayment.setVisibility(View.VISIBLE);

        } else {//去支付
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
            EventBus.getDefault().post(new MineListEvent());
            dialog.dismiss();
            ComboBuyActivity.this.finish();
            if (!TextUtils.isEmpty(pOrderId)){
                Bundle bundle = new Bundle();
                bundle.putString("orderId",pOrderId);
                IntentUtils.startNewIntentBundle(this,bundle, ComoOrderDetailActivity.class);
            }
        });

        btnOk.setOnClickListener(v -> {
            dialog.dismiss();
            //if (progressBar != null) progressBar.show();
            if (flags) {
                //去支付
                confirmOrder();
            } else {
                //去购买
//                Intent intent = new Intent(ComboBuyActivity.this, SelectPayTypeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("flag", 2);
//                bundle.putInt("from", 1);
//                bundle.putDouble("buyNum", buyUntiketMoney);
//                bundle.putInt("number", buyUntiketnumber);
//                bundle.putString("untiketId", untiketId);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
        preferential_tv.setOnClickListener(v -> {
            dialog.dismiss();
            if (!TextUtils.isEmpty(pOrderId)) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 2);
                bundle.putString("orderId", pOrderId);
                IntentUtils.startNewIntentBundle(ComboBuyActivity.this, bundle, UTicketActivity.class);
                finish();
            }
        });
        originBuy.setOnClickListener(V->{
            dialog.dismiss();
            //去购买
            Intent intent = new Intent(ComboBuyActivity.this, SelectPayTypeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("flag", 2);
            bundle.putInt("from", 1);
            bundle.putDouble("buyNum", buyUntiketMoney);
            bundle.putInt("number", buyUntiketnumber);
            bundle.putString("untiketId", untiketId);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        untiketRemainBuy.setOnClickListener(v->{
            dialog.dismiss();
            //去支付
           if (TextUtils.isEmpty(pOrderId))confirmOrder();
        });
        tvNonpayment.setOnClickListener(v->{
            EventBus.getDefault().post(new MineListEvent());
            dialog.dismiss();
            ComboBuyActivity.this.finish();
            if (!TextUtils.isEmpty(pOrderId)){
                Bundle bundle = new Bundle();
                bundle.putString("orderId",pOrderId);
                IntentUtils.startNewIntentBundle(this,bundle, ComoOrderDetailActivity.class);
            }
        });

    }
    public void onEventMainThread(PayOrderEvent event) {
        confirmOrder();//买邮票成功后掉支付接口
    }
    public void onEventMainThread(CourseOrderDetailEvent event) {
        if (!TextUtils.isEmpty(pOrderId)) {
            Bundle bundle = new Bundle();
            bundle.putString("orderId", pOrderId);
            IntentUtils.startNewIntentBundle(this, bundle, ComoOrderDetailActivity.class);
            ComboBuyActivity.this.finish();
        }
    }
    private void confirmOrder() {
        MineUtils.getInstance().signUpPayComboOrder(pOrderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                EventBus.getDefault().post(new MineEvent());
                EventBus.getDefault().post(new MineListEvent());
                Bundle bundle = new Bundle();
                bundle.putString("tcId",pOrderId);
                IntentUtils.startNewIntentBundle(ComboBuyActivity.this, bundle, ComboSignUpSucessActivity.class);
                ComboBuyActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }
}
