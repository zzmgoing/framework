package com.zzming.core.net

import com.zzming.core.LibCore
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author ZhongWei
 * @time 2020/7/15 15:10
 * @description 请求头
 **/
class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        LibCore.httpHeaders?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }
        return chain.proceed(builder.build())
    }
}