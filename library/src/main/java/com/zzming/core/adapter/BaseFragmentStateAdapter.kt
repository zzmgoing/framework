package com.zzming.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * @author ZhongZiMing
 * @time 2022/5/20
 * @description FragmentStateAdapter
 **/
class BaseFragmentStateAdapter(
    private val fragments: List<Fragment>, fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    constructor(fragments: List<Fragment>, fragmentActivity: FragmentActivity) : this(
        fragments,
        fragmentActivity.supportFragmentManager,
        fragmentActivity.lifecycle
    )

    constructor(fragments: List<Fragment>, fragment: Fragment) : this(
        fragments,
        fragment.childFragmentManager,
        fragment.lifecycle
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

}