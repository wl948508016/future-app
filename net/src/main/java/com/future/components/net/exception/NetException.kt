package com.future.components.net.exception

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2022/5/24 17:14
 */
data class NetException(val error: String?="请求失败，请稍后再试", val throwable: Throwable? = null)