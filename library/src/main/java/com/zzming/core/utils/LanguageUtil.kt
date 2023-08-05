package com.zzming.core.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import com.zzming.core.extension.getJsonBean
import com.zzming.core.extension.saveJson
import java.util.*


/**
 * @author ZhongZiMing
 * @time 2020/8/5 16:10
 * @description
 **/
object LanguageUtil {

    private const val LOCALE_KEY = "locale_key"

    /**
     * 切换语言
     * attachBaseContext
     */
    fun attachBaseContext(context: Context): Context {
        val locale = getJsonBean(LOCALE_KEY, Locale::class.java) ?: return context
        val tempContext: Context
        val res: Resources = context.resources
        val configuration: Configuration = res.configuration
        when {
            BuildUtils.isAtLeast24Api() -> {
                configuration.setLocale(locale)
                tempContext = context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.locale = locale
                res.updateConfiguration(configuration, res.displayMetrics)
                tempContext = context
            }
        }
        return tempContext
    }

    /**
     * 切换语言
     */
    fun changLanguage(context: Context, locale: Locale? = null) {
        var newLocale: Locale? = locale
        if (locale == null) {
            newLocale = getJsonBean(LOCALE_KEY, Locale::class.java)
        }
        if (newLocale == null) {
            return
        }
        val res: Resources = context.resources
        val configuration: Configuration = res.configuration
        Locale.setDefault(newLocale)
        configuration.setLocale(newLocale)
        res.updateConfiguration(configuration, res.displayMetrics)
        saveJson(LOCALE_KEY, newLocale)
    }

    /**
     * 获取系统语言
     */
    private fun getSystemLocal(): Locale {
        return if (BuildUtils.isAtLeast24Api()) {
            LocaleList.getDefault()[0]
        } else {
            Locale.getDefault()
        }
    }

}