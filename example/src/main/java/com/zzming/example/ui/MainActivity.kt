package com.zzming.example.ui

import androidx.viewpager.widget.ViewPager
import com.zzming.core.LibCore
import com.zzming.core.adapter.LazyFragmentPagerAdapter
import com.zzming.core.base.BaseActivity
import com.zzming.core.extension.bind
import com.zzming.core.extension.transparentStatusBar
import com.zzming.example.R
import com.zzming.example.databinding.ActivityMainBinding
import com.zzming.example.ui.fragment.FunctionFragment
import com.zzming.example.ui.fragment.HomeFragment
import com.zzming.example.ui.fragment.MineFragment

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener {

    private lateinit var binding: ActivityMainBinding

    private val fragments = arrayListOf(HomeFragment(), FunctionFragment(), MineFragment())

    private val adapter = LazyFragmentPagerAdapter(fragments, supportFragmentManager)

    override fun beforeOnCreate() {
        transparentStatusBar()
    }

    override fun initContentView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        val titles = arrayListOf(
            LibCore.context.getString(R.string.main_tab_home),
            getString(R.string.main_tab_function),
            getString(R.string.main_tab_mine)
        )
        val icons = arrayListOf(
            R.drawable.main_tab_home_selector,
            R.drawable.main_tab_function_selector,
            R.drawable.main_tab_mine_selector
        )
        adapter.bindViewPagerAndBottomNavigationView(binding.mainViewPager, binding.mainBottomTab)
        binding.mainBottomTab.bind(titles, icons)
        binding.mainViewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

}