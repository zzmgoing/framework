package com.zzming.core.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import java.util.*


/**
 * @author ZhongWei
 * @time 2020/8/5 16:10
 * @description
 **/
object APPUtils {

    /**
     * 切换语言
     * attachBaseContext
     */
    fun attachBaseContext(newBase: Context?): Context? {
        var tempContext = newBase
        tempContext?.apply {
            SPUtils.getLocale(this)?.let {
                tempContext = changLanguage(this, it)
            }
        }
        return tempContext
    }

    /**
     * 切换语言
     */
    fun changLanguage(context: Context, locale: Locale): Context {
        var tempContext = context
        if (!needUpdateLocale(context, locale)) {
            return tempContext
        }
        val res: Resources = context.resources
        val configuration: Configuration = res.configuration
        when {
            BuildUtils.isAtLeast24Api() -> {
                val defaultList = LocaleList.getDefault()
                val arrayOfLocale = arrayOfNulls<Locale>(defaultList.size() + 1)
                arrayOfLocale[0] = locale
                for (i in 0 until defaultList.size()) {
                    arrayOfLocale[i + 1] = locale
                }
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                tempContext = context.createConfigurationContext(configuration)
            }
            BuildUtils.isAtLeast17Api() -> {
                configuration.setLocale(locale)
                tempContext = context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.locale = locale
                res.updateConfiguration(configuration, res.displayMetrics)
            }
        }
        SPUtils.saveLocale(tempContext, locale)
        return tempContext
    }

    /**
     * 是否需要切换Locale
     */
    private fun needUpdateLocale(context: Context, newUserLocale: Locale?): Boolean {
        return newUserLocale != null && getCurrentLocale(context) != newUserLocale
    }

    /**
     * 获取当前Locale
     */
    private fun getCurrentLocale(context: Context): Locale {
        return if (BuildUtils.isAtLeast24Api()) {
            context.resources.configuration.locales.get(0)
        } else {
            context.resources.configuration.locale
        }
    }

}