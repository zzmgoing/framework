package com.zzming.example.ui

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.gyf.immersionbar.ktx.immersionBar
import com.zzming.core.LibCore
import com.zzming.core.adapter.makeFragmentStateAdapter
import com.zzming.core.base.BaseActivity
import com.zzming.core.extension.bind
import com.zzming.example.R
import com.zzming.example.databinding.ActivityMainBinding
import com.zzming.example.ui.fragment.function.FunctionFragment
import com.zzming.example.ui.fragment.home.HomeFragment
import com.zzming.example.ui.fragment.mine.MineFragment

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    private val fragments = arrayListOf(HomeFragment(), FunctionFragment(), MineFragment())

    private val fragmentAdapter = makeFragmentStateAdapter(fragments)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        immersionBar {
            transparentStatusBar()
            statusBarDarkFont(true)
        }
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
        binding.mainBottomTab.apply {
            bind(titles, icons)
            setOnItemSelectedListener {
                binding.mainViewPager.currentItem = it.itemId
                true
            }
        }
        binding.mainViewPager.apply {
            adapter = fragmentAdapter
            registerOnPageChangeCallback(MainOnPageChangeCallback(binding.mainBottomTab))
        }
    }

    class MainOnPageChangeCallback(private val navigation: BottomNavigationView): ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            navigation.let {
                if (it.selectedItemId != position) {
                    it.selectedItemId = position
                }
            }
        }

    }

}