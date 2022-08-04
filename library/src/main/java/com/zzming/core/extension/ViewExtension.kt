package com.zzming.core.extension

import android.app.Activity
import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.Looper
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
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
import com.zzming.core.R
import com.zzming.core.base.AnyEvent
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.common.Constant
import com.zzming.core.utils.BuildUtils
import com.zzming.core.utils.ViewUtils


val Any?.appContext: Application
    get() = LibCore.context

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
            Toast(LibCore.context).apply {
                val layout = ViewUtils.createView(R.layout.core_default_toast)
                layout.findViewById<TextView>(R.id.toast_str).text = msg
                view = layout
            }.show()
        } else {
            runOnMainThread {
//                Toast.makeText(LibCore.context, msg, Toast.LENGTH_SHORT).show()
                Toast(LibCore.context).apply {
                    val layout = ViewUtils.createView(R.layout.core_default_toast)
                    layout.findViewById<TextView>(R.id.toast_str).text = msg
                    view = layout
                }.show()
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
fun ComponentActivity.showLoading() {
    LoadingCollector.showLoading(this)
}

/**
 * hideLoading
 */
fun ComponentActivity.hideLoading() {
    LoadingCollector.hideLoading(this)
}

/**
 * showLoading
 */
fun Fragment.showLoading() {
    activity?.let { LoadingCollector.showLoading(it) }
}

/**
 * hideLoading
 */
fun Fragment.hideLoading() {
    activity?.let { LoadingCollector.hideLoading(it) }
}

fun ComponentActivity.checkLoading(any: AnyEvent?) {
    any?.let {
        if (it.key == Constant.SHOW_LOADING) {
            showLoading()
        } else if (it.key == Constant.HIDE_LOADING) {
            hideLoading()
        }
    }
}

fun Fragment.checkLoading(any: AnyEvent?) {
    any?.let {
        if (it.key == Constant.SHOW_LOADING) {
            showLoading()
        } else if (it.key == Constant.HIDE_LOADING) {
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
fun Any?.dp2px(dp: Float): Int {
    val displayMetrics: DisplayMetrics = LibCore.context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics).toInt()
}

/**
 * px2dp
 */
fun Any?.px2dp(px: Int): Float {
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