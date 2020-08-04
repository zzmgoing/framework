package com.zzming.example.ui

import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.zzming.core.adapter.LazyFragmentPagerAdapter
import com.zzming.core.base.BaseActivity
import com.zzming.core.base.DoSomeThingListener
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

    private val fragments = arrayListOf(HomeFragment(),FunctionFragment(),MineFragment())

    private val adapter = LazyFragmentPagerAdapter(fragments, supportFragmentManager)

    override fun initContentView() {
        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
    }

    override fun initView() {
        adapter.bindViewPagerAndBottomNavigationView(main_view_pager,main_bottom_tab)
        main_bottom_tab.apply {
            titleKey = arrayListOf(Language.MAIN_TAB_HOME,
                Language.MAIN_TAB_FUNCTION,
                Language.MAIN_TAB_MINE)
            titleListener = object : DoSomeThingListener{
                override fun doSomeThing(any: Any?): Any? {
                    return LanguageLib.find(any as String)
                }
            }
            bind()
        }
        LanguageLib.language.observe(this, Observer {
            main_bottom_tab.updateTitles()
        })
    }

}