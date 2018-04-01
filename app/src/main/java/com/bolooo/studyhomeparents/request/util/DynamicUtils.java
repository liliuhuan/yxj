package com.bolooo.studyhomeparents.request.util;

import android.content.Context;

import com.bolooo.studyhomeparents.entity.MessageWordEntity;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicCommentListEntity;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicDetailEntity;
import com.bolooo.studyhomeparents.entity.dynamic.DynamicListEntity;
import com.bolooo.studyhomeparents.entity.dynamic.UzoneZanListEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.DynamicService;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * 动态留言
 */

public class DynamicUtils {

    private static DynamicUtils instance;
    private DynamicUtils(){}
    public static DynamicUtils getInstance(){
        if(null==instance){
            instance = new DynamicUtils();
        }
        return instance;
    }
    /**
     * 获取留言列表
     * @param
     */
    public Call<JSONObject> getTeacherMessage(String tid, String pid, int page , int count, final OnMessageInfoListener onMessageInfoListener) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.getTeacherMessage(tid,pid,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                List<MessageWordEntity> messageEntityList
                        = new ArrayList(com.alibaba.fastjson.JSONObject.parseArray(result, MessageWordEntity.class));
                onMessageInfoListener.getTeacherMessageSuccess(messageEntityList);
            }

            @Override
            public void onError(String error) {
                onMessageInfoListener.getTeacherMessageFailure(error);
            }
        }));
        return call;
    }
    public interface OnMessageInfoListener{
        /*获取留言成功*/
        void getTeacherMessageSuccess(List<MessageWordEntity> messageEntityList);
        /*获取留言失败*/
        void getTeacherMessageFailure(String error);
    }

    /**
     * 得到动态信息
     * @param
     */
    public Call<JSONObject> getDynamicinfo(String teacherId, final int page, final int count, final OnDynamicInfoLisenter onDynamicInfoLisenter) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.getMyDynamicInfo(teacherId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
               // ToastUtils.showToast(result);
                List<DynamicListEntity> dynamicListEntityList
                        = new ArrayList( com.alibaba.fastjson.JSONObject.parseArray(result, DynamicListEntity.class));
                onDynamicInfoLisenter.OnGetDynamicinfoSucessful(dynamicListEntityList);
            }

            @Override
            public void onError(String error) {
                onDynamicInfoLisenter.OnGetDynamicinfoFail(error);
            }
        }));
        return call;
    }

    public interface OnDynamicInfoLisenter{
        void OnGetDynamicinfoSucessful(List<DynamicListEntity> dynamicListEntityList);
        void OnGetDynamicinfoFail(String error);
    }

    /**
     * 动态点赞
     * @param
     * @param callback
     */
    public Call<JSONObject> AddDynamcLike(String uzid, IRequestCallBack callback ) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.addLikeForDynamic(uzid,new HashMap<String, String>());
        call.enqueue(new WrapperCallBack<JSONObject>(callback));
        return call;
    }



    /**
     * 动态详情
     * @param
     * @param
     */
    public Call<JSONObject> getDynamcDetail(final Context mConext, String uid, final OnDynamicDetailInfoLisenter onDynamicDetailInfoLisenter ) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.getMyDynamicDetail(uid);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                DynamicDetailEntity dynamicDetailEntity = com.alibaba.fastjson.JSONObject.parseObject(result, DynamicDetailEntity.class);
                onDynamicDetailInfoLisenter.OnGetDynamicDetailinfoSucessful(dynamicDetailEntity);
            }

            @Override
            public void onError(String error) {
                onDynamicDetailInfoLisenter.OnGetDynamicDetailinfoFail(error);
            }

        }));
        return call;
    }

    public interface OnDynamicDetailInfoLisenter{
        void OnGetDynamicDetailinfoSucessful(DynamicDetailEntity dynamicDetailEntity);
        void OnGetDynamicDetailinfoFail(String error);

    }


    /**
     * 发布动态评论
     * @param
     * @param callback
     */
    public Call<JSONObject> publishComment(Map<String ,String > map, IRequestCallBack callback ) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.publishComment(map);
        call.enqueue(new WrapperCallBack<JSONObject>(callback));
        return call;
    }


    /**
     * 得到动态评论列表
     * @param
     */
    public Call<JSONObject> getDynamicCommentList(String uzoneId,  int page,  int count, final OnDynamicCommentListLisenter onDynamicCommentListLisenter) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.getDynamicCommentlist(uzoneId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                //ToastUtils.showToast("评论");
                List<DynamicCommentListEntity> dynamicCommentListEntityList
                        = new ArrayList( com.alibaba.fastjson.JSONObject.parseArray(result, DynamicCommentListEntity.class));
                onDynamicCommentListLisenter.OnGetDynamicCommentListSucessful(dynamicCommentListEntityList);
            }

            @Override
            public void onError(String error) {
                onDynamicCommentListLisenter.OnGetDynamicCommentListFail(error);
            }
        }));
        return call;
    }

    public interface OnDynamicCommentListLisenter{
        void OnGetDynamicCommentListSucessful(List<DynamicCommentListEntity> dynamicCommentListEntityList);
        void OnGetDynamicCommentListFail(String error);
    }


    /**
     * 得到点赞列表
     * @param
     */
    public Call<JSONObject> getUZoneZaList(String uzoneId,  int page,  int count, final OnUZoneZanListLisenter onUZoneZanListLisenter) {
        DynamicService service = RetrofitUtil.getInstance().createService(DynamicService.class);
        Call<JSONObject> call = service.getUzoneZanlist(uzoneId,page,count);
        call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
            @Override
            public void onStartLoading() {

            }

            @Override
            public void onSuccess(String result) {
                //ToastUtils.showToast("点赞");
                List<UzoneZanListEntity> uzoneZanListEntityList
                        = new ArrayList( com.alibaba.fastjson.JSONObject.parseArray(result, UzoneZanListEntity.class));
                onUZoneZanListLisenter.OnGetUzonezanSucessful(uzoneZanListEntityList);
            }

            @Override
            public void onError(String error) {
                onUZoneZanListLisenter.OnGetUzonezanFail(error);
            }
        }));
        return call;
    }

    public interface OnUZoneZanListLisenter{
        void OnGetUzonezanSucessful(List<UzoneZanListEntity> uzoneZanListEntityList);
        void OnGetUzonezanFail(String error);
    }

}
