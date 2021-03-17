package com.zzming.core.base

import androidx.annotation.MainThread
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzming.core.bean.PageInfo
import com.zzming.core.common.Constant

/**
 * @author ZhongZiMing
 * @time 2020/6/6 15:16
 * @description BaseViewModel
 **/
abstract class BaseViewModel : ViewModel() {

    /**
     * 数据状态同步管理
     */
    val loadStatusMap = HashMap<String, MutableLiveData<Int>>()

    /**
     * 注册数据状态
     */
    fun registerLoad(type: String = Constant.LOAD_DEFAULT): MutableLiveData<Int>? {
        if (!loadStatusMap.containsKey(type)) {
            loadStatusMap[type] = MutableLiveData<Int>()
        }
        return loadStatusMap[type]
    }

    /**
     * 解除数据状态
     */
    fun unRegisterLoad(type: String = Constant.LOAD_DEFAULT) {
        if (loadStatusMap.containsKey(type)) {
            loadStatusMap.remove(type)
        }
    }

    /**
     * 通知数据状态变化
     */
    @MainThread
    fun notifyLoadStatus(type: String, status: Int) {
        loadStatusMap[type]?.let {
            it.value = status
        }
    }

    /**
     * 列表加载页数
     */
    val pageInfo = PageInfo()

    /**
     * 刷新
     */
    open fun refresh() {

    }

    /**
     * 加载更多
     */
    open fun loadMore() {

    }

}