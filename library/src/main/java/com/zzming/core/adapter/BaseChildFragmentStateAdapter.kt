package com.zzming.core.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author ZhongZiMing
 * @time 2022/5/20
 * @description FragmentStateAdapter
 **/
class BaseChildFragmentStateAdapter(fragment: Fragment, private val fragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}