package com.zzming.example.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzming.core.base.BaseFragment
import com.zzming.example.databinding.FragmentMineBinding

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MineFragment: BaseFragment() {

    private lateinit var binding: FragmentMineBinding

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }

}