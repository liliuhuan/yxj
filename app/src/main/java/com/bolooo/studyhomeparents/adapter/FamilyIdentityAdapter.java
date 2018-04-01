package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import butterknife.Bind;
import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;

/**
 * 家长身份适配器
 * nanfeifei
 * 2017/3/2 14:31
 *
 * @version 3.7.0
 */
public class FamilyIdentityAdapter extends NBaseAdapter<String> {


  public FamilyIdentityAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_familyidentity;
  }

  @Override public BaseViewHolder<String> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<String> {
    @Bind(R.id.family_identity_tv) TextView familyIdentityTv;
    public String data;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(String data, int position) {
      if (data == null) {
        return;
      }
      this.data = data;
      familyIdentityTv.setText(data);
    }
  }
}
