package com.zzming.core.extension

import com.zzming.core.room.RoomAgent

fun Any.setConfig(key: String, value: String) {
    RoomAgent.configDao.insertOrUpdate(key, value)
}

fun Any.getStringConfig(key: String, default: String = ""): String {
    return RoomAgent.configDao.findByKey(key)?.value ?: default
}

fun Any.getBooleanConfig(key: String, default: Boolean = false): Boolean {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toBoolean()
}

fun Any.getIntConfig(key: String, default: Int = -1): Int {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toInt()
}

fun Any.getLongConfig(key: String, default: Long = -1): Long {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toLong()
}