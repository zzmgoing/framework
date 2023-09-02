package com.zzming.core.extension

import com.zzming.core.room.RoomAgent
import com.zzming.core.room.table.Json
import com.zzming.core.utils.JsonUtil

fun saveJson(key: String, value: Any) {
    val json = if (value is String) value else JsonUtil.gson.toJson(value)
    RoomAgent.jsonDao.insertOrUpdate(key, json)
}

fun getJson(key: String): String? {
    return RoomAgent.jsonDao.findByKey(key)?.value
}

fun deleteJson(json: Json) {
    RoomAgent.jsonDao.delete(json)
}

fun deleteJsonByKey(key: String) {
    RoomAgent.jsonDao.deleteByKey(key)
}

fun <T> getJsonBean(key: String, clazz: Class<T>): T? {
    val json = getJson(key)
    if (json.isNullOrEmpty()) {
        return null
    }
    return JsonUtil.gson.fromJson(json, clazz)
}