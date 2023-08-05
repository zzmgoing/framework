package com.zzming.example.ui.fragment.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.example.databinding.FragmentMineBinding

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class MineFragment: BaseFragment() {

    private lateinit var binding: FragmentMineBinding
    private val viewModel by lazy {
        ViewModelProvider(this)[MineViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentMineBinding.inflate(inflater, container, false)
        return binding.root
    }

}