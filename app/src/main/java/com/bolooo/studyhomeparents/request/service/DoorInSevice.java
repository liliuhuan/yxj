package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-09-07
 * DES : ${}
 * =======================================
 */

public interface DoorInSevice {
    //家长端：根据课程Id获取可报名课程信息与课程学习班列表信息
    @GET(Constants.NEW_COURSE_DOOR_LIST)
    Call<JSONObject> getDoorInCourseList(@Query("pf_cId") String pf_cId);

    //家长端：获取地址列表
    @GET(Constants.GET_DOOR_ADDRESS_LIST)
    Call<JSONObject> getDoorAddressList();
    //添加地址接口
    @FormUrlEncoded
    @POST(Constants.ADD_DOOR_ADDRESS)
    Call<JSONObject> addDoorAddress(@FieldMap Map<String, String> fields);

    //家长端：购课时在家长端添加上门购课或自由购课 2017-09-06 for version 1.6.0
    @FormUrlEncoded
    @POST(Constants.DOOR_OR_FREE_BUY_COURSE)
    Call<JSONObject> doorOrFreeBuyCourse(@Query("addParam") String addParam,@FieldMap Map<String, String> fields);

    //根据FrequencyId,获得确认报名信息
    @GET(Constants.SIGN_UP_URL)
    Call<JSONObject> getConfrimSignUpInfo(@Query("fId") String fId,@Query("addressId") String addressId);
}
