package com.future.components.net

import android.util.Log
import android.widget.Toast
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.future.components.client.appContext
import com.future.components.client.utils.LogUtils
import com.future.components.net.interceptor.CacheInterceptor
import com.future.components.net.interceptor.HeaderInterceptor
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 11:38
 */
class NetworkHelper {

    companion object {
        val INSTANCE: NetworkHelper by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            NetworkHelper()
        }
    }

    fun <T> getApi(serviceClass: Class<T>, baseUrl: String): T? {
        var retrofitBuilder:Retrofit.Builder? = null
        try {
            retrofitBuilder = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
        }catch (t:Throwable){
            LogUtils.e("Throwable: $t")
            Toast.makeText(appContext,"Expected URL scheme 'http' or 'https' but no colon was found",Toast.LENGTH_LONG).show()
        }
        return if(retrofitBuilder==null) null else setRetrofitBuilder(retrofitBuilder).build().create(serviceClass)
    }

    private fun setHttpClientBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder{
        builder.apply {
            //设置缓存配置 缓存最大10M
            cache(Cache(File(appContext.cacheDir, "future_cache"), 10 * 1024 * 1024))
            //添加Cookies自动持久化
            cookieJar(cookieJar)
            //示例：添加公共heads 注意要设置在日志拦截器之前，不然Log中会不显示head信息
            addInterceptor(HeaderInterceptor())
            //添加缓存拦截器 可传入缓存天数，不传默认7天
            addInterceptor(CacheInterceptor())
            // 日志拦截器
            addInterceptor(HttpLoggingInterceptor { message -> if(BuildConfig.DEBUG) Log.d("————Retrofit————: ", "————网络访问————OkHttp: $message") }.setLevel(HttpLoggingInterceptor.Level.BODY))
            //超时时间 连接、读、写
            connectTimeout(10, TimeUnit.SECONDS)
            readTimeout(5, TimeUnit.SECONDS)
            writeTimeout(5, TimeUnit.SECONDS)
        }
        return builder
    }

    private fun setRetrofitBuilder(builder: Retrofit.Builder): Retrofit.Builder{
        return builder.apply {
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        }
    }

    fun getHttpClientBuilder():OkHttpClient.Builder{
        if(okHttpBuilder==null){
            okHttpBuilder = setHttpClientBuilder(OkHttpClient.Builder())
        }
        return okHttpBuilder as OkHttpClient.Builder
    }

    private var okHttpBuilder:OkHttpClient.Builder? = null

    /**
     * 配置http
     */
    private val okHttpClient: OkHttpClient
        get() {
            return getHttpClientBuilder().build()
        }

    private val cookieJar: PersistentCookieJar by lazy {
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(appContext))
    }
}