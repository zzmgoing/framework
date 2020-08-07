package com.zzming.example.ui.fragment

import com.zzming.core.base.BaseFragment
import com.zzming.example.R
import com.zzming.example.ui.dialog.CommonDialog
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class HomeFragment: BaseFragment() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun initView() {
        test_language.setOnClickListener {
            CommonDialog.showLanguage(baseActivity)
        }
    }


}