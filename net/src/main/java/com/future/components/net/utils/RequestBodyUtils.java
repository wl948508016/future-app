package com.future.components.net.utils;

import com.google.gson.Gson;

import java.io.File;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
  *
  * @Description:
  * @Author:         future
  * @CreateDate:     2022/6/8 18:01
 */
public class RequestBodyUtils {

    private RequestBodyUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static RequestBody createRequestBody(Map<String, Object> params) {
        String jsonString = new Gson().toJson(params);
        MediaType mediaType = MediaType.Companion.parse("application/json;charset=utf-8");
        return RequestBody.Companion.create(jsonString, mediaType);
    }

    public static RequestBody createMultipartRequestBody(Map<String, Object> params) {
        // 添加请求类型
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(Objects.requireNonNull(MediaType.parse("multipart/form-data")));

        //  创建请求的请求体
        for (Map.Entry<String,Object> entry : params.entrySet()) {
            // 追加表单信息
            String key = entry.getKey();
            Object object = entry.getValue();
            if (object instanceof File) {
                File file = (File) object;
                builder.addFormDataPart(key, file.getName(), RequestBody.create(file, null));
            } else {
                builder.addFormDataPart(key, object.toString());
            }
        }

        return builder.build();
    }

}
