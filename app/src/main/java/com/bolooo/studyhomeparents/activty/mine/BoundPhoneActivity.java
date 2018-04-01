package com.bolooo.studyhomeparents.activty.mine;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.entity.LoginResultEntity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.LoginUtils;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

/**
 * 微信登录绑定手机号
 * nanfeifei
 * 2017/3/16 10:22
 *
 * @version 3.7.0
 */
public class BoundPhoneActivity extends BaseActivity {
  @Bind(R.id.input_phone_et) EditText inputPhoneEt;
  @Bind(R.id.get_code_bt) Button getCodeBt;
  @Bind(R.id.input_code_et) EditText inputCodeEt;
  @Bind(R.id.submit_bt) Button submitBt;
  private CountDownTimer countDownTimer;
  LoginResultEntity loginResultEntity;

  @Override protected void initView() {
    initBar();
    insureBar.setTitle(getString(R.string.bound_phone_title));
    countDownTimer = new CountDownTimer(60000, 1000) {
      @Override
      public void onTick(long millisUntilFinished) {
        getCodeBt.setText("重新获取（"+millisUntilFinished / 1000 + "）");
        getCodeBt.setClickable(false);
      }
      @Override
      public void onFinish() {
        getCodeBt.setBackgroundResource(R.drawable.login_shape);
        getCodeBt.setText("获取验证码");
        getCodeBt.setClickable(true);
      }
    };
  }

  @Override protected void initDate() {
    super.initDate();
    Bundle bundle = getIntent().getExtras();
    if(bundle!=null){
      loginResultEntity = bundle.getParcelable("loginResultEntity");
    }
  }

  @Override public int initLoadResId() {
    return R.layout.activity_bound_phone;
  }


  @OnClick({ R.id.get_code_bt, R.id.submit_bt }) public void onClick(View view) {
    switch (view.getId()) {
      case R.id.get_code_bt: {
        String phone = inputPhoneEt.getText().toString();
        if (TextUtils.isEmpty(phone)) {
          ToastUtils.showToast("请输入手机号");
          return;
        }
        if (!CommentUtils.isMobileNO(phone)) {
          ToastUtils.showToast("请输入正确的手机号");
          return;
        }
        LoginUtils.getInstance().getVerifyCode(1, phone, new IRequestCallBack() {
          @Override public void onStartLoading() {
            getCodeBt.setClickable(false);
          }

          @Override public void onSuccess(String result) {
            ToastUtils.showToast("验证码获取成功");
            countDownTimer.start();
            getCodeBt.setClickable(true);
          }

          @Override public void onError(String error) {
            ToastUtils.showToast(error);
            getCodeBt.setClickable(true);
          }
        });
        break;
      }
      case R.id.submit_bt: {
        String phone = inputPhoneEt.getText().toString();
        String code = inputCodeEt.getText().toString();
        if (TextUtils.isEmpty(phone)) {
          ToastUtils.showToast("请输入手机号");
          return;
        }
        if (!CommentUtils.isMobileNO(phone)) {
          ToastUtils.showToast("请输入正确的手机号");
          return;
        }
        if(TextUtils.isEmpty(code)){
          ToastUtils.showToast("请输入验证码");
        }
        if(OnClickUtils.isFastDoubleClick()){
          return;
        }
        LoginUtils.getInstance().loginByBtn(phone, code, loginResultEntity.UnionId, loginResultEntity.UserName
            , loginResultEntity.WeChatHeadPhoto, new IRequestCallBack() {
          @Override
          public void onStartLoading() {
          }
          @Override
          public void onSuccess(String result) {
            LoginResultEntity loginResultEntity = com.alibaba.fastjson.JSONObject
                .parseObject(result, LoginResultEntity.class);
            if(loginResultEntity!=null){

              if (loginResultEntity.IsNew) {
                Intent intent = new Intent(BoundPhoneActivity.this, SetInterestActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("token", loginResultEntity.Token);
                bundle.putInt("sourse", SetInterestActivity.SOURSE_LOGIN);
                intent.putExtras(bundle);
                startActivity(intent);
              } else {
                SharedPreferencesUtil.getInstance().saveToken(loginResultEntity.Token);
                EventBus.getDefault().post(new MineEvent());
                EventBus.getDefault  ().post(new LoginEvent());
                IntentUtils.startIntent(BoundPhoneActivity.this, MainActivity.class);
              }
              finish();
            }
          }
          @Override
          public void onError(String error) {
            ToastUtils.showToast(error);
          }
        });
        break;
      }
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    countDownTimer.cancel();
    countDownTimer = null;
  }
}
