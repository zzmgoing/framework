package com.zzming.core.net

import androidx.annotation.MainThread
import com.zzming.core.utils.GsonUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * @author ZhongWei
 * @time 2020/8/10 17:50
 * @description 网络请求回调
 **/
abstract class HttpCallback<T> : Callback {

    companion object {

        /**
         * 网路错误
         */
        const val FAIL_STATUS_NET = 0

        /**
         * 服务器响应错误
         */
        const val FAIL_STATUS_RESPONSE = 1

        /**
         * 服务器无返回结果
         */
        const val FAIL_STATUS_NO_BODY = 2

        /**
         * 结果解析失败
         */
        const val FAIL_STATUS_PARSE_BEAN = 3

    }

    override fun onFailure(call: Call, e: IOException) {
        onFail(FAIL_STATUS_NET)
        onFinish()
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {
            val body = response.body()
            val json = body?.string()
            if (body != null && !json.isNullOrEmpty()) {
                if (GsonUtil.getClassType(javaClass, 0) == String::class.java) {
                    onSuccess(json as T)
                } else {
                    try {
                        val type: Type = javaClass.genericSuperclass
                        val types: Array<Type> = (type as ParameterizedType).actualTypeArguments
                        val bean = GsonUtil.json2Bean<T>(json, types[0])
                        if (bean != null) {
                            onSuccess(bean)
                        } else {
                            onFail(FAIL_STATUS_PARSE_BEAN)
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        onFail(FAIL_STATUS_PARSE_BEAN)
                    }
                }
            } else {
                onFail(FAIL_STATUS_NO_BODY)
            }
        } else {
            onFail(FAIL_STATUS_RESPONSE)
        }
        onFinish()
    }

    /**
     * 成功
     */
    @MainThread
    abstract fun onSuccess(t: T)

    /**
     * 失败
     */
    @MainThread
    open fun onFail(status: Int) {

    }

    /**
     * 结束
     */
    @MainThread
    open fun onFinish() {

    }

}