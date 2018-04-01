package com.bolooo.studyhomeparents.activty.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.adapter.PayTypeAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.AlipayPayResultEntity;
import com.bolooo.studyhomeparents.entity.PayResult;
import com.bolooo.studyhomeparents.entity.PayTypeEntity;
import com.bolooo.studyhomeparents.entity.SelectPayTypeEvent;
import com.bolooo.studyhomeparents.entity.WeichatPayEntity;
import com.bolooo.studyhomeparents.entity.dynamic.UntiketListEntity;
import com.bolooo.studyhomeparents.entity.order.NoPayOrderEntity;
import com.bolooo.studyhomeparents.event.CourseOrderDetailEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.PayOrderEvent;
import com.bolooo.studyhomeparents.event.UTicketEvent;
import com.bolooo.studyhomeparents.request.util.MineUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.PayUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import de.greenrobot.event.EventBus;

/**
 * 游票购买选择支付方式
 * nanfeifei
 * 2017/3/20 16:34
 *
 * @version 3.7.0
 */
public class SelectPayTypeActivity extends BaseActivity
    implements AdapterView.OnItemClickListener, MineUtils.OnUTicketAlipayListener,
    MineUtils.OnAuthPayResultListener, MineUtils.OnUTicketWeichatListener {
  @Bind(R.id.pay_type_list_lv) ListView payTypeListLv;
  TextView uticketPayMoneyTv;
  PayTypeAdapter payTypeAdapter;
  UntiketListEntity.UTicketsEntity uticketEntity;
  String uticketId;
  @Bind(R.id.progressBar) WaitProgressBar progressBar;
  private IWXAPI api;
  PayUtils payUtils;
  private static final int SDK_PAY_FLAG = 1;

  private String htmlStr;
  private int number = 1;//购买的数量
  @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
    public void handleMessage(Message msg) {
      switch (msg.what) {
        case SDK_PAY_FLAG: {
          PayResult payResult = new PayResult((Map<String, String>) msg.obj);
          /**
           对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
           */
          String resultInfo = payResult.getResult();// 同步返回需要验证的信息
          AlipayPayResultEntity alipayPayResultEntity =
              JSONObject.parseObject(resultInfo, AlipayPayResultEntity.class);
          if (alipayPayResultEntity != null) {
            AlipayPayResultEntity.AlipayTradeAppPayResponseBean tradeAppPayResponseBean =
                alipayPayResultEntity.alipay_trade_app_pay_response;
            if (tradeAppPayResponseBean != null) {
              progressBar.show();
              String orderSn = tradeAppPayResponseBean.out_trade_no;
              MineUtils.getInstance().authPayResult(orderSn, SelectPayTypeActivity.this);
            }
          }
          String resultStatus = payResult.getResultStatus();
          // 判断resultStatus 为9000则代表支付成功
          //if (TextUtils.equals(resultStatus, "9000")) {
          // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
          //Toast.makeText(SelectPayTypeActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
          //} else {
          // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
          //Toast.makeText(SelectPayTypeActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
          //}
          break;
        }
        default:
          break;
      }
    }
  };
  private String childId;
  private int from;
  private NoPayOrderEntity noPayOrderEntity;


  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.pay_type_title));
    insureBar.getBack().setOnClickListener(v->back());
    progressBar.hide();
  }

  private void back() {
    if (from == 1){
      EventBus.getDefault().post(new CourseOrderDetailEvent());
      finish();
    }else {
      finish();
    }
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    back();
  }

  @Override protected void initDate() {
    super.initDate();
    api = WXAPIFactory.createWXAPI(StudyApplication.getInstance(), Constants.APP_ID);//初始化微信API
    View view = View.inflate(this, R.layout.list_header_uticket_pay_type, null);
    uticketPayMoneyTv = (TextView) view.findViewById(R.id.uticket_pay_money_tv);
    payTypeListLv.addHeaderView(view);

    payUtils = new PayUtils();
    Bundle bundle = getIntent().getExtras();
    if (bundle != null){
      flag = bundle.getInt("flag");//买邮票
      if (flag == 1){
        uticketEntity = bundle.getParcelable("uticketEntity");
        uticketId = uticketEntity.getId();
        number = 1;
        childId = "";
        htmlStr = "<font color = '#FAB005'>" + uticketEntity.getPrice() + "</font>元";
      }else {
        uticketId = bundle.getString("untiketId");
        number = bundle.getInt("number");
        from = bundle.getInt("from");
        childId = bundle.getString("childId");
        htmlStr = "<font color = '#FAB005'>" + bundle.getDouble("buyNum") + "</font>元";
      }
    }

    uticketPayMoneyTv.setText(Html.fromHtml(htmlStr));
    payTypeAdapter = new PayTypeAdapter(this);
    payTypeListLv.setAdapter(payTypeAdapter);
    payTypeListLv.setOnItemClickListener(this);
  }

  @Override public int initLoadResId() {
    return R.layout.activity_select_pay_type;
  }

  @Override protected void prepareData() {
    super.prepareData();
    List<PayTypeEntity> list = PayTypeEntity.getPayTypeList();
    payTypeAdapter.setItems(list);
  }

  @Override public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    PayTypeAdapter.ViewHolder holder = (PayTypeAdapter.ViewHolder) view.getTag();
    String id = holder.id;
    if(OnClickUtils.isFastDoubleClick()){
      return;
    }
    if (StringUtil.isEmpty(childId)) childId = "";
    if ("1".equals(id)) {
      MineUtils.getInstance().getUTicketAlipayInfo(uticketId,String.valueOf(number),childId,this);
    } else if ("2".equals(id)) {
      MineUtils.getInstance().getUTicketWeichatInfo(this,uticketId,String.valueOf(number),childId,this);
    }
    progressBar.show();
  }

  @Override public void onUTicketAlipaySucess(String orderInfo) {
    progressBar.hide();
    if (TextUtils.isEmpty(orderInfo)) {
      return;
    }
    payUtils.sentAliPay(orderInfo, SelectPayTypeActivity.this, mHandler);
  }

  @Override public void onUTicketAlipayFailure(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
  }

  @Override public void onAuthPayResultSucess(String orderInfo) {//支付宝购买成功
    progressBar.hide();
    ToastUtils.showToast(orderInfo);
    EventBus.getDefault().post(new UTicketEvent());
    EventBus.getDefault().post(new MineEvent());
    EventBus.getDefault().post(new SelectPayTypeEvent());
    if (flag == 2){
      EventBus.getDefault().post(new PayOrderEvent());
    }else {
      IntentUtils.startIntent(this,UntiketBuySucessActivity.class);
    }
    finish();
  }

  @Override public void onAuthPayResultFailure(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
    EventBus.getDefault().post(new UTicketEvent());
    EventBus.getDefault().post(new MineEvent());
    finish();
  }

  @Override public void onUTicketWeichatSucess(WeichatPayEntity weichatPayEntity) {
    progressBar.hide();
    payUtils.sendMyPayReq(weichatPayEntity, api);
  }

  @Override public void onUTicketWeichatFailure(String error) {
    progressBar.hide();
    ToastUtils.showToast(error);
  }

  public void onEventMainThread(SelectPayTypeEvent event) {//微信支付成功
    EventBus.getDefault().post(new UTicketEvent());
    EventBus.getDefault().post(new MineEvent());
    if (flag == 2){//1.来自游票列表 2.直接购买
      EventBus.getDefault().post(new PayOrderEvent());
    }else {
      IntentUtils.startIntent(this,UntiketBuySucessActivity.class);
    }
    finish();
  }
}
