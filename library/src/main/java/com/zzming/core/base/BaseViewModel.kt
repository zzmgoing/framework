package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzming.core.bean.PageInfo
import com.zzming.core.common.Constant

/**
 * @author ZhongZiMing
 * @time 2020/6/6 15:16
 * @description BaseViewModel
 **/
open class BaseViewModel : ViewModel() {

    /**
     * 加载中的状态
     */
    val loadingState = MutableLiveData<Int>()

    /**
     * 列表页面加载更多的状态
     */
    val loadMoreState = MutableLiveData<Int>()

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

    /**
     * 错误处理
     */
    open fun requestFail() {
        if (loadingState.value == Constant.LOADING) {
            loadingState.value = Constant.FAIL
        }
        if (loadMoreState.value == Constant.LOADING) {
            loadMoreState.value = Constant.FAIL
        }
    }

}