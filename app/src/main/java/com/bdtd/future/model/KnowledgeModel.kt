package com.bdtd.future.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2022/5/24 15:54
 */
@Parcelize
data class KnowledgeModel(
    val baseOdId: Int?,
    val content: String?,
    val createBy: String?,
    val createTime: String?,
    val delFlag: String?,
    val deptId: String?,
    val id: Int?,
    val name: String?,
    val remark: String?,
    val searchValue: String?,
    val self: Int?,
    val seqNum: Int?,
    val updateBy: String?,
    val updateTime: String?,
    val userId: String?
) : Parcelable