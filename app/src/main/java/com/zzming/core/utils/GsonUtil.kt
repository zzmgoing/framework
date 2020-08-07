package com.zzming.core.utils

import com.google.gson.Gson

/**
 * @author ZhongWei
 * @time 2020/8/5 14:17
 * @description
 **/
object GsonUtil {

    val gson = Gson()

    /**
     * json2Bean
     */
    fun <T> json2Bean(json: String?, clazz: Class<T>): T? {
        if (json.isNullOrEmpty() || json == "null") {
            return null
        }
        return gson.fromJson(json, clazz)
    }


}