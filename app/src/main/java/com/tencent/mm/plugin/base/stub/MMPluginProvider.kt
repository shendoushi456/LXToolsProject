package com.tencent.mm.plugin.base.stub

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor

/* loaded from: classes4.dex */
class MMPluginProvider : ContentProvider() {
    // android.content.ContentProvider
    val tag: String = "MMPluginProvider";
    override fun delete(uri: Uri, str: String?, strArr: Array<String>?): Int {
        return 0
    }

    // android.content.ContentProvider
    override fun getType(uri: Uri): String? {
        return null
    }

    // android.content.ContentProvider
    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        return null
    }

    private fun getLastUpdateTime(context: Context): Long {
        try {
            val packageManager = context.applicationContext.packageManager
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.lastUpdateTime
        } catch (e: Exception) {
        }
        return 0
    }

    // android.content.ContentProvider
    override fun onCreate(): Boolean {

        return true
    }

    // android.content.ContentProvider
    override fun openFile(
        uri: Uri,
        str: String,
        cancellationSignal: CancellationSignal?
    ): ParcelFileDescriptor? {
        return super.openFile(uri, str, cancellationSignal)
    }

    // android.content.ContentProvider
    override fun query(
        uri: Uri,
        strArr: Array<String>?,
        str: String?,
        strArr2: Array<String>?,
        str2: String?
    ): Cursor? {
        return null
    }

    // android.content.ContentProvider
    override fun update(
        uri: Uri,
        contentValues: ContentValues?,
        str: String?,
        strArr: Array<String>?
    ): Int {
        return 0
    }

    companion object {
        fun getContentUri(context: Context): String {
            return "content://" + context.packageName + ".p0"
        }

        fun init(context: Context?) {
            if (context == null) {
                return
            }
            try {
                val query = context.contentResolver.query(
                    Uri.parse(getContentUri(context)),
                    null,
                    null,
                    null,
                    null
                )
                if (query != null) {
                    try {
                        query.close()
                    } catch (unused: Throwable) {
                    }
                }
            } finally {
            }
        }

        fun getContentUri(str: String): String {
            return "content://$str.p0"
        }
    }
}