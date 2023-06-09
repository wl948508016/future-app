package com.future.components.client.base.delegate

import androidx.annotation.NonNull


/**
 *
 * @Description:    根据需要实现
 * @Author:         future
 * @CreateDate:     2022/4/25 16:48
 */
interface LView {

    /**
     * 显示加载
     */
     fun showLoading(){
         // noting
     }

    /**
     * 隐藏加载
     */
    fun hideLoading(){
        // noting
    }

    /**
     * 显示信息
     */
    fun showMessage(message:String =""){
        // noting
    }
}