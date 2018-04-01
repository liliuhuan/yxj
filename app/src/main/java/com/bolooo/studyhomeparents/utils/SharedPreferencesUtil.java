package com.bolooo.studyhomeparents.utils;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.bolooo.studyhomeparents.StudyApplication;

import org.json.JSONObject;


/**
 */
public class SharedPreferencesUtil {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";
    private static SharedPreferences mCacheShareParences = null;
    private static Editor mCacheEditor;
    private static SharedPreferencesUtil instance = null;
    private SharedPreferencesUtil() {
        if (null == mCacheShareParences){
            mCacheShareParences = StudyApplication.getContext().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
            mCacheEditor = mCacheShareParences.edit();
        }
    }

    public static SharedPreferencesUtil getInstance() {
        if (null == instance) {
            synchronized (SharedPreferencesUtil.class) {
                if (null == instance)
                    instance = new SharedPreferencesUtil();
            }
        }
        return instance;
    }
//判断是否是第一次登录
    public boolean isFirstTime() {
        return mCacheShareParences.getBoolean("isFirstTime", true);
    }

    public void saveFirstTime(boolean flag) {
        mCacheEditor.putBoolean("isFirstTime", flag).apply();
    }
//存取token值
    public void saveToken(String token) {
        mCacheEditor.putString("token", token).apply();
    }

    public String getToken() {
        return mCacheShareParences.getString("token", "");
    }
    //存取userId
    public void saveUserId(String userI) {
        mCacheEditor.putString("userId", userI).apply();
    }

    public String getUserId() {
        return mCacheShareParences.getString("userId", "");
    }
    //存取AdNum
    public void saveAdNum(int numCode) {
        mCacheEditor.putInt("numCode", numCode).apply();
    }

    public int getAdNum() {
        return mCacheShareParences.getInt("numCode", 0);
    }

    //存取cityList
    public void saveCityData(String cityData) {
        mCacheEditor.putString(Constants.CITYDATA, cityData).apply();
    }
    public String getCityData() {
        return mCacheShareParences.getString(Constants.CITYDATA, "");
    }
  /**
   * 清除数据
   */
  public void clearData() {
    mCacheEditor.clear();
    mCacheEditor.commit();
    saveFirstTime(false);
    }
    //存取手机号
    public void savePhone(String phone) {
        mCacheEditor.putString("phone", phone).apply();
    }

    public String getPhone() {
        return mCacheShareParences.getString("phone", "");
    }
    /**
     * 保存家长基本信息
     * @param result
     */
    public void saveParentsInfo(JSONObject result) {
        mCacheEditor.putString("parentsInfo", result.toString()).apply();
    }
    public JSONObject getParentsInfo() {
        JSONObject jobect = null;
        try {
            String hint = mCacheShareParences.getString("parentsInfo", "");
            if(!StringUtil.isEmpty(hint)){
                jobect = new JSONObject(hint);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return jobect;
    }


}
