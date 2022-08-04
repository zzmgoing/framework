package com.zzming.core.widget

import android.view.View
import com.zzming.core.utils.ClickUtils

/**
 * @author MackZhong
 * @time 2022/8/4
 * @description
 **/
class SingleClickListener(
    private val listener: View.OnClickListener? = null,
    private val l: ((view: View?) -> Unit)? = null
) : View.OnClickListener {

    override fun onClick(p0: View?) {
        if (ClickUtils.checkDoubleClick()) {
            return
        }
        listener?.onClick(p0)
        l?.invoke(p0)
    }

}

fun View.setOnSingleClickListener(listener: View.OnClickListener) {
    setOnClickListener(SingleClickListener(listener))
}

fun View.setOnSingleClickListener(listener: (view: View?) -> Unit) {
    setOnClickListener(SingleClickListener(listener))
}