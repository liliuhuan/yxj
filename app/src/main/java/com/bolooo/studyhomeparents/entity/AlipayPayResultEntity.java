package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 支付宝支付完成返回数据
 * nanfeifei
 * 2017/3/22 15:07
 *
 * @version 3.7.0
 */
public class AlipayPayResultEntity implements Parcelable {

  /**
   * alipay_trade_app_pay_response : {"code":"10000","msg":"Success","app_id":"2017030606072113","auth_app_id":"2017030606072113","charset":"utf-8","timestamp":"2017-03-22 14:01:04","total_amount":"0.01","trade_no":"2017032221001004860265547695","seller_id":"2088121819484615","out_trade_no":"20170322374460"}
   * sign : MYasC+Cf9qdTxjpabQk7F5OFki8mQXfaa8Hsd2siI39XAymXJT6KxuDor1PwxQRzNSSQz8iOBNTZK7KLaWKZ4ZFviT+g3okHUDGsC2WrWFnRQWXrDaDJnYC2WHnfVZu2X72AVuKKZL+1+JYDctvbN6kubJ63FMZu3sj0WsrcPzQnuhFU5+8G0ggD7m99wBr/5niuNZ905prHwuFSW7hrzXxw8Cf/c/DWXksQnY1/8iPe9DT6DD5NLWXn789zWAinwqu5gnFBDZRuVslZRUojpVzm+kK/WiFX0tQztYFpptjLBfoujZSdA02rwoXZRWn2AK99l5rR9S92v+e9hKG8sA==
   * sign_type : RSA2
   */

  public AlipayTradeAppPayResponseBean alipay_trade_app_pay_response;
  public String sign;
  public String sign_type;

  public static class AlipayTradeAppPayResponseBean implements Parcelable {
    /**
     * code : 10000
     * msg : Success
     * app_id : 2017030606072113
     * auth_app_id : 2017030606072113
     * charset : utf-8
     * timestamp : 2017-03-22 14:01:04
     * total_amount : 0.01
     * trade_no : 2017032221001004860265547695
     * seller_id : 2088121819484615
     * out_trade_no : 20170322374460
     */

    public String code;
    public String msg;
    public String app_id;
    public String auth_app_id;
    public String charset;
    public String timestamp;
    public String total_amount;
    public String trade_no;
    public String seller_id;
    public String out_trade_no;

    @Override public int describeContents() {
      return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
      dest.writeString(this.code);
      dest.writeString(this.msg);
      dest.writeString(this.app_id);
      dest.writeString(this.auth_app_id);
      dest.writeString(this.charset);
      dest.writeString(this.timestamp);
      dest.writeString(this.total_amount);
      dest.writeString(this.trade_no);
      dest.writeString(this.seller_id);
      dest.writeString(this.out_trade_no);
    }

    public AlipayTradeAppPayResponseBean() {
    }

    protected AlipayTradeAppPayResponseBean(Parcel in) {
      this.code = in.readString();
      this.msg = in.readString();
      this.app_id = in.readString();
      this.auth_app_id = in.readString();
      this.charset = in.readString();
      this.timestamp = in.readString();
      this.total_amount = in.readString();
      this.trade_no = in.readString();
      this.seller_id = in.readString();
      this.out_trade_no = in.readString();
    }

    public static final Parcelable.Creator<AlipayTradeAppPayResponseBean> CREATOR =
        new Parcelable.Creator<AlipayTradeAppPayResponseBean>() {
          @Override public AlipayTradeAppPayResponseBean createFromParcel(Parcel source) {
            return new AlipayTradeAppPayResponseBean(source);
          }

          @Override public AlipayTradeAppPayResponseBean[] newArray(int size) {
            return new AlipayTradeAppPayResponseBean[size];
          }
        };
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.alipay_trade_app_pay_response, flags);
    dest.writeString(this.sign);
    dest.writeString(this.sign_type);
  }

  public AlipayPayResultEntity() {
  }

  protected AlipayPayResultEntity(Parcel in) {
    this.alipay_trade_app_pay_response =
        in.readParcelable(AlipayTradeAppPayResponseBean.class.getClassLoader());
    this.sign = in.readString();
    this.sign_type = in.readString();
  }

  public static final Parcelable.Creator<AlipayPayResultEntity> CREATOR =
      new Parcelable.Creator<AlipayPayResultEntity>() {
        @Override public AlipayPayResultEntity createFromParcel(Parcel source) {
          return new AlipayPayResultEntity(source);
        }

        @Override public AlipayPayResultEntity[] newArray(int size) {
          return new AlipayPayResultEntity[size];
        }
      };
}
