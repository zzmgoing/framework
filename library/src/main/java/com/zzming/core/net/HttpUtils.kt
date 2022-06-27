package com.zzming.core.net

import com.zzming.core.common.LibHttpConfig
import com.zzming.core.utils.JsonUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.concurrent.TimeUnit


/**
 * @author ZhongZiMing
 * @time 2020/9/9 18:31
 * @description
 **/
object HttpUtils {

    /**
     * JSON_TYPE
     */
    private val JSON_TYPE = "application/json; charset=utf-8".toMediaTypeOrNull()

    /**
     * FILE_TYPE
     */
    private val FILE_TYPE = "image/*".toMediaTypeOrNull()

    /**
     * OkHttpClient
     */
    private var okHttpClient = defaultHttpClient()

    /**
     * get同步
     */
    fun get(
        url: String,
        params: HashMap<String, String>? = null,
        headers: HashMap<String, String>? = null
    ): String? {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return null
        }
        val request = Request.Builder().apply {
            url(createUrl(finalUrl, params))
            addHeader(this, headers)
            get()
        }.build()
        return okHttpClient.newCall(request).execute().body?.string()
    }

    /**
     * get异步
     */
    fun get(url: String, callback: HttpCallback<*>) {
        get(url, null, null, callback)
    }

    /**
     * get异步
     */
    fun get(url: String, params: HashMap<String, String>, callback: HttpCallback<*>) {
        get(url, params, null, callback)
    }

    /**
     * get异步
     */
    fun get(
        url: String,
        params: HashMap<String, String>? = null,
        headers: HashMap<String, String>? = null,
        callback: HttpCallback<*>
    ) {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return
        }
        val request = Request.Builder().apply {
            url(createUrl(finalUrl, params))
            addHeader(this, headers)
            get()
        }.build()
        okHttpClient.newCall(request).enqueue(callback)
    }

    /**
     * post
     */
    fun postJson(
        url: String,
        params: HashMap<String, String>? = null,
        headers: HashMap<String, String>? = null
    ): String? {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return null
        }
        val request = Request.Builder().apply {
            url(finalUrl)
            addHeader(this, headers)
            post(JsonUtil.gson.toJson(params).toRequestBody(JSON_TYPE))
        }.build()
        return okHttpClient.newCall(request).execute().body?.string()
    }

    /**
     * post
     */
    fun postJson(
        url: String,
        params: HashMap<String, String>? = null,
        callback: HttpCallback<*>
    ) {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return
        }
        val request = Request.Builder().apply {
            url(finalUrl)
            post(JsonUtil.gson.toJson(params).toRequestBody(JSON_TYPE))
        }.build()
        okHttpClient.newCall(request).enqueue(callback)
    }

    /**
     * post
     */
    fun postJson(
        url: String,
        params: HashMap<String, String>? = null,
        headers: HashMap<String, String>? = null,
        callback: HttpCallback<*>
    ) {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return
        }
        val request = Request.Builder().apply {
            url(finalUrl)
            addHeader(this, headers)
            post(JsonUtil.gson.toJson(params).toRequestBody(JSON_TYPE))
        }.build()
        okHttpClient.newCall(request).enqueue(callback)
    }

    /**
     * postFile
     */
    fun postFile(
        url: String,
        file: File,
        callback: HttpCallback<*>
    ) {
        postFile(url, file, null, "file", callback)
    }

    /**
     * postFile
     */
    fun postFile(
        url: String,
        file: File,
        urlParams: HashMap<String, String>? = null,
        fileName: String = "file",
        callback: HttpCallback<*>
    ) {
        val finalUrl = getUrl(url)
        if (!checkUrl(finalUrl)) {
            return
        }
        val body = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(fileName, file.name, file.asRequestBody(FILE_TYPE))
            .build()
        val request = Request.Builder().apply {
            url(createUrl(finalUrl, urlParams))
            post(body)
        }.build()
        okHttpClient.newCall(request).enqueue(callback)
    }

    /**
     * 拼接新的url
     */
    private fun createUrl(url: String, params: HashMap<String, String>?): String {
        return params?.let {
            val sb = StringBuffer().append("?")
            for ((k, v) in it) {
                sb.append("$k=$v&")
            }
            url + sb.toString().substring(0, sb.toString().length - 1)
        } ?: url
    }

    private fun getUrl(url: String): String {
        if (!LibHttpConfig.BASE_URL.isNullOrEmpty() &&
            !url.startsWith("http")
        ) {
            return "${LibHttpConfig.BASE_URL}${url}"
        }
        return url
    }

    private fun checkUrl(url: String): Boolean {
        if (!url.startsWith("http")) {
            return false
        }
        return true
    }

    /**
     * 添加请求头
     */
    private fun addHeader(builder: Request.Builder, headers: HashMap<String, String>?) {
        headers?.let {
            for ((key, value) in it) {
                builder.addHeader(key, value)
            }
        }
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