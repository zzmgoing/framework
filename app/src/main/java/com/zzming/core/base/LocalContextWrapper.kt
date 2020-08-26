package com.zzming.core.base

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import com.zzming.core.utils.BuildUtils
import java.util.*


/**
 * @author ZhongWei
 * @time 2020/8/5 14:35
 * @description https://stackoverflow.com/questions/39705739/android-n-change-language-programmatically/40849142#40849142
 **/
class LocalContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {
        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var tempContext = context
            val res: Resources = tempContext.resources
            val configuration: Configuration = res.configuration
            when {
                BuildUtils.isAtLeast24Api() -> {
                    configuration.setLocale(newLocale)
                    val localeList = LocaleList(newLocale)
                    LocaleList.setDefault(localeList)
                    configuration.setLocales(localeList)
                    tempContext = tempContext.createConfigurationContext(configuration)
                }
                BuildUtils.isAtLeast17Api() -> {
                    configuration.setLocale(newLocale)
                    tempContext = tempContext.createConfigurationContext(configuration)
                }
                else -> {
                    configuration.locale = newLocale
                    res.updateConfiguration(configuration, res.displayMetrics)
                }
            }
            return LocalContextWrapper(tempContext)
        }
    }

}