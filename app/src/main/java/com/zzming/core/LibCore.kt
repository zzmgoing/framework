package com.zzming.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.zzming.core.collector.ActivityCollector
import okhttp3.OkHttpClient
import java.lang.ref.WeakReference

/**
 * @author ZhongZiMing
 * @time 2020/6/8 16:25
 * @description 初始化入口
 **/
class LibCore private constructor() {

    companion object {

        /**
         * LibCore
         */
        val instance: LibCore by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LibCore() }

        /**
         * 全局上下文
         */
        lateinit var context: Application

        /**
         * 主线程Handler
         */
        lateinit var handler: Handler

        /**
         * BASE_URL
         */
        var BASE_URL: String? = null

        /**
         * OkHttpClient
         */
        var httpClient: OkHttpClient? = null

        /**
         * Header
         */
        var httpHeaders: MutableMap<String, String>? = null

    }

    /**
     * 初始化
     */
    fun init(application: Application): LibCore {
        context = application
        handler = Handler(Looper.getMainLooper())
        AutoSizeInitializer.init(context)
        registerActivityLifeCycle()
        return this
    }

    /**
     * 设置BASE_URL
     */
    fun initBaseUrl(baseUrl: String?): LibCore {
        BASE_URL = baseUrl
        return this
    }

    /**
     * 设置 OkHttpClient
     */
    fun initHttpClient(client: OkHttpClient?): LibCore {
        httpClient = client
        return this
    }

    /**
     * 设置网络请求头
     */
    fun initHttpHeader(headers: MutableMap<String, String>?): LibCore {
        httpHeaders = headers
        return this
    }

    /**
     * 注册Activity生命周期回调
     */
    private fun registerActivityLifeCycle(){
        context.registerActivityLifecycleCallbacks(object: Application.ActivityLifecycleCallbacks{

            override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
            }

            override fun onActivityStopped(p0: Activity) {
            }

            override fun onActivityCreated(p0: Activity, p1: Bundle?) {
                ActivityCollector.INSTANCE.addActivity(p0)
                val contentView = p0.window.decorView.findViewById<View>(android.R.id.content)
                val binding = DataBindingUtil.findBinding<ViewDataBinding>(contentView)
//                binding?.
            }

            override fun onActivityStarted(p0: Activity) {
            }

            override fun onActivityResumed(p0: Activity) {
                ActivityCollector.INSTANCE.setCurrentActivity(p0)
            }

            override fun onActivityPaused(p0: Activity) {
            }

            override fun onActivityDestroyed(p0: Activity) {
                ActivityCollector.INSTANCE.removeActivity(p0)
            }
        })
    }
}