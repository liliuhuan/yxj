package com.bolooo.studyhomeparents.request.util;

import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.service.SearchService;

import org.json.JSONObject;

import retrofit2.Call;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public class HotSearchUtils {

    private static HotSearchUtils instance;
    private HotSearchUtils(){}
    public static HotSearchUtils getInstance(){
        if(null==instance){
            instance = new HotSearchUtils();
        }
        return instance;
    }
    /**
     * 获取热门课程标签
     * @param callBack
     */
    public Call<JSONObject> getHotCourseTag(IRequestCallBack callBack) {
        SearchService service = RetrofitUtil.getInstance().createService(SearchService.class);
        Call<JSONObject> call = service.getHotCourseTag();
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
}
