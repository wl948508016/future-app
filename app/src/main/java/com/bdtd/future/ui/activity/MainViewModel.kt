package com.bdtd.future.ui.activity

import com.future.components.net.NetworkHelper
import com.future.components.net.ext.request
import com.bdtd.future.retrofit.RetrofitService
import com.future.components.client.utils.LogUtils
import com.future.components.client.viewmodel.BaseViewModel
import com.google.gson.Gson

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/4/25 18:08
 */
class MainViewModel:BaseViewModel() {

    fun getUserTipsData(){
       val apiService = NetworkHelper.INSTANCE.getApi(RetrofitService::class.java, RetrofitService.SERVER_URL)
        apiService?.apply {
            request({ userTipsData()},{
                LogUtils.e("response: ${Gson().toJson(it)}")
            },{
                LogUtils.e("error: ${it.error}")
            })
        }

    }
}