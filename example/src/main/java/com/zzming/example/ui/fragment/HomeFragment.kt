package com.zzming.example.ui.fragment

import androidx.lifecycle.ViewModelProvider
import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.showToast
import com.zzming.example.R
import com.zzming.example.ui.dialog.CommonDialog
import com.zzming.example.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment : BaseFragment() {

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

    override fun initView() {
        test_language.setOnClickListener {
            CommonDialog.showLanguage(baseActivity)
        }
        get_request.setOnClickListener {
            viewModel.getBanner()
        }
    }


}