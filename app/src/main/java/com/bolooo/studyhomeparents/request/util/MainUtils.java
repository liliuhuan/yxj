package com.bolooo.studyhomeparents.request.util;

import android.util.Log;

import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.MainService;
import com.bolooo.studyhomeparents.utils.SharedPreferencesUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;

/**
 * Created by Guojunhong on 2017/2/21.
 */

public class MainUtils {

    private static MainUtils instance;
    private MainUtils(){}
    public static MainUtils getInstance(){
        if(null==instance){
            instance = new MainUtils();
        }
        return instance;
    }
    /**
     * 获取s首页广告
     * @param callBack
     */
    public Call<JSONObject> getMainAD(IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getMainAd();
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 首页获取热门讲师：
     * @param callBack
     */
    public Call<JSONObject> getHotTeacherList(IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getHotTeacherList(1);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 首页获取课程类型
     * @param callBack
     */
    public Call<JSONObject> getCourseType(IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getCourseType();
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 首页获取全部课程
     * @param callBack
     */
    public Call<JSONObject> getFristCourse(String myLongitude,String myLatitude,String keyword,int page,int count,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getFristCourse(myLongitude,myLatitude,keyword,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据分类搜索的课程
     * @param callBack
     */
    public Call<JSONObject> getLessonCourse(String myLongitude,String myLatitude,String id,int page,int count,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getLessonCourse(myLongitude,myLatitude,id,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师的基本信息
     * @param callBack
     */
    public Call<JSONObject> getTeacherInfoById(String id,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getTeacherInfoById(id);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师个人页滑动课程
     * @param callBack
     */
    public Call<JSONObject> getTeacherCourse(String id,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getTeacherCourse(id);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师个人页滑动课程
     * @param callBack
     */
    public Call<JSONObject> getDetailCourse(String courseId,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getDetailCourse(courseId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师评价列表
     * @param callBack
     */
    public Call<JSONObject> getAssementList(String teacherId,int page ,int count ,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getAssementList(teacherId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师资料
     * @param callBack
     */
    public Call<JSONObject> getInfoTeacher(String teacherId,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getInfoTeacher(teacherId);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据教师id获取教师  留言
     * @param callBack
     */
    public Call<JSONObject> getTeacherMessage(String teacherId,int page,int count,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getTeacherMessage(teacherId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 根据教师id
     *  添加/取消关注
     * @param callBack
     */
    public Call<JSONObject> AddTeacherFav(String teacherId,IRequestCallBack callBack) {
        Map<String ,String> map = new HashMap<>();
        map.put("TeacherId",teacherId);
        Log.d("AAAAA==",map.toString()+"token=="+ SharedPreferencesUtil.getInstance().getToken());
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.addFavoriteTeacher(map);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
     * 根据课程id
     *  添加/取消关注
     * @param callBack
     */
    public Call<JSONObject> AddTeacherCourse(String courseId,IRequestCallBack callBack) {
        Map<String ,String> map = new HashMap<>();
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.addFavoriteCourse(courseId,map);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }
    /**
    留言

     * @param callBack
     */
    public Call<JSONObject> AddMessage(Map<String ,String> params,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.addMessage(params);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }


    /**
     * 根据教师id
     * 获得讲师相册
     * @param callBack
     */
    public Call<JSONObject> getTeacherPics(String teacherId,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getTeacherPics(teacherId,2);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     *获取城市列表
     * @param
     * @return
     */
    public Call<JSONObject> getCityList(IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getCityList();
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 根据搜索条件获取首页课程列表
     * @param callBack
     */
    public Call<JSONObject> getNewHomeCourses( int count,int page,Map<String ,String> map,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getHomeCousre(count,page,1,map);
        call.enqueue(new WrapperCallBack<JSONObject>(callBack));
        return call;
    }

    /**
     * 首页获取热门讲师：(新)
     * @param callBack
     */
    public Call<JSONObject> getNewHotTeacherList(String cityName , String cityId ,IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getNewHotTeacherList(cityName,cityId);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }

    /**
     * 首页获取热门城市
     * @param callBack
     */
    public Call<JSONObject> getNewHotCity(IRequestCallBack callBack) {
        MainService service = RetrofitUtil.getInstance().createService(MainService.class);
        Call<JSONObject> call = service.getNewHotCity(1);
        call.enqueue(new WrapperCallBack<>(callBack));
        return call;
    }
}
