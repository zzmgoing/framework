package com.zzming.core.extension

import android.content.SharedPreferences

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