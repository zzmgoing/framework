package com.zzming.core.extension

import android.util.Log
import com.zzming.core.BuildConfig
import com.zzming.core.LibCore

/**
 * 日志扩展
 */
fun Any.logDebug(msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, msg.toString())
    }
}

fun Any.logDebug(tag: String?, msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, msg.toString())
    }
}

fun Any.logError(msg: String?, error: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(javaClass.simpleName, msg.toString(), error)
    }
}

fun Any.logError(tag: String?, msg: String?, error: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg.toString(), error)
    }
}

fun logDebug(msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(LibCore.context?.applicationInfo?.packageName, msg.toString())
    }
}

fun logDebug(tag: String?, msg: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(tag, msg.toString())
    }
}

fun logError(msg: String?, error: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(LibCore.context?.applicationInfo?.packageName, msg.toString(), error)
    }
}

fun logError(tag: String?, msg: String?, error: Throwable? = null) {
    if (BuildConfig.DEBUG) {
        Log.e(tag, msg.toString(), error)
    }
}