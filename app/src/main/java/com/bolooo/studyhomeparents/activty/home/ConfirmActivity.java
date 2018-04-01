package com.bolooo.studyhomeparents.activty.home;

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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.LessonsOrderDetailActivity;
import com.bolooo.studyhomeparents.activty.mine.SelectPayTypeActivity;
import com.bolooo.studyhomeparents.activty.mine.UTicketActivity;
import com.bolooo.studyhomeparents.adapter.CheckTimeAdapter;
import com.bolooo.studyhomeparents.adapter.NewChildListAdapter;
import com.bolooo.studyhomeparents.adapter.vip.ComfirmVipSelectChildAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.LessonDetailEntity;
import com.bolooo.studyhomeparents.event.CourseOrderDetailEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.MineListEvent;
import com.bolooo.studyhomeparents.event.MyBabyEvent;
import com.bolooo.studyhomeparents.event.PayOrderEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.DoorInUtil;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.request.util.SignUpUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.MyListView;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;


public class ConfirmActivity extends BaseActivity implements IRequestCallBack {

    @Bind(R.id.teacher_image)
    ImageView teacherImage;
    @Bind(R.id.teacher_name1)
    TextView teacherName;
    @Bind(R.id.teacher_course)
    TextView teacherCourse;
    @Bind(R.id.course_time)
    TextView courseTime;
    @Bind(R.id.course_adrass)
    TextView courseAdrass;
    @Bind(R.id.course_drucation)
    TextView courseDrucation;
    @Bind(R.id.course_attention)
    TextView courseAttention;
    @Bind(R.id.child_grid)
    GridView childGrid;
    @Bind(R.id.course_price)
    TextView coursePrice;
    @Bind(R.id.course_allnumber)
    TextView courseAllnumber;
    @Bind(R.id.course_signup_number)
    TextView courseSignupNumber;
    @Bind(R.id.receivable)
    TextView receivable;
    @Bind(R.id.vip_select_listview)
    MyListView vipSelectListview;

    @Bind(R.id.all_count_uticket)
    TextView allCountUticket;
    @Bind(R.id.see_course_time)
    TextView seeCourseTime;
    @Bind(R.id.course_sign_up)
    TextView courseSignUp;
    @Bind(R.id.ll_pay_info)
    LinearLayout llPayInfo;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;


    private String frequencyId;
    private String startTime;
    NewChildListAdapter adapter;
    private List<JSONObject> childList;

    private int all_untikets;
    private String parentsId;
    private JSONObject jsonData;
    private List<JSONObject> allSelect;
    private List<String> idList;
    private JSONArray idsarray;
    private double buyNum;
    private String untiketId;
    private String myPrice;
    private int nedCount;
    private int number;
    private int courseNum;

    private List<JSONObject> overTimeList;
    private ComfirmVipSelectChildAdapter comfirmVipSelectChildAdapter;
    private ArrayList<JSONObject> selectChilds;
    private double lastCourseTime;
    private String uTicketPrice;
    private String courseCount;
    private String nuk;
    private String courseId;
    private String orderId;
    private int mPrice;
    private int selecetSize = 1;
    private JSONArray allChildArray;
    private String addressId;
    private boolean isStudyClass;
    private boolean isCharge = true ;
    @Override
    public int initLoadResId() {
        return R.layout.activity_confirm;
    }
    public void onEventMainThread(MyBabyEvent event) {
        selecetSize +=1;
        prepareData();
        getChildData();//获取孩子信息
    }
    @Override
    protected void initView() {
        overTimeList = new ArrayList<>();
        progressBar.show();
        initBar();
        insureBar.setTitle("确认报名");
        insureBar.getBack().setOnClickListener(view -> finish());
        insureBar.getRightImage().setVisibility(View.GONE);

        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            frequencyId = bundle.getString("frequencyId");//场次Id
            startTime = bundle.getString("frequencyName");
            nedCount = bundle.getInt("needCount");
            courseNum = bundle.getInt("courseNum");
            addressId = bundle.getString("addressId");
            isStudyClass = bundle.getBoolean("isStudyClass");
            if (TextUtils.isEmpty(addressId)){
                addressId = "";
            }
            //Log.d("")
        }
        if (isStudyClass){
            vipSelectListview.setVisibility(View.VISIBLE);
        }else {
            vipSelectListview.setVisibility(View.GONE);
        }
        childList = new ArrayList<>();
        adapter = new NewChildListAdapter(this,frequencyId,isStudyClass);
        childGrid.setAdapter(adapter);

