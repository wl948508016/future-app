package com.future.components.client.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2020/1/7 9:59
 */
abstract class BaseHolder<T>(val view: View) :
    RecyclerView.ViewHolder(view), View.OnClickListener {
    private var mOnViewClickListener: OnViewClickListener? = null

    /**
     * 设置数据
     *
     * @param data     数据
     * @param position 在 RecyclerView 中的位置
     */
    abstract fun setData(data: T, position: Int)

    /**
     * 在 Activity 的 onDestroy 中使用 [DefaultAdapter.releaseAllHolder] 方法 (super.onDestroy() 之前)
     * [BaseHolder.onRelease] 才会被调用, 可以在此方法中释放一些资源
     */
    open fun onRelease() {
        // noting
    }

    override fun onClick(view: View) {
        mOnViewClickListener?.onViewClick(view, this.layoutPosition)
    }

    /**
     * item 点击事件
     */
    interface OnViewClickListener {
        /**
         * item 被点击
         *
         * @param view     被点击的 [View]
         * @param position 在 RecyclerView 中的位置
         */
        fun onViewClick(view: View, position: Int)
    }

    fun setOnItemClickListener(listener: OnViewClickListener) {
        mOnViewClickListener = listener
    }

    init {
        //点击事件
        itemView.setOnClickListener(this)
        //        //屏幕适配
//        if (ThirdViewUtil.isUseAutolayout()) AutoUtils.autoSize(itemView);
//        //绑定 ButterKnife
//        ThirdViewUtil.bindTarget(this, itemView);
    }
}