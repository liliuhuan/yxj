package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.FavoriteTeacherEntity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.utils.StringUtil;

import butterknife.Bind;


/**
 * 我关注的讲师适配器
 * nanfeifei
 * 2017/2/23 14:14
 *
 * @version 3.7.0
 */
public class FavoriteTeacherAdapter extends NBaseAdapter<FavoriteTeacherEntity> {


  public FavoriteTeacherAdapter(Context context) {
    super(context);
  }

  @Override public int getConvertViewId(int position) {
    return R.layout.item_list_favorite_teacher;
  }

  @Override public BaseViewHolder<FavoriteTeacherEntity> getNewHolder(View view) {
    return new ViewHolder(view);
  }

  public class ViewHolder extends BaseViewHolder<FavoriteTeacherEntity> {
    @Bind(R.id.teacher_headimg_iv) ImageView teacherHeadimgIv;
    @Bind(R.id.teacher_jobs_tv) TextView teacherJobsTv;
    @Bind(R.id.teacher_name_tv) TextView teacherNameTv;
    @Bind(R.id.teacher_latest_curriculum_tv) TextView teacherLatestCurriculumTv;
    public FavoriteTeacherEntity favoriteTeacherEntity;
    public ViewHolder(View view) {
      super(view);
    }

    @Override public void loadData(FavoriteTeacherEntity data, int position) {
      if(data == null){
        return;
      }
      favoriteTeacherEntity = data;
      imageLoaderUtils.loadRoundImage(Constants.imageUrl+data.HeadPhoto+"?w=400&h=400"
          , teacherHeadimgIv, R.drawable.noavatar, DensityUtil.dip2px(context, 60));
      if (StringUtil.isEmpty(data.JobTitle) ){
        teacherJobsTv.setVisibility(View.GONE);
      }else{
        teacherJobsTv.setVisibility(View.VISIBLE);
        teacherJobsTv.setText(data.JobTitle);
      }

      teacherNameTv.setText(data.TeacherName);
     // teacherSignatureTv.setText(data.Summary);
      String str=context.getString(R.string.latest_curriculum)+"<font color ='black'>《"+data.LatestCourse+"》</font>";
      teacherLatestCurriculumTv.setText(Html.fromHtml(str));

    }
  }
}
