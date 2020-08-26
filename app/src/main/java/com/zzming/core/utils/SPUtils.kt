package com.zzming.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.zzming.core.LibCore
import com.zzming.core.extension.corePreferences
import com.zzming.core.extension.putSharedString
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/8/5 15:20
 * @description
 **/
object SPUtils {

    const val ZZM_CORE_LIBRARY_SP = "zzm_core_library_sp"

    private const val LOCALE_KEY = "locale_key"

    /**
     * saveLocale
     */
    fun saveLocale(context: Context, locale: Locale): Boolean {
        if (locale == getLocale(context)) {
            return false
        }
        val json = GsonUtil.gson.toJson(locale)
        context.corePreferences().putSharedString(LOCALE_KEY, json)
        return true
    }

    /**
     * getLocale
     */
    fun getLocale(context: Context): Locale? {
        val json = context.corePreferences().getString(LOCALE_KEY, null)
        return GsonUtil.json2Bean(json, Locale::class.java)
    }

    /**
     * 获取SharedPreferences
     */
    fun getPreferences(
        name: String,
        context: Context = LibCore.context,
        mode: Int = Context.MODE_PRIVATE
    ): SharedPreferences {
        return context.getSharedPreferences(name, mode)
    }

}