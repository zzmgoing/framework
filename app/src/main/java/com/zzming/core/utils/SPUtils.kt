package com.zzming.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.zzming.core.LibCore
import com.zzming.core.extension.putSharedString
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/8/5 15:20
 * @description
 **/
object SPUtils {

    private const val ZZM_CORE_LIBRARY_SP = "zzm_core_library_sp"

    private const val LOCALE_KEY = "locale_key"

    private var localConfig: SharedPreferences? = null

    /**
     * 初始化localConfig
     */
    fun init(context: Context) {
        if (localConfig == null) {
            localConfig = getPreferences(ZZM_CORE_LIBRARY_SP, context = context)
        }
    }

    /**
     * saveLocale
     */
    fun saveLocale(locale: Locale): Boolean {
        if (locale == getLocale()) {
            return false
        }
        val json = GsonUtil.gson.toJson(locale)
        localConfig?.putSharedString(LOCALE_KEY, json)
        return true
    }

    /**
     * getLocale
     */
    fun getLocale(): Locale? {
        val json= localConfig?.getString(LOCALE_KEY, null)
        return GsonUtil.json2Bean(json, Locale::class.java)
    }

    /**
     * 获取SharedPreferences
     */
    fun getPreferences(
        name: String,
        mode: Int = Context.MODE_PRIVATE,
        context: Context? = LibCore.context
    ): SharedPreferences? {
        return context?.getSharedPreferences(name, mode)
    }

}