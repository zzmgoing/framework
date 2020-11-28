package com.zzming.example.ui.fragment

import android.view.View
import com.zzming.core.base.BaseFragment
import com.zzming.example.R
import com.zzming.example.databinding.FragmentMineBinding

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MineFragment: BaseFragment() {

    private lateinit var binding: FragmentMineBinding

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun onCreateView(view: View): View {
        binding = FragmentMineBinding.bind(view)
        return binding.root
    }

    override fun initView() {
    }

}