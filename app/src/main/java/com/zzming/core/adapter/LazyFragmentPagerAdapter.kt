package com.zzming.core.adapter

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zzming.core.base.BaseFragment

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class LazyFragmentPagerAdapter(private val fragments: List<BaseFragment>, fm: FragmentManager, behavior: Int = BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) : FragmentPagerAdapter(fm,behavior) {

    var currentPosition: Int = 0

    var LoadOnce = true

    lateinit var currentFragment: BaseFragment

    override fun getItem(position: Int): BaseFragment {
        currentPosition = position
        currentFragment = fragments[position]
        return currentFragment
    }

    override fun getCount(): Int {
        return fragments.size
    }

    /**
     * 加载fragment数据
     */
    fun loadFragmentData(){
        if(LoadOnce){
            currentFragment.onRefresh()
        }
    }

}