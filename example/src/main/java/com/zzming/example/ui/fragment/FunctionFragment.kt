package com.zzming.example.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zzming.core.base.BaseFragment
import com.zzming.example.R
import com.zzming.example.databinding.FragmentFunctionBinding

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class FunctionFragment: BaseFragment() {

    private lateinit var binding: FragmentFunctionBinding

    override fun getContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentFunctionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun initView() {
    }
}