package com.zzming.core.widget

import android.view.View

/**
 * @author MackZhong
 * @time 2022/8/4
 * @description
 **/
class SingleClickListener(private val listener: (view: View?) -> Unit) : View.OnClickListener {

    private var lastClickTime: Long = 0

    override fun onClick(p0: View?) {
        if (checkDoubleClick()) {
            return
        }
        listener.invoke(p0)
    }

    private fun checkDoubleClick() : Boolean {
        val currentClickTime = System.currentTimeMillis()
        val isDoubleClick = currentClickTime - lastClickTime < 1000
        lastClickTime = currentClickTime
        return isDoubleClick
    }

}

fun View.setOnSingleClickListener(listener: (view: View?) -> Unit) {
    setOnClickListener(SingleClickListener(listener))
}