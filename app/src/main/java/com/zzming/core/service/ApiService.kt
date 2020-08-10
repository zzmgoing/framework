package com.zzming.core.service

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author ZhongWei
 * @time 2020/8/10 17:40
 * @description
 **/
interface ApiService {

    @GET("user")
    fun getUser(@Query("id") id: String): Call<String>

}