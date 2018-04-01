package com.bolooo.studyhomeparents.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import butterknife.ButterKnife;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * ViewHolder基类
 */
public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder {
    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    /**
     * 装载数据
     * <功能详细描述>
     * @param data
     * @see [类、类#方法、类#成员]
     */
    public abstract void loadData(T data,int position);
}
