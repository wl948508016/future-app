package com.future.components.client

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/5/24 16:12
 */
val appContext: Application by lazy { ClientProvider.app }

class ClientProvider: ContentProvider() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate(): Boolean {
        val application = context?.applicationContext as Application
        init(application)
        return true
    }

    private fun init(application: Application){
        app = application
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? =null

    override fun getType(uri: Uri): String? =null

    override fun insert(uri: Uri, values: ContentValues?): Uri? =null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int =0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int =0
}