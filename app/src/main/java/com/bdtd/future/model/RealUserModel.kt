package com.bdtd.future.model

import android.os.Parcelable
import com.bdtd.bdohs.model.UserPeopleModel
import kotlinx.parcelize.Parcelize

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/6/8 17:51
 */
@Parcelize
data class RealUserModel(
    val userId: Int=0,
    val userName: String="",
    val nickName: String="",
    val email: String?="",
    val phonenumber: String="",
    val people:UserPeopleModel = UserPeopleModel("",""),
    var sex:String="0",
    val avatar:String="",
) : Parcelable
