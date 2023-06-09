package com.bdtd.future.utils

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*
import java.util.regex.Pattern

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/1/26 4:48 下午
 */

// 两次点击按钮之间的点击间隔不能少于1000毫秒
private const val MIN_CLICK_DELAY_TIME: Int = 1500
private var lastClickTime: Long = 0
// 两次点击按钮之间的点击间隔不能少于1000毫秒
private const val SAND_BOX_MIN_CLICK_DELAY_TIME: Int = 1000
private var lastSandBoxClickTime: Long = 0

/**
 * 防止快速点击（建议跳转页面都加上）
 *
 * @return
 */
fun isFastClick(): Boolean {
    var flag = false
    val curClickTime = System.currentTimeMillis()
    if (curClickTime - lastClickTime >= MIN_CLICK_DELAY_TIME) {
        flag = true
    }
    lastClickTime = curClickTime
    return flag
}

/**
 * 防止快速点击（建议跳转页面都加上）
 *
 * @return
 */
fun isSandBoxClick(): Boolean {
    var flag = false
    val curClickTime = System.currentTimeMillis()
    if (curClickTime - lastSandBoxClickTime >= SAND_BOX_MIN_CLICK_DELAY_TIME) {
        flag = true
    }
    lastSandBoxClickTime = curClickTime
    return flag
}

fun <T> checkNotNull(reference: T?): T? {
    return reference ?: throw NullPointerException()
}

fun filterTime(start: String, end: String): IntArray? {
    if (TextUtils.isEmpty(start) || TextUtils.isEmpty(end)) {
        return intArrayOf()
    }
    val starts = start.split(":".toRegex()).toTypedArray()
    val ends = end.split(":".toRegex()).toTypedArray()
    return if (starts.size == 1 || ends.size == 1) {
        intArrayOf()
    } else intArrayOf(starts[0].toInt(), ends[0].toInt())
}

/**
 * 获取准确时间 HH:MMM:SS
 *
 * @return
 */
fun getExactDateTime(): String {
    return "yyyy-MM-dd HH:mm:ss".format(Date())
}

fun hideKeyBoard(context: Context, view: View) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0) // 强制隐藏键盘
}

fun matchesInputPassword(password: String): Boolean {
    val pattern =
        "^(?![0-9]+$)(?!a-zA-Z]+$)[0-9A-Za-z\\W]{" + 5 + "," + 20 + "}$"
    return Pattern.matches(pattern, password)
}

/**
 * 根据Uri获取图片的绝对路径
 *
 * @param context 上下文对象
 * @param uri     图片的Uri
 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
 */
fun getRealPathFromUri(context: Context, uri: Uri?): String? {
    val sdkVersion = Build.VERSION.SDK_INT
    return if (sdkVersion >= 19) {
        getRealPathFromUriAboveApi19(context, uri)
    } else {
        getRealPathFromUriBelowAPI19(context, uri)
    }
}

/**
 * 适配api19及以上,根据uri获取图片的绝对路径
 *
 * @param context 上下文对象
 * @param uri     图片的Uri
 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
 */
@SuppressLint("NewApi")
private fun getRealPathFromUriAboveApi19(context: Context, uri: Uri?): String? {
    var filePath: String? = null
    if (DocumentsContract.isDocumentUri(context, uri)) {
        // 如果是document类型的 uri, 则通过document id来进行处理
        val documentId = DocumentsContract.getDocumentId(uri)
        if (isMediaDocument(uri)) {
            // 使用':'分割
            val id = documentId.split(":").toTypedArray()[1]
            val selection = MediaStore.Images.Media._ID + "=?"
            val selectionArgs = arrayOf(id)
            filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs)
        } else if (isDownloadsDocument(uri)) {
            val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(documentId))
            filePath = getDataColumn(context, contentUri, null, null)
        }
    } else if ("content".equals(uri?.scheme, ignoreCase = true)) {
        // 如果是 content 类型的 Uri
        filePath = uri?.let { getDataColumn(context, it, null, null) }
    } else if ("file" == uri?.scheme) {
        // 如果是 file 类型的 Uri,直接获取图片对应的路径
        filePath = uri.path
    }
    return filePath
}

/**
 * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
 *
 * @param context 上下文对象
 * @param uri     图片的Uri
 * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
 */
private fun getRealPathFromUriBelowAPI19(context: Context, uri: Uri?): String? {
    return getDataColumn(context, uri, null, null)
}

/**
 * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
 */
private fun getDataColumn(context: Context, uri: Uri?, selection: String?, selectionArgs: Array<String>?): String? {
    var path: String? = null
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    var cursor: Cursor? = null
    try {
        cursor = uri?.let { context.contentResolver.query(it, projection, selection, selectionArgs, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
            path = cursor.getString(columnIndex)
        }
    } catch (e: Exception) {
        cursor?.close()
    }
    return path
}

/**
 * @param uri the Uri to check
 * @return Whether the Uri authority is MediaProvider
 */
private fun isMediaDocument(uri: Uri?): Boolean {
    return "com.android.providers.media.documents" == uri?.authority
}

/**
 * @param uri the Uri to check
 * @return Whether the Uri authority is DownloadsProvider
 */
private fun isDownloadsDocument(uri: Uri?): Boolean {
    return "com.android.providers.downloads.documents" == uri?.authority
}

inline fun <T1: Any, T2: Any, T3: Any> ifLet(p1: T1?, p2: T2?, p3: T3?, closure: (T1, T2, T3) -> Unit) {
    if (p1 != null && p2 != null && p3 !=null) closure(p1,p2,p3)
}

inline fun <T1: Any, T2: Any> ifLet(p1: T1?, p2: T2?, closure: (T1, T2) -> Unit) {
    if (p1 != null && p2 != null) closure(p1,p2)
}

fun <T1: Any, T2: Any, R: Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2)->R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

// 3 variables
fun <T1: Any, T2: Any, T3: Any, R: Any> safeLet(p1: T1?, p2: T2?, p3: T3?, block: (T1, T2, T3)->R?): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}