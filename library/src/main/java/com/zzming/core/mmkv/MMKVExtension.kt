package com.zzming.core.mmkv

import com.tencent.mmkv.MMKV

val kv: MMKV
    get() = MMKV.mmkvWithID("lib_core")

fun setConfig(key: String, value: String? = "") {
    kv.encode(key, value ?: "")
}

fun setConfig(key: String, value: Boolean) {
    kv.encode(key, value)
}

fun setConfig(key: String, value: Int) {
    kv.encode(key, value)
}

fun setConfig(key: String, value: Long) {
    kv.encode(key, value)
}

fun setConfig(key: String, value: Double) {
    kv.encode(key, value)
}

fun getStringConfig(key: String, default: String? = ""): String? {
    return kv.decodeString(key, default)
}

fun getBooleanConfig(key: String, default: Boolean? = false): Boolean {
    return kv.decodeBool(key, default ?: false)
}

fun getIntConfig(key: String, default: Int? = 0): Int {
    return kv.decodeInt(key, default ?: 0)
}

fun getLongConfig(key: String, default: Long? = 0): Long {
    return kv.decodeLong(key, default ?: 0)
}

fun getDoubleConfig(key: String, default: Double? = 0.0): Double {
    return kv.decodeDouble(key, default ?: 0.0)
}

fun String.stringConfigValue(default: String? = ""): String? {
    return getStringConfig(this, default)
}

fun String.booleanConfigValue(default: Boolean? = false): Boolean {
    return getBooleanConfig(this, default)
}

fun String.intConfigValue(default: Int? = 0): Int {
    return getIntConfig(this, default)
}

fun String.longConfigValue(default: Long? = 0): Long {
    return getLongConfig(this, default)
}

fun String.doubleConfigValue(default: Double? = 0.0): Double {
    return getDoubleConfig(this, default)
}

fun removeConfig(key: String) {
    kv.removeValueForKey(key)
}

fun containsConfig(key: String): Boolean {
    return kv.containsKey(key)
}