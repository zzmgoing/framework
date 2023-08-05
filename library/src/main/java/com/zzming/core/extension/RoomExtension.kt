package com.zzming.core.extension

import com.zzming.core.room.RoomAgent
import com.zzming.core.utils.JsonUtil

fun saveJson(key: String, value: Any) {
    val json = if (value is String) value else JsonUtil.gson.toJson(value)
    RoomAgent.configDao.insertOrUpdate(key, json)
}

fun getJson(key: String): String? {
    return RoomAgent.configDao.findByKey(key)?.value
}

fun <T> getJsonBean(key: String, clazz: Class<T>): T? {
    val json = getJson(key)
    if (json.isNullOrEmpty()) {
        return null
    }
    return JsonUtil.gson.fromJson(json, clazz)
}