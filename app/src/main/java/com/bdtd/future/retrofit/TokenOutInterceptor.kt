package com.bdtd.future.retrofit

import com.future.components.net.model.BaseResponse
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 *
 * @Description:    token过期
 * @Author:         future
 * @CreateDate:     2022/5/26 17:06
 */
class TokenOutInterceptor : Interceptor {

    private val gson: Gson by lazy { Gson() }

    @Throws(Throwable::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            val response = chain.proceed(chain.request())
            if (response.body != null && response.body?.contentType() != null) {
                val mediaType = response.body?.contentType()
                val string = response.body?.string()
                val responseBody = string?.toResponseBody(mediaType)
                val request = chain.request()
                try {
                    val baseModel = gson.fromJson(string, BaseResponse::class.java)
                    //判断逻辑 模拟一下
                    if (baseModel.code == 401) {
                        //如果是普通的activity话 可以直接跳转，如果是navigation中的fragment，可以发送通知跳转
//                        val intent = Intent(appContext, LoginActivity::class.java)
//                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
//                        appContext.startActivity(intent)
//                        EventBus.getDefault().post(MessageEvent("TOKEN_FAILURE",true))
                    }
                    response.newBuilder().body(responseBody).build()
                } catch (e: Throwable) {
                    chain.proceed(request)
                }
            } else {
                response
            }
        } catch (e: Throwable) {
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message("网络出错")
                .body(ResponseBody.create(null, "{${e}}")).build()
        }
    }

}