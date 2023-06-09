package com.bdtd.future.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/6 10:32
 */
@Parcelize
data class TestModel(val name:String,val age:Int):Parcelable
