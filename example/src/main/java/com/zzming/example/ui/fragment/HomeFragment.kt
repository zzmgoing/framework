package com.zzming.example.ui.fragment

import com.zzming.core.base.BaseFragment
import com.zzming.core.extension.hideLoading
import com.zzming.core.extension.showLoading
import com.zzming.core.extension.showToast
import com.zzming.core.net.HttpCallback
import com.zzming.core.net.HttpUtils
import com.zzming.example.R
import com.zzming.example.bean.BannerBean
import com.zzming.example.ui.dialog.CommonDialog
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment : BaseFragment() {

    companion object {
        const val TEST_GET_URL = "https://www.wanandroid.com/banner/json"
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        test_language.setOnClickListener {
            CommonDialog.showLanguage(baseActivity)
        }
        get_request.setOnClickListener {
            getRequestSync()
        }
    }

    private fun getRequestSync() {
        showLoading()
        HttpUtils.get(TEST_GET_URL, object : HttpCallback<BannerBean>() {
            override fun onSuccess(t: BannerBean) {
                showToast(t.toString())
            }

            override fun onFinish() {
                hideLoading()
            }
        })
    }

}