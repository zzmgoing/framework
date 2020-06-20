package com.zzming.core.base

import androidx.lifecycle.ViewModelProvider

/**
 * @author ZhongZiMing
 * @time 2020/6/5 18:57
 * @description BaseViewModelActivity
 **/
abstract class BaseViewModelActivity<T : BaseViewModel> : BaseActivity() {

    val viewModel: T by lazy {
        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(getModelClass())
    }

    override fun initViewModel() {
    }

    abstract fun getModelClass(): Class<T>

}