package com.zzming.core.net

import com.zzming.core.base.LoadStatus

/**
 * @author MackZhong
 * @time 2022/8/4
 * @description
 **/
data class Result<out T>(val status: LoadStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(LoadStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): Result<T> {
            return Result(LoadStatus.FAIL, data, msg)
        }

        fun <T> loading(data: T? = null): Result<T> {
            return Result(LoadStatus.LOADING, data, null)
        }
    }

    val isSuccess: Boolean get() = status == LoadStatus.SUCCESS

    val isFail: Boolean get() = status == LoadStatus.FAIL

    inline fun onFailure(action: (message: String?) -> Unit): Result<T> {
        if (isFail) action(message)
        return this
    }

    inline fun onSuccess(action: (value: T?) -> Unit): Result<T> {
        if (isSuccess) action(data)
        return this
    }
}