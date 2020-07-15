package com.zzming.core.utils

import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.zzming.core.LibCore.Companion.context
import com.zzming.core.R
import com.zzming.core.extension.runOnMainThread

/**
 * @author ZhongZiMing
 * @version 1.0
 * @date on 2018/11/12 17:17
 * @package com.zzming.mvvm.ui.widget
 * @describe 自定义Toast
 */
object ToastUtil {

    @JvmStatic
    private val mToast: Toast = initToast()
    private var imageView: ImageView? = null
    private var textView: TextView? = null

    /**
     * 原生类型
     */
    private const val TYPE_DEFAULT = 0

    /**
     * 默认类型
     */
    private const val TYPE_NOMAL = 1

    /**
     * 初始化
     */
    private fun initToast(): Toast{
        return Toast(context).apply {
            val view = View.inflate(context, R.layout.core_common_toast, null)
            imageView = view.findViewById(R.id.toast_img)
            textView = view.findViewById(R.id.toast_str)
            this.view = view
            this.duration = Toast.LENGTH_SHORT
            this.setGravity(Gravity.CENTER or Gravity.FILL_HORIZONTAL, 0, 0)
            this.setMargin(0f, 0f)
        }
    }

    /**
     * show
     * @param str
     * @param type
     * @param duration
     */
    @JvmOverloads
    fun show(str: String, type: Int = TYPE_NOMAL, duration: Int = Toast.LENGTH_SHORT) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            realShow(str, type, duration)
        } else {
            runOnMainThread{ realShow(str, type, duration) }
        }
    }

    /**
     * 设置内容
     * @param str
     * @param type
     * @param duration
     */
    private fun realShow(str: String, type: Int, duration: Int) {
        if (TYPE_DEFAULT == type) {
            Toast.makeText(context, str, duration).show()
        } else {
            textView!!.text = str
            mToast.duration = duration
            mToast.show()
        }
    }
}