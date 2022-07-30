package com.zzming.example.ui.fragment

import android.os.Bundle
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initView() {
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

}