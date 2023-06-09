package com.future.components.client.ext

import java.lang.reflect.ParameterizedType


/**
 *
 * @Description:    获取当前类绑定的泛型ViewModel-clazz
 * @Author:         future
 * @CreateDate:     2022/4/25 18:11
 */
@Suppress("UNCHECKED_CAST")
fun <VM> getVmClazz(obj: Any): VM {
    val parameterizedType = obj.javaClass.genericSuperclass as ParameterizedType
    return parameterizedType.actualTypeArguments[parameterizedType.actualTypeArguments.size-1] as VM
}







