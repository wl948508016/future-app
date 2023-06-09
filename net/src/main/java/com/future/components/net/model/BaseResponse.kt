package com.future.components.net.model

/**
 *
 * @Description:    分页就取rows
 * @Author:         future
 * @CreateDate:     2022/5/24 15:49
 */
data class BaseResponse<T>(val msg:String,val code:Int,val data:T,val rows:T){

    fun isSuccess():Boolean = code == 200
    fun isWarn():Boolean = code == 201
}
