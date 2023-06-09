package com.bdtd.future.retrofit

import com.bdtd.future.model.KnowledgeModel
import com.bdtd.future.model.LoginInfoModel
import com.future.components.net.model.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.*

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2022/5/24 15:38
 */
interface RetrofitService {
    /**
     * @Headers({"Content-type:application/json;charset=UTF-8"})
     * 1. JSON  格式 请求体
     * RequestBody body = RequestBody.create(JSON, "json格式的字符串");
     *
     * 2.  文件上传
     * RequestBody requestBody = new MultipartBody.Builder()
     * .setType(MultipartBody.FORM)
     * .addFormDataPart("file", file.getName(), RequestBody.create(PNG, file))
     * .build();
     * 3. 表单
     * FormBody body = new FormBody.Builder()
     * .add("limit", String.valueOf(LIMIT))
     * .add("page", String.valueOf(pageValue))
     * .build();
     */
    companion object {
         var SERVER_URL = "http://1.1.1.1:3080"
    }

    /**
     * 密码登录
     */
    @Headers("Content_Type:application/json", "charset:UTF-8")
    @POST("/login")
    suspend fun login(@Body body: RequestBody): LoginInfoModel

    @Headers("Content_Type:application/json", "charset:UTF-8")
    @GET("/public/getOdInfolist")
    suspend fun userTipsData(): BaseResponse<MutableList<KnowledgeModel>>

}