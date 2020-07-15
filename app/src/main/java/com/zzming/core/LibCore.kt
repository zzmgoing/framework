package com.zzming.core

import android.content.Context
import android.os.Handler
import android.os.Looper
import okhttp3.OkHttpClient

/**
 * @author ZhongZiMing
 * @time 2020/6/8 16:25
 * @description 初始化入口
 **/
class LibCore private constructor() {

    companion object {

        /**
         * LibCore
         */
        val instance: LibCore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LibCore() }

        /**
         * 全局上下文
         */
        lateinit var context: Context

        /**
         * 主线程Handler
         */
        lateinit var handler: Handler

        /**
         * BASE_URL
         */
        var BASE_URL: String? = null

        /**
         * OkHttpClient
         */
        var httpClient: OkHttpClient? = null

        /**
         * Header
         */
        var httpHeaders: MutableMap<String, String>? = null

    }

    /**
     * 初始化
     */
    fun init(c: Context): LibCore {
        context = c.applicationContext
        handler = Handler(Looper.getMainLooper())
        return this
    }

    /**
     * 设置BASE_URL
     */
    fun initBaseUrl(baseUrl: String?): LibCore {
        BASE_URL = baseUrl
        return this
    }

    /**
     * 设置 OkHttpClient
     */
    fun initHttpClient(client: OkHttpClient?): LibCore {
        httpClient = client
        return this
    }

    /**
     * 设置网络请求头
     */
    fun initHttpHeader(headers: MutableMap<String, String>?): LibCore {
        httpHeaders = headers
        return this
    }

}