package com.zzming.core.utils

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.LocaleList
import android.os.Process
import com.zzming.core.base.LocalContextWrapper
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.service.RestartAppService
import java.util.*
import kotlin.system.exitProcess


/**
 * @author ZhongWei
 * @time 2020/8/5 16:10
 * @description
 **/
object APPUtils {

    /**
     * 切换语言
     */
    fun attachBaseContext(newBase: Context?): Context? {
        CorePreferencesUtils.init(newBase)
        var tempContext = newBase
        tempContext?.apply {
            CorePreferencesUtils.getLocale(newBase)?.let {
                if (needUpdateLocale(this, it)) {
                    tempContext = LocalContextWrapper.wrap(this, it)
                }
            }
        }
        return tempContext
    }

    /**
     * 切换语言
     */
    fun changLanguage(context: Context, locale: Locale) {
        if (!needUpdateLocale(context, locale)) {
            return
        }
        val res: Resources = context.resources
        val configuration: Configuration = res.configuration
        when {
            BuildUtils.isAtLeast24Api() -> {
                configuration.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                context.createConfigurationContext(configuration)
            }
            BuildUtils.isAtLeast17Api() -> {
                configuration.setLocale(locale)
                context.createConfigurationContext(configuration)
            }
            else -> {
                configuration.locale = locale
                res.updateConfiguration(configuration, res.displayMetrics)
            }
        }
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

    /**
     * 重启APP
     */
    fun restartApp(context: Context) {
        ActivityCollector.INSTANCE.finishAll()
        val service = Intent(context, RestartAppService::class.java)
        service.action = "android.intent.action.RESPOND_VIA_MESSAGE"
        service.putExtra(RestartAppService.APP_PACKAGE_NAME, context.packageName)
        context.startService(service)
//        Process.killProcess(Process.myPid())
    }

    /**
     * 杀死当前进程
     */
    private fun killAppProcess(context: Context) {
        context.getSystemService(Context.ACTIVITY_SERVICE).apply {
            this as ActivityManager
            runningAppProcesses.forEach {
                if (it.pid != Process.myPid()) {
                    Process.killProcess(it.pid)
                }
            }
        }
        Process.killProcess(Process.myPid())
        exitProcess(0)
    }

}