package com.zzming.example.ui.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zzming.core.base.BaseFragment
import com.zzming.example.databinding.FragmentFunctionBinding
import com.zzming.example.ui.adapter.FunctionAdapter

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
        binding.recyclerView.layoutManager = LinearLayoutManager(baseActivity)
        val list = mutableListOf("1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16")
        val adapter = FunctionAdapter()
        adapter.setNewInstance(list)
        binding.recyclerView.adapter = adapter
    }



}