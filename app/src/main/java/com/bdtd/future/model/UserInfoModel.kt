package com.bdtd.future.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/6/8 17:45
 */
@Parcelize
data class UserInfoModel(val token: String,val userId:Int,val username:String,val user:RealUserModel?) : Parcelable
