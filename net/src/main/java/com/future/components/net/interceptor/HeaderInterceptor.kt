package com.future.components.net.interceptor

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 15:02
 */
class HeaderInterceptor: Interceptor {

    private val TIMEOUT_CONNECT = 5 //5秒

    @Throws(Throwable::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            //获取retrofit @headers里面的参数，参数可以自己定义，在本例我自己定义的是cache，跟@headers里面对应就可以了
            var cache = chain.request().header("cache")
            var originalResponse = chain.proceed(chain.request())
            val cacheControl = originalResponse.header("Cache-Control")
            //如果cacheControl为空，就让他TIMEOUT_CONNECT秒的缓存，本例是5秒，方便观察。注意这里的cacheControl是服务器返回的
            if (cacheControl == null) {
                //如果cache没值，缓存时间为TIMEOUT_CONNECT，有的话就为cache的值
                if (cache == null || "" == cache) {
                    cache = TIMEOUT_CONNECT.toString() + ""
                }
                //  LoginUserEntity lastUserInfo = DataBaseManager.getLastUserInfo();
                val token = ""
                //  if (lastUserInfo != null) {
                //      token = lastUserInfo.getToken();
                //  }
                originalResponse = originalResponse.newBuilder()
                    .header("Cache-Control", "public, max-age=$cache")
                    .header("access_token", token)
                    .build()
            }
            return originalResponse
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