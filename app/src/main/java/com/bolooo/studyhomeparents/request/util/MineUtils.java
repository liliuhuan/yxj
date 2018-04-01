package com.bolooo.studyhomeparents.request.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.bolooo.studyhomeparents.entity.BabyEntity;
import com.bolooo.studyhomeparents.entity.CommentTagEntity;
import com.bolooo.studyhomeparents.entity.MineLessonsEntity;
import com.bolooo.studyhomeparents.entity.VipProductEntity;
import com.bolooo.studyhomeparents.entity.WeichatPayEntity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.callback.WrapperCallBack;
import com.bolooo.studyhomeparents.request.retrofit.RetrofitUtil;
import com.bolooo.studyhomeparents.request.service.MineService;
import com.bolooo.studyhomeparents.utils.CommentUtils;
import com.bolooo.studyhomeparents.utils.LogUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;

/**
 * ${tags}
 * nanfeifei
 * 2017/2/22 13:28
 *
 * @version 3.7.0
 */
public class MineUtils {
  private static MineUtils instance;
  private MineUtils(){}
  public static MineUtils getInstance(){
    if(null==instance){
      instance = new MineUtils();
    }
    return instance;
  }
  /**
   * 根据家长Id查询家长不同状态订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getLessonsList(int status, int page, int count, IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getLessonsList(status, page, count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取扫码上课页面数据
   * @param callBack
   * @return
   */
  public Call<JSONObject> getRQCodeLessons(String frequencyDetailId,String orderId , IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getQRodeLessons(frequencyDetailId,orderId);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取家长信息
   * @param callBack
   * @return
   */
  public Call<JSONObject> getParent(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getParent();
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * 获取我关注的讲师
   * @param callBack
   * @param page
   * @return
   */
  public Call<JSONObject> getFavoriteTeacherList(IRequestCallBack callBack, int page, int count) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getFavoriteTeacherList(page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * 获取我关注的课程
   * @param callBack
   * @param page
   * @return
   */
  public Call<JSONObject> getFavoriteCurriculumList(IRequestCallBack callBack
      , double lat, double lon, int page, int count) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getFavoriteCurriculumList(lat+"", lon+"", page, count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取我的宝贝
   */
  public Call<JSONObject> getBabyList(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getBabyList();
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 添加宝贝
   * @param callBack
   */
  public Call<JSONObject> addBaby(BabyEntity babyEntity,IRequestCallBack callBack) {
    Map<String,String> params = new HashMap<>();
    params.put("Name", babyEntity.Name);
    params.put("NickName", babyEntity.NickName);
    params.put("Gender", babyEntity.Gender+"");
    params.put("Birthday", babyEntity.Birthday);
    params.put("HeadPhoto", babyEntity.HeadPhoto);
    params.put("Description", babyEntity.Description);
    Log.d("params==",params.toString());
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.addBaby(params);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取宝贝信息
   * @param onBabyInfoListener
   */
  public Call<JSONObject> getBabyInfo(String childId ,final OnBabyInfoListener onBabyInfoListener) {
    LogUtils.d("childId==",childId+"");
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getBabyInfo(childId);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        BabyEntity babyEntity = com.alibaba.fastjson.JSONObject.parseObject(result, BabyEntity.class);
        onBabyInfoListener.getBabyInfoSuccess(babyEntity);
      }

      @Override public void onError(String error) {
        onBabyInfoListener.getBabyInfoFailure(error);
      }
    }));
    return call;
  }
  public interface OnBabyInfoListener{
    /*获取孩子信息成功*/
    void getBabyInfoSuccess(BabyEntity babyEntity);
    /*获取修改孩子信息失败*/
    void getBabyInfoFailure(String error);
  }
  /**
   * 编辑宝贝
   * @param onEditBabyListener
   */
  public Call<JSONObject> editBaby(BabyEntity babyEntity, final OnEditBabyListener onEditBabyListener) {
    Map<String,String> params = new HashMap<>();
    params.put("ParentId", babyEntity.ParentId);
    params.put("Id", babyEntity.Id);
    params.put("Name", babyEntity.Name);
    params.put("NickName", babyEntity.NickName);
    params.put("Gender", babyEntity.Gender+"");
    params.put("Birthday", babyEntity.Birthday);
    params.put("HeadPhoto", babyEntity.HeadPhoto);
    params.put("Description", babyEntity.Description);
    Log.d("params==",params.toString());
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.editBaby(1, params);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        onEditBabyListener.editSuccess(result);
      }

      @Override public void onError(String error) {
        onEditBabyListener.editFailure(error);
      }
    }));
    return call;
  }
  public interface OnEditBabyListener{
    /*修改孩子信息成功*/
    void editSuccess(String result);
    /*修改孩子信息失败*/
    void editFailure(String error);
  }
  /**
   * 消息通知列表
   * @param callBack
   * @param page
   * @return
   */
  public Call<JSONObject> getMessageList(IRequestCallBack callBack, int page, int count) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getMessageList(page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 修改家长信息
   * @param callBack
   * @param fieldName
   * @param value
   * @return
   */
  public Call<JSONObject> editParent(IRequestCallBack callBack, String fieldName, String value) {
    Map<String,String> params = new HashMap<>();
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.editParent(fieldName,value, params);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 游票购买列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getUTicketList(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUTicketList("");
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 游票购买(支付宝)
   * @param uTicketId
   * @return
   */
  public Call<JSONObject> getUTicketAlipayInfo(String uTicketId, String number, String childId,final OnUTicketAlipayListener listener) {

    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUTicketAlipayInfo(uTicketId,number,childId);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {}
      @Override public void onSuccess(String result) {
        listener.onUTicketAlipaySucess(result);
      }

      @Override public void onError(String error) {
        listener.onUTicketAlipayFailure(error);
      }
    }));
    return call;
  }
  public interface OnUTicketAlipayListener{
    /*获取点评标签成功*/
    void onUTicketAlipaySucess(String orderInfo);
    /*获取点评标签失败*/
    void onUTicketAlipayFailure(String error);
  }
  /**
   * 游票购买(微信)
   * @param uTicketId
   * @return
   */
  public Call<JSONObject> getUTicketWeichatInfo(Context context, String uTicketId, String number, String childId, final OnUTicketWeichatListener listener) {
    String ip = CommentUtils.getLocalIpAddress(context);
    if(TextUtils.isEmpty(ip)){
      ip = "192.168.1.1";
    }
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUTicketWeichatInfo(uTicketId,number,childId,ip);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        WeichatPayEntity weichatPayEntity = com.alibaba.fastjson.JSONObject
            .parseObject(result, WeichatPayEntity.class);
        if(weichatPayEntity == null){
          listener.onUTicketWeichatFailure("解析出错");
          return;
        }
        listener.onUTicketWeichatSucess(weichatPayEntity);
      }

      @Override public void onError(String error) {
        listener.onUTicketWeichatFailure(error);
      }
    }));
    return call;
  }
  public interface OnUTicketWeichatListener{
    /*获取点评标签成功*/
    void onUTicketWeichatSucess(WeichatPayEntity weichatPayEntity);
    /*获取点评标签失败*/
    void onUTicketWeichatFailure(String error);
  }
  /**
   * 游票购买(支付结果)
   * @param orderSn
   * @return
   */
  public Call<JSONObject> authPayResult(String orderSn, final OnAuthPayResultListener listener) {

    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.authPayResult(orderSn);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {
      }
      @Override public void onSuccess(String result) {
        listener.onAuthPayResultSucess(result);
      }

      @Override public void onError(String error) {
        listener.onAuthPayResultFailure(error);
      }
    }));
    return call;
  }
  public interface OnAuthPayResultListener{
    /*获取点评标签成功*/
    void onAuthPayResultSucess(String orderInfo);
    /*获取点评标签失败*/
    void onAuthPayResultFailure(String error);
  }
  /**
   * 游票购买记录列表
   * @param callBack
   * @param page
   * @return
   */
  public Call<JSONObject> getUTicketRecordList(IRequestCallBack callBack, int page, int count) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUTicketRecordList("1",page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 游票购买记录详情
   * @param callBack
   * @param
   * @return
   */
  public Call<JSONObject> getUTicketRecordDeatil(String rId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUTicketRecordDetail(rId);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 积分兑换
   * @param point
   * @param ticketCount
   * @return
   */
  public Call<JSONObject> submitPointExchange(IRequestCallBack callBack, int point, int ticketCount) {
    Map<String,String> params = new HashMap<>();
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.submitPointExchange(point, ticketCount, params);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取点评标签
   * @return
   */
  public Call<JSONObject> getCommentTagList(final OnCommentTagListener listener) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getCommentTagList();
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        List<CommentTagEntity> commentTagEntityList = new ArrayList
            (com.alibaba.fastjson.JSONObject.parseArray(result, CommentTagEntity.class));
        if(commentTagEntityList!=null){
          listener.onCommentTagSucess(commentTagEntityList);
        }else {
          listener.onCommentTagFailure("解析出错");
        }
      }

      @Override public void onError(String error) {
        listener.onCommentTagFailure(error);
      }
    }));
    return call;
  }
  public interface OnCommentTagListener{
    /*获取点评标签成功*/
    void onCommentTagSucess(List<CommentTagEntity> commentTagEntityList);
    /*获取点评标签失败*/
    void onCommentTagFailure(String error);
  }
  /**
   * 点评课程
   * @return
   */
 public Call<JSONObject> submitComment(String OrderId, String FrequencyDetailId, int ProfessionScore
     , int InteractiveScore, int AffinityScore, String Content, List<String> TagIds
     , List<String> ImgIds, final OnSubmitCommentListener listener) {
    Map<String,String> params = new HashMap<>();
    params.put("OrderId", OrderId);
    params.put("FrequencyDetailId", FrequencyDetailId);
    params.put("ProfessionScore", ProfessionScore+"");
    params.put("InteractiveScore", InteractiveScore+"");
    params.put("AffinityScore", AffinityScore+"");
    params.put("Content", Content);
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.submitComment(TagIds, ImgIds,params);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        listener.onCommentSucess(result);
      }

      @Override public void onError(String error) {
        listener.onCommentFailure(error);
      }
    }));
    return call;
  }
  public interface OnSubmitCommentListener{
    /*点评课程成功*/
    void onCommentSucess(String result);
    /*点评课程失败*/
    void onCommentFailure(String error);
  }
  /**
   * 购课详情
   * @param orderId
   * @return
   */
  public Call<JSONObject> getLessonsOrderDetail(String orderId, String frequencyId,final OnLessonsOrderDetailListener listener) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getLessonsOrderDetail(orderId,frequencyId);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {

      }

      @Override public void onSuccess(String result) {
        MineLessonsEntity mineLessonsEntity = com.alibaba.fastjson.JSONObject
            .parseObject(result, MineLessonsEntity.class);
        if(mineLessonsEntity!=null){
          listener.onLessonsOrderDetailSucess(mineLessonsEntity);
        }else {
          listener.onLessonsOrderDetailFailure("解析出错");
        }

      }

      @Override public void onError(String error) {
        listener.onLessonsOrderDetailFailure(error);
      }
    }));
    return call;
  }
  public interface OnLessonsOrderDetailListener{
    /*获取购课详情成功*/
    void onLessonsOrderDetailSucess(MineLessonsEntity mineLessonsEntity);
    /*获取购课详情失败*/
    void onLessonsOrderDetailFailure(String error);
  }
  /**
   * 购课详情
   * @param orderId
   * @return
   */
  public Call<JSONObject> getSuccessLessonsOrderDetail(String orderId, String frequencyId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getLessonsOrderDetail(orderId,frequencyId);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * vip状态
   * @param callBack
   * @return
   */
  public Call<JSONObject> getVipState(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getVipState();
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * vip产品列表
   * @param
   * @return
   */
  public Call<JSONObject> getVipProduct(String childId,final OnVipProductListener listener) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getVipProduct(childId);
    call.enqueue(new WrapperCallBack<JSONObject>(new IRequestCallBack() {
      @Override public void onStartLoading() {
      }
      @Override public void onSuccess(String result) {
        VipProductEntity vipProductEntity = com.alibaba.fastjson.JSONObject
                .parseObject(result, VipProductEntity.class);
        if(vipProductEntity!=null){
          listener.onVipProductSucess(vipProductEntity);
        }else {
          listener.onVipProductFailure("解析出错");
        }

      }

      @Override public void onError(String error) {
        listener.onVipProductFailure(error);
      }
    }));
    return call;
  }
  public interface OnVipProductListener{
    void onVipProductSucess(VipProductEntity vipProductEntity);
    void onVipProductFailure(String error);
  }

  /**
   * 删除孩子
   * @param callBack
   * @return
   */
  public Call<JSONObject> deleteChild( String childId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.deleteBaby(childId,new HashMap<>());
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * 获取讲师详情页图片信息
   * @param callBack
   * @return
   */
  public Call<JSONObject> getUtickImage(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getUticketImage("UticketNotice");
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }

  /**
   * 获取新消息列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getNewMessage(int page,int count,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getNewMessageList(page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 获取聊天列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getChatList(int page,int count,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getChatList(page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 添加聊天信息
   * @param callBack
   * @return
   */
  public Call<JSONObject> getChatMessageDetail(int page,int count ,String teacherId,String courseid,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getChatMessageDetail(page,count,teacherId,courseid);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 添加聊天信息
   * @param callBack
   * @return
   */
  public Call<JSONObject> addChatMessage(Map<String ,String > map,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.addChatMessage(map);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 家长端：获取教师最新课程、与教师所有留言列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getLeaveMessageList(String tId,int page,int count,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getLeaveMessageList(tId,page,count);
    call.enqueue(new WrapperCallBack<JSONObject>(callBack));
    return call;
  }
  /**
   * 家长端：获取教师Id获取教师的基本信息
   * @param callBack
   * @return
   */
  public Call<JSONObject> getTeacherBaseInfo(String tId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getTeacherBasicInfo(tId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }

  /**
   * 家长端：获取课程订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getCourseOrderList(int page,int count,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getMyOrderList(1,page,count);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }

  /**
   * 取消订单
   * @param
   * @return
   */
  public Call<JSONObject> cancelOrder(String orderId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.cancelMyOrder(orderId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 获取首页的轮播图
   * @param
   * @return
   */
  public Call<JSONObject> getHomeAdvertismentList(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getAdVertisementList(2);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }

  /**
   * 获取套餐的状态
   * @param
   * @return
   */
  public Call<JSONObject> getComboInfo(String tcId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getComboInfo(tcId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 获取套餐的状态
   * @param tId 套餐Id
   * @return
   */
  public Call<JSONObject> getComboBuyInfo(String tId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getComboBuyInfo(tId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  } /**
   * 支付订单
   * @param
   * @return
   */
  public Call<JSONObject> payMyOrder(String orderId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.payMyOrder(orderId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 创建套餐订单订单
   * @param
   * @return
   */
  public Call<JSONObject> createComoboOrder(String packageId,List<String> childIds,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.confirmOrder(packageId,childIds);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 检查孩子是否已经购买过某套餐
   * @param
   * @return
   */
  public Call<JSONObject> checkChildCanBuyCombo(String packageId,String childId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.checkChildCanCombo(packageId,childId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 家长端：支付套餐订单
   * @param
   * @return
   */
  public Call<JSONObject> signUpPayComboOrder(String pOrderId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.payComboOrder(pOrderId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }

  /**
   * 家长端：获取套餐订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getComboOrderList(int page,int count,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getComboOrderList(page,count);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
  /**
   * 家长端：获取套餐订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> cancelComboOrderList(String pOrderId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.cancelComboOrder(pOrderId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }  /**
   * 家长端：获取套餐订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getComboOrderDetail(String pOrderId,IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getComboOrderDetail(pOrderId);
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }

  /**
   * 家长端：获取套餐订单列表
   * @param callBack
   * @return
   */
  public Call<JSONObject> getNoPayOrder(IRequestCallBack callBack) {
    MineService service = RetrofitUtil.getInstance().createService(MineService.class);
    Call<JSONObject> call = service.getNoPayOrder("1");
    call.enqueue(new WrapperCallBack<>(callBack));
    return call;
  }
}
