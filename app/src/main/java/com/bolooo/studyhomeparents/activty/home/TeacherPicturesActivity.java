package com.bolooo.studyhomeparents.activty.home;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.adapter.TeacherPicturesAdapter;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.request.callback.IRequestCallBack;
import com.bolooo.studyhomeparents.request.util.MainUtils;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.DensityUtil;
import com.bolooo.studyhomeparents.views.WaitProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/*
讲师相册展示
 */
public class TeacherPicturesActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.lv_pictures)
    ListView lvPictures;
    @Bind(R.id.teacher_name)
    TextView teacherName;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.progressBar)
    WaitProgressBar progressBar;
    private List<String> mGuides = new ArrayList<>();
    private String teacherId;
    private TeacherPicturesAdapter imageAdapter;

    @Override
    protected void initView() {
        title.setText("相册");
        checkBox.setVisibility(View.GONE);
        back.setOnClickListener(view -> finish());
        imageAdapter = new TeacherPicturesAdapter(this);
        lvPictures.setDivider(new ColorDrawable(Color.WHITE));
        lvPictures.setDividerHeight(DensityUtil.dip2px(this,20));
        lvPictures.setAdapter(imageAdapter);
    }

    @Override
    protected void initDate() {
        Bundle bundle = getIntent().getBundleExtra("bundle");
        if (bundle != null) {
            teacherId = bundle.getString("teacherId");
            teacherName.setText(bundle.getString("teachername") + "老师的作品相册");
        }
    }

    @Override
    protected void prepareData() {
        getTeacherPics();
    }

    private void getTeacherPics() {
        MainUtils.getInstance().getTeacherPics(teacherId, new IRequestCallBack() {
            @Override
            public void onStartLoading() {
                progressBar.show();
            }

            @Override
            public void onSuccess(String result) {
                try {
                    if (progressBar != null) progressBar.hide();
                    JSONArray array = new JSONArray(result);
                    if (array != null && array.length() > 0) {
                        for (int i = 0; i < array.length(); i++) {
                            mGuides.add(Constants.imageUrl + array.getJSONObject(i).optString("Id")+"?w=1000");
                        }
                        imageAdapter.setItems(mGuides);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                if (progressBar != null) progressBar.hide();
            }
        });
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_teacher_pictures;
    }
}
