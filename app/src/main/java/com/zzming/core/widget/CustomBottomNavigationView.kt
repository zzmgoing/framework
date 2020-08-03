package com.zzming.core.widget

import android.content.Context
import android.util.AttributeSet
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.adapter.LazyFragmentPagerAdapter
import com.zzming.core.utils.ViewUtils

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class CustomBottomNavigationView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
    BottomNavigationView(context, attrs, defStyleAttr) {

    constructor(context: Context): this(context,null,0)

    constructor(context: Context, attrs: AttributeSet?): this(context,attrs,0)

    private lateinit var viewPager: ViewPager

    private lateinit var titles: ArrayList<String>

    private lateinit var icons: ArrayList<Int>

    /**
     * 绑定
     */
    fun bind(titles: ArrayList<String>,icons: ArrayList<Int>,viewPager: ViewPager) {
        this.titles = titles
        this.icons = icons
        this.viewPager = viewPager
        if (titles.size != icons.size) {
            return
        }
        for (i in titles.indices) {
            menu.add(0, i, i, titles[i]).icon = ViewUtils.getDrawable(icons[i])
        }
        viewPager.offscreenPageLimit = titles.size
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                if (selectedItemId != position) {
                    selectedItemId = position
                }
                if (viewPager.adapter is LazyFragmentPagerAdapter) {
                    (viewPager.adapter as LazyFragmentPagerAdapter).loadFragmentData()
                }
            }
        })
        setOnNavigationItemSelectedListener {
            if (viewPager.currentItem != it.itemId) {
                viewPager.currentItem = it.itemId
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

}