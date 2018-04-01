package com.bolooo.studyhomeparents.request.util;


import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.CityAreasSevice;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by 李刘欢
 * 2017/4/10
 * 描述:${}
 */

public class CityAreasUtil {
    private static CityAreasUtil instance;
    private CityAreasUtil(){}
    public static CityAreasUtil getInstance(){
        if(null==instance){
            instance = new CityAreasUtil();
        }
        return instance;
    }
    /**
     * 添加
     * @param callBack
     */
    public Call<JSONObject> getCityAreas(IRequestCallBack callBack) {
        CityAreasSevice service = RetrofitUtil.getInstance().createService(CityAreasSevice.class);
        Call<JSONObject> call = service.getCityAreas("1");
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }
}
