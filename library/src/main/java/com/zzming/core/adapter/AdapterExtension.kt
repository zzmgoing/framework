package com.zzming.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun Fragment.makeFragmentStateAdapter(fragments: List<Fragment>): BaseFragmentStateAdapter {
    return BaseFragmentStateAdapter(fragments, this)
}

fun FragmentActivity.makeFragmentStateAdapter(fragments: List<Fragment>): BaseFragmentStateAdapter {
    return BaseFragmentStateAdapter(fragments, this)
}