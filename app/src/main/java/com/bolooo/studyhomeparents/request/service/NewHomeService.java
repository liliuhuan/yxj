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
 * DATA : 2017-10-17
 * DES : ${}
 * =======================================
 */

public interface NewHomeService {
    //获取分类列表
    @GET(Constants.GET_DIRECTORY_LIST_URL)
    Call<JSONObject> getDirectoryType();

    /**
     * 找课堂 for version 1.7.0 add in 2017-10-13
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.LOOK_COURSE_LIST_URL)
    Call<JSONObject> lookCourseList( @Query("page") int page, @Query("count") int count,@Query("findCourse") int findCourse,@FieldMap Map<String, String> fields);

    /**
     * 找课堂 for version 1.7.0 add in 2017-10-13
     *
     * @return
     */
    @FormUrlEncoded
    @POST(Constants.NEW_HOME_DATA_URL)
    Call<JSONObject> homeCourseList(@Query("home") int home,@FieldMap Map<String, String> fields);
}
