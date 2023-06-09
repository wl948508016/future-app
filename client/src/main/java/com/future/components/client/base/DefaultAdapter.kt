package com.future.components.client.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.future.components.client.base.BaseHolder.OnViewClickListener

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:      2020/1/7 9:59
 */
abstract class DefaultAdapter<T> (var infoList: MutableList<T>) : RecyclerView.Adapter<BaseHolder<T>>() {
    private var mOnItemClickListener: OnRecyclerViewItemClickListener<T>? = null

    /**
     * 创建 [BaseHolder]
     *
     * @param parent   父容器
     * @param viewType 布局类型
     * @return [BaseHolder]
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder<T> {
        val viewDataBinding = createViewBinding(LayoutInflater.from(parent.context),parent,false,viewType)
        val holder = getHolder(viewDataBinding,parent, viewType)
        //设置Item点击事件
        holder.setOnItemClickListener(object : OnViewClickListener {
            override fun onViewClick(view: View, position: Int) {
                if (mOnItemClickListener != null && infoList.isNotEmpty()) {
                    mOnItemClickListener?.onItemClick(view, viewType, infoList[position], position)
                }
            }
        })
        return holder
    }

    /**
     * 绑定数据
     *
     * @param holder   [BaseHolder]
     * @param position 在 RecyclerView 中的位置
     */
    override fun onBindViewHolder(holder: BaseHolder<T>, position: Int) {
        holder.setData(infoList[position], position)
    }

    /**
     * 返回数据总个数
     *
     * @return 数据总个数
     */
    override fun getItemCount(): Int {
        return infoList.size
    }

    /**
     * 获得 RecyclerView 中某个 position 上的 item 数据
     *
     * @param position 在 RecyclerView 中的位置
     * @return 数据
     */
    fun getItem(position: Int): T {
        return infoList[position]
    }

    /**
     * 让子类实现用以提供 [BaseHolder]
     *
     * @param viewDataBinding 用于展示的 [ViewDataBinding]
     * @param parent   父布局
     * @param viewType 布局类型
     * @return [BaseHolder]
     */
    abstract fun getHolder(viewDataBinding: ViewDataBinding,parent: ViewGroup, viewType: Int): BaseHolder<T>

    /**
     *  让子类实现具体ViewDataBinding
     *
     * @param inflater 布局解析器
     * @param parent   父布局
     * @param attachToRoot 是否附加到根布局
     * @param viewType 布局类型
     * @return [ViewDataBinding]
     */
    abstract fun createViewBinding(inflater: LayoutInflater,parent: ViewGroup,attachToRoot: Boolean, viewType: Int):ViewDataBinding

    /**
     * item 点击事件
     *
     * @param <T>
    </T> */
    interface OnRecyclerViewItemClickListener<T> {
        /**
         * item 被点击
         *
         * @param view     被点击的 [View]
         * @param viewType 布局类型
         * @param data     数据
         * @param position 在 RecyclerView 中的位置
         */
        fun onItemClick(view: View, viewType: Int, data: T, position: Int)
    }

    /**
     * 设置 item 点击事件
     *
     * @param listener
     */
    fun setOnItemClickListener(listener: OnRecyclerViewItemClickListener<T>) {
        mOnItemClickListener = listener
    }

    companion object {
        /**
         * 遍历所有 [BaseHolder], 释放他们需要释放的资源
         *
         * @param recyclerView [RecyclerView]
         */
        fun releaseAllHolder(recyclerView: RecyclerView?) {
            if (recyclerView == null) return
            for (i in recyclerView.childCount - 1 downTo 0) {
                val view = recyclerView.getChildAt(i)
                val viewHolder = recyclerView.getChildViewHolder(view)
                if (viewHolder is BaseHolder<*>) {
                    viewHolder.onRelease()
                }
            }
        }
    }
}