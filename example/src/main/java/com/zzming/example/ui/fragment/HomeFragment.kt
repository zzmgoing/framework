package com.zzming.example.ui.fragment

import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.showToast
import com.zzming.example.R
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
        ViewModelProvider(requireActivity()).get(HomeViewModel::class.java).apply {
            bannerLiveData.observe(requireActivity(), {
                showToast(it.toString())
            })
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onCreateView(view: View): View {
        binding = FragmentHomeBinding.bind(view)
        return binding.root
    }

    override fun initView() {
        binding.testLanguage.setOnClickListener {
            CommonDialog.showLanguage(baseActivity)
        }
        binding.getRequest.setOnClickListener {
            viewModel.getBanner()
        }
    }

    override fun onRefresh() {
    }

}