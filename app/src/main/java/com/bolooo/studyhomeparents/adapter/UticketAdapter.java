package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.dynamic.UntiketListEntity;
import com.bolooo.studyhomeparents.utils.Constants;

import butterknife.Bind;


/**
 * 游票实体类
 * nanfeifei
 * 2017/3/2 18:02
 *
 * @version 3.7.0
 */
public class UticketAdapter extends NBaseAdapter<UntiketListEntity.UTicketsEntity> {


  public UticketAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_grid_uticket;
  }

  @Override public BaseViewHolder<UntiketListEntity.UTicketsEntity> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<UntiketListEntity.UTicketsEntity> {
    @Bind(R.id.uticket_img_iv) ImageView uticketImgIv;
    @Bind(R.id.uticket_num_tv) TextView uticketNumTv;
    @Bind(R.id.uticket_original_price_tv) TextView uticketOriginalPriceTv;
    @Bind(R.id.uticket_price_tv) TextView uticketPriceTv;
    @Bind(R.id.uticket_recommend_tv) TextView uticket_recommend_tv;
    public UntiketListEntity.UTicketsEntity uticketEntity;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(UntiketListEntity.UTicketsEntity data, int position) {
      if (data == null) {
        return;
      }
      uticketEntity =data;
      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
      params.width = dm.widthPixels*32/100;
      params.height = params.width;
      params.gravity = Gravity.CENTER_HORIZONTAL;
      uticketImgIv.setLayoutParams(params);
      if(data.getImg()!=null){
        imageLoaderUtils.loadImage(Constants.imageUrl+data.getImg(), uticketImgIv, 0);
      }
      uticketNumTv.setText(data.getTitle());
      uticketOriginalPriceTv.setText(data.getViceTitle());
      uticketPriceTv.setText("¥"+data.getPrice()+"购买");
      if(data.isIsRecommand()){
        uticket_recommend_tv.setVisibility(View.VISIBLE);
      }else {
        uticket_recommend_tv.setVisibility(View.GONE);
      }

    }
  }
}
