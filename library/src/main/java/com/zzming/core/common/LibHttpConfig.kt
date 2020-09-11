package com.zzming.core.common

import okhttp3.OkHttpClient

/**
 * @author ZhongZiMing
 * @time 2020/8/10 10:01
 * @description 网络请求配置
 **/
object LibHttpConfig {

    /**
     * BASE_URL
     */
    var BASE_URL: String? = null

    /**
     * OkHttpClient
     */
    var httpClient: OkHttpClient? = null

    /**
     * 通用请求头Header
     */
    var commonHeaders: MutableMap<String, String>? = null

}