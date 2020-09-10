package com.zzming.core.extension

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zzming.core.LibCore
import com.zzming.core.collector.LoadingCollector


/**
 *  Activity tag
 */
val Activity.A_TAG: String
    get() {
        return javaClass.name
    }

/**
 * Fragment tag
 */
val Fragment.F_TAG: String
    get() {
        return javaClass.name
    }

/**
 * simpleName
 */
val Any.SIMPLE_NAME_TAG: String
    get() {
        return javaClass.simpleName
    }

/**
 * 在主线程上执行操作
 */
fun runOnMainThread(runnable: () -> Unit) {
    LibCore.handler.post(runnable)
}

/**
 * 显示土司
 */
fun Any.showToast(msg: String) {
    if ("main" == Thread.currentThread().name) {
        Toast.makeText(LibCore.context, msg, Toast.LENGTH_SHORT).show()
    } else {
        runOnMainThread {
            Toast.makeText(LibCore.context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}

/**
 * 跟据key获取value
 */
fun String.localized(context: Context? = LibCore.context): String {
    val id = context?.resources?.getIdentifier(this, "string", context.packageName) ?: 0
    if (id == 0) {
        return this
    }
    return context?.getString(id) ?: this
}

/**
 * 跟据key获取value
 */
fun Int.localized(context: Context? = LibCore.context): String {
    return context?.getString(this) ?: ""
}

/**
 * Activity是否存活
 */
fun Activity?.isAlive(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
        this != null && !isFinishing && !isDestroyed
    } else {
        this != null && !isFinishing
    }
}

/**
 * showLoading
 */
fun Activity.showLoading() {
    LoadingCollector.showLoading(this)
}

/**
 * hideLoading
 */
fun Activity.hideLoading() {
    LoadingCollector.hideLoading(this)
}

/**
 * showLoading
 */
fun Fragment.showLoading() {
    LoadingCollector.showLoading(activity)
}

/**
 * hideLoading
 */
fun Fragment.hideLoading() {
    LoadingCollector.hideLoading(activity)
}