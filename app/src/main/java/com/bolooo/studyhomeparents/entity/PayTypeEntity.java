package com.bolooo.studyhomeparents.entity;

import android.os.Parcel;
import android.os.Parcelable;
import com.bolooo.studyhomeparents.R;
import java.util.ArrayList;
import java.util.List;

/**
 * 支付方式
 * nanfeifei
 * 2017/3/20 16:50
 *
 * @version 3.7.0
 */
public class PayTypeEntity implements Parcelable {
  public String id;
  public int resId;
  public String payTitle;
  public String payInfo;

  public PayTypeEntity() {
  }
  public static String ids[] = {
      "1",
      "2"
  };
  public static int resIds[] = {
      R.drawable.pay_ali,
      R.drawable.pay_wechat
  };
  public static String titles[] = {
      "支付宝",
      "微信"
  };
  public static String infos[] = {
      "数亿用户都在用，安全可托付",
      ""
  };

  /**
   * 获取支付方式列表
   * @return
   */
  public static List<PayTypeEntity> getPayTypeList(){
    List<PayTypeEntity> list = new ArrayList<>();
    PayTypeEntity payTypeEntity;
    for(int i = 0; i<resIds.length; i++){
      payTypeEntity = new PayTypeEntity();
      payTypeEntity.id = ids[i];
      payTypeEntity.resId = resIds[i];
      payTypeEntity.payTitle = titles[i];
      payTypeEntity.payInfo = infos[i];
      list.add(payTypeEntity);
    }

    return list;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.id);
    dest.writeInt(this.resId);
    dest.writeString(this.payTitle);
    dest.writeString(this.payInfo);
  }

  protected PayTypeEntity(Parcel in) {
    this.id = in.readString();
    this.resId = in.readInt();
    this.payTitle = in.readString();
    this.payInfo = in.readString();
  }

  public static final Creator<PayTypeEntity> CREATOR = new Creator<PayTypeEntity>() {
    @Override public PayTypeEntity createFromParcel(Parcel source) {
      return new PayTypeEntity(source);
    }

    @Override public PayTypeEntity[] newArray(int size) {
      return new PayTypeEntity[size];
    }
  };
}
