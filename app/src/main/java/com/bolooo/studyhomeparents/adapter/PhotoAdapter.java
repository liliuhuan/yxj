package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.Bind;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import java.io.File;

/**
 * 多图上传Adapter
 * nanfeifei
 * 2017/3/10 16:43
 *
 * @version 3.7.0
 */
public class PhotoAdapter extends NBaseAdapter<String> {


  public PhotoAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_grid_photo;
  }

  @Override public BaseViewHolder getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<String> {
    @Bind(R.id.photo_tv) ImageView photoTv;
    @Bind(R.id.delete_photo_iv) ImageView deletePhotoIv;
    public String imagePath;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(String data, int position) {

      final int index = position;
      RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
          RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
      params.width= (dm.widthPixels- DensityUtil.dip2px(context,96))/3;
      params.height = params.width;
      params.setMargins(DensityUtil.dip2px(context,8),DensityUtil.dip2px(context,8)
          ,DensityUtil.dip2px(context,8),DensityUtil.dip2px(context,8));
      photoTv.setLayoutParams(params);
      if(parent!=null&&parent.getChildCount() == position){
        if(data == null){
          return;
        }
        imagePath = data;
        if("add".equals(data)){
          photoTv.setImageResource(R.drawable.comm_photo_add);
          deletePhotoIv.setVisibility(View.GONE);
        }else {
          File file = new File(data);
          imageLoaderUtils.loadImage(file, photoTv, 0);
          deletePhotoIv.setVisibility(View.VISIBLE);
        }
      }
      deletePhotoIv.setTag(index);
      deletePhotoIv.setOnClickListener(listener);

    }
  }
}
