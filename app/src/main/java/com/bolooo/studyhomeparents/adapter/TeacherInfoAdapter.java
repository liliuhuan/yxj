package com.bolooo.studyhomeparents.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.TeacherPicturesActivity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.NetworkUtils;
import com.bolooo.studyhomeparents.utils.StringUtil;
import com.bolooo.studyhomeparents.views.MyGridView;
import com.bolooo.studyhomeparents.views.MyListView;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import fm.jiecao.jcvideoplayer_lib.JCFullScreenActivity;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Guojunhong on 2017/3/2.
 */

public class TeacherInfoAdapter extends RecyclerView.Adapter<TeacherInfoAdapter.MessgageViewholder> {


    private String teacherName;
    private String teacherId;
    private ArrayList<String> mGuides;
    private GlideUtils imageLoaderUtils;
    private Context context;
    private JSONObject jsonObject;
    TeacherRecordListAdapter teacherResumsAdapter;
    TeacherArticleListAdapter teacherArticleListAdapter;
    List<JSONObject> teacherResumsList;
    List<JSONObject> teacherArticleLis;

    public TeacherInfoAdapter(Context context, String teacherName, String teacherID) {
        this.context = context;
        this.teacherName = teacherName;
        this.teacherId = teacherID;
        imageLoaderUtils = new GlideUtils(context);
        jsonObject = new JSONObject();
        teacherArticleLis = new ArrayList<>();
    }

