package com.bolooo.studyhomeparents.request.util;

import android.util.Log;

import com.bolooo.studyhomeparents.StudyApplication;
import com.bolooo.studyhomeparents.entity.LoginResultEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.LoginService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public class LoginUtils {

    private static LoginUtils instance;
    private LoginUtils(){}
    public static LoginUtils getInstance(){
        if(null==instance){
            instance = new LoginUtils();
        }
        return instance;
    }
    /**
     * 获取验证码
     * @param callBack
     */
    public Call<JSONObject> getVerifyCode(int type,String phone,IRequestCallBack callBack) {
        Map<String,String> params = new HashMap<>();
        params.put("Mobile", phone);
        params.put("DeviceType", StudyApplication.DEVICE_TYPE );
        params.put("DeviceId", StudyApplication.deviceId);
        LoginService service = RetrofitUtil.getInstance().createService(LoginService.class);
        Call<JSONObject> call = service.getVerifyCode(type, params);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 按钮登录
     * @param callBack
     */
    public Call<JSONObject> loginByBtn(String phone,String code,String UnionId, String UserName
        , String WeChatHeadPhoto, IRequestCallBack callBack) {
        Map<String,String> params = new HashMap<>();
        params.put("Mobile", phone);
        params.put("Code", code);
        params.put("UnionId", UnionId);
        params.put("UserName", UserName);
        params.put("WeChatHeadPhoto", WeChatHeadPhoto);
        params.put("DeviceType", StudyApplication.DEVICE_TYPE );
        params.put("DeviceId", StudyApplication.deviceId);
        Log.d("params==",params.toString());
        LoginService service = RetrofitUtil.getInstance().createService(LoginService.class);
        Call<JSONObject> call = service.loginByBtn(2,params);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 微信登录
     * @param listener
     */
    public Call<JSONObject> weichatLogin(String code, final OnWeichatLoginListener listener) {
        LoginService service = RetrofitUtil.getInstance().createService(LoginService.class);
        Call<JSONObject> call = service.weichatLogin(code);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override public void onStartLoading() {

            }

            @Override public void onSuccess(String result) {
                LoginResultEntity loginResultEntity = com.alibaba.fastjson.JSONObject
                    .parseObject(result, LoginResultEntity.class);
                listener.onWeichatLoginSucess(loginResultEntity);
            }

            @Override public void onError(String error) {
                listener.onWeichatLoginFailure(error);
            }
        }));
        return call;
    }
    public interface OnWeichatLoginListener{
        /*获取购课详情成功*/
        void onWeichatLoginSucess(LoginResultEntity loginResultEntity);
        /*获取购课详情失败*/
        void onWeichatLoginFailure(String error);
    }
    /**
     * 绑定微信
     * @param listener
     */
    public Call<JSONObject> boundWeichat(String code, final OnBoundWeichatListener listener) {
        LoginService service = RetrofitUtil.getInstance().createService(LoginService.class);
        Call<JSONObject> call = service.weichatBound(code);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override public void onStartLoading() {

            }

            @Override public void onSuccess(String result) {
                listener.onBoundWeichatSucess(result);
            }

            @Override public void onError(String error) {
                listener.onBoundWeichatFailure(error);
            }
        }));
        return call;
    }
    public interface OnBoundWeichatListener{
        /*获取购课详情成功*/
        void onBoundWeichatSucess(String result);
        /*获取购课详情失败*/
        void onBoundWeichatFailure(String error);
    }
    /**
     * 意向选择
     * @param callBack
     */
    public Call<JSONObject> getMyLearnIntention(String id1,String id2,String id3, IRequestCallBack callBack) {
        Map<String,String> params = new HashMap<>();
        String[] strings = {id1, id2, id3};
        List<String> list = new ArrayList<>();
        list.add(id1);
        list.add(id2);
        list.add(id3);
        LoginService service = RetrofitUtil.getInstance().createService(LoginService.class);
        Call<JSONObject> call = service.getMyLearnIntention(list);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
}
