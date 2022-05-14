package com.zzming.core.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Looper
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.LibCore
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.utils.BuildUtils
import com.zzming.core.utils.ViewUtils


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
//    GlobalScope.launch(Dispatchers.Main) {
//        runnable.invoke()
//    }
}

/**
 * 显示土司
 */
fun Any.showToast(msg: String?) {
    msg?.apply {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            Toast.makeText(LibCore.context, msg, Toast.LENGTH_SHORT).show()
        } else {
            runOnMainThread {
                Toast.makeText(LibCore.context, msg, Toast.LENGTH_SHORT).show()
            }
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

/**
 * BottomNavigationView绑定titles和icons
 */
fun BottomNavigationView.bind(titles: ArrayList<String>, icons: ArrayList<Int>? = null) {
    itemIconTintList = null
    for (i in titles.indices) {
        val item = menu.add(0, i, i, titles[i])
        if (!icons.isNullOrEmpty()) {
            item.icon = ViewUtils.getDrawable(icons[i])
        }
    }
}

fun AppCompatActivity.fullscreen() {
    WindowCompat.setDecorFitsSystemWindows(window,false)
    ViewCompat.getWindowInsetsController(window.decorView)?.let {
        it.hide(WindowInsetsCompat.Type.systemBars())
        it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

fun AppCompatActivity.transparentStatusBar() {
    WindowCompat.setDecorFitsSystemWindows(window,false)
    window.apply {
        if (BuildUtils.isAtLeast21Api()) {
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }
        if (BuildUtils.isAtLeast28Api()) {
            navigationBarDividerColor = Color.TRANSPARENT
        }
    }
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))?.let {
        it.isAppearanceLightStatusBars = true
        it.isAppearanceLightNavigationBars = true
    }
}