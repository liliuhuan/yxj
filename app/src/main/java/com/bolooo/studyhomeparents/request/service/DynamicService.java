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

public interface DynamicService {
    //留言
    @GET(Constants.TEACHER_MESSAGE_URL)
    Call<JSONObject> getTeacherMessage(@Query("teacherId") String teacherId,
                                     @Query("parentId") String parentId,
                                     @Query("page") int page,
                                     @Query("count") int count);


    //获取动态
    @GET(Constants.GET_UZONE)
    Call<JSONObject> getMyDynamicInfo(@Query("teacherId") String teacherId,@Query("page") int page, @Query("count") int count);

    //添加动态
    @FormUrlEncoded
    @POST(Constants.PUBLISH_DYNAMIC)
    Call<JSONObject> addMyDynamicInfo(@Field("Info") String etContent,
                                      @Field("ImageIds") List<String> imageIds);


    //获取动态详情
    @GET(Constants.GET_UZONE)
    Call<JSONObject> getMyDynamicDetail(@Query("uZoneId") String uZoneId);

    //获取动态详情
    @FormUrlEncoded
    @POST(Constants.ADD_DYNAMICZAN)
    Call<JSONObject> addLikeForDynamic(@Query("uZoneId") String uZoneId,@FieldMap Map<String, String> fields);


    //获取点赞列表
    @GET(Constants.UZONEZAN_LIST)
    Call<JSONObject> getUzoneZanlist(@Query("uZoneId") String uZoneId,@Query("page") int page, @Query("count") int count);

    //获取动态评论列表
    @GET(Constants.DYNAMIC_COMMENT_LIST)
    Call<JSONObject> getDynamicCommentlist(@Query("uZoneId") String uZoneId,@Query("page") int page, @Query("count") int count);

    //发布评论
    @FormUrlEncoded
    @POST(Constants.PUBLISH_COMMENT)
    Call<JSONObject> publishComment(@FieldMap Map<String, String> fields);

}
