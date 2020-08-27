package com.zzming.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.zzming.core.LibCore
import com.zzming.core.extension.preferences
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
    fun saveLocale(locale: Locale, context: Context? = LibCore.context) {
        val json = GsonUtil.gson.toJson(locale)
        context?.preferences()?.putSharedString(LOCALE_KEY, json)
    }

    /**
     * getLocale
     */
    fun getLocale(context: Context? = LibCore.context): Locale? {
        val json = context?.preferences()?.getString(LOCALE_KEY, null)
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