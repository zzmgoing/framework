package com.zzming.example.ui

import androidx.databinding.DataBindingUtil
import com.zzming.core.LibCore
import com.zzming.core.adapter.LazyFragmentPagerAdapter
import com.zzming.core.base.BaseActivity
import com.zzming.example.R
import com.zzming.example.databinding.ActivityMainBinding
import com.zzming.example.ui.fragment.FunctionFragment
import com.zzming.example.ui.fragment.HomeFragment
import com.zzming.example.ui.fragment.MineFragment
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MainActivity : BaseActivity() {

    private val fragments = arrayListOf(HomeFragment(), FunctionFragment(), MineFragment())

    private val adapter = LazyFragmentPagerAdapter(fragments, supportFragmentManager)

    override fun initContentView() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
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
        adapter.bindViewPagerAndBottomNavigationView(main_view_pager, main_bottom_tab)
        main_bottom_tab.bind(titles, icons)
    }

}