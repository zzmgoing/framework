package com.zzming.core

import android.app.Application
import android.os.Handler
import android.os.Looper
import okhttp3.OkHttpClient

/**
 * @author ZhongZiMing
 * @time 2020/6/8 16:25
 * @description 初始化入口
 **/
class LibCore {

    companion object {
        /**
         * 全局上下文
         */
        var context: Application? = null

        /**
         * 主线程Handler
         */
        var handler: Handler? = null

        /**
         * 主线程ID
         */
        var mainThreadId: Int = 0

        /**
         * BASE_URL
         */
        var BASE_URL: String? = null

        /**
         * OkHttpClient
         */
        var httpClient: OkHttpClient? = null

        /**
         * 初始化
         */
        fun init(c: Application): Companion {
            context = c
            handler = Handler(Looper.getMainLooper())
            mainThreadId = android.os.Process.myTid()
            return this
        }

        /**
         * 设置BASE_URL
         */
        fun initBaseUrl(baseUrl: String?): Companion {
            BASE_URL = baseUrl?: ""
            return this
        }

        /**
         * 设置 OkHttpClient
         */
        fun initHttpClient(httpClient: OkHttpClient?): Companion {
            Companion.httpClient = httpClient?: OkHttpClient.Builder().build()
            return this
        }

    }

}