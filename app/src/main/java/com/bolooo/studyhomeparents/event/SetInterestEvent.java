package com.bolooo.studyhomeparents.event;

import com.bolooo.studyhomeparents.entity.UserInfoEntity;
import java.util.List;

/**
 * 学习意向
 * nanfeifei
 * 2017/3/1 15:31
 *
 * @version 3.7.0
 */
public class SetInterestEvent {
  public List<UserInfoEntity.IntentionsBean> list;
  public SetInterestEvent(){
  }
  public SetInterestEvent(List<UserInfoEntity.IntentionsBean> list){
    super();
    this.list = list;
  }
}
