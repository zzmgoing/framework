package com.zzming.core.utils

import android.os.Build

/**
 * @author ZhongZiMing
 * @time 2020/8/5 14:37
 * @description
 **/
object BuildUtils {

    fun isAtLeast24Api(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    fun isAtLeast17Api(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1
    }

}