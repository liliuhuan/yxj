package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.PayTypeEntity;

/**
 * 支付方式
 * nanfeifei
 * 2017/3/20 16:48
 *
 * @version 3.7.0
 */
public class PayTypeAdapter extends NBaseAdapter<PayTypeEntity> {

  public PayTypeAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_pay_type;
  }

  @Override public BaseViewHolder getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<PayTypeEntity> {
    @Bind(R.id.pay_type_icon_iv) ImageView payTypeIconIv;
    @Bind(R.id.pay_type_next_iv) ImageView payTypeNextIv;
    @Bind(R.id.pay_type_title_tv) TextView payTypeTitleTv;
    @Bind(R.id.pay_type_into_tv) TextView payTypeIntoTv;
    public String id;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(PayTypeEntity data, int position) {
      if(data == null){
        return;
      }
      id = data.id;
      payTypeIconIv.setImageResource(data.resId);
      payTypeTitleTv.setText(data.payTitle);
      payTypeIntoTv.setText(data.payInfo);
    }
  }
}
