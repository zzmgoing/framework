package com.zzming.core.net

import com.google.gson.JsonSyntaxException
import com.zzming.core.common.LibHttpConfig
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logError
import com.zzming.core.extension.runOnMainThread
import com.zzming.core.utils.JsonUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.IOException

suspend fun <T> OkHttpClient.get(
    url: String,
    clazz: Class<T>,
    method: (DslHttpBuilder<T>.() -> Unit)? = null
): LoadResult<T> {
    val httpRequestBuilderImpl = DslHttpRequestBuilderImpl(this)
    return httpRequestBuilderImpl.get(url, clazz, method)
}

suspend fun <T> OkHttpClient.post(
    url: String,
    clazz: Class<T>,
    method: (DslHttpBuilder<T>.() -> Unit)? = null
): LoadResult<T> {
    val httpRequestBuilderImpl = DslHttpRequestBuilderImpl(this)
    return httpRequestBuilderImpl.post(url, clazz, method)
}

suspend fun <T> OkHttpClient.postFile(
    url: String,
    clazz: Class<T>,
    file: File,
    fileName: String?,
    method: (DslHttpBuilder<T>.() -> Unit)? = null
): LoadResult<T> {
    val httpRequestBuilderImpl = DslHttpRequestBuilderImpl(this)
    return httpRequestBuilderImpl.postFile(url, clazz, file, fileName, method)
}

interface DslHttpRequestBuilder {
    suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): LoadResult<T>

    suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): LoadResult<T>

    suspend fun <T> postFile(
        url: String,
        clazz: Class<T>,
        file: File,
        fileName: String?,
        method: (DslHttpBuilder<T>.() -> Unit)? = null
    ): LoadResult<T>
}

interface DslHttpBuilder<T> {
    fun setHeaders(headers: HashMap<String, String>)
    fun setParams(params: HashMap<String, String>)
    fun setQueryParams(queryParams: HashMap<String, String>)
    fun setBody(body: RequestBody)
    fun setPostForm(isForm: Boolean)
    fun onSuccess(action: ((t: T) -> Unit))
    fun onFail(action: ((msg: String?) -> Unit))
    fun onFinish(action: ((result: LoadResult<T>) -> Unit))
}

class DslHttpRequestBuilderImpl(private val okHttpClient: OkHttpClient) : DslHttpRequestBuilder {

    private val requestBuilder = Request.Builder()

    companion object {
        private val JSON_TYPE = "application/json".toMediaTypeOrNull()

        private val FILE_TYPE = "image/*".toMediaTypeOrNull()
    }

