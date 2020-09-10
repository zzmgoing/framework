package com.zzming.core.net

import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

/**
 * @author ZhongWei
 * @time 2020/9/9 18:31
 * @description
 **/
object HttpUtils {

    /**
     * OkHttpClient
     */
    var okHttpClient: OkHttpClient? = null
        get() = if (field == null) {
            defaultHttpClient()
        } else {
            field
        }

    /**
     * get
     */
    fun get(url: String, callback: HttpCallback<*>) {
        get(url, null, null, callback)
    }

    /**
     * get
     */
    fun get(
        url: String,
        params: HashMap<String, String>? = null,
        headers: HashMap<String, String>? = null,
        callback: HttpCallback<*>
    ) {
        var newUrl = url
        if (!params.isNullOrEmpty()) {
            val sb = StringBuffer()
            sb.append("?")
            for ((k, v) in params) {
                sb.append("$k=$v&")
            }
            newUrl = url + sb.toString().substring(0, sb.toString().length - 1)
        }
        val request = Request.Builder().apply {
            if (!headers.isNullOrEmpty()) {
                for ((k, v) in headers) {
                    addHeader(k, v)
                }
            }
            url(newUrl)
            get()
        }.build()
        okHttpClient?.newCall(request)?.enqueue(callback)
    }

    /**
     * OkHttpClient
     */
    private fun defaultHttpClient(): OkHttpClient {
        return LibHttpConfig.httpClient ?: OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(LogInterceptor())
            .connectTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .build()
    }

}