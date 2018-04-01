package com.bolooo.studyhomeparents.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.activty.mine.BoundPhoneActivity;
import com.bolooo.studyhomeparents.entity.LoginResultEntity;
import com.bolooo.studyhomeparents.event.LoginEvent;
import com.bolooo.studyhomeparents.event.MineEvent;
import com.bolooo.studyhomeparents.event.SetInterestEvent;
import com.bolooo.studyhomeparents.request.util.LoginUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler,
		LoginUtils.OnWeichatLoginListener, LoginUtils.OnBoundWeichatListener {
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID, false);

        
      try {
        	api.handleIntent(getIntent(), this);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }

	@Override
	public void onReq(BaseReq req) {
		switch (req.getType()) {
		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
			break;
		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
			break;
		default:
			break;
		}
	}

	@Override
	public void onResp(BaseResp resp) {
		int result = 0;
		switch (resp.errCode) {
		case BaseResp.ErrCode.ERR_OK:
			switch (resp.getType()) {
				case ConstantsAPI.COMMAND_SENDAUTH:
					//登录回调,处理登录成功的逻辑
					String code = ((SendAuth.Resp) resp).code;
					if(StudyApplication.getInstance().weichatLoginType == 1){//微信登录
						LoginUtils.getInstance().weichatLogin(code, this);
					}else if(StudyApplication.getInstance().weichatLoginType == 2){
						LoginUtils.getInstance().boundWeichat(code, this);
					}
					break;
				case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
					//分享回调,处理分享成功后的逻辑
					result =  R.string.share_ok;
					finish();
					break;
				default:
					break;
			}
			break;
		case BaseResp.ErrCode.ERR_USER_CANCEL:
			switch (resp.getType()) {
				case ConstantsAPI.COMMAND_SENDAUTH:
					result = 0;
					break;
				case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:
					//分享回调,处理分享成功后的逻辑
					result = R.string.errcode_cancel;
					break;
				default:
					break;
			}

			finish();
			break;
		case BaseResp.ErrCode.ERR_AUTH_DENIED:
			result = R.string.errcode_deny;
			finish();
			break;
		case BaseResp.ErrCode.ERR_UNSUPPORT:
			result = R.string.errcode_unsupported;
			finish();
			break;
		default:
			result = R.string.errcode_unknown;
			finish();
			break;
		}
		if (result != 0){
			Toast.makeText(this, result, Toast.LENGTH_LONG).show();
		}

	}

	@Override public void onWeichatLoginSucess(LoginResultEntity loginResultEntity) {

		if (loginResultEntity.IsNew) {
			//IntentUtils.startIntent(WXEntryActivity.this, SetInterestActivity.class);
			Intent intent = new Intent(WXEntryActivity.this, BoundPhoneActivity.class);
			Bundle bundle = new Bundle();
			bundle.putParcelable("loginResultEntity", loginResultEntity);
			intent.putExtras(bundle);
			startActivity(intent);
			finish();
		} else {
			SharedPreferencesUtil.getInstance().saveToken(loginResultEntity.Token);
			EventBus.getDefault().post(new MineEvent());
			EventBus.getDefault().post(new LoginEvent());
			new Handler().postDelayed(() -> runOnUiThread(() -> {
				IntentUtils.startIntent(WXEntryActivity.this, MainActivity.class);
				finish();
			}), 200);
			//EventBus.getDefault().post(new WeiChatEnent());
			finish();
		}

	}
	@Override public void onWeichatLoginFailure(String error) {
		ToastUtils.showToast(error);
		finish();
	}
	@Override public void onBoundWeichatSucess(String result) {
		ToastUtils.showToast("绑定成功");
		EventBus.getDefault().post(new SetInterestEvent());
		finish();
	}

	@Override public void onBoundWeichatFailure(String error) {
		ToastUtils.showToast(error);
		finish();
	}


}