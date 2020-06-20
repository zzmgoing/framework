package com.zzming.core.utils

import android.content.res.Resources.NotFoundException
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.zzming.core.LibCore
import com.zzming.core.extension.logError

/**
 * @author ZhongZiMing
 * @time 2020/6/8 17:49
 * @description View工具类
 **/
class ViewUtils {

    companion object{
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
            return try{
                LibCore.context?.resources?.getString(id)
            }catch (e: NotFoundException){
                logError("没有找到id为 $id 的String，请检查是否配置。")
                null
            }
        }

        /**
         * 得到Drawable
         */
        fun getDrawable(@DrawableRes id: Int): Drawable? {
            return ContextCompat.getDrawable(LibCore.context!!,id)
        }

        /**
         * 得到Drawable
         */
        fun getColor(@ColorRes id: Int): Int? {
            return ContextCompat.getColor(LibCore.context!!,id)
        }

    }

}