package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzming.core.bean.PageInfo
import com.zzming.core.common.Constant
import com.zzming.core.extension.logError
import com.zzming.core.net.RetrofitUtils
import com.zzming.core.service.ApiService
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

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
     * 协程处理
     */
    fun launch(
        block: suspend (CoroutineScope) -> Unit,
        context: CoroutineContext = Dispatchers.Main): Job {
        return viewModelScope.launch(context + CoroutineExceptionHandler { _, e ->
            logError(e.message, e)
            requestFail()
        }) {
            try {
                block(this)
            } catch (e: Exception) {
                logError(e.message, e)
                requestFail()
            }
        }
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