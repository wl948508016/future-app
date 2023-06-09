package com.bdtd.future

import android.app.Application
import com.bdtd.future.model.Constants.BUG_ID
import com.bdtd.future.retrofit.TokenOutInterceptor
import com.future.components.client.utils.LogUtils
import com.future.components.net.NetworkHelper
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.mmkv.MMKV

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/6/8 11:55
 */
class GasApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
//        initBugly()
        //自定义过滤器
        NetworkHelper.INSTANCE.getHttpClientBuilder().addInterceptor(TokenOutInterceptor())
        LogUtils.getConfig().isLogSwitch = BuildConfig.DEBUG
    }

    private fun initBugly() {
        CrashReport.initCrashReport(applicationContext, BUG_ID, false)
    }

}