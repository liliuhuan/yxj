package com.bolooo.studyhomeparents.request.util;


import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.DoorInSevice;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;

/**
 * Created by 李刘欢
 * 2017/4/10
 * 描述:${}
 */

public class DoorInUtil {
    private static DoorInUtil instance;
    private DoorInUtil(){}
    public static DoorInUtil getInstance(){
        if(null==instance){
            instance = new DoorInUtil();
        }
        return instance;
    }
    /**
     * 家长端：根据课程Id获取可报名课程信息与课程学习班列表信息
     * @param callBack
     */
    public Call<JSONObject> getDoorInCourseList(String pfCid,IRequestCallBack callBack) {
        DoorInSevice service = RetrofitUtil.getInstance().createService(DoorInSevice.class);
        Call<JSONObject> call = service.getDoorInCourseList(pfCid);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }

    /**
     * 添加上门地址
     * @param callBack
     */
    public Call<JSONObject> addDoorAddress(Map<String,String> map, IRequestCallBack callBack) {
        DoorInSevice service = RetrofitUtil.getInstance().createService(DoorInSevice.class);
        Call<JSONObject> call = service.addDoorAddress(map);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }
    /**
     * 家长端：获取地址列表
     * @param callBack
     */
    public Call<JSONObject> getDoorAddressList(IRequestCallBack callBack) {
        DoorInSevice service = RetrofitUtil.getInstance().createService(DoorInSevice.class);
        Call<JSONObject> call = service.getDoorAddressList();
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }

    /**
     * 添加上门地址
     * @param callBack
     */
    public Call<JSONObject> doorOrFreeBuyCourse(Map<String,String> map, IRequestCallBack callBack) {
        DoorInSevice service = RetrofitUtil.getInstance().createService(DoorInSevice.class);
        Call<JSONObject> call = service.doorOrFreeBuyCourse("1",map);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }

    /**
     * 根据FrequencyId,获得确认报名信息
     * @param callBack
     */
    public Call<JSONObject> getDoorInCourseList(String fId,String addressId,IRequestCallBack callBack) {
        DoorInSevice service = RetrofitUtil.getInstance().createService(DoorInSevice.class);
        Call<JSONObject> call = service.getConfrimSignUpInfo(fId,addressId);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }
}
