package com.zzming.core.utils

import com.google.gson.Gson
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

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

    /**
     * json2Bean
     */
    fun <T> json2Bean(json: String?, typeOfT: Type): T? {
        if (json.isNullOrEmpty() || json == "null") {
            return null
        }
        return gson.fromJson(json, typeOfT)
    }

    /**
     * 获取泛型的类型
     */
    fun getClassType(clazz: Class<Any>, index: Int): Class<Any> {
        val genType = clazz.genericSuperclass as? ParameterizedType ?: return Any::class.java
        val params = genType.actualTypeArguments
        if (index >= params.size || index < 0) {
            return Any::class.java
        }
        return if (params[index] !is Class<*>) {
            Any::class.java
        } else params[index] as Class<Any>
    }

}