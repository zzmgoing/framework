package com.zzming.core.net

import com.zzming.core.extension.logDebug
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException


/**
 * @author ZhongWei
 * @time 2020/7/15 15:10
 * @description 日志
 **/
class LogInterceptor : Interceptor {

    companion object {
        const val TAG = "LIB-CORE-OK-HTTP"
        private val UTF8 = Charset.forName("UTF-8")
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(chain.request())
        logDebug(TAG, request.url().toString())
        val headers = request.headers()
        for (i in 0 until headers.size()) {
            logDebug(TAG, headers.name(i).toString() + ": " + headers.value(i))
        }
        logDebug(TAG, requestBody2String(request.body()))
        logDebug(TAG, responseBody2String(response.body()))
        return response
    }

    private fun requestBody2String(requestBody: RequestBody?): String {
        var body = ""
        requestBody?.let {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = UTF8
            val contentType = requestBody.contentType()
            contentType?.let {
                charset = contentType.charset(UTF8)
            }
            body = buffer.readString(charset!!)
        }
        return body
    }

    private fun responseBody2String(responseBody: ResponseBody?): String {
        var body = ""
        responseBody?.let {
            val source = it.source()
            source.request(Long.MAX_VALUE)
            var charset: Charset? = UTF8
            it.contentType()?.let { m ->
                try {
                    charset = m.charset(UTF8)
                } catch (e: UnsupportedCharsetException) {
                    e.printStackTrace()
                }
            }
            body = source.buffer.clone().readString(charset!!)
        }
        return body
    }

}