package com.bolooo.studyhomeparents.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.entity.SelectPayTypeEvent;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import de.greenrobot.event.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


	private IWXAPI api;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		int code = resp.errCode;
		String codeStr = resp.errStr;
		String message = "";
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(code == -1){
				message = getString(R.string.pay_result_fail);
			}else if(code == 0){
				message = getString(R.string.pay_result_success);
				EventBus.getDefault().post(new SelectPayTypeEvent());
			}else if(code == -2){
				message = getString(R.string.pay_result_cancel);
			}else{
				message = getString(R.string.pay_result_fail);
			}
		}
		ToastUtils.showToast(message);
		finish();
	}

}