package com.bolooo.studyhomeparents.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.MainActivity;
import com.bolooo.studyhomeparents.entity.home.ConfigEntity;
import com.bolooo.studyhomeparents.utils.Constants;
import com.bolooo.studyhomeparents.utils.GlideUtils;
import com.bolooo.studyhomeparents.utils.HomeJumpManager;

import java.util.List;

/**
 * 首页大模板
 * nanfeifei
 * 2016/11/28 13:40
 *
 * @version 3.7.0
 */
public class HomeModelView extends LinearLayout {
    private DisplayMetrics dm = new DisplayMetrics();
    public GlideUtils glideUtils;
    HomeJumpManager homeJumpManager;

    public HomeModelView(Context context) {
        super(context);
    }

    public HomeModelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public void setHomeModelData(Activity context, List<ConfigEntity.DataEntity> homeModel) {
        this.setOrientation(LinearLayout.VERTICAL);
        this.removeAllViews();
        ((MainActivity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        glideUtils = new GlideUtils(context);
        homeJumpManager = new HomeJumpManager(context);
        if (homeModel == null) {
            return;
        }

        int rowSize = homeModel.size();
        for (int i = 0; i < rowSize; i++) {
            ConfigEntity.DataEntity dataEntity = homeModel.get(i);
            String ratio = dataEntity.getRatio();
            LinearLayout childLay = new LinearLayout(context);
            LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, (int) (Constants.screenWidth * Double.valueOf(ratio)));
            childLay.setOrientation(LinearLayout.HORIZONTAL);
            childLay.setLayoutParams(params);

            List<ConfigEntity.DataEntity.BlocksEntity> blocks = dataEntity.getBlocks();
            for (int j = 0; j < blocks.size(); j++) {
                ConfigEntity.DataEntity.BlocksEntity blocksEntity = blocks.get(j);
                String blockRatio = blocksEntity.getBlockRatio();
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                LayoutParams imgParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
                imgParams.width = (int) (Constants.screenWidth * Double.valueOf(blockRatio));
                imgParams.height = (int) (Constants.screenWidth * Double.valueOf(ratio));
                imageView.setLayoutParams(imgParams);
                childLay.addView(imageView);
                if (blocksEntity.getBlockBgUrl() != null) {
                    glideUtils.loadRoundImage(blocksEntity.getBlockBgUrl(), imageView, R.drawable.noimage, 0);
                }
                imageView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homeJumpManager.jump(blocksEntity);
                    }
                });
            }
            this.addView(childLay);
        }
    }
}
