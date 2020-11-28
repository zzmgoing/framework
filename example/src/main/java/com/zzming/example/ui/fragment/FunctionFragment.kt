package com.zzming.example.ui.fragment

import android.view.View
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

    override fun getLayoutId(): Int {
        return R.layout.fragment_function
    }

    override fun onCreateView(view: View): View {
        binding = FragmentFunctionBinding.bind(view)
        return binding.root
    }

    override fun initView() {
    }
}