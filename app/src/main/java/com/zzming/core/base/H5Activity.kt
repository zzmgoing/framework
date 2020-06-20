package com.zzming.core.base

import com.zzming.core.R

/**
 * @author ZhongZiMing
 * @time 2020/6/7 16:29
 * @description H5Activity
 **/
class H5Activity : BaseViewModelActivity<H5ViewModel>(){
    override fun getModelClass(): Class<H5ViewModel> {
        return H5ViewModel::class.java
    }

    override fun initContentView() {
        setContentView(R.layout.core_activity_h5)
    }

    override fun initView() {

    }
}