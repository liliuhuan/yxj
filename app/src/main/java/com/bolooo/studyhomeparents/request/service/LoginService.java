package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public interface LoginService {

    //登录
    @FormUrlEncoded
    @POST(Constants.LOGIN_URL)
    Call<JSONObject> loginByBtn(@Query("isMobile") int isMobile, @FieldMap Map<String, String> fields);
   //微信登录
   @GET(Constants.WEICHAT_LOGIN_URL)
   Call<JSONObject> weichatLogin(@Query("loginCode") String code);
    //微信綁定
    @GET(Constants.WEICHAT_LOGIN_URL)
    Call<JSONObject> weichatBound(@Query("code") String code);
    //获取验证码
    @FormUrlEncoded
    @POST(Constants.GET_VERTY_CODE_URL)
    Call<JSONObject> getVerifyCode(@Query("type") int type, @FieldMap Map<String, String> fields);

   //意向选择
   @FormUrlEncoded
    @POST(Constants.CHOOSE_INTENTTION_URL)
    Call<JSONObject> getMyLearnIntention(@Field("tagIds") List<String> list);



}
