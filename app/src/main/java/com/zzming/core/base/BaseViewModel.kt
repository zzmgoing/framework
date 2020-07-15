package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzming.core.LibCore
import com.zzming.core.bean.PageInfo
import com.zzming.core.common.Constant
import com.zzming.core.extension.logError
import com.zzming.core.net.HeaderInterceptor
import com.zzming.core.net.LogInterceptor
import com.zzming.core.net.RetrofitUtils
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import kotlin.coroutines.CoroutineContext

/**
 * @author ZhongZiMing
 * @time 2020/6/6 15:16
 * @description BaseViewModel
 **/
open class BaseViewModel : ViewModel() {

    /**
     * 是否在加载中 true加载中  false加载完成
     */
    val loadingState = MutableLiveData<Int>()

    /**
     * 是否在加载中 true加载中  false加载完成
     */
    val loadMoreState = MutableLiveData<Int>()

    /**
     * 列表加载页数
     */
    val pageInfo = PageInfo()

    /**
     * retrofit
     */
    val retrofit = RetrofitUtils.instance.retrofit

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