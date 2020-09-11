package com.zzming.core.utils

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import java.util.*


/**
 * @author ZhongZiMing
 * @time 2020/8/5 16:10
 * @description
 **/
object LanguageUtil {

    lateinit var context: Application

    /**
     * 切换语言
     * attachBaseContext
     */
    fun attachBaseContext(context: Context): Context {
        val locale = SPUtils.getLocale(context) ?: return context
        val tempContext: Context
        val res: Resources = context.resources
        val configuration: Configuration = res.configuration
        when {
            BuildUtils.isAtLeast24Api() -> {
                configuration.setLocale(locale)
                tempContext = context.createConfigurationContext(configuration)
            }
            BuildUtils.isAtLeast17Api() -> {
                configuration.setLocale(locale)
                res.updateConfiguration(configuration, res.displayMetrics)
                tempContext = context
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
    fun changLanguage(locale: Locale? = null, context: Context = this.context) {
        locale?.apply {
            val res: Resources = context.resources
            val configuration: Configuration = res.configuration
            if (BuildUtils.isAtLeast17Api()) {
                configuration.setLocale(this)
            } else {
                configuration.locale = this
            }
            res.updateConfiguration(configuration, res.displayMetrics)
            SPUtils.saveLocale(this, context)
        }
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