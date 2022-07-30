package com.zzming.example.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.showToast
import com.zzming.example.databinding.FragmentHomeBinding
import com.zzming.example.ui.dialog.CommonDialog
import com.zzming.example.viewmodel.HomeViewModel

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(HomeViewModel::class.java).apply {
            bannerLiveData.observe(this@HomeFragment, Observer { showToast(it.toString()) })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.testLanguage.setOnClickListener {
            baseActivity?.let { CommonDialog.showLanguage(it) }
        }
        binding.getRequest.setOnClickListener {
            viewModel.getBanner()
        }
    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onRefresh() {
    }

}