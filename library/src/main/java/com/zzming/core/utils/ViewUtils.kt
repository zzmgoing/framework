package com.zzming.core.utils

import android.content.Context
import android.content.Intent
import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.*
import androidx.core.content.ContextCompat
import com.zzming.core.LibCore
import com.zzming.core.base.BaseActivity
import com.zzming.core.extension.logError

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:49
 * @description View工具类
 **/
class ViewUtils {

    companion object {
        /**
         * 创建布局
         * @param layoutId
         * @return
         */
        fun createView(@LayoutRes layoutId: Int): View {
            return View.inflate(LibCore.context, layoutId, null)
        }

        /**
         * 得到string
         * @param id
         * @return
         */
        fun getString(@StringRes id: Int): String? {
            return try {
                LibCore.context.resources?.getString(id)
            } catch (e: NotFoundException) {
                logError("没有找到id为 $id 的String，请检查是否配置。")
                null
            }
        }

        /**
         * 得到Drawable
         */
        fun getDrawable(@DrawableRes id: Int): Drawable? {
            return ContextCompat.getDrawable(LibCore.context, id)
        }

        /**
         * 得到Color
         */
        fun getColor(@ColorRes id: Int): Int {
            return ContextCompat.getColor(LibCore.context, id)
        }

        /**
         * 点击空白区域收起键盘
         */
        fun hieKeyboard(view: View?, event: MotionEvent?) {
            if (MotionEvent.ACTION_DOWN == event?.action) {
                if (isShouldHideKeyboard(view, event)) {
                    view?.windowToken.let {
                        val im =
                            LibCore.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        im.hideSoftInputFromWindow(it, InputMethodManager.HIDE_NOT_ALWAYS)
                    }
                }
            }
        }

        /**
         * 是否为键盘外区域
         */
        private fun isShouldHideKeyboard(view: View?, event: MotionEvent?): Boolean {
            if (view == null || event == null || view !is EditText) {
                return false
            }
            val l = intArrayOf(0, 0)
            view.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + view.getHeight()
            val right = left + view.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }

        /**
         * 开启动画
         */
        fun startAnimation(view: View?, @AnimRes animRes: Int) {
            if (view == null || view.visibility != View.VISIBLE) {
                return
            }
            val animationTag = view.getTag(animRes)
            val animation = if (animationTag != null && animationTag is Animation) {
                animationTag
            } else {
                val loadAnimation = AnimationUtils.loadAnimation(view.context, animRes)
                view.setTag(animRes, loadAnimation)
                loadAnimation
            }
            view.startAnimation(animation)
        }

        /**
         * 返回到之前的Activity
         */
        fun goBackActivity(back: BaseActivity, intent: Intent? = null) {
            val backIntent = intent ?: Intent()
            backIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            LibCore.context.startActivity(intent)
        }

        fun getScreenWidth(): Int {
            return LibCore.context.resources.displayMetrics.widthPixels
        }

        fun getScreenHeight(): Int {
            return LibCore.context.resources.displayMetrics.heightPixels
        }

        fun getColorWithAlpha(alpha: Float, baseColor: Int): Int {
            val a = 255.coerceAtMost(0.coerceAtLeast((alpha * 255).toInt())) shl 24
            val rgb = 0x00ffffff and baseColor
            return a + rgb
        }

    }

}