package com.zzming.core.net

/**
 * @author MackZhong
 * @time 2022/8/4
 * @description
 **/
data class LoadResult<out T>(val status: LoadStatus, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): LoadResult<T> {
            return LoadResult(LoadStatus.SUCCESS, data, null)
        }

        fun <T> error(msg: String?, data: T? = null): LoadResult<T> {
            return LoadResult(LoadStatus.FAIL, data, msg)
        }

        fun <T> loading(): LoadResult<T> {
            return LoadResult(LoadStatus.LOADING, null, null)
        }
    }

    val isSuccess: Boolean get() = status == LoadStatus.SUCCESS

    val isFail: Boolean get() = status == LoadStatus.FAIL

    inline fun onFailure(action: (message: String?) -> Unit): LoadResult<T> {
        if (isFail) action(message)
        return this
    }

    inline fun onSuccess(action: (value: T?) -> Unit): LoadResult<T> {
        if (isSuccess) action(data)
        return this
    }
}