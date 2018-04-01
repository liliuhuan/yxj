package com.bolooo.studyhomeparents.activty;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.activty.home.HtmlActivity;
import com.bolooo.studyhomeparents.activty.mine.SetInterestActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.RefreshMessageEvnet;
import com.bolooo.studyhomeparents.event.WeiChatEnent;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.LoginUtils;
import com.bolooo.studyhomeparents.utils.AndroidBug5497Workaround;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.NetworkUtils;
import com.bolooo.studyhomeparents.utils.OnClickUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;
import com.bolooo.studyhomeparents.views.MyTextWatcher;
import com.bolooo.studyhomeparents.views.WaitProgressBar;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.xw.repo.XEditText;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class LoginActivity extends BaseActivity {

    @Bind(R.id.phone_number)
    XEditText phoneNumber;
    @Bind(R.id.verification_code)
    EditText verificationCode;
    @Bind(R.id.get_code_btn)
    TextView getcodeBtn;
    @Bind(R.id.login_btn)
    TextView loginBtn;
    @Bind(R.id.user_agreement)
    TextView userAgreement;
    @Bind(R.id.login_weixin)
    RelativeLayout loginWeixin;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    @Bind(R.id.activity_login)
    FrameLayout activityLogin;
    private SharedPreferencesUtil spf;
    private CountDownTimer countDownTimer;
    private IWXAPI api;
    private boolean req1;

    @Override
    public int initLoadResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        AndroidBug5497Workaround.assistActivity(this);
        progressBar.hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        spf = SharedPreferencesUtil.getInstance();
        String token = spf.getToken();
        if (!TextUtils.isEmpty(token)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            init();
        }
    }

    private void init() {
        String phone = spf.getPhone();
        if (!TextUtils.isEmpty(phone)) {
            phoneNumber.setText(phone);
            verificationCode.requestFocus();
        }
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                getcodeBtn.setText(getString(R.string.try_again) + "（" + millisUntilFinished / 1000 + "）");
                getcodeBtn.setClickable(false);
            }

            @Override
            public void onFinish() {
                getcodeBtn.setBackgroundResource(R.drawable.login_shape);
                getcodeBtn.setText(getString(R.string.get_code));
                getcodeBtn.setClickable(true);
            }
        };
        phoneNumber.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextEidte(String toString) {
                if (toString.length() > 11) {
                    ToastUtils.showToast("手机号最大不超过11位");
                    String s = toString.substring(0, 11);
                    phoneNumber.setText(s);
                    phoneNumber.setSelection(11);
                }else if (toString.length() == 11){
                    if (!StringUtil.isMobileNO(toString)) {
                        ToastUtils.showToast("手机号错误，请查正修改");
                        phoneNumber.setSelection(11);
                    }
                }
            }
        });
    }

    @Override
    protected void initDate() {
        super.initDate();
        api = WXAPIFactory.createWXAPI(LoginActivity.this,
                Constants.APP_ID);
    }

    @OnClick({R.id.get_code_btn, R.id.login_btn, R.id.login_weixin, R.id.user_agreement})
    public void click(View v) {
        switch (v.getId()) {
            case R.id.get_code_btn://验证码获取
                if (OnClickUtils.isFastDoubleClick()) {
                    return;
                }
                getCode();
                break;
            case R.id.login_btn://登录按钮
                if (OnClickUtils.isFastDoubleClick()) {
                    return;
                }

                loginFromBtn();
                break;
            case R.id.login_weixin://微信登录

                if (OnClickUtils.isFastDoubleClick()) {
                    return;
                }
                loginFromWeixin();
                break;
            case R.id.user_agreement://用户协议
                if (OnClickUtils.isFastDoubleClick()) {
                    return;
                }
                HtmlActivity.openHtmlActivity(LoginActivity.this, UIUtil.getString(R.string.contract), "游学家协议");
                break;
        }
    }

    private void getCode() {

        String phone = phoneNumber.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请输手机号");
            return;
        }
        if (!TimeUtils.isMobile(phone)) {
            ToastUtils.showToast("手机号不正确");
            return;
        }
        verificationCode.requestFocus();
        if (NetworkUtils.isNetworkConnected(this)) {
            spf.savePhone(phone);
            LoginUtils.getInstance().getVerifyCode(0, phone, new IRequestCallBack() {
                @Override
                public void onStartLoading() {

                }

                @Override
                public void onSuccess(String result) {
                    ToastUtils.showToast("验证码获取成功");
                   // ToastUtils.showToast(result);
                    countDownTimer.start();
                }

                @Override
                public void onError(String error) {
                    ToastUtils.showToast(error);
                }
            });
        }
    }

    private void loginFromBtn() {
        String phone = phoneNumber.getText().toString().trim();
        String code = verificationCode.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.showToast("请填写手机号");
            return;
        }
        if (!TimeUtils.isMobile(phone)) {
            ToastUtils.showToast("手机号不正确");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showToast("请输入验证码");
            return;
        }
        if (NetworkUtils.isNetworkConnected(this)) {
            LoginUtils.getInstance().loginByBtn(phone, code, "", "", "", new IRequestCallBack() {
                @Override
                public void onStartLoading() {
                    progressBar.show();
                }

                @Override
                public void onSuccess(String result) {

                    try {
                        countDownTimer.cancel();
                        progressBar.hide();
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.length() > 0) {
                            if (jsonObject.optBoolean("IsNew")) {
                                Intent intent = new Intent(LoginActivity.this, SetInterestActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("token", jsonObject.optString("Token"));
                                bundle.putInt("sourse", SetInterestActivity.SOURSE_LOGIN);
                                intent.putExtras(bundle);
                                startActivity(intent);
                            } else {
                                SharedPreferencesUtil.getInstance().saveToken(jsonObject.optString("Token"));
                                EventBus.getDefault().post(new MineEvent());
                                EventBus.getDefault().post(new LoginEvent());
                                EventBus.getDefault().post(new RefreshMessageEvnet());
                                IntentUtils.startIntent(LoginActivity.this, MainActivity.class);

                            }
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {

                    progressBar.hide();
                    ToastUtils.showToast(error);

                }
            });
        }
    }

    private void loginFromWeixin() {
        if(api.isWXAppInstalled() && api.isWXAppSupportAPI()){
            progressBar.show();
            StudyApplication.getInstance().weichatLoginType = 1;
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "studyhomeparents";
            api.sendReq(req);
        }else{
            ToastUtils.showToast("您还没有安装微信或微信版本过低");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (progressBar != null) progressBar.hide();
    }

    public void onEventMainThread(WeiChatEnent event) {
        new Handler().postDelayed(() -> runOnUiThread(() -> {
            IntentUtils.startIntent(LoginActivity.this, MainActivity.class);
            finish();
        }), 200);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }

    }
}
