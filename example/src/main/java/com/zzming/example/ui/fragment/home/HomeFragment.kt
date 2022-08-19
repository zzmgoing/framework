package com.zzming.example.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.showToast
import com.zzming.example.databinding.FragmentHomeBinding
import com.zzming.example.listpreloader.activity.ListPreLoadActivity
import com.zzming.example.listpreloader.activity.PreLoadActivity
import com.zzming.example.listpreloader.activity.RecyclerViewActivity
import com.zzming.example.ui.dialog.CommonDialog

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java].apply {
            bannerLiveData.observe(this@HomeFragment) { showToast(it.toString()) }
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

        binding.listPreloadActivity.setOnClickListener {
            startActivity(Intent(baseActivity,ListPreLoadActivity::class.java))
        }
        binding.preloadActivity.setOnClickListener {
            startActivity(Intent(baseActivity, PreLoadActivity::class.java))
        }
        binding.noPreloadActivity.setOnClickListener {
            startActivity(Intent(baseActivity,RecyclerViewActivity::class.java))
        }

    }

    override fun createContentView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onRefresh() {
    }

}