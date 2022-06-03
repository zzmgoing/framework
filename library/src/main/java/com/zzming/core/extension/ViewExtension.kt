package com.zzming.core.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.LibCore
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.common.Constant
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
}

/**
 * 显示土司
 */
fun showToast(msg: String?) {
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
    return this != null && !isFinishing && !isDestroyed
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
    LoadingCollector.showLoading(requireActivity())
}

/**
 * hideLoading
 */
fun Fragment.hideLoading() {
    LoadingCollector.hideLoading(requireActivity())
}

fun Activity.checkLoading(any: Any?) {
    any?.let {
        if (it == Constant.SHOW_LOADING) {
            showLoading()
        } else if (it == Constant.HIDE_LOADING) {
            hideLoading()
        }
    }
}

fun Fragment.checkLoading(any: Any?) {
    any?.let {
        if (it == Constant.SHOW_LOADING) {
            showLoading()
        } else if (it == Constant.HIDE_LOADING) {
            hideLoading()
        }
    }
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

/**
 * 全屏
 */
fun AppCompatActivity.fullscreen() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))?.let {
        it.hide(WindowInsetsCompat.Type.systemBars())
        it.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

/**
 * 退出全屏
 */
fun AppCompatActivity.exitFullscreen() {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))?.let {
        it.show(WindowInsetsCompat.Type.systemBars())
    }
}

/**
 * 透明状态栏
 */
fun AppCompatActivity.transparentStatusBar() {
    setStatusBarColor()
    statusBarFontDark()
}

/**
 * 设置状态栏颜色
 */
fun AppCompatActivity.setStatusBarColor(color: Int = Color.TRANSPARENT) {
    WindowCompat.setDecorFitsSystemWindows(window, false)
    window.apply {
        if (BuildUtils.isAtLeast21Api()) {
            statusBarColor = color
            navigationBarColor = color
        }
        if (BuildUtils.isAtLeast28Api()) {
            navigationBarDividerColor = color
        }
    }
}

/**
 * 状态栏黑色或白色
 */
fun AppCompatActivity.statusBarFontDark(isDark: Boolean = true) {
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))?.let {
        it.isAppearanceLightStatusBars = isDark
        it.isAppearanceLightNavigationBars = isDark
    }
}

/**
 * 是否显示状态栏
 */
fun AppCompatActivity.isShowStatusBar(isShow: Boolean = true) {
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))?.let {
        if (isShow) {
            it.show(WindowInsetsCompat.Type.systemBars())
        } else {
            it.hide(WindowInsetsCompat.Type.systemBars())
        }
    }
}

/**
 * 是否显示状态栏
 */
fun AppCompatActivity.getStatusBarHeight(): Int {
    val compat = ViewCompat.getRootWindowInsets(findViewById<FrameLayout>(android.R.id.content))
    return compat?.getInsets(WindowInsetsCompat.Type.statusBars())?.top ?: 0
}

/**
 * 显示键盘
 */
fun AppCompatActivity.showKeyBoard() {
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))
        ?.show(WindowInsetsCompat.Type.ime())
}

/**
 * 隐藏键盘
 */
fun AppCompatActivity.hideKeyBoard() {
    ViewCompat.getWindowInsetsController(findViewById<FrameLayout>(android.R.id.content))
        ?.hide(WindowInsetsCompat.Type.ime())
}

/**
 * 显示键盘
 */
fun EditText.showKeyBoard() {
    isEnabled = true
    isFocusable = true
    isFocusableInTouchMode = true
    requestFocus()
    context.getSystemService(Context.INPUT_METHOD_SERVICE).let {
        it as InputMethodManager
        setSelection(text?.length ?: 0)
        it.showSoftInput(this, 0)
    }
}

/**
 * dp2px
 */
fun dp2px(dp: Float): Int {
    val displayMetrics: DisplayMetrics = LibCore.context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
}

/**
 * px2dp
 */
fun px2dp(px: Int): Float {
    val density: Float = LibCore.context.resources.displayMetrics.density
    return (px / density)
}

/**
 * screenWidth
 */
val screenWidth: Int
    get() = LibCore.context.resources.displayMetrics.widthPixels

/**
 * screenHeight
 */
val screenHeight: Int
    get() = LibCore.context.resources.displayMetrics.heightPixels

/**
 * 设置间距
 */
fun RecyclerView.spaceDriver() {
    spaceDriver(10f, Color.TRANSPARENT)
}

/**
 * 设置间距
 */
fun RecyclerView.spaceDriver(space: Float = 10f) {
    spaceDriver(space, Color.TRANSPARENT)
}

/**
 * 设置间距
 */
fun RecyclerView.spaceDriver(space: Float = 10f, color: Int = Color.TRANSPARENT) {
    context.dividerBuilder()
        .size(dp2px(space))
        .color(color)
        .build()
        .addTo(this)
}