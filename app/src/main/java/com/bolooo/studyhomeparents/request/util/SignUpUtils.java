package com.bolooo.studyhomeparents.request.util;

import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.SignUpService;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public class SignUpUtils {

    private static SignUpUtils instance;
    private SignUpUtils(){}
    public static SignUpUtils getInstance(){
        if(null==instance){
            instance = new SignUpUtils();
        }
        return instance;
    }
    /**
     * 根据CcourseId获取可报名课程
     * @param callBack
     */
    public Call<JSONObject> getCourseSignUp(String courseId,int page,int count,IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.getCourseSignUp(courseId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 根据CcourseId获取可报名课程
     * @param callBack
     */
    public Call<JSONObject> getCourseFrequencyDetail(String frequencyId,String orderId,IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.getCourseFrequencyDetailId(frequencyId,orderId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据CcourseId获取可报名课程
     * @param callBack
     */
    public Call<JSONObject> getCourseFrequencyInfo(String frequencyId,IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.getCourseFrequencyInfo(frequencyId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
   /**
     * 家长端：我的宝贝列表
     * @param callBack
     */
    public Call<JSONObject> getChild(IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.getChild();
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
   /**
     * 家长端：我的宝贝列表
     * @param callBack
     */
    public Call<JSONObject> confirmOrder(String FrequencyId, String ParentId, List<String> list
            , IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.confirmOrder("1",FrequencyId, ParentId, list);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

   /**
     * 根据游票数获取该游票产品价格
     * @param callBack
     */
    public Call<JSONObject> getUntitcketPrice( IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.getUTicketPrice("1");
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }


    /**
     * 验证孩子是否可以报名接口：
     * @param callBack
     */
    public Call<JSONObject> checkChildCanSign( String fid,String childId,IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.checkChildCanSign(fid,childId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 根据课程id判断
     * @param callBack
     */
    public Call<JSONObject> jugeCourse(String frequencyId,IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.judgeCourse(frequencyId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 家长端：我的宝贝列表
     * @param callBack
     */
    public Call<JSONObject> createOrder(String FrequencyId, String addressId,String ParentId, List<String> list
            , IRequestCallBack callBack) {
        SignUpService service = RetrofitUtil.getInstance().createService(SignUpService.class);
        Call<JSONObject> call = service.confirmCreateOrder("2",FrequencyId, addressId,ParentId, list);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
}
