package com.bolooo.studyhomeparents.request.service;

import com.bolooo.studyhomeparents.utils.Constants;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 闪屏
 * nanfeifei
 * 2017/3/30 11:20
 *
 * @version 3.7.0
 */
public interface SplashService {
  //闪屏
  @GET(Constants.SPLASH_URL)
  Call<JSONObject> getSplash(@Query("key") String key);
}