        selectChilds = new ArrayList<>();
        vipSelectListview.setDividerHeight(0);
        comfirmVipSelectChildAdapter = new ComfirmVipSelectChildAdapter(ConfirmActivity.this);
        vipSelectListview.setAdapter(comfirmVipSelectChildAdapter);
        courseSignUp.setOnClickListener(v -> {
            getParentsInfo();//checkVerify();
        });
        if (isStudyClass){
            seeCourseTime.setVisibility(View.VISIBLE);
        }else {
            seeCourseTime.setVisibility(View.GONE);
        }
        seeCourseTime.setOnClickListener(V->{
            if (!TextUtils.isEmpty(frequencyId))showCheckTime();
        });
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
        CheckTimeAdapter checkTimeAdapter = new CheckTimeAdapter(this, 1);
        lvList.setAdapter(checkTimeAdapter);
        SignUpUtils.getInstance().getCourseFrequencyDetail(frequencyId,"", new IRequestCallBack() {
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
        SignUpUtils.getInstance().getUntitcketPrice(this);
        getConfirmInfo();
    }

    private void checkVerify() {
        nuk = "";
        String needUtickets = receivable.getText().toString();
        if (needUtickets.contains("游")){
            nuk = needUtickets.substring(0, needUtickets.indexOf("游"));
        }
        allSelect = adapter.getAllSelect();
        idsarray = new JSONArray();
        idList = new ArrayList<>();
        for (int i = 0; i < allSelect.size(); i++) {
            idsarray.put(allSelect.get(i).optString("Id"));
            idList.add(allSelect.get(i).optString("Id"));
        }
        parentsId = allSelect.get(0).optString("ParentId");
        if (StringUtil.isEmpty(nuk)) {
            ToastUtils.showToast("请先选中报名孩子!");
            return;
        }
        if (isStudyClass){
//            if (allSelect.size() > nedCount) {
//                ToastUtils.showToast("名额不足。这个班级现在还有 " + nedCount + "个名额!");
//                return;
//            }
            SignUpUtils.getInstance().jugeCourse(courseId, new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                    if (progressBar != null) progressBar.show();
                }

                @Override
                public void onSuccess(String result) {
                    LessonDetailEntity lessonDetailEntity = com.alibaba.fastjson.JSONObject.parseObject(result, LessonDetailEntity.class);
                    int courseStatus = lessonDetailEntity.getCourseStatus();
                    if (courseStatus == 2){//报名中
                        createOrder();//创建订单
                    }else {
                        ToastUtils.showToast("您报名的课程，刚刚下架了！");
                    }
                }
                @Override
                public void onError(String error) {
                    if (progressBar != null) progressBar.hide();
                    ToastUtils.showToast(error);
                }
            });
        }else {
            createOrder();//创建订单，进行购买
        }
    }
    private void createOrder() {
        Map<String, String> params = new HashMap<>();
        params.put("FrequencyId", frequencyId);
        params.put("ParentId", parentsId);
        params.put("ChildrenIds", idsarray.toString());
        params.put("DropInAddressId", addressId);
        Log.d("params===",params.toString());
        SignUpUtils.getInstance().createOrder(frequencyId,addressId, parentsId, idList, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
            }
            @Override
            public void onSuccess(String result) {
                orderId = result;
                EventBus.getDefault().post(new MineListEvent());
                if (progressBar != null) progressBar.hide();
                if (Integer.parseInt(nuk) > all_untikets) {
                    showDialogInsufficient(nuk, all_untikets, myPrice, false);
                    return;
                } else {
                    showDialogInsufficient(nuk, all_untikets, myPrice, true);
                }
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
                ToastUtils.showToast(error);
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        if (adapter == null) return;
        childList = adapter.getChildList();
    }
    private void confirmOrder() {
        MineUtils.getInstance().payMyOrder(orderId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                if (progressBar != null) progressBar.hide();
                EventBus.getDefault().post(new MineListEvent());
                EventBus.getDefault().post(new MineEvent());
                ToastUtils.showToast("支付成功");
                Bundle bundle = new Bundle();
                bundle.putString("frequencyId", frequencyId);
                bundle.putString("orderId", orderId);
                bundle.putInt("flag", 1);//课程名称
                IntentUtils.startIntentBundle(ConfirmActivity.this, bundle, SignUpSucessActivity.class);
                ConfirmActivity.this.finish();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
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
//            buyUntiketnumber = Math.abs(utickets - Integer.valueOf(needUtickets));
//            buyUntiketMoney = Math.abs(utickets - Integer.valueOf(needUtickets)) * Double.valueOf(price);

            number = Integer.valueOf(needUtickets);
            buyNum = Integer.valueOf(needUtickets) * Double.valueOf(price);

            preferential_tv.setVisibility(View.VISIBLE);
            tvdown = "报名需要使用 " + "<font color ='#1EADEC'>" + needUtickets + " </font> 游票 或 "+"<font color ='#B90000'>" + buyNum + " </font> 元";
            tvup = "您当前游票余额 " + "<font color ='#1EADEC'>" + utickets + " </font> 张 ";

            bug_untiket.setTextSize(14);
            tvLine.setVisibility(View.GONE);
            bug_untiket.setVisibility(View.GONE);
            tv_title.setVisibility(View.INVISIBLE);
            llAction.setVisibility(View.GONE);
            originBuy.setVisibility(View.VISIBLE);
            untiketRemainBuy.setVisibility(View.GONE);

            originBuy.setText("原价支付 " + buyNum + "元");
            tvNonpayment.setVisibility(View.VISIBLE);

            buyNeedsMoney = "是否支付 " + "<font color ='#B90000'> " + buyNum + " </font>元购买" + "<font color ='#1EADEC'> " + Math.abs(number) + " </font>游票";
            btnOk.setText("购买并报名");
            bug_untiket.setTextSize(14);
            bug_untiket.setText(Html.fromHtml(buyNeedsMoney));
            tv_title.setText("游票不足");
        } else {//去支付
            number = Integer.valueOf(needUtickets);
            buyNum = Integer.valueOf(needUtickets) * Double.valueOf(price);

            preferential_tv.setVisibility(View.GONE);
            tvLine.setVisibility(View.GONE);
            bug_untiket.setVisibility(View.GONE);
            tv_title.setVisibility(View.INVISIBLE);
            llAction.setVisibility(View.GONE);

            untiketRemainBuy.setVisibility(View.VISIBLE);
            if (buyNum == 0){
                originBuy.setText("原价支付 " + buyNum + "元");
                originBuy.setVisibility(View.GONE);
            }else {
                originBuy.setText("原价支付 " + buyNum + "元");
                originBuy.setVisibility(View.VISIBLE);
            }

            tvNonpayment.setVisibility(View.VISIBLE);

            tvdown = "报名需要使用 " + "<font color ='#1EADEC'>" + needUtickets + " </font> 游票 或 "+"<font color ='#B90000'>" + buyNum + " </font> 元";
            tvup = "您当前游票余额 " + "<font color ='#1EADEC'>" + utickets + " </font> 张 ";

            btnOk.setText("确认报名");
            tv_title.setText("确认报名");
        }
        cofirm_unticket.setText(Html.fromHtml(tvup));
        my_untiket.setTextSize(14);
        my_untiket.setText(Html.fromHtml(tvdown));
        cancel.setOnClickListener(v -> {//已改版 这个用不到了
            dialog.dismiss();
            ConfirmActivity.this.finish();
            if (!TextUtils.isEmpty(orderId)){
                Bundle bundle = new Bundle();
                bundle.putString("orderId",orderId);
                bundle.putString("frequencyId",frequencyId);
                IntentUtils.startNewIntentBundle(this,bundle, LessonsOrderDetailActivity.class);
            }
            });

        btnOk.setOnClickListener(v -> {//已改版 这个用不到了
            dialog.dismiss();
            if (progressBar != null) progressBar.show();
            if (flags) {
                //去支付
                if (!TextUtils.isEmpty(orderId))  confirmOrder();
            } else {     //去购买
//                Intent intent = new Intent(ConfirmActivity.this, SelectPayTypeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putInt("flag", 2);
//                bundle.putInt("from", 1);
//                bundle.putDouble("buyNum", buyNum);
//                bundle.putInt("number", number);
//                bundle.putString("untiketId", untiketId);
//                intent.putExtras(bundle);
//                startActivity(intent);
//                if (progressBar != null) progressBar.hide();
            }
        });
        preferential_tv.setOnClickListener(v -> {//游票商城
            dialog.dismiss();
            if (!TextUtils.isEmpty(orderId)) {
                Bundle bundle = new Bundle();
                bundle.putInt("flag", 1);
                bundle.putString("orderId", orderId);
                bundle.putString("frequencyId",frequencyId);
                IntentUtils.startNewIntentBundle(ConfirmActivity.this, bundle, UTicketActivity.class);
                finish();
            }
        });
        originBuy.setOnClickListener(V->{//邮票不足，原价支付（用钱付）
            dialog.dismiss();
            //去购买
            Intent intent = new Intent(ConfirmActivity.this, SelectPayTypeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("flag", 2);
            bundle.putInt("from", 1);
            bundle.putDouble("buyNum", buyNum);
            bundle.putInt("number", number);
            bundle.putString("untiketId", untiketId);
            intent.putExtras(bundle);
            startActivity(intent);
            if (progressBar != null) progressBar.hide();
        });
        untiketRemainBuy.setOnClickListener(V->{//游票充足用游票支付
            dialog.dismiss();
            //去支付
            if (!TextUtils.isEmpty(orderId))  confirmOrder();
        });

        tvNonpayment.setOnClickListener(v->{//暂不支付
            dialog.dismiss();
            ConfirmActivity.this.finish();
            if (!TextUtils.isEmpty(orderId)){
                Bundle bundle = new Bundle();
                bundle.putString("orderId",orderId);
                bundle.putString("frequencyId",frequencyId);
                IntentUtils.startNewIntentBundle(this,bundle, LessonsOrderDetailActivity.class);
            }
        });


    }
    public void onEventMainThread(PayOrderEvent event) {
       confirmOrder();
    }
    public void onEventMainThread(CourseOrderDetailEvent event) {
        if (!TextUtils.isEmpty(orderId)) {
            Bundle bundle = new Bundle();
            bundle.putString("orderId", orderId);
            bundle.putString("frequencyId",frequencyId);
            IntentUtils.startNewIntentBundle(this, bundle, LessonsOrderDetailActivity.class);
            finish();
        }
    }
    private void getParentsInfo() {
        MineUtils.getInstance().getParent(new IRequestCallBack() {
            @Override
            public void onStartLoading() {}
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    all_untikets = object.optInt("UTickets");
                    checkVerify();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String error) {
            }
        });
    }
    private void getChildData() {
        SignUpUtils.getInstance().getChild(new IRequestCallBack() {
            @Override
            public void onStartLoading() {}
            @Override
            public void onSuccess(String result) {
                Log.d("child==", result);
                try {
                    if (progressBar != null) progressBar.hide();
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        llPayInfo.setVisibility(View.VISIBLE);
                        allChildArray = array;
                        checkVipchild();
                    } else {
                        selecetSize = 0;
                        JSONObject lastObeject = new JSONObject();
                        lastObeject.put("NickName", "lastOne");
                        lastObeject.put("Id", "lastOne");
                        lastObeject.put("Name", "lastOne");
                        lastObeject.put("HeadPhoto", "lastOne");
                        lastObeject.put("isSelect", false);
                        childList.add(lastObeject);
                        adapter.setItems(childList);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
            }
        });
    }
    private void checkVipchild() {
        JSONObject jsonObject = allChildArray.optJSONObject(0);
        boolean isVIP = jsonObject.optBoolean("IsVIP");
        if (isVIP&&isStudyClass){
            String vipExpiration = jsonObject.optString("VIPExpiration");
            double showTime = repaceAllBeString(vipExpiration) - lastCourseTime;
            if (showTime > 0) {//vip时间大于课程时间 不收费
                isCharge = false ;
            }else isCharge = true ;
            String childId = jsonObject.optString("Id");
            SignUpUtils.getInstance().checkChildCanSign(frequencyId, childId, new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                    if (progressBar != null) progressBar.show();
                }
                @Override
                public void onSuccess(String result) {
                    selecetSize = 1;
                    if (progressBar != null) progressBar.hide();
                    setAllData(true);
                }
                @Override
                public void onError(String error) {
                    if (progressBar != null) progressBar.hide();
                    selecetSize = 0 ;
                    setAllData(false);
                    ToastUtils.showToast(error);
                }
            });
        }else {
           setAllData(true);
        }
    }
    private void setAllData(boolean isCanSelectFrist) {
        try {
            List<JSONObject> list = new ArrayList<JSONObject>();
            JSONObject fristChild = null;
            for (int i = 0; i < allChildArray.length(); i++) {
                JSONObject obejct = allChildArray.getJSONObject(i);
                fristChild = allChildArray.getJSONObject(0);
                if (i == 0) {
                    if (isCanSelectFrist)
                    obejct.put("isSelect", true);
                    else obejct.put("isSelect", false);
                } else {
                    obejct.put("isSelect", false);
                }
                list.add(obejct);
            }
            JSONObject lastObeject = new JSONObject();
            lastObeject.put("NickName", "lastOne");
            lastObeject.put("Id", "lastOne");
            lastObeject.put("Name", "lastOne");
            lastObeject.put("HeadPhoto", "lastOne");
            lastObeject.put("isSelect", false);
            list.add(lastObeject);
            if (list != null && childList != null && list.size() > childList.size() && childList.size() > 0) {
                childList.add(0, list.get(0));
            } else {
                if (childList == null || childList.size() == 0) childList.addAll(list);
            }
            adapter.setItems(childList);
            if (fristChild != null) {
                initSignUpinfo(fristChild,isCanSelectFrist);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initSignUpinfo(JSONObject jsonObject, boolean isCanSelectFrist) {
           if (selectChilds != null)selectChilds.clear();
            if (jsonObject.optBoolean("IsVIP")&&isStudyClass) {
                selectChilds.add(jsonObject);
                if (isCanSelectFrist)
                comfirmVipSelectChildAdapter.setItems(selectChilds);
                else
                    comfirmVipSelectChildAdapter.setItems(null);
            }
            courseSignupNumber.setText(selecetSize + "人");

            if (courseNum == 0) {
                courseAllnumber.setText(courseCount + "课时");
                allCountUticket.setText(selecetSize * mPrice * Integer.valueOf(courseCount)
                        + "游票(价值" + selecetSize * mPrice * Integer.valueOf(courseCount)
                        * Double.valueOf(uTicketPrice)+ "元)");
                if (!isCharge){
                    selecetSize-=1;
                }
                String str = "<font color='#EE5F5F'> "+ selecetSize*mPrice * Integer.valueOf(courseCount)+"</font>"
                        + "游票(价值"+"<font color='#EE5F5F'> " +selecetSize*mPrice * Integer.valueOf(courseCount) * Double.valueOf(uTicketPrice) + "</font>元)";
                receivable.setText(Html.fromHtml(str));

            } else {
                courseAllnumber.setText(courseNum + "课时");
                allCountUticket.setText(selecetSize* mPrice * courseNum + "游票(价值" +
                        selecetSize* mPrice * courseNum * Double.valueOf(uTicketPrice) + "元)");
                if (!isCharge){
                    selecetSize-=1;
                }
                String str1 = "<font color='#EE5F5F'> "+ selecetSize*mPrice * courseNum+"</font>" +
                        "游票(价值"+"<font color='#EE5F5F'> " + selecetSize*mPrice * courseNum * Double.valueOf(uTicketPrice)+ "</font>元)";
                receivable.setText(Html.fromHtml(str1));

            }
          // comfirmVipSelectChildAdapter.setItems(selectChilds);
    }
    private void getConfirmInfo() {
        DoorInUtil.getInstance().getDoorInCourseList(frequencyId, addressId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                fillData(result);
                getChildData();
            }

            @Override
            public void onError(String error) {
                ToastUtils.showToast(error);
            }
        });
    }
    private void fillData(String result) {
        try {
            jsonData = new JSONObject(result);
            String headPhoto = jsonData.optString("HeadPhoto");
            String teacherNames = jsonData.optString("TeacherName");
            String courseName = jsonData.optString("CourseName");
            String frequencyName = jsonData.optString("FrequencyName");
            String duration = jsonData.optString("Duration");


            courseId = jsonData.optString("CourseId");
            courseCount = jsonData.optString("CourseCount");
            final int price = jsonData.optInt("Price");

            mPrice = price;
            String provinceName = jsonData.optString("ProvinceName");
            String cityName = jsonData.optString("CityName");
            String areaName = jsonData.optString("AreaName");
            String street = jsonData.optString("Street");
            String district = jsonData.optString("District");
            String houseNum = jsonData.optString("HouseNum");
            String addressDetail = jsonData.optString("AddressDetail");
            int frequencyType = jsonData.optInt("FrequencyType");

            String attention = jsonData.optString("Attention");
            uTicketPrice = jsonData.optString("UTicketPrice");
            String optString = jsonData.optString("LastCourseTime");
            if (TextUtils.isEmpty(optString) || "null".equals(optString)){

            }else {
                lastCourseTime = repaceAllBeString(optString);
            }

            untiketId = jsonData.optString("UTicketId");
            imageLoaderUtils.loadFileImageRound(headPhoto, teacherImage);
            teacherName.setText(teacherNames);
            teacherCourse.setText("《" + courseName + "》");
          //  if (!TextUtils.isEmpty(startTime)) {
            courseTime.setText(frequencyName);
           // }
            if (frequencyType == 2){//上门课程
                courseAdrass.setText(provinceName+" "+cityName+" " + areaName +" " + addressDetail);
            }else {
                courseAdrass.setText(areaName + street + district + houseNum);
            }

            if (StringUtil.isEmpty(attention)) {
                courseAttention.setText("无");
            } else {
                courseAttention.setText(attention);
            }
            courseDrucation.setText(" 共" + courseCount + "课");
            coursePrice.setText(price + "游票 (价值" + price * Double.valueOf(uTicketPrice)  + "元) /课时");
            adapter.setOnclickLister(new NewChildListAdapter.OnNumClickLister() {
                @Override
                public void changeNum(int size) {
                    selecetSize = size ;
                    courseSignupNumber.setText(size + "人");
                    if (courseNum == 0) {
                        allCountUticket.setText(size * price * Integer.valueOf(courseCount) + "游票(价值" + size * price * Integer.valueOf(courseCount) * Double.valueOf(uTicketPrice)+ "元)");
                    } else {
                        allCountUticket.setText(size * price * courseNum + "游票(价值" + size * price * courseNum * Double.valueOf(uTicketPrice) + "元)");
                    }
                }
                @Override
                public void getAllSelect(List<JSONObject> objectList) {
                    try {
                        int size = 0;
                        if (objectList == null || objectList.size() == 0) {
                            llPayInfo.setVisibility(View.GONE);
                        } else {
                            llPayInfo.setVisibility(View.VISIBLE);
                            selectChilds.clear();
                            overTimeList.clear();
                            for (int i = 0; i < objectList.size(); i++) {
                                JSONObject jsonObject = objectList.get(i);
                                if (jsonObject.getBoolean("IsVIP")) {
                                    String vipExpiration = objectList.get(i).optString("VIPExpiration");
                                    double showTime = repaceAllBeString(vipExpiration) - ConfirmActivity.this.lastCourseTime;
                                    if (showTime > 0) {
                                        jsonObject.put("overtime", false);
                                        overTimeList.add(jsonObject);//vip时间大于课程时间 不收费
                                    } else {
                                        jsonObject.put("overtime", true);
                                    }
                                    selectChilds.add(jsonObject);
                                }
                            }
                            if (overTimeList !=null){
                                //所有孩子-不收费孩子== 收费孩子
                                if (isStudyClass) size = objectList.size() - overTimeList.size();
                                else size = objectList.size();
                            }
                            if (courseNum == 0) {
                                String str = "<font color='#EE5F5F'> "+size * price * Integer.valueOf(courseCount)+"</font>" + "游票(价值"+"<font color='#EE5F5F'> " + size * price * Integer.valueOf(courseCount) * Double.valueOf(uTicketPrice) + "</font>元)";
                                receivable.setText(Html.fromHtml(str));
                                courseAllnumber.setText(courseCount + "课时");
                            } else {
                                String str1 = "<font color='#EE5F5F'> "+size * price * courseNum+"</font>" + "游票(价值"+"<font color='#EE5F5F'> " + size * price * courseNum * Double.valueOf(uTicketPrice)+ "</font>元)";
                                receivable.setText(Html.fromHtml(str1));
                                courseAllnumber.setText(courseNum + "课时");
                            }
                            comfirmVipSelectChildAdapter.setItems(selectChilds);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private double repaceAllBeString(String vipExpiration) {
        if (TextUtils.isEmpty(vipExpiration) || "null".equals(vipExpiration))return 0;
        String endMD = TimeUtils.getEndMD(vipExpiration);
        String replaceAll = endMD.replaceAll("-", "").replaceAll(":", "").replaceAll(" ", "");
        Double doule = Double.valueOf(replaceAll);
        return  doule.doubleValue();
    }
    @Override
    public void onStartLoading() {}
    @Override
    public void onSuccess(String result) {
        myPrice = result;
       // getChildData();//获取孩子信息
    }
    @Override
    public void onError(String error) {}

}
