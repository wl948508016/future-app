package com.bdtd.bdohs.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/7/8 15:11
 */
@Parcelize
data class UserPeopleModel(val age:String,val cardNum:String?) : Parcelable
