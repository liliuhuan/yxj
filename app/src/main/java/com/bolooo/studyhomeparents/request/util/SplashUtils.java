package com.bolooo.studyhomeparents.request.util;

import com.bolooo.studyhomeparents.entity.SplashEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.SplashService;
import com.bolooo.studyhomeparents.utils.LogUtils;
import org.json.JSONObject;
import retrofit2.Call;

/**
 * 闪屏
 * nanfeifei
 * 2017/3/30 11:25
 *
 * @version 3.7.0
 */
public class SplashUtils {
  private static SplashUtils instance;
  private SplashUtils(){}
  public static SplashUtils getInstance(){
    if(null==instance){
      instance = new SplashUtils();
    }
    return instance;
  }
  /**
   * 获取闪屏
   */
  public Call<JSONObject> getSplash(final OnSplashListener listener) {
    SplashService service = RetrofitUtil.getInstance().createService(SplashService.class);
    Call<JSONObject> call = service.getSplash("AdUrl");
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        LogUtils.d("splash", result);
        SplashEntity splashEntity = com.alibaba.fastjson.JSONObject
            .parseObject(result, SplashEntity.class);
        listener.getSplashSuccess(splashEntity);
      }

      @Override public void onError(String error) {
        LogUtils.d("splash", error);
        listener.getSplashFailure(error);
      }
    }));
    return call;
  }
  public interface OnSplashListener{
    /*获取孩子信息成功*/
    void getSplashSuccess(SplashEntity splashEntity);
    /*获取修改孩子信息失败*/
    void getSplashFailure(String error);
  }
}
