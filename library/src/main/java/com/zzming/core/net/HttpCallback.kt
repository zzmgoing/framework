package com.zzming.core.net

import com.jeremyliao.liveeventbus.LiveEventBus
import com.zzming.core.base.AnyEvent
import com.zzming.core.common.Constant
import com.zzming.core.extension.runOnMainThread
import com.zzming.core.utils.JsonUtil
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * @author ZhongZiMing
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

        /**
         * 需要登录
         */
        const val FAIL_STATUS_NEED_LOGIN = 4

        /**
         * 服务端数据异常
         */
        const val FAIL_STATUS_SERVICE_ERROR = 5

    }

    override fun onFailure(call: Call, e: IOException) {
        runOnMainThread {
            onFail(FAIL_STATUS_NET, e)
            onFinish()
        }
    }

    @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun onResponse(call: Call, response: Response) {
        if (response.isSuccessful) {
            val body = response.body
            val json = body?.string()
            if (body != null && !json.isNullOrEmpty()) {
                if (JsonUtil.getClassType(javaClass, 0) == String::class.java) {
                    runOnMainThread {
                        onSuccess(json as T)
                    }
                } else {
                    try {
                        val type: Type = javaClass.genericSuperclass
                        val types: Array<Type> = (type as ParameterizedType).actualTypeArguments
                        val bean = JsonUtil.gson.fromJson<T>(json, types[0])
                        if (bean != null) {
                            runOnMainThread {
                                onSuccess(bean)
                            }
                        } else {
                            runOnMainThread {
                                onFail(FAIL_STATUS_PARSE_BEAN, Exception("JSON解析异常：$json"))
                            }
                        }
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                        runOnMainThread {
                            onFail(FAIL_STATUS_PARSE_BEAN, ex)
                        }
                    }
                }
            } else {
                runOnMainThread {
                    onFail(FAIL_STATUS_NO_BODY, Exception("服务器无返回数据"))
                }
            }
        } else {
            runOnMainThread {
                onFail(FAIL_STATUS_RESPONSE, Exception("服务器请求错误，错误码：${response.code}"))
            }
        }
        runOnMainThread {
            onFinish()
        }
    }

    /**
     * 成功
     */
    abstract fun onSuccess(t: T)

    /**
     * 失败
     */
    open fun onFail(status: Int, ex: Exception) {
        ex.printStackTrace()
    }

    /**
     * 结束
     */
    open fun onFinish() {
        LiveEventBus.get(AnyEvent::class.java).post(AnyEvent(Constant.HIDE_LOADING))
    }

}