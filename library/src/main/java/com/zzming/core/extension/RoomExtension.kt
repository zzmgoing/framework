package com.zzming.core.extension

import com.zzming.core.room.RoomAgent

fun Any.setConfig(key: String, value: String) {
    RoomAgent.configDao.insertOrUpdate(key, value)
}

fun Any.getStringConfig(key: String): String? {
    return RoomAgent.configDao.findByKey(key)?.value
}

fun Any.getBooleanConfig(key: String): Boolean? {
    return getStringConfig(key)?.toBoolean()
}

fun Any.getIntConfig(key: String): Int? {
    return getStringConfig(key)?.toInt()
}

fun Any.getLongConfig(key: String): Long? {
    return getStringConfig(key)?.toLong()
}