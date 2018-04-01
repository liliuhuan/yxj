package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public interface SearchService {
    //热门标签查找
    @GET(Constants.HOT_COURSE_TAG_URL)
    Call<JSONObject> getHotCourseTag();

}
