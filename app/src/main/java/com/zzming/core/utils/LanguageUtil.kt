package com.zzming.core.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import com.zzming.core.base.LocalContextWrapper
import java.util.*


/**
 * @author ZhongWei
 * @time 2020/8/5 16:10
 * @description
 **/
object LanguageUtil {

    /**
     * 切换语言
     * attachBaseContext
     */
    fun attachBaseContext(newBase: Context?): Context? {
        var tempContext = newBase
        tempContext?.apply {
            SPUtils.getLocale(newBase)?.let {
                tempContext = LocalContextWrapper.wrap(this, it)
            }
        }
        return tempContext
    }

    /**
     * 切换语言
     */
    fun changLanguage(context: Context) {
        SPUtils.getLocale(context)?.let {
            val res: Resources = context.resources
            val configuration: Configuration = res.configuration
            when {
                BuildUtils.isAtLeast24Api() -> {
                    configuration.setLocale(it)
                    val localeList = LocaleList(it)
                    LocaleList.setDefault(localeList)
                    configuration.setLocales(localeList)
                    context.createConfigurationContext(configuration)
                }
                BuildUtils.isAtLeast17Api() -> {
                    configuration.setLocale(it)
                    context.createConfigurationContext(configuration)
                }
                else -> {
                    configuration.locale = it
                    res.updateConfiguration(configuration, res.displayMetrics)
                }
            }
        }
    }

}