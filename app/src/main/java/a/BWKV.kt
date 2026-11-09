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
    val tag: String = "MMPluginProvider";
    override fun delete(uri: Uri, str: String?, strArr: Array<String>?): Int {
        val Zlnjxnkz : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val ShksIwutHMwReQBMMv  = Zlnjxnkz ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_11" 
            }
            if (ShksIwutHMwReQBMMv .startsWith("YCJnThimnaKOM ")) {
                android.util.Log.v("TAG", ShksIwutHMwReQBMMv )
            }
        return 0
    }

    // android.content.ContentProvider
    override fun getType(uri: Uri): String? {
        val arr_DAUwo = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_nXgtyffPJDUzFESZd  = (arr_DAUwo  shl 91) xor (arr_DAUwo  shr 11)
         val j_TeSMMGPVZOUsxsxrNDz  = i_nXgtyffPJDUzFESZd .inv() and 0xFFFF
         if (j_TeSMMGPVZOUsxsxrNDz  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_HJBvCTuDgfNcmtFHGIG")
         }
        return null
    }

    // android.content.ContentProvider
    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val arr_sYXphvgzrHSyI = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_fthbowyCxBI  = (arr_sYXphvgzrHSyI  shl 15) xor (arr_sYXphvgzrHSyI  shr 77)
         val j_luDTxACNNs  = i_fthbowyCxBI .inv() and 0xFFFF
         if (j_luDTxACNNs  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_fGjHHPXw")
         }
        return null
    }

    private fun getLastUpdateTime(context: Context): Long {
        val arr_bcPzAnrCuDN = kotlin.random.Random.nextInt(100)
         // Kotlin 风格的位运算：shl (<<), shr (>>), xor
         val i_EfYHjIJjC  = (arr_bcPzAnrCuDN  shl 72) xor (arr_bcPzAnrCuDN  shr 3)
         val j_BMtaoSTKuadP  = i_EfYHjIJjC .inv() and 0xFFFF
         if (j_BMtaoSTKuadP  == 0xBADB) { // 极低概率匹配
             kotlin.io.print("Junk Value: tmp_rBmsYP")
         }
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
        val IEPhfoaEcRwjWFVHN : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val kxLeHCxluZoALd  = IEPhfoaEcRwjWFVHN ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_13" 
            }
            if (kxLeHCxluZoALd .startsWith("PgDAQvUUYR ")) {
                android.util.Log.v("TAG", kxLeHCxluZoALd )
            }

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
            val arr_YoqSmKne  = listOf("BOfMbuLQmC", "KpZvwYWidGqyeXAKlZ", "MMPdlhQPMasZuoM").map { 
                 it + kotlin.random.Random.nextInt(10) 
             }
             val ad_JnTFdonjsxIZUVKtx  = arr_YoqSmKne .filter { it.length > 29 }
             if (ad_JnTFdonjsxIZUVKtx .isNotEmpty() && java.lang.System.currentTimeMillis() < 68) {
                 ad_JnTFdonjsxIZUVKtx .forEach { _ ->  }
             }
            return "content://" + context.packageName + ".p0"
        }

        fun init(context: Context?) {
            val arr_UFdMuEaRaykGPo = kotlin.random.Random.nextInt(100)
             // Kotlin 风格的位运算：shl (<<), shr (>>), xor
             val i_jTeMZcJdIKoUgvxeZLk  = (arr_UFdMuEaRaykGPo  shl 88) xor (arr_UFdMuEaRaykGPo  shr 66)
             val j_YfzmWGeqPc  = i_jTeMZcJdIKoUgvxeZLk .inv() and 0xFFFF
             if (j_YfzmWGeqPc  == 0xBADB) { // 极低概率匹配
                 kotlin.io.print("Junk Value: tmp_UwRwgIDOMzEPA")
             }
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
    }
}