    override suspend fun <T> get(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)?
    ): LoadResult<T> {
        val httpBuilderImpl = DslHttpBuilderImpl<T>()
        method?.let { httpBuilderImpl.it() }
        val finalUrl = getFinalUrl(url)
        if (!checkUrl(finalUrl)) {
            error(httpBuilderImpl, "The url is wrong")
            return LoadResult.error("The url is wrong")
        }
        requestBuilder.url(createUrl(finalUrl, httpBuilderImpl.queryParamMap))
            .addHeaders(httpBuilderImpl.headerMap)
        return request(clazz, httpBuilderImpl)
    }

    override suspend fun <T> post(
        url: String,
        clazz: Class<T>,
        method: (DslHttpBuilder<T>.() -> Unit)?
    ): LoadResult<T> {
        val httpBuilderImpl = DslHttpBuilderImpl<T>()
        method?.let { httpBuilderImpl.it() }
        val finalUrl = getFinalUrl(url)
        if (!checkUrl(finalUrl)) {
            error(httpBuilderImpl, "The url is wrong")
            return LoadResult.error("The url is wrong")
        }
        requestBuilder.url(createUrl(finalUrl, httpBuilderImpl.queryParamMap))
            .addHeaders(httpBuilderImpl.headerMap)
        if (httpBuilderImpl.isPostForm == true) {
            val builder = MultipartBody.Builder().setType(MultipartBody.FORM)
            httpBuilderImpl.paramsMap?.let { map ->
                for ((k,v) in map) {
                    builder.addFormDataPart(k,v)
                }
            }
            requestBuilder.post(builder.build())
        } else {
            requestBuilder.post(JsonUtil.toJson(httpBuilderImpl.paramsMap ?: "{}").toRequestBody(JSON_TYPE))
        }
        return request(clazz, httpBuilderImpl)
    }

    override suspend fun <T> postFile(
        url: String,
        clazz: Class<T>,
        file: File,
        fileName: String?,
        method: (DslHttpBuilder<T>.() -> Unit)?
    ): LoadResult<T> {
        val httpBuilderImpl = DslHttpBuilderImpl<T>()
        method?.let { httpBuilderImpl.it() }
        val finalUrl = getFinalUrl(url)
        if (!checkUrl(finalUrl)) {
            error(httpBuilderImpl, "The url is wrong")
            return LoadResult.error("The url is wrong")
        }
        val body = MultipartBody.Builder().apply {
            setType(MultipartBody.FORM)
            addFormDataPart(fileName ?: "file", file.name, file.asRequestBody(FILE_TYPE))
            httpBuilderImpl.paramsMap?.let { map ->
                for ((k,v) in map) {
                    addFormDataPart(k,v)
                }
            }
        }.build()
        requestBuilder.url(createUrl(finalUrl, httpBuilderImpl.queryParamMap))
            .addHeaders(httpBuilderImpl.headerMap)
            .post(body)
        return request(clazz, httpBuilderImpl)
    }

    private fun <T> request(clazz: Class<T>, httpBuilderImpl: DslHttpBuilderImpl<T>): LoadResult<T> {
        try {
            val response = okHttpClient.newCall(build()).execute()
            val bodyString = response.body?.string()
            return if (response.isSuccessful && !bodyString.isNullOrEmpty()) {
                try {
                    val t = JsonUtil.fromJson(bodyString, clazz)
                    runOnMainThread {
                        httpBuilderImpl.onSuccess?.invoke(t)
                        httpBuilderImpl.onFinish?.invoke(LoadResult.success(t))
                    }
                    LoadResult.success(t)
                } catch (ex: JsonSyntaxException) {
                    ex.printStackTrace()
                    error(httpBuilderImpl, "JSON parsing exception")
                }
            } else {
                error(httpBuilderImpl, "The response is not successful")
            }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return error(httpBuilderImpl, "The network is wrong")
        }
    }

    private fun <T> error(httpBuilderImpl: DslHttpBuilderImpl<T>, msg: String?): LoadResult<T> {
        logError(okHttpClient.SIMPLE_NAME_TAG, msg ?: "Error")
        runOnMainThread {
            httpBuilderImpl.onFail?.invoke(msg)
            httpBuilderImpl.onFinish?.invoke(LoadResult.error(msg))
        }
        return LoadResult.error(msg ?: "Error")
    }

    private fun getFinalUrl(url: String): String {
        if (!LibHttpConfig.BASE_URL.isNullOrEmpty() && !checkUrl(url)) {
            return "${LibHttpConfig.BASE_URL}${url}"
        }
        return url
    }

    private fun checkUrl(url: String): Boolean {
        return url.startsWith("http")
    }

    private fun createUrl(url: String, params: HashMap<String, String>?): String {
        return params?.let {
            val sb = StringBuffer().append("?")
            for ((k, v) in it) {
                sb.append("$k=$v&")
            }
            url + sb.toString().substring(0, sb.toString().length - 1)
        } ?: url
    }

    fun build(): Request {
        return requestBuilder.build()
    }
}

class DslHttpBuilderImpl<T> : DslHttpBuilder<T> {

    var headerMap: HashMap<String, String>? = null
    var queryParamMap: HashMap<String, String>? = null
    var paramsMap: HashMap<String, String>? = null
    var isPostForm: Boolean? = null
    var requestBody: RequestBody? = null
    var onSuccess: ((t: T) -> Unit)? = null
    var onFail: ((msg: String?) -> Unit)? = null
    var onFinish: ((result: LoadResult<T>) -> Unit)? = null


    override fun setHeaders(headers: HashMap<String, String>) {
        this.headerMap = headers
    }

    override fun setQueryParams(queryParams: HashMap<String, String>) {
        this.queryParamMap = queryParams
    }

    override fun setParams(params: HashMap<String, String>) {
        this.paramsMap = params
    }

    override fun setBody(body: RequestBody) {
        this.requestBody = body
    }

    override fun setPostForm(isForm: Boolean) {
        this.isPostForm = isForm
    }

    override fun onSuccess(action: ((t: T) -> Unit)) {
        this.onSuccess = action
    }

    override fun onFail(action: ((msg: String?) -> Unit)) {
        this.onFail = action
    }

    override fun onFinish(action: (result: LoadResult<T>) -> Unit) {
        this.onFinish = action
    }

}

fun Request.Builder.addHeaders(headers: HashMap<String, String>?): Request.Builder {
    headers?.forEach {
        addHeader(it.key, it.value)
    }
    return this
}