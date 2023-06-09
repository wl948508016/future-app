package com.bdtd.future.model

object Constants {
    const val numberDigists = "0123456789"

    const val BUG_ID = "7d4648307a"

    const val REQUEST_CODE_ALBUM = 20
    const val REQUEST_CODE_CAMERA = 10

    /**
     * IR预览数据相对于RGB预览数据的横向偏移量，注意：是预览数据，一般的摄像头的预览数据都是 width > height
     */
    const val HORIZONTAL_OFFSET = 0

    /**
     * IR预览数据相对于RGB预览数据的纵向偏移量，注意：是预览数据，一般的摄像头的预览数据都是 width > height
     */
    const val VERTICAL_OFFSET = 0


    const val MAX_DETECT_NUM = 10

    /**
     * 当FR成功，活体未成功时，FR等待活体的时间
     */
    const val WAIT_LIVENESS_INTERVAL = 100

    /**
     * 失败重试间隔时间（ms）
     */
    const val FAIL_RETRY_INTERVAL: Long = 1000

    /**
     * 延迟启动间隔时间（ms）
     */
    const val START_MEASUREMENT_INTERVAL: Long = 1000

    /**
     * 出错重试最大次数
     */
    const val MAX_RETRY_TIME = 3

    /**
     * 注册人脸状态码，准备注册
     */
    const val REGISTER_STATUS_READY = 0

    /**
     * 注册人脸状态码，注册中
     */
    const val REGISTER_STATUS_PROCESSING = 1

    /**
     * 注册人脸状态码，注册结束（无论成功失败）
     */
    const val REGISTER_STATUS_DONE = 2

    const val ANALYZING = 10

    /**
     * 识别阈值
     */
    const val SIMILAR_THRESHOLD = 0.8f

    const val ACTION_REQUEST_PERMISSIONS = 0x001

    const val URL_TAG="URL_TAG"

    const val DATA_TAG="DATA_TAG"

    const val CONTENT_TAG="CONTENT_TAG"

    const val NOTIFICATION_TAG="NOTIFICATION_TAG"

    /**
     * 通知ID
     */
    const val NOTIFICATION_CHANNEL="BD_Health_App"

    const val NOTIFICATION_SOS_ID=9999

    const val NOTIFICATION_ALARM_ID=9099
}