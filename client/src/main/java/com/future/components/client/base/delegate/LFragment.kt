package com.future.components.client.base.delegate

import android.os.Bundle
import androidx.annotation.Nullable

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/26 10:08
 */
interface LFragment {

    /**
     * 初始化数据
     */
    fun initData(@Nullable saveInstanceState: Bundle?)

    fun createObserver(){
        // noting
    }
}