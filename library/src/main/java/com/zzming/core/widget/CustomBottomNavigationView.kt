package com.zzming.core.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class CustomBottomNavigationView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    BottomNavigationView(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    /**
     * 绑定
     */
    fun bind(titles: ArrayList<String>, icons: ArrayList<Int>? = null) {
        itemIconTintList = null
        for (i in titles.indices) {
            val item = menu.add(0, i, i, titles[i])
            if (!icons.isNullOrEmpty()) {
                item.icon = ViewUtils.getDrawable(icons[i])
            }
        }
    }

}