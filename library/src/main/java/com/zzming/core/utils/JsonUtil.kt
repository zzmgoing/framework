package com.zzming.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.ParameterizedType

/**
 * @author ZhongZiMing
 * @time 2020/8/5 14:17
 * @description
 **/
object JsonUtil {

    val gson = Gson()

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

    fun <T> fromJsonToList(json: String): ArrayList<T> {
        val listType = object : TypeToken<ArrayList<T?>?>() {}.type
        return gson.fromJson(json, listType)
    }

}