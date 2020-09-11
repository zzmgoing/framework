package com.zzming.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.common.LibConfig
import com.zzming.core.common.LibViewConfig
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logDebug
import com.zzming.core.utils.LanguageUtil
import com.zzming.core.utils.SPUtils

/**
 * @author ZhongZiMing
 * @time 2020/6/8 16:25
 * @description 初始化入口
 **/
object LibCore : Application.ActivityLifecycleCallbacks {

    /**
     * 是否已经初始化
     */
    private var isInit = false

    /**
     * 全局上下文
     */
    lateinit var context: Application

    /**
     * 主线程Handler
     */
    lateinit var handler: Handler

    /**
     * 初始化
     */
    fun init(application: Application): LibCore {
        if (!isInit) {
            isInit = true
            context = application
            handler = Handler(Looper.getMainLooper())
            LibConfig.init()
            registerActivityLifecycleCallbacks()
            LanguageUtil.context = context
            LanguageUtil.changLanguage(SPUtils.getLocale(), context)
            logDebug(SIMPLE_NAME_TAG, "LibCore初始化,进程ID:${Process.myPid()}")
        }
        return this
    }

    /**
     * 注册Activity生命周期回调
     */
    private fun registerActivityLifecycleCallbacks() {
        context.registerActivityLifecycleCallbacks(this)
    }

    fun unregisterActivityLifecycleCallbacks() {
        context.unregisterActivityLifecycleCallbacks(this)
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        ActivityCollector.addActivity(p0)
        LanguageUtil.changLanguage(SPUtils.getLocale(), p0)
        LibViewConfig.loadLoadingDialog.invoke(p0)
    }

    override fun onActivityStarted(p0: Activity) {
    }

    override fun onActivityResumed(p0: Activity) {
        ActivityCollector.setCurrentActivity(p0)
    }

    override fun onActivityPaused(p0: Activity) {
    }

    override fun onActivityDestroyed(p0: Activity) {
        ActivityCollector.removeActivity(p0)
    }
}