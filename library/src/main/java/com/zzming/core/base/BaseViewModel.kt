package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
open class BaseViewModel : ViewModel() {

    /**
     * 通知事务
     */
    val observer = MutableLiveData<Any>()
}