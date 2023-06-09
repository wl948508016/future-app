package com.future.components.net.ext

import androidx.lifecycle.viewModelScope
import com.future.components.net.exception.NetException
import com.future.components.net.model.BaseResponse
import com.future.components.client.utils.LogUtils
import com.future.components.client.viewmodel.BaseViewModel
import kotlinx.coroutines.*

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 16:49
 */

/**
 * 过滤服务器结果，失败抛异常
 * @param block 请求体方法，必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不传
 * @param warn 警告回调 可不给，用于区分特殊响应请求
 */
fun <T> BaseViewModel.request(
    block: suspend () -> BaseResponse<T>,
    success:(T) -> Unit,
    error: (NetException) -> Unit = {},
    warn: (T) -> Unit = {},
): Job {
    //如果需要弹窗 通知Activity/fragment弹窗
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            runCatching {
                //校验请求结果码是否正确，不正确会抛出异常走下面的onFailure
                executeResponse(it,{ t -> success(t) },{ t -> warn(t) })
            }.onFailure { e ->
                //打印错误消息
                LogUtils.e(e.message)
                //打印错误栈信息
                e.printStackTrace()
                //失败回调
                error(NetException(it.msg,e))
            }
        }.onFailure {
            //网络请求异常 关闭弹窗
            //打印错误消息
            LogUtils.e(it.message)
            //打印错误栈信息
            it.printStackTrace()
            //失败回调
            error(NetException(it.message,it))
        }
    }
}

/**
 *  不过滤请求结果
 * @param block 请求体 必须要用suspend关键字修饰
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseViewModel.requestNoCheck(
    block: suspend () -> T,
    success: (T) -> Unit,
    error: (NetException) -> Unit = {},
): Job {
    return viewModelScope.launch {
        runCatching {
            //请求体
            block()
        }.onSuccess {
            //成功回调
            success(it)
        }.onFailure {
            //打印错误消息
            LogUtils.e(it.message)
            //打印错误栈信息
            it.printStackTrace()
            //失败回调
            error(NetException(it.message,it))
        }
    }
}

/**
 * 请求结果过滤，判断请求服务器请求结果是否成功，不成功则会抛出异常
 */
suspend fun <T> executeResponse(
    response: BaseResponse<T>,
    success: suspend CoroutineScope.(T) -> Unit,
    warn: suspend CoroutineScope.(T) -> Unit
) {
    coroutineScope {
        when {
            response.isSuccess() -> {
                success(response.data)
            }
            response.isWarn()->{
                warn(response.data)
            }
            else -> {
                error(NetException(response.msg))
            }
        }
    }
}

/**
 *  调用携程
 * @param block 操作耗时操作任务
 * @param success 成功回调
 * @param error 失败回调 可不给
 */
fun <T> BaseViewModel.launch(
    block: () -> T,
    success: (T) -> Unit,
    error: (Throwable) -> Unit = {}
) {
    viewModelScope.launch {
        kotlin.runCatching {
            withContext(Dispatchers.IO) {
                block()
            }
        }.onSuccess {
            success(it)
        }.onFailure {
            error(it)
        }
    }
}
