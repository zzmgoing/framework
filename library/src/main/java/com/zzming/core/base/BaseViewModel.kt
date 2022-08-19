package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzming.core.net.*
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.io.File

/**
 * @author ZhongZiMing
 * @time 2022/5/24
 * @description
 **/
open class BaseViewModel : ViewModel() {

    val observer = MutableLiveData<Any>()

    val ioScope = viewModelScope.coroutineContext + Dispatchers.IO

    private val okHttpClient by lazy {
        HttpUtils.okHttpClient
    }

    suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): Result<T> {
        return okHttpClient.get(url, clazz, method)
    }

    suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): Result<T> {
        return okHttpClient.post(url, clazz, method)
    }

    suspend fun <T> postFile(
        url: String,
        clazz: Class<T>,
        file: File,
        fileName: String?,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): Result<T> {
        return okHttpClient.postFile(url, clazz, file, fileName, method)
    }

}