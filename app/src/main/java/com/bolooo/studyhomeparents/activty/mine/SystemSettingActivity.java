package com.bolooo.studyhomeparents.activty.mine;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.home.HtmlActivity;
import com.bolooo.studyhomeparents.base.BaseActivity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.ImageCatchUtil;
import com.bolooo.studyhomeparents.utils.ToastUtils;
import com.bolooo.studyhomeparents.utils.db.RealmHelper;

import butterknife.Bind;

public class SystemSettingActivity extends BaseActivity {

    @Bind(R.id.title)
    TextView title;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.aboutUs)
    TextView aboutUs;
    @Bind(R.id.useHelp)
    TextView useHelp;
    @Bind(R.id.cache_size_tv)
    TextView cacheSizeTv;
    @Bind(R.id.clear_cache_ll)
    LinearLayout clearCacheLl;
    @Bind(R.id.back)
    ImageView back;
    private ImageCatchUtil imageCatchUtil;

    @Override
    protected void initView() {
        imageCatchUtil = new ImageCatchUtil();
        String cacheSize = imageCatchUtil.getCacheSize();
        cacheSizeTv.setText(cacheSize);
        title.setText("系统设置");
        checkBox.setVisibility(View.GONE);
        back.setOnClickListener(v->finish());
        aboutUs.setOnClickListener(v -> HtmlActivity.openHtmlActivity(this, Constants.aboutUs, "关于我们"));
        clearCacheLl.setOnClickListener(v -> {
                    imageCatchUtil.clearImageAllCache();
                    RealmHelper.getInstance().deleteAllRecord();
                    RealmHelper.getInstance().deleteAllHotCityRecord();
                    cacheSizeTv.setText("0.0KB");
                    ToastUtils.showToast("清除成功");
                }
        );
        useHelp.setOnClickListener(v -> HtmlActivity.openHtmlActivity(this, Constants.useHelp, "使用帮助"));
    }

    @Override
    public int initLoadResId() {
        return R.layout.activity_system_setting;
    }

}
