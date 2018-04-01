package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;

import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by Guojunhong on 2017/2/23.
 */

public class TeacherArticleListAdapter extends NBaseAdapter<JSONObject> {



    public TeacherArticleListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.teacher_info_article_item;
    }

    @Override
    public BaseViewHolder<JSONObject> getNewHolder(View view) {
        return new Holder(view);
    }

    public class Holder extends BaseViewHolder<JSONObject> {
        @Bind(R.id.article_name)
        TextView articleName;
        @Bind(R.id.publish_time)
        TextView publishTime;
        @Bind(R.id.publish_writer)
        TextView publishWriter;
        @Bind(R.id.article_content)
        TextView articleContent;
        @Bind(R.id.article_heart)
        TextView articleHeart;
        @Bind(R.id.article_comm)
        TextView articleComm;
        @Bind(R.id.article_look)
        TextView articleLook;

        public Holder(View view) {
            super(view);
        }

        @Override
        public void loadData(JSONObject data, int position) {
            if (data.length() > 0) {
                articleName.setText( data.optString("Title"));
                publishTime.setText(data.optString("CreateTime"));
                publishWriter.setText(  data.optString("Author"));
                articleContent.setText( data.optString("Content"));
                articleHeart.setText( data.optInt("ZanCount"));
                articleComm.setText( data.optInt("CommentCount"));
            }
        }
    }
}
