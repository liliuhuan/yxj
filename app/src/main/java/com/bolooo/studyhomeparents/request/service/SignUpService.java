package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 报名
 */

public interface SignUpService {
    //根据课程Id,得到报名课程的列表
    @GET(Constants.SIGN_UP_URL)
    Call<JSONObject> getCourseSignUp(@Query("courseId") String courseId
                                     ,@Query("page") int page,
                                     @Query("count") int count);

    //根据FrequencyId,查询课程安排时间
    @GET(Constants.FRAQUENCY_DETAIL_URL)
    Call<JSONObject> getCourseFrequencyDetailId(@Query("frequencyId") String frequencyId,@Query("orderId") String orderId);

    //根据FrequencyId,获得确认报名信息
    @GET(Constants.SIGN_UP_URL)
    Call<JSONObject> getCourseFrequencyInfo(@Query("frequencyId") String frequencyId);

    //家长端：我的宝贝列表
    @GET(Constants.CHILD_LIST_URL)
    Call<JSONObject> getChild();

    //家长端：我的宝贝列表
    @FormUrlEncoded
    @POST(Constants.CHILD_Order_URL)
    Call<JSONObject> confirmOrder(@Query("param") String param,
                                  @Field("FrequencyId") String FrequencyId,
                                  @Field("ParentId") String ParentId,
                                  @Field("ChildrenIds") List<String> list);

    //家长端：我的宝贝列表
    @POST(Constants.CHILD_Order_URL)
    Call<JSONObject> confirmOrders(@Body String body);

    //根据游票数获取该游票产品价格
    @GET(Constants.UTICKET_URL)
    Call<JSONObject> getUTicketPrice(@Query("count") String count);

    //验证孩子是否可以报名接口：
    @GET(Constants.CHECK_CHILD)
    Call<JSONObject> checkChildCanSign(@Query("frequencId") String FrequencyId,@Query("childId") String childId);

    //根据游票数获取该游票产品价格
    @GET("api/Course/{id}")
    Call<JSONObject> judgeCourse(@Path("id") String id);

    //家长端：报名 2017-07-22 for v1.4.8 先下单，不支付，生成未支付订单
    @FormUrlEncoded
    @POST(Constants.CHILD_Order_URL)
    Call<JSONObject> confirmCreateOrder(@Query("flag") String flag,
                                  @Field("FrequencyId") String FrequencyId,
                                  @Field("DropInAddressId") String addressId,
                                  @Field("ParentId") String ParentId,
                                  @Field("ChildrenIds") List<String> list);
}
