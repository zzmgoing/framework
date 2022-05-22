package com.zzming.core.extension

import com.zzming.core.room.RoomAgent

fun setConfig(key: String, value: String) {
    RoomAgent.configDao.insertOrUpdate(key, value)
}

fun getStringConfig(key: String): String {
    return getStringConfig(key, "")
}

fun getStringConfig(key: String, default: String = ""): String {
    return RoomAgent.configDao.findByKey(key)?.value ?: default
}

fun getBooleanConfig(key: String): Boolean {
    return getBooleanConfig(key, false)
}

fun getBooleanConfig(key: String, default: Boolean = false): Boolean {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toBoolean()
}

fun getIntConfig(key: String): Int {
    return getIntConfig(key, -1)
}

fun getIntConfig(key: String, default: Int = -1): Int {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toInt()
}

fun getLongConfig(key: String): Long {
    return getLongConfig(key, -1)
}

fun getLongConfig(key: String, default: Long = -1): Long {
    val config = getStringConfig(key)
    if (config.isEmpty()) {
        return default
    }
    return config.toLong()
}