    @Override
    public MessgageViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.teacher_info_item, null);
        return new MessgageViewholder(view);
    }

    @Override
    public void onBindViewHolder(MessgageViewholder holder, int position) {
        try {
            if (jsonObject.length() > 0) {
                boolean isNameVerified = jsonObject.optBoolean("IsNameVerified");//实名认证
                boolean isSiteVerified = jsonObject.optBoolean("IsSiteVerified");//现场认账
                boolean isProfessionVerified = jsonObject.optBoolean("IsProfessionVerified");//职业认证
                if (isNameVerified) {
                    holder.imageIsNameVerified.setVisibility(View.VISIBLE);
                } else {
                    holder.imageIsNameVerified.setVisibility(View.GONE);
                }

                if (isSiteVerified) {
                    holder.imageIsSiteVerified.setVisibility(View.VISIBLE);
                } else {
                    holder.imageIsSiteVerified.setVisibility(View.GONE);
                }

                if (isProfessionVerified) {
                    holder.imageIsProfessionVerified.setVisibility(View.VISIBLE);
                } else {
                    holder.imageIsProfessionVerified.setVisibility(View.GONE);
                }

                if (isNameVerified || isSiteVerified || isProfessionVerified) {
                    holder.llVerify.setVisibility(View.VISIBLE);
                } else {
                    holder.llVerify.setVisibility(View.INVISIBLE);
                }

                JSONArray teacherImages = jsonObject.optJSONArray("TeacherImages");//图片集合
                JSONArray articles = jsonObject.optJSONArray("Articles");//文章
                JSONArray teacherResumes = jsonObject.optJSONArray("TeacherResumes");//履历TeacherResumes

                String videoUrl = jsonObject.optString("VideoUrl");
                String videoScreenshot = jsonObject.optString("VideoScreenshot");
                boolean isVideoCertify = jsonObject.optBoolean("IsVideoCertify");
                if (isVideoCertify){
                    holder.videoplayer.setVisibility(View.VISIBLE);
                    holder.videoplayer.ivBack.setVisibility(View.GONE);
                    ((JCVideoPlayer)holder.videoplayer).ivStart.setVisibility(View.GONE);
                   // Bitmap bitmapFromVideoPath = BitmapUtils.createBitmapFromVideoPath(videoUrl, 800, 640);
                    //holder.videoplayer.ivThumb.setImageBitmap(bitmapFromVideoPath);
                    holder.videoplayer.ivThumb.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    //holder.videoplayer.ivThumb.setAdjustViewBounds(true);
                    Glide.with(context).load(Constants.imageUrl+videoScreenshot)
                            .error(R.drawable.noimage).into(holder.videoplayer.ivThumb);
                    holder.imageAction.setVisibility(View.VISIBLE);
                    holder.cardView.setVisibility(View.VISIBLE);
                }else {
                    holder.videoplayer.setVisibility(View.GONE);
                    holder.imageAction.setVisibility(View.GONE);
                    holder.cardView.setVisibility(View.GONE);
                }

                //个人介绍
                String description = jsonObject.optString("Description");
                if ((teacherImages == null || teacherImages.length() == 0)
                        && (articles == null || articles.length() == 0)
                        && (teacherResumes == null || teacherResumes.length() == 0)
                        && StringUtil.isEmpty(description)
                        && (!isVideoCertify )) {
                    holder.llTeacherAllInfo.setVisibility(View.GONE);
                    holder.emptyImage.setVisibility(View.VISIBLE);
                } else {
                    holder.llTeacherAllInfo.setVisibility(View.VISIBLE);
                    holder.emptyImage.setVisibility(View.GONE);

                }

                holder.imageAction.setOnClickListener(V->{
                    int netWorkType = NetworkUtils.getNetWorkType(context);
                    if (netWorkType == 4){
                        JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
                    }else {
                        showAdialogIsWifi(videoUrl);
                    }
                });
                holder.videoplayer.ivThumb.setOnClickListener(V->{
                    int netWorkType = NetworkUtils.getNetWorkType(context);
                    if (netWorkType == 4){
                        JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
                    }else {
                        showAdialogIsWifi(videoUrl);
                    }
                });
                if (StringUtil.isEmpty(description)) {
                    //holder.selfIntroduction.setText("");
                    //holder.llSelfIntroduction.setVisibility(View.GONE);
                    holder.selfIntroduction.setVisibility(View.GONE);
                    holder.llSelfIntroduction.setVisibility(View.GONE);
                } else {
                    holder.llSelfIntroduction.setVisibility(View.VISIBLE);
                    holder.selfIntroduction.setVisibility(View.VISIBLE);
                    Spanned description1 = Html.fromHtml(jsonObject.optString("Description"));
                    holder.selfIntroduction.setText(description1.toString().trim());
//                    holder.selfIntroduction.getSettings().setDefaultFontSize(DensityUtil.dip2px(context,15));
//                    holder.selfIntroduction.setBackgroundColor(0); // 设置背景色
//                    holder.selfIntroduction.getBackground().setAlpha(0); // 设置填充透明度 范围：0-255
//                    holder.selfIntroduction.getSettings().setDefaultTextEncodingName("UTF-8");
//                    holder.selfIntroduction.loadData(description,"text/html; charset=UTF-8", null);
                }
                if (StringUtil.isEmpty(jsonObject.optString("TeaherResumes"))) {
                    holder.llTeacherResumes.setVisibility(View.GONE);
                }
                //履历
                if (teacherResumes != null && teacherResumes.length() > 0) {
                    teacherResumsList = new ArrayList<>();
                    holder.llTeacherResumes.setVisibility(View.VISIBLE);
                    teacherResumsAdapter = new TeacherRecordListAdapter(context);
                    holder.teacherRecord.setAdapter(teacherResumsAdapter);
                    for (int i = 0; i < teacherResumes.length(); i++) {
                        teacherResumsList.add(teacherResumes.getJSONObject(i));
                    }
                    teacherResumsAdapter.setItems(teacherResumsList);
                    teacherResumsAdapter.notifyDataSetChanged();
                } else {
                    // holder.llTeacherResumes.setVisibility(View.GONE);
                }
                if (articles != null && articles.length() > 0) {
                    holder.llArticle.setVisibility(View.GONE);
                    teacherArticleListAdapter = new TeacherArticleListAdapter(context);
                    holder.myArticle.setAdapter(teacherArticleListAdapter);
                    for (int i = 0; i < articles.length(); i++) {
                        teacherArticleLis.add(articles.getJSONObject(i));
                    }
                    teacherArticleListAdapter.setItems(teacherArticleLis);
                    teacherArticleListAdapter.notifyDataSetChanged();
                } else {
                    holder.llArticle.setVisibility(View.GONE);
                }
                if (teacherImages == null || teacherImages.length() == 0) {
                    holder.llPictures.setVisibility(View.GONE);
                } else {
                    holder.llPictures.setVisibility(View.VISIBLE);
                    int length = teacherImages.length();
                    Log.d("images==", teacherImages.toString());
                    if (length > 0) {
                        mGuides = new ArrayList<>();
                        holder.llPictures.setVisibility(View.VISIBLE);
                        holder.llImages.setVisibility(View.VISIBLE);
                        TeacherAssisImagesNewAdapter imageAdapter = new TeacherAssisImagesNewAdapter(context);
                        holder.myGridView.setAdapter(imageAdapter);
                        holder.myGridView.setOnItemClickListener((adapterView, view, pos, l) -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("teachername", teacherName);
                            bundle.putString("teacherId", teacherId);
                            IntentUtils.startIntentBundle(context, bundle, TeacherPicturesActivity.class);
                        });
                        for (int i = 0; i < length; i++) {
                            mGuides.add(Constants.imageUrl + teacherImages.getJSONObject(i).optString("Id") + "?w=300&h=300");
                        }
                        mGuides.add("more");
                        imageAdapter.setItems(mGuides);
                        imageAdapter.notifyDataSetChanged();
                    } else {
                        holder.llImages.setVisibility(View.GONE);
                    }
                }

            } else {
                //ToastUtils.showToast("数据有误请重试!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAdialogIsWifi(String videoUrl) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("您当前还不是wifi环境，确定要播放吗？").setPositiveButton("确定", (dialog, which) -> {
            JCFullScreenActivity.toActivity(context, videoUrl, JCVideoPlayerStandard.class, "");
            dialog.dismiss();
        }).setNegativeButton("取消", (dialog, which) -> dialog.dismiss()).show();
    }
    @Override
    public int getItemCount() {
        return 1;
    }

    public void setData(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        notifyDataSetChanged();
    }

    class MessgageViewholder extends RecyclerView.ViewHolder {
        @Bind(R.id.image_gridview)
        MyGridView myGridView;


        @Bind(R.id.ll_verify)
        LinearLayout llVerify;

        @Bind(R.id.image_IsNameVerified)
        ImageView imageIsNameVerified;
        @Bind(R.id.image_IsSiteVerified)
        ImageView imageIsSiteVerified;
        @Bind(R.id.image_IsProfessionVerified)
        ImageView imageIsProfessionVerified;

        @Bind(R.id.ll_images)
        LinearLayout llImages;
        @Bind(R.id.self_introduction)
        TextView selfIntroduction;
        @Bind(R.id.teacher_record)
        MyListView teacherRecord;
        @Bind(R.id.my_article)
        ListView myArticle;

        @Bind(R.id.ll_pictures)
        LinearLayout llPictures;
        @Bind(R.id.ll_selfIntroduction)
        LinearLayout llSelfIntroduction;
        @Bind(R.id.ll_teacherResumes)
        LinearLayout llTeacherResumes;
        @Bind(R.id.ll_article)
        LinearLayout llArticle;
        @Bind(R.id.ll_teacher_all_info2)
        LinearLayout llTeacherAllInfo;
        @Bind(R.id.empty_image)
        ImageView emptyImage;
        @Bind(R.id.image_action)
        ImageView imageAction;

        @Bind(R.id.videoplayer)
        JCVideoPlayerStandard videoplayer;
        @Bind(R.id.card_view)
        CardView cardView;
        public MessgageViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
