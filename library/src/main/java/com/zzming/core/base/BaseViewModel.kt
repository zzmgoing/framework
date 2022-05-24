package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
class BaseViewModel : ViewModel() {

    /**
     * ture 显示loading
     * false 隐藏loading
     */
    val loading = MutableLiveData<Boolean>()

}