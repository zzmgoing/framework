package com.zzming.core.utils

import android.content.Context
import android.content.SharedPreferences
import com.zzming.core.LibCore
import java.util.*

/**
 * @author ZhongWei
 * @time 2020/8/5 15:20
 * @description
 **/
object CorePreferencesUtils {

    private const val ZZM_CORE_LIBRARY_SP = "zzm_core_library_sp"

    private const val LOCALE_KEY = "locale_key"

    private var configPreferences: SharedPreferences? = null

    /**
     * 初始化
     */
    fun init(context: Context?): CorePreferencesUtils {
        if (configPreferences == null) {
            configPreferences = context?.getSharedPreferences(ZZM_CORE_LIBRARY_SP, Context.MODE_PRIVATE)
        }
        return this
    }

    /**
     * saveLocale
     */
    fun saveLocale(locale: Locale): Boolean {
        if(locale == getLocale()){
            return false
        }
        val json = GsonUtil.gson.toJson(locale)
        configPreferences?.edit()?.putString(LOCALE_KEY, json)?.apply()
        return true
    }

    /**
     * getLocale
     */
    fun getLocale(context: Context? = LibCore.context): Locale? {
        init(context)
        val json = configPreferences?.getString(LOCALE_KEY, null)
        return GsonUtil.json2Bean(json, Locale::class.java)
    }

}