package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by 李刘欢
 * 2017/4/19
 * 描述:${}
 */

public interface CityAreasSevice {
    //获取认证信息
    @GET(Constants.GET_CITY_LIST)
    Call<JSONObject> getCityAreas(@Query("param") String params);
}
