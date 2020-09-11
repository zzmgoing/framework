package com.zzming.core.extension

import android.util.Log
import com.zzming.core.common.LibConfig

/**
 * 日志扩展
 */
fun Any.logDebug(msg: String?) {
    if (LibConfig.isOpenLog) {
        Log.d(javaClass.simpleName, msg.toString())
    }
}

fun Any.logDebug(tag: String?, msg: String?) {
    if (LibConfig.isOpenLog) {
        Log.d(tag, msg.toString())
    }
}

fun Any.logError(msg: String?, error: Throwable? = null) {
    if (LibConfig.isOpenLog) {
        Log.e(javaClass.simpleName, msg.toString(), error)
    }
}

fun Any.logError(tag: String?, msg: String?, error: Throwable? = null) {
    if (LibConfig.isOpenLog) {
        Log.e(tag, msg.toString(), error)
    }
}

fun logDebug(msg: String?) {
    if (LibConfig.isOpenLog) {
        Log.d(LibConfig.packageName, msg.toString())
    }
}

fun logDebug(tag: String?, msg: String?) {
    if (LibConfig.isOpenLog) {
        Log.d(tag, msg.toString())
    }
}

fun logError(msg: String?, error: Throwable? = null) {
    if (LibConfig.isOpenLog) {
        Log.e(LibConfig.packageName, msg.toString(), error)
    }
}

fun logError(tag: String?, msg: String?, error: Throwable? = null) {
    if (LibConfig.isOpenLog) {
        Log.e(tag, msg.toString(), error)
    }
}