package com.bolooo.studyhomeparents.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.mine.UntiketRecordDetailActivity;
import com.bolooo.studyhomeparents.base.BaseViewHolder;
import com.bolooo.studyhomeparents.base.NBaseAdapter;
import com.bolooo.studyhomeparents.entity.UTicketRecordEntity;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.TimeUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import butterknife.Bind;

/**
 * 游票购买记录Adapter
 * nanfeifei
 * 2017/3/3 15:39
 *
 * @version 3.7.0
 */
public class UTicketRecordAdapter extends NBaseAdapter<UTicketRecordEntity> {




    public UTicketRecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int position) {
        return R.layout.item_untiket_record_layout;
    }

    @Override
    public BaseViewHolder<UTicketRecordEntity> getNewHolder(View view) {
        return new ViewHolder(view);
    }

    public class ViewHolder extends BaseViewHolder<UTicketRecordEntity> {
        @Bind(R.id.untiket_title)
        TextView untiketTitle;
        @Bind(R.id.untiket_date)
        TextView untiketDate;
        @Bind(R.id.untiket_number)
        TextView untiketNumber;
        @Bind(R.id.rl_item)
        RelativeLayout rlItem;
        public ViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(UTicketRecordEntity data, int position) {
            if (data == null) {
                return;
            }
            untiketDate.setText(TimeUtils.getEndMD(data.CreateTime));
            untiketNumber.setTextColor(UIUtil.getColor(R.color.theme_blue));
            int uTicketCount = data.UTicketCount;
            int recordType = data.RecordType;
            String recordTitle = "";
            String uTicketText = "+ "+uTicketCount;
            switch (recordType) {
                case 1:
                    recordTitle = context.getString(R.string.sign_up_course);
                    untiketNumber.setTextColor(UIUtil.getColor(R.color.vip_color));
                    uTicketText = "- "+uTicketCount;
                    break;
                case 2:
                    recordTitle = context.getString(R.string.untiket_recharge);
                    break;
                case 3:
                    recordTitle = context.getString(R.string.untiket_tixian);
                    break;
                case 4:
                    recordTitle = context.getString(R.string.teacher_get);
                    break;
                case 5:
                    recordTitle = context.getString(R.string.sign_up_course_fail);
                    break;
                case 6:
                    recordTitle = context.getString(R.string.system_add);
                    break;
                case 7:
                    recordTitle = context.getString(R.string.credits_exchange);
                    break;
                case 8:
                    recordTitle = context.getString(R.string.buy_combo);
                    untiketNumber.setTextColor(UIUtil.getColor(R.color.vip_color));
                    uTicketText = "- "+uTicketCount;
                    break;
                case 9:
                    recordTitle = context.getString(R.string.cancel_combo_order);
                    break;
                case 10:
                    recordTitle = context.getString(R.string.cancel_order);
                    break;
            }
            untiketTitle.setText(recordTitle);
            untiketNumber.setText(uTicketText);
            rlItem.setOnClickListener(V->{
                Bundle bundle = new Bundle();
                bundle.putString("rId",data.Id);
                IntentUtils.startNewIntentBundle(context,bundle, UntiketRecordDetailActivity.class);
            });
        }
    }

}
