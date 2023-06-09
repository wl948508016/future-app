package com.future.components.net.interceptor

import com.future.components.client.appContext
import com.future.components.net.utils.NetworkUtils
import okhttp3.*


/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 11:52
 */
class CacheInterceptor(var day: Int = 7) : Interceptor {

    @Throws(Throwable::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            var request = chain.request()
            if (!NetworkUtils.isNetworkAvailable(appContext)) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (!NetworkUtils.isNetworkAvailable(appContext)) {
                val maxAge = 60 * 60
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * day // tolerate 4-weeks stale
                response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            }
            return response
        }catch (e: Throwable) {
            Response.Builder()
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .code(999)
                .message("网络出错")
                .body(ResponseBody.create(null, "{${e}}")).build()
        }

    }
}