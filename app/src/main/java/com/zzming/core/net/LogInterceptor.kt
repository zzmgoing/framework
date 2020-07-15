package com.zzming.core.net

import com.zzming.core.extension.logError
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer


/**
 * @author ZhongWei
 * @time 2020/7/15 15:10
 * @description 日志
 **/
class LogInterceptor : Interceptor {

    companion object {
        const val TAG = "OK-HTTP-LOG"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())
        val url: HttpUrl = request.url()
        val scheme = url.scheme()
        val host = url.host()
        val path = url.encodedPath()
        val query = url.encodedQuery()
        val bodyString: String = requestBody2String(request.body())
        logError(TAG, scheme)
        logError(TAG, host)
        logError(TAG, path)
        logError(TAG, query)
        logError(TAG,bodyString)
        val responseBody = response.body()
        logError(TAG,responseBody.toString())
        val headers = response.headers()
        for(i in 0..headers.size()){
            logError(TAG,headers.name(i).toString() + ": " + headers.value(i))
        }
        return response
    }

    private fun requestBody2String(body: RequestBody?): String {
        val buffer = Buffer()
        body?.writeTo(buffer)
        return buffer.readUtf8()
    }

}