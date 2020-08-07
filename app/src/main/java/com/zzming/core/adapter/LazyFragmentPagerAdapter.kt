package com.zzming.core.adapter

import android.view.MenuItem
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logError
import com.zzming.core.widget.CustomBottomNavigationView

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class LazyFragmentPagerAdapter(
    private val fragments: List<BaseFragment>,
    fm: FragmentManager,
    behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) : FragmentPagerAdapter(fm, behavior),
    ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * currentPosition
     */
    var currentPosition: Int = 0

    /**
     * 点击时是否刷新数据
     */
    var isRefresh = false

    /**
     * currentFragment
     */
    private lateinit var currentFragment: BaseFragment

    /**
     * ViewPager
     */
    var viewPager: ViewPager? = null

    /**
     * CustomBottomNavigationView
     */
    var customBottomNavigationView: CustomBottomNavigationView? = null

    /**
     * getItem
     */
    override fun getItem(position: Int): BaseFragment {
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
        if (viewPager.adapter == null) {
            viewPager.adapter = this
        }
        viewPager.addOnPageChangeListener(this)
    }

    /**
     * 绑定bottomNavigationView
     */
    fun bindBottomNavigationView(bottomNavigationView: CustomBottomNavigationView) {
        this.customBottomNavigationView = bottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    /**
     * 绑定ViewPager，绑定bottomNavigationView
     */
    fun bindViewPagerAndBottomNavigationView(
        viewPager: ViewPager,
        bottomNavigationView: CustomBottomNavigationView
    ) {
        bindViewPager(viewPager)
        bindBottomNavigationView(bottomNavigationView)
    }

    /**
     * 加载fragment数据
     */
    fun loadFragmentData() {
        if (isRefresh) {
            currentFragment.onRefresh()
        } else {
            if (!currentFragment.isLoadData) {
                currentFragment.onRefresh()
            }
        }
    }

    override fun onPageSelected(position: Int) {
        refreshFragment(position)
        customBottomNavigationView?.let {
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
                it.currentItem = item.itemId
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
        loadFragmentData()
    }

}