package com.bolooo.studyhomeparents.request.util;

import com.bolooo.studyhomeparents.entity.home.HomeDataEntity;
import com.bolooo.studyhomeparents.entity.newhome.DirectoryTypeEntity;
import com.bolooo.studyhomeparents.entity.newhome.LookCourseEnity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.NewHomeService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 动态留言
 */

public class NewHomeUtils {

    private static NewHomeUtils instance;
    private NewHomeUtils(){}
    public static NewHomeUtils getInstance(){
        if(null==instance){
            instance = new NewHomeUtils();
        }
        return instance;
    }
    /**
     * 获取分类
     * @param
     */
    public Call<JSONObject> getDirectoryType(OnDirectoryTypeListener onMessageInfoListener) {
        NewHomeService service = RetrofitUtil.getInstance().createService(NewHomeService.class);
        Call<JSONObject> call = service.getDirectoryType();
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                List<DirectoryTypeEntity> messageEntityList
                        = new ArrayList(com.alibaba.fastjson.JSONObject.parseArray(result, DirectoryTypeEntity.class));
                onMessageInfoListener.getDirectoryTypeSuccess(messageEntityList);
            }

            @Override
            public void onError(String error) {
                onMessageInfoListener.getDirectoryTypeFailure(error);
            }
        }));
        return call;
    }
    public interface OnDirectoryTypeListener{
        void getDirectoryTypeSuccess(List<DirectoryTypeEntity> messageEntityList);
        void getDirectoryTypeFailure(String error);
    }

    /**
     *  找课堂 for version 1.7.0 add in 2017-10-13
     * @param
     */
    public Call<JSONObject> getLookCourseList(int page, int count,Map<String,String> map, final OnLookCourseListLisenter onDynamicInfoLisenter) {
        NewHomeService service = RetrofitUtil.getInstance().createService(NewHomeService.class);
        Call<JSONObject> call = service.lookCourseList(page,count,1,map);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                LookCourseEnity lookCourseEnity = com.alibaba.fastjson.JSONObject.parseObject(result, LookCourseEnity.class);
                onDynamicInfoLisenter.OnLookCourseListSucessful(lookCourseEnity);
            }

            @Override
            public void onError(String error) {
                onDynamicInfoLisenter.OnLookCourseListFail(error);
            }
        }));
        return call;
    }

    public interface OnLookCourseListLisenter{
        void OnLookCourseListSucessful(LookCourseEnity lookCourseEnity);
        void OnLookCourseListFail(String error);
    }

    /**
     *  首页课程新版 for version 1.7.0 add in 2017-10-13
     * @param
     */
    public Call<JSONObject> getHomeCourseList(Map<String,String> map, final OnHomeCourseListLisenter onDynamicInfoLisenter) {
        NewHomeService service = RetrofitUtil.getInstance().createService(NewHomeService.class);
        Call<JSONObject> call = service.homeCourseList(170,map);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                HomeDataEntity homeDataEntity = com.alibaba.fastjson.JSONObject.parseObject(result, HomeDataEntity.class);
                onDynamicInfoLisenter.OnHomeCourseListSucessful(homeDataEntity);
            }

            @Override
            public void onError(String error) {
                onDynamicInfoLisenter.OnHomeCourseListFail(error);
            }
        }));
        return call;
    }

    public interface OnHomeCourseListLisenter{
        void OnHomeCourseListSucessful(HomeDataEntity homeDataEntity);
        void OnHomeCourseListFail(String error);
    }
}
