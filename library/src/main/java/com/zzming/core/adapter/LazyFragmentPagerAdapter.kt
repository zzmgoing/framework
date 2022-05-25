package com.zzming.core.adapter

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class LazyFragmentPagerAdapter(
    private val fragments: List<Fragment>,
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior),
    ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * currentPosition
     */
    var currentPosition: Int = 0

    /**
     * 切换时是否刷新数据
     */
    var isRefresh = false

    /**
     * currentFragment
     */
    private lateinit var currentFragment: Fragment

    /**
     * ViewPager
     */
    var viewPager: ViewPager? = null

    /**
     * CustomBottomNavigationView
     */
    var bottomNavigationView: BottomNavigationView? = null

    /**
     * getItem
     */
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    /**
     * getCount
     */
    override fun getCount(): Int {
        return fragments.size
    }

    /**
     * 绑定ViewPager
     */
    fun bindViewPager(viewPager: ViewPager) {
        this.viewPager = viewPager
        this.viewPager?.adapter = this
        this.viewPager?.addOnPageChangeListener(this)
    }

    /**
     * 绑定bottomNavigationView
     */
    fun bindBottomNavigationView(bottomNavigationView: BottomNavigationView) {
        this.bottomNavigationView = bottomNavigationView
        this.bottomNavigationView?.setOnNavigationItemSelectedListener(this)
    }

    /**
     * 绑定ViewPager，绑定bottomNavigationView
     */
    fun bindViewPagerAndBottomNavigationView(
        viewPager: ViewPager,
        bottomNavigationView: BottomNavigationView
    ) {
        bindViewPager(viewPager)
        bindBottomNavigationView(bottomNavigationView)
    }

    override fun onPageSelected(position: Int) {
        refreshFragment(position)
        bottomNavigationView?.let {
            if (it.selectedItemId != position) {
                it.selectedItemId = position
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewPager?.let {
            if (it.currentItem != item.itemId) {
                it.setCurrentItem(item.itemId, false)
            }
        }
        return true
    }

    /**
     * 刷新Fragment
     */
    private fun refreshFragment(position: Int) {
        currentPosition = position
        currentFragment = fragments[position]
    }

}