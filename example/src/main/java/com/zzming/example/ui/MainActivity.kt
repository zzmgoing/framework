package com.zzming.example.ui

import androidx.core.view.get
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zzming.core.adapter.LazyFragmentPagerAdapter
import com.zzming.core.base.BaseActivity
import com.zzming.example.Language
import com.zzming.example.LanguageLib
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
class MainActivity: BaseActivity() {

    private val fragments by lazy {
        arrayListOf(HomeFragment(),FunctionFragment(),MineFragment())
    }

    override fun initContentView() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
    }

    override fun initView() {
        main_view_pager.adapter = LazyFragmentPagerAdapter(fragments, supportFragmentManager)
        val keys = arrayListOf(Language.MAIN_TAB_HOME,
            Language.MAIN_TAB_FUNCTION,
            Language.MAIN_TAB_MINE)
        val titles = arrayListOf(LanguageLib.find(keys[0])!!,
            LanguageLib.find(keys[1])!!,
            LanguageLib.find(keys[2])!!)
        val icons = arrayListOf(R.drawable.app_home_tab_img_selector,
            R.drawable.app_function_tab_img_selector,
            R.drawable.app_mine_tab_img_selector)
        main_bottom_tab.bind(titles,icons,main_view_pager)
        LanguageLib.language.observe(this, Observer {
            for (i in 0 until main_bottom_tab.menu.size()) {
                main_bottom_tab.menu.getItem(i).title = it[keys[i]]
            }
        })
    }

}