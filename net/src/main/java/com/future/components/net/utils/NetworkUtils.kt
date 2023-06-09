package com.future.components.net.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 11:52
 */
object NetworkUtils {

    fun isNetworkAvailable(context: Context): Boolean {
        val manager = context.applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val info = manager.activeNetworkInfo
        return null != info && info.isAvailable
    }
}