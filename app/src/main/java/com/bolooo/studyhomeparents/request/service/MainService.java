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
 * Created by Guojunhong on 2017/2/22.
 */

public interface MainService {
    //得到家长的基本信息
    @GET("api/Parent")
    Call<JSONObject> getParentsInfo();

    //得到首页广告
    @GET(Constants.MAIN_AD_URL)
    Call<JSONObject> getMainAd();

    //首页获取热门讲师
    @GET(Constants.HOT_TEAHCER_URL)
    Call<JSONObject> getHotTeacherList(@Query("hot") int hot);

    //获取课程类型
    @GET(Constants.COURSE_TYPE_URL)
    Call<JSONObject> getCourseType();

    //获取第一次课程
    @GET(Constants.FRIST_LESSONS_URL)
    Call<JSONObject> getFristCourse(
            @Query("myLongitude") String myLongitude,
            @Query("myLatitude") String myLatitude,
            @Query("keyword") String keyword,
            @Query("page") int page,
            @Query("count") int count);

    //根据类型获取课程类型
    @GET(Constants.FRIST_LESSONS_URL)
    Call<JSONObject> getLessonCourse(
            @Query("myLongitude") String myLongitude,
            @Query("myLatitude") String myLatitude,
            @Query("type") String id,
            @Query("page") int page,
            @Query("count") int count   );

    //根据Tid获取教师的基本信息
    @GET(Constants.HOT_TEAHCER_URL)
    Call<JSONObject> getTeacherInfoById(@Query("tId_basic") String id);

    //根据Tid获取 教师个人页滑动课程
    @GET(Constants.FRIST_LESSONS_URL)
    Call<JSONObject> getTeacherCourse(@Query("teacherId") String id);

    //根据Tid获取 教师个人页评价列表
    @GET(Constants.COURSE_DYNAMIC_URL)
    Call<JSONObject> getAssementList(@Query("teacherId") String id,@Query("page") int page, @Query("count") int count);

    //根据courseId获取 课程详情
    @GET(Constants.FRIST_LESSONS_URL)
    Call<JSONObject> getDetailCourse(@Query("courseId") String id);

    //根据tId教师个人页资料：
    @GET(Constants.HOT_TEAHCER_URL)
    Call<JSONObject> getInfoTeacher(@Query("tId_detail") String id);
    //根据tId教师  讲师留言
    @GET(Constants.TEACHER_MESSAGE_URL+"?")
    Call<JSONObject> getTeacherMessage(@Query("teacherId") String id, @Query("page") int page, @Query("count") int count);


    //添加取消关注教师
    @FormUrlEncoded
    @POST(Constants.FAV_TEACHER_URL)
    Call<JSONObject> addFavoriteTeacher(@FieldMap Map<String, String> fields);

    //添加取消想去课程
    @FormUrlEncoded
    @POST(Constants.WANT_GO_URL)
    Call<JSONObject> addFavoriteCourse(@Query("courseId") String courseId,@FieldMap Map<String, String> fields);

    //添加添加留言
    @FormUrlEncoded
    @POST(Constants.TEACHER_MESSAGE_URL)
    Call<JSONObject> addMessage(@FieldMap Map<String, String> fields);

    //根据tId教师  讲师相册
    @GET(Constants.TEACHER_PICS)
    Call<JSONObject> getTeacherPics(@Query("teacherId") String id, @Query("imageType") int type);

    //获取城市列表
    @GET(Constants.CITY_LIST)
    Call<JSONObject> getCityList();

    //新的首页接口
    @FormUrlEncoded
    @POST(Constants.NEW_HOME_COURSE)
    Call<JSONObject> getHomeCousre(@Query("count") int Count,@Query("page") int PageIndex,@Query("flag") int flag,@FieldMap Map<String, String> fields);

    //首页获取热门讲师 （新）
    @GET(Constants.NEW_HOT_TEAHCER_URL)
    Call<JSONObject> getNewHotTeacherList(@Query("cityName") String cityName,@Query("cityId") String cityId);

    //首页获取热门城市
    @GET(Constants.NEW_HOT_CITY)
    Call<JSONObject> getNewHotCity(@Query("param") int param);
}
