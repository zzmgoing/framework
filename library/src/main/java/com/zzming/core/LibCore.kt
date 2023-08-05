package com.zzming.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import com.zzming.core.collector.ActivityCollector
import com.zzming.core.collector.LoadingCollector
import com.zzming.core.common.LibViewConfig
import com.zzming.core.extension.SIMPLE_NAME_TAG
import com.zzming.core.extension.logDebug
import com.zzming.core.utils.LanguageUtil

/**
 * @author ZhongZiMing
 * @time 2020/6/8 16:25
 * @description 初始化入口
 **/
object LibCore : Application.ActivityLifecycleCallbacks {

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
    fun init(application: Application) {
        if (::context.isInitialized && context == application) {
            return
        }
        context = application
        handler = Handler(Looper.getMainLooper())
        LanguageUtil.changLanguage(context)
        context.registerActivityLifecycleCallbacks(this)
        logDebug(SIMPLE_NAME_TAG, "LibCore初始化,进程ID:${Process.myPid()}")
    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
    }

    override fun onActivityStopped(p0: Activity) {
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {
        ActivityCollector.addActivity(p0)
        LanguageUtil.changLanguage(p0)
        LibViewConfig.loadLoadingDialog?.invoke(p0)?.let {
            LoadingCollector.addLoading(it)
        }
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