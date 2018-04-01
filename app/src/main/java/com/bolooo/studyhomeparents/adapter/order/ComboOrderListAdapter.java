package com.bolooo.studyhomeparents.adapter.order;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolooo.studyhomeparents.R;
import com.bolooo.studyhomeparents.activty.adver.ComoOrderDetailActivity;
import com.bolooo.studyhomeparents.base.BaseRecycleViewAdapter;
import com.bolooo.studyhomeparents.base.BaseRecycleViewHolder;
import com.bolooo.studyhomeparents.entity.order.ComboOrderEntity;
import com.bolooo.studyhomeparents.utils.IntentUtils;
import com.bolooo.studyhomeparents.utils.UIUtil;

import butterknife.Bind;

/**
 * =======================================
 * Author :李刘欢
 * DATA : 2017-07-24
 * DES : 课程报名订单
 * =======================================
 */

public class ComboOrderListAdapter extends BaseRecycleViewAdapter<ComboOrderEntity> {


    public ComboOrderListAdapter(Context context) {
        super(context);
    }

    @Override
    public int getConvertViewId(int viewTypeviewType) {
        return R.layout.itme_course_order_layout;
    }

    @Override
    public BaseRecycleViewHolder<ComboOrderEntity> getNewHolder(View view) {
        return new CourseOrderViewHolder(view);
    }

    public class CourseOrderViewHolder extends BaseRecycleViewHolder<ComboOrderEntity> {
        @Bind(R.id.order_number_textview)
        TextView orderNumber;
        @Bind(R.id.teacher_image)
        ImageView teacherImage;
        @Bind(R.id.teacher_name)
        TextView teacherName;
        @Bind(R.id.teacher_course)
        TextView teacherCourse;
        @Bind(R.id.order_status)
        TextView orderStatusTv;
        @Bind(R.id.order_detail)
        TextView orderDetail;
        public CourseOrderViewHolder(View view) {
            super(view);
        }

        @Override
        public void loadData(ComboOrderEntity data, int position) {
            if (data == null) return;
            orderNumber.setText("订单号： "+data.getOrderNumber());
            glideUtils.loadFileImageWH(data.getPackageImg(),teacherImage);
            teacherName.setText("套餐");
            teacherCourse.setText(" "+data.getPackageName()+" ");
            int orderStatus = data.getOrderStatus();
            String str = null;
            String rightStr = null;
            int orderDetailColor = 0;
            int orderDetailResource = 0;
            rightStr = "<font color='#999999'>详情</font>";
            orderDetailColor = UIUtil.getColor(R.color.text_color_99);
            orderDetailResource = R.drawable.new_rectangle_border_99_corner6_transparency;
            switch (orderStatus){
                case 1://待支付
                    str = "状态：<font color='#EE5F5F'>待支付</font>";
                    orderDetailColor = UIUtil.getColor(R.color.setting_reply);
                    orderDetailResource = R.drawable.rectangle_border_orange_corner6_transparency;
                    rightStr = "<font color='#EE5F5F'>支付</font>";
                    break;
//                case 2://已报名
//                    str = "状态：<font color='#EE5F5F'>已报名，等待老师确认</font>";
//                    rightStr = "<font color='#EE5F5F'>详情</font>";
//                    orderDetailColor = UIUtil.getColor(R.color.setting_reply);
//                    orderDetailResource = R.drawable.rectangle_border_orange_corner6_transparency;
//                    break;
//                case 3://支付失败，订单已过期
//                    str = "状态：<font color='#999999'>支付失败，订单已过期</font>";
//                    break;
                case 2://报名成功
                    str = "状态：<font color='#1EADEB'>报名成功</font>";
                    rightStr = "<font color='#1EADEB'>详情</font>";
                    orderDetailColor = UIUtil.getColor(R.color.theme_blue);
                    orderDetailResource = R.drawable.rectangle_border_blue_corner6_transparency;
                    break;
                case 3://已取消
                    str = "状态：<font color='#999999'>已取消</font>";
                    break;
//                case 5://报名失败
//                    str = "状态：<font color='#999999'>报名失败</font>";
//                    break;
//                case 9://家长未上课，已过期
//                    str = "状态：<font color='#999999'>家长未上课，已过期</font>";
//                    break;
//                case 10://超时未审核
//                    str = "状态：<font color='#999999'>超时未审核</font>";
//                    break;
                default:
                    str = "状态：<font color='#999999'>其他</font>";
                    rightStr = "<font color='#999999'>详情</font>";
                    orderDetailColor = UIUtil.getColor(R.color.text_color_99);
                    orderDetailResource = R.drawable.new_rectangle_border_99_corner6_transparency;
            }
            orderStatusTv.setText(Html.fromHtml(str));
            orderDetail.setTextColor(orderDetailColor);
            orderDetail.setBackgroundResource(orderDetailResource);
            orderDetail.setText(Html.fromHtml(rightStr));
            Bundle bundle = new Bundle();
            bundle.putString("orderId",data.getId());
            itemView.setOnClickListener(V-> IntentUtils.startNewIntentBundle(mContext,bundle, ComoOrderDetailActivity.class));
        }
    }
}
