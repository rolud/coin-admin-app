package com.flockware.coinadmin.utils

import android.util.Log

object LoggerCompat {

    private const val TAG = "CoinAdminApp"

    fun log    (obj: Any, title: String = "") { Log.d(TAG, "$title : $obj") }
    fun verbose(obj: Any, title: String = "") { Log.v(TAG, "$title : $obj") }
    fun info   (obj: Any, title: String = "") { Log.i(TAG, "$title : $obj") }
    fun warning(obj: Any, title: String = "") { Log.w(TAG, "$title : $obj") }
    fun error  (obj: Any, title: String = "") { Log.e(TAG, "$title : $obj") }
}