package com.bdtd.future.model

/**
 *
 * @Description:    结构不是基本响应格式，所以特殊处理
 * @Author:         future
 * @CreateDate:     2022/6/8 17:35
 */
data class LoginInfoModel(val code: Int,val msg: String,val token: String,val roles: List<String>,val user:UserInfoModel,val pwdTime:Boolean)
