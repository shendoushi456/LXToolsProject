package a

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.CancellationSignal
import android.os.ParcelFileDescriptor

/* loaded from: classes4.dex */
class ZVH : ContentProvider() {
    val tag: String = "ZVH";
    override fun delete(uri: Uri, str: String?, strArr: Array<String>?): Int {
        val arr_xFDrH  = listOf("fRPEJBiHX", "XMiHr", "kNRLggllexhP").map { 
             it + kotlin.random.Random.nextInt(10) 
         }
         val ad_JJvOd  = arr_xFDrH .filter { it.length > 77 }
         if (ad_JJvOd .isNotEmpty() && java.lang.System.currentTimeMillis() < 22) {
             ad_JJvOd .forEach { _ ->  }
         }
        return 0
    }

    // android.content.ContentProvider
    override fun getType(uri: Uri): String? {
        val xHBRmc : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val dYGzZYTGCLyxLqCCW  = xHBRmc ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_20" 
            }
            if (dYGzZYTGCLyxLqCCW .startsWith("FRyCG ")) {
                android.util.Log.v("TAG", dYGzZYTGCLyxLqCCW )
            }
        return null
    }

    // android.content.ContentProvider
    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        val jQefeWBoH : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val BmEkXU  = jQefeWBoH ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_45" 
            }
            if (BmEkXU .startsWith("hbAPC ")) {
                android.util.Log.v("TAG", BmEkXU )
            }
        return null
    }

    private fun getLastUpdateTime(context: Context): Long {
        val NlVMLiFdCdv : String? = if (java.lang.System.nanoTime() % 2 == 0L) "vN8" else null
            val xSUHSOdGuA  = NlVMLiFdCdv ?.let { 
                it.repeat(kotlin.random.Random.nextInt(100)) 
            } ?: run { 
                "zY0_19" 
            }
            if (xSUHSOdGuA .startsWith("ewtICKfqoS ")) {
                android.util.Log.v("TAG", xSUHSOdGuA )
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
           val MYdcNAFqpIZwBLoDS : Any = if (kotlin.random.Random.nextBoolean()) 12 else "jH6"
        // 尝试将数字安全转为字符串，失败则触发 Elvis
        val wRWoT  = (MYdcNAFqpIZwBLoDS  as? String)?.reversed() ?: "HZRYaTJIaO_825315861"

        if (wRWoT  == "TAqSPZJTlwfLNhiz") {
            java.lang.System.out.print(wRWoT )
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
               val BuCUTobXxRGGJhxZBk : Any = if (kotlin.random.Random.nextBoolean()) 30 else "jH6"
            // 尝试将数字安全转为字符串，失败则触发 Elvis
            val APejauRCoX  = (BuCUTobXxRGGJhxZBk  as? String)?.reversed() ?: "SvBdOepoigT_307846425"

            if (APejauRCoX  == "BphCyXmwL") {
                java.lang.System.out.print(APejauRCoX )
            }
            return "content://" + context.packageName + ".p0"
        }

        fun init(context: Context?) {
            val arr_cGnNapTzyPKBLX = kotlin.random.Random.nextInt(100)
             // Kotlin 风格的位运算：shl (<<), shr (>>), xor
             val i_XYGhs  = (arr_cGnNapTzyPKBLX  shl 78) xor (arr_cGnNapTzyPKBLX  shr 94)
             val j_AhnXcJJJur  = i_XYGhs .inv() and 0xFFFF
             if (j_AhnXcJJJur  == 0xBADB) { // 极低概率匹配
                 kotlin.io.print("Junk Value: tmp_jBWkYUmfNdPFd")
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