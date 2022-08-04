package com.zzming.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zzming.core.net.*
import okhttp3.OkHttpClient
import java.io.File

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

    private val okHttpClient by lazy {
        HttpUtils.okHttpClient
    }

    suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ) {
        okHttpClient.get(url, clazz, method)
    }

    suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ) {
        okHttpClient.post(url, clazz, method)
    }

    suspend fun <T> postFile(
        url: String,
        clazz: Class<T>,
        file: File,
        fileName: String?,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ) {
        okHttpClient.postFile(url, clazz, file, fileName, method)
    }

}