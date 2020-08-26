package com.zzming.example

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.zzming.core.utils.APPUtils
import com.zzming.core.utils.SPUtils

/**
 * @author ZhongZiMing
 * @time 2020/8/3
 * @description
 **/
class ExampleApplication : Application() {

    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
//        SPUtils.getLocale(context)?.apply {
//            APPUtils.changLanguage(context,this)
//        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(APPUtils.attachBaseContext(base?:context))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        SPUtils.getLocale(context)?.apply {
//            APPUtils.changLanguage(context,this)
//        }
    }

}