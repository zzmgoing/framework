package com.zzming.core.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.forEach
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.base.DoSomeThingListener

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
     * titleKey
     */
    var titleKey: ArrayList<String>? = null

    /**
     * titleValue
     */
    var titleValue: ArrayList<String>? = null

    /**
     * icons
     */
    var icons: ArrayList<Int>? = null

    /**
     * 获取Title
     */
    var titleListener: DoSomeThingListener? = null

    /**
     * 绑定
     */
    fun bind() {
        titleValue?.forEach {
            val index = titleValue?.indexOf(it)!!
            menu.add(0, index, index, it)
        }
        titleListener?.let { l ->
            titleKey?.forEach {
                val index = titleKey?.indexOf(it)!!
                menu.add(0, index, index, l.doSomeThing(it) as String)
            }
        }
    }

    /**
     * 更新
     */
    fun updateTitles() {
        titleListener?.apply {
            menu.forEach { item ->
                titleKey?.let {
                    item.title = doSomeThing(it[item.itemId]) as String
                }
            }
        }
    }

}