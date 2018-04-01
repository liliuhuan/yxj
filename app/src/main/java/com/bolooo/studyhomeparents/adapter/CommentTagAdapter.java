package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.CommentTagEntity;
import com.bolooo.studyhomeparents.utils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * 点评标签
 * nanfeifei
 * 2017/3/10 14:15
 *
 * @version 3.7.0
 */
public class CommentTagAdapter extends NBaseAdapter<CommentTagEntity> {


  public Map<Integer, String> map;
  public CommentTagAdapter(Context context) {
    super(context);
    map = new HashMap<>();
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_grid_comment_tag;
  }

  @Override public BaseViewHolder<CommentTagEntity> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<CommentTagEntity> {
    @Bind(R.id.comment_tag_cb) CheckBox commentTagCb;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(CommentTagEntity data, final int position) {
      if(data == null){
        return;
      }
      final int index = position;
      commentTagCb.setText(data.TagInfo);
      final String tagId = data.Id;
      commentTagCb.setOnCheckedChangeListener((compoundButton, b) -> {
        if(b){
          if(map.size()<3){
            map.put(index,tagId);
          }else {
            compoundButton.toggle();
            ToastUtils.showToast("最多可选择3个标签");
          }

        }else {
          if(map.containsKey(index)){
            map.remove(index);
          }
        }
      });
    }
  }
}
