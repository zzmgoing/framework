package com.zzming.core.extension

import android.content.Context
import android.content.SharedPreferences
import com.zzming.core.utils.SPUtils

/**
 * 获取默认的SharedPreferences
 */
fun Context.preferences(name: String = SPUtils.DEFAULT_SP_NAME): SharedPreferences {
    return SPUtils.getPreferences(name, this)
}

/**
 * putSharedString
 **/
fun SharedPreferences.putSharedString(key: String, value: String) {
    edit()?.putString(key, value)?.apply()
}

/**
 * putSharedBoolean
 **/
fun SharedPreferences.putSharedBoolean(key: String, value: Boolean) {
    edit()?.putBoolean(key, value)?.apply()
}

/**
 * putSharedInt
 **/
fun SharedPreferences.putSharedInt(key: String, value: Int) {
    edit()?.putInt(key, value)?.apply()
}

/**
 * putSharedLong
 **/
fun SharedPreferences.putSharedLong(key: String, value: Long) {
    edit()?.putLong(key, value)?.apply()
}