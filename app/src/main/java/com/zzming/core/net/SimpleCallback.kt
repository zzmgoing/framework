package com.zzming.core.net

import com.zzming.core.common.UrlConstant
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author ZhongWei
 * @time 2020/8/10 17:50
 * @description
 **/
class SimpleCallback<T> : Callback<T> {

    var tag: UrlConstant? = null

    override fun onResponse(call: Call<T>, response: Response<T>) {
        tag = call.request().tag(UrlConstant::class.java)
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        tag = call.request().tag(UrlConstant::class.java)
    }

}