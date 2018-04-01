package com.bolooo.studyhomeparents.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.alipay.sdk.app.PayTask;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.entity.WeichatPayEntity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import java.util.Map;

public class PayUtils {
	private static final int SDK_PAY_FLAG = 1;

	/**
	 * 调起支付宝客户端进行支付
	 * @param payInfo
	 * void
	 */
	public void sentAliPay(final String payInfo,final Activity activity,final Handler handler){

		Runnable payRunnable = new Runnable() {

			@Override
			public void run() {
				PayTask alipay = new PayTask(activity);
				Map<String, String> result = alipay.payV2(payInfo, true);
				Log.i("msp", result.toString());

				Message msg = new Message();
				msg.what = SDK_PAY_FLAG;
				msg.obj = result;
				handler.sendMessage(msg);
			}
		};

		Thread payThread = new Thread(payRunnable);
		payThread.start();
	}

	/**
	 * 判断客户端是否已安装微信客户端
	 * @param context
	 * @param api
	 * @return
	 */
	private boolean isWXAppInstalledAndSupported(Context context,
			IWXAPI api) {
		// LogOutput.d(TAG, "isWXAppInstalledAndSupported");
		boolean sIsWXAppInstalledAndSupported = api.isWXAppInstalled()
				&& api.isWXAppSupportAPI();
		if (!sIsWXAppInstalledAndSupported) {
			ToastUtils.showToast(context.getString(R.string.not_install_wechat));
		}
		return sIsWXAppInstalledAndSupported;
	}
	/**
	 * 微信支付调起微信客户端
	 * @param weichatPayEntity
	 * @param api
	 */
	public void sendMyPayReq(WeichatPayEntity weichatPayEntity, IWXAPI api) {
		boolean isUninstall = isWXAppInstalledAndSupported(StudyApplication.getInstance(),api);
		////校验是否安装微信客户端
		//if(!isUninstall){
		//	return;
		//}
		PayReq req = new PayReq();

		req.appId = weichatPayEntity.appid;
		req.partnerId = weichatPayEntity.partnerid;
		req.prepayId = weichatPayEntity.prepayid;
		req.packageValue = weichatPayEntity.packageValue;
		req.nonceStr = weichatPayEntity.noncestr;
		req.timeStamp = weichatPayEntity.timestamp;
		req.sign = weichatPayEntity.sign;
		//支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
		api.registerApp(weichatPayEntity.appid);
		api.sendReq(req);
	}


}
