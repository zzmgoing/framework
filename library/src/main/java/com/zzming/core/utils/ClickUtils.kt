package com.zzming.core.utils

/**
 * @author ZhongZiMing
 * @time 2022/5/14
 * @description 点击事件工具类
 **/
object ClickUtils {

    private var lastClickTime: Long = 0

    private var exitClickTime: Long = 0

    fun checkDoubleClick() : Boolean {
        val currentClickTime = System.currentTimeMillis()
        val isDoubleClick = currentClickTime - lastClickTime < 1000
        lastClickTime = currentClickTime
        return isDoubleClick
    }

    fun checkExitClick() : Boolean {
        val currentClickTime = System.currentTimeMillis()
        val isExitClick = currentClickTime - exitClickTime < 3000
        exitClickTime = currentClickTime
        return isExitClick
    }